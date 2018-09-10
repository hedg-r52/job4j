package parser;

import org.junit.Test;

import javax.swing.text.DateFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.junit.Assert.*;

public class ParserSqlRuTest {

    @Test
    public void parseDateTime() {
        //ParserSqlRu parserSqlRu = new ParserSqlRu();
        //LocalDateTime today = parserSqlRu.parseDateTime("сегодня, 14:59");
        //LocalDateTime yesterday = parserSqlRu.parseDateTime("вчера, 20:34");
        LocalDateTime common =
                LocalDateTime.parse(
                        "7 янв 18, 19:54",
                        DateTimeFormatter.ofPattern("d MMM yy, HH:mm").withLocale( new Locale("ru"))
                );
        System.out.println(common);
    }
}