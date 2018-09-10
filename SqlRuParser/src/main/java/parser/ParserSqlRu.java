package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ParserSqlRu {

    private int MAX_COUNT_PAGE;
    private String START_PAGE_URL = "http://www.sql.ru/forum/job-offers";
    private Logger LOG = LoggerFactory.getLogger(ParserSqlRu.class);
    private Connection connection;
    private Config config;

    private List<String> needAllWords = new ArrayList<>(
            Arrays.asList(
                    "java"
            )
    );

    private List<String> excludeWords = new ArrayList<>(
            Arrays.asList(
                    "java script",
                    "javascript",
                    "js"
            )
    );

    public ParserSqlRu() throws SQLException {
        this.config = new Config();
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
        checkTablesAndCreateIfAbsent();
    }

    private void checkTablesAndCreateIfAbsent() throws SQLException {
        Statement st = connection.createStatement();
        //TODO create script
        st.executeQuery("create table if not exists vacancies (" +
                ")");
    }

    public static void main(String[] args) throws IOException, SQLException {
        ParserSqlRu parserSqlRu = new ParserSqlRu();
        parserSqlRu.parse(getLastTimeStampFromTable("sqlru", "date"));
    }


    public void parse(LocalDateTime onTheDate) throws IOException {
        if (onTheDate == null) {
            onTheDate = LocalDateTime.now().withDayOfYear(1);
        }
        String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(2, 4);
        boolean working = true;
        MAX_COUNT_PAGE = getMaxPageNumber(START_PAGE_URL);
        int counter = 1;
        do {
            Document document = Jsoup.connect(getURL(counter)).get();
            Element table = document.select("table[class=forumTable]").get(0);
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
                        System.out.format("[%s] %s(%s) <%s>\n", date, title, authorName, href);
                    }
                }
            }
            counter++;
            if (counter > MAX_COUNT_PAGE) {
                working = false;
            }
        } while (working);
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
        Element table = document.select("table[class=sort_options]").get(1);
        Element td = table.select("td").get(0);
        Elements pages = td.select("a");
        return Integer.valueOf(pages.get(pages.size() - 1).text());
    }

    private boolean isJavaVacancy(String text) {
        String textLowerCase = text.toLowerCase();
        return textLowerCase.matches(".*java.*") && !(textLowerCase.matches(".*java\\s*script.*"));
    }

    public LocalDateTime parseDateTime(String dateTime) {
        LocalDateTime result = null;
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

    public static LocalDateTime getLastTimeStampFromTable(String table, String column) {
        LocalDateTime result = null;
        //TODO
        return result;
    }

}
