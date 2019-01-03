package parser;

import ru.job4j.utils.jdbc.DBHelper;
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

    private final String startPageUrl = "http://www.sql.ru/forum/job-offers";
    private final static Logger LOG = LogManager.getLogger(ParserSqlRu.class);
    private final Pattern needPattern;
    private final Pattern excludePattern;
    private final Connection connection;
    private final DBHelper db;

    public ParserSqlRu() throws SQLException {
        Config config = new Config();
        config.load("app.properties");
        connection = DriverManager.getConnection(
                config.getValue("jdbc.url"),
                config.getValue("jdbc.username"),
                config.getValue("jdbc.password")
        );
        db = new DBHelper(connection, LogManager.getLogger(ParserSqlRu.class));
        needPattern = Pattern.compile("\\bjava\\b|\\bjava8\\b|\\bJEE\\b", Pattern.CASE_INSENSITIVE);
        excludePattern = Pattern.compile("\\bjava script\\b|\\bjavascript\\b|\\bjs\\b", Pattern.CASE_INSENSITIVE);
        checkTablesAndCreateIfAbsent();
    }

    private void checkTablesAndCreateIfAbsent() {
        db.query("CREATE TABLE if NOT EXISTS vacancies\n"
                + "(\n"
                + "    href char(300) PRIMARY KEY NOT NULL,\n"
                + "    datetime TIMESTAMP,\n"
                + "    author varchar(100),\n"
                + "    title varchar(300),\n"
                + "    msg text\n"
                + ");",
                Arrays.asList(),
                ps -> {
                    ps.execute();
                });
    }

    public void execute() throws IOException, SQLException {
        LOG.info("SQL Parser started...");
        ParserSqlRu parserSqlRu = new ParserSqlRu();
        List<Vacancy> vacancies = parserSqlRu.parse(getLastTimeStampFromTable(connection));
        parserSqlRu.insert2db(vacancies);
        LOG.info("SQL Parser finished.");
    }

    private void insert2db(List<Vacancy> vacancies) {
        List<Object> params = vacancies
                .stream()
                .map(v -> Arrays.asList(v.getUrl(), Timestamp.valueOf(v.getDateTime()), v.getAuthorName(), v.getTitle(), v.getMessage()))
                .collect(Collectors.toCollection(ArrayList::new));
        db.queryCycle(
                "INSERT INTO vacancies(href, datetime, author, title, msg) VALUES (?, ?, ?, ?, ?)",
                params,
                ps -> {
                   ps.execute();
                }
        );
    }

    private List<Vacancy> parse(LocalDateTime onTheDate) throws IOException {
        List<Vacancy> result = new ArrayList<>();
        if (onTheDate == null) {
            onTheDate = LocalDateTime.now().withDayOfYear(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        }
        boolean working = true;
        int maxCountPage = getMaxPageNumber(startPageUrl);
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
            url = String.format("%s/%s", startPageUrl, counter);
        } else {
            url = startPageUrl;
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
            result = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("d MMM yy, HH:mm").withLocale(new Locale("ru")));
        }
        return result;
    }

    private LocalDateTime getLastTimeStampFromTable(Connection connection) {

        long count = db.query(
                "SELECT COUNT(datetime) FROM vacancies",
                Arrays.asList(),
                ps -> {
                    long c = 0;
                    try (final ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            c = rs.getLong(1);
                        }
                    }

                    return c;
                }
        ).orElse(0L);
        LocalDateTime result = null;
        if (count > 1) {
            result = db.query(
                    "SELECT MAX(datetime) FROM vacancies",
                    Arrays.asList(),
                    ps -> {
                        LocalDateTime r = null;
                        try (final ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                r = rs.getObject(1, LocalDateTime.class);
                            }
                        }
                        return r;
                    }
            ).orElse(null);
        }
        return result;
    }
}
