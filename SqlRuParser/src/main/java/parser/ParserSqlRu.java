package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.apache.log4j.*;
import java.io.IOException;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ParserSqlRu {

    private String START_PAGE_URL = "http://www.sql.ru/forum/job-offers";
    private static Logger LOG = LogManager.getLogger(ParserSqlRu.class);
    private static Connection connection;
    private Pattern needPattern;
    private Pattern excludePattern;

    public ParserSqlRu() throws SQLException {
        Config config = new Config();
        config.load("app.properties");
        String dbUrl = String.format(
                "%s%s",
                config.getValue("jdbc.driver"),
                config.getValue("jdbc.url")
        );
        connection = DriverManager.getConnection(
                dbUrl,
                config.getValue("jdbc.username"),
                config.getValue("jdbc.password")
        );
        List<String> needAllWords = new ArrayList<>(
                Arrays.asList("java", "java8", "JEE")
        );
        List<String> excludeWords = new ArrayList<>(
                Arrays.asList("java script", "javascript", "js")
        );
        String regexNeed = String.join("|", needAllWords.stream().map(word -> "\\b" + word + "\\b").collect(Collectors.toList()));
        needPattern = Pattern.compile(regexNeed, Pattern.CASE_INSENSITIVE);
        String regexExclude = String.join("|", excludeWords.stream().map(word -> "\\b" + word + "\\b").collect(Collectors.toList()));
        excludePattern = Pattern.compile(regexExclude, Pattern.CASE_INSENSITIVE);
        checkTablesAndCreateIfAbsent();
    }

    private void checkTablesAndCreateIfAbsent() throws SQLException {
        Statement st = connection.createStatement();
        st.execute("CREATE TABLE if NOT EXISTS vacancies\n" +
                "(\n" +
                "    href char(300) PRIMARY KEY NOT NULL,\n" +
                "    datetime TIMESTAMP,\n" +
                "    author varchar(100),\n" +
                "    title varchar(300),\n" +
                "    msg text\n" +
                ");");
    }

    public void execute() throws IOException, SQLException {
        ParserSqlRu parserSqlRu = new ParserSqlRu();
        List<Vacancy> vacancies = parserSqlRu.parse(getLastTimeStampFromTable(connection));
        parserSqlRu.insert2db(vacancies);
    }

    private void insert2db(List<Vacancy> vacancies) {
        String q = "INSERT INTO vacancies(href, datetime, author, title, msg) VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement st = connection.prepareStatement(q)) {
            connection.setAutoCommit(false);
            for (Vacancy v : vacancies) {
                st.setString(1, v.getUrl());
                st.setTimestamp(2,  Timestamp.valueOf(v.getDateTime()));
                st.setString(3, v.getAuthorName());
                st.setString(4, v.getTitle());
                st.setString(5, v.getMessage());
                st.addBatch();
            }
            st.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOG.warn(e.toString());
        }
    }

    private List<Vacancy> parse(LocalDateTime onTheDate) throws IOException {
        List<Vacancy> result = new ArrayList<>();
        if (onTheDate == null) {
            onTheDate = LocalDateTime.now().withDayOfYear(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        }
        boolean working = true;
        int maxCountPage = getMaxPageNumber(START_PAGE_URL);
        int counter = 1;
        do {
            Document document = Jsoup.connect(getURL(counter)).get();
            Element table = document.select("table.forumTable").get(0);
            Elements rows = table.select("tr");
            for (Element row : rows) {
                Elements cols = row.select("td");
                if (cols.size() > 0) {
                    String title = cols.get(1).text();
                    if (title.contains("Важно:")) {
                        continue;
                    }
                    String authorName = cols.get(2).text();
                    String href = cols.get(1).select("a").get(0).attr("href");
                    LocalDateTime date = parseDateTime(cols.get(5).text());
                    if (date.isBefore(onTheDate)) {
                        working = false;
                        break;
                    }
                    if (isJavaVacancy(title)) {
                        Document vacancyDoc = Jsoup.connect(href).get();
                        Element msgTable = vacancyDoc.select("table.msgTable").get(0);
                        String message = msgTable.select("td.msgBody").get(1).text();
                        String strDate = msgTable.select("td.msgFooter").get(0).text();
                        LocalDateTime msgDate = parseDateTime(strDate.split("\\[")[0].replace("\u00A0", ""));
                        if (msgDate.isAfter(onTheDate)) {
                            result.add(new Vacancy(title, href, authorName, msgDate, message));
                        }
                    }
                }
            }
            counter++;
            if (counter > maxCountPage) {
                working = false;
            }
        } while (working);
        return result;
    }

    private String getURL(int counter) {
        String url;
        if (counter > 1) {
            url = String.format("%s/%s", START_PAGE_URL, counter);
        } else {
            url = START_PAGE_URL;
        }
        return url;
    }

    private int getMaxPageNumber(String startPage) throws IOException {
        Document document = Jsoup.connect(startPage).get();
        Element table = document.select("table.sort_options").get(1);
        Element td = table.select("td").get(0);
        Elements pages = td.select("a");
        return Integer.valueOf(pages.get(pages.size() - 1).text());
    }

    private boolean isJavaVacancy(String text) {
        return needPattern.matcher(text).find() && !excludePattern.matcher(text).find();
    }

    private LocalDateTime parseDateTime(String dateTime) {
        LocalDateTime result;
        dateTime = dateTime.replace("май", "мая");
        if (dateTime.contains("сегодня") || dateTime.contains("вчера")) {
            String[] splitDate = dateTime.split("[, :]");
            int hh = Integer.parseInt(splitDate[2]);
            int mm = Integer.parseInt(splitDate[3]);
            result = LocalDate.now().atTime(hh, mm);
            if (dateTime.contains("вчера")) {
                result = result.minusDays(1);
            }
        } else {
            result = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("d MMM yy, HH:mm").withLocale( new Locale("ru")));
        }
        return result;
    }

    private static LocalDateTime getLastTimeStampFromTable(Connection connection) {
        LocalDateTime result = null;
        long count = 0;
        String queryCountRecords = "SELECT COUNT(datetime) FROM vacancies";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryCountRecords)) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                count = rs.getLong(1);
            }
        } catch (SQLException e) {
            LOG.warn("No fields with TimeStamp");
        }
        if (count > 1) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(datetime) FROM vacancies")) {
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    result = rs.getObject(1, LocalDateTime.class);
                }
                LOG.info("Get last TimeStamp from table vacancies : " + result);
            } catch (SQLException e) {
                LOG.warn("No fields with TimeStamp");
            }
        }
        return result;
    }
}
