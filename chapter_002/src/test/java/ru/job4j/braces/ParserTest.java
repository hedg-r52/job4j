package ru.job4j.braces;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ParserTest {
    @Test
    public void whenValidStringThenSequenceValid() {
        String str = "[{}{}]";
        Parser parser = new Parser(str);
        parser.add(new Brace('(', ')'));
        parser.add(new Brace('{', '}'));
        parser.add(new Brace('[', ']'));
        boolean result = parser.validate();
        assertThat(result, is(true));
    }

    @Test
    public void whenInvalidStringThenSequenceInvalid() {
        String str = "{[}]";
        Parser parser = new Parser(str);
        parser.add(new Brace('(', ')'));
        parser.add(new Brace('{', '}'));
        parser.add(new Brace('[', ']'));
        boolean result = parser.validate();
        assertThat(result, is(false));
    }

    @Test
    public void whenValidStringThenGetStringBracePositions() {
        String str = "{{[]()}()}";
        Parser parser = new Parser(str);
        parser.add(new Brace('(', ')'));
        parser.add(new Brace('{', '}'));
        parser.add(new Brace('[', ']'));
        String result = parser.parse();
        String expected = "{:0 }:9\r\n" +
                "{:1 }:6\r\n" +
                "[:2 ]:3\r\n" +
                "(:4 ):5\r\n" +
                "(:7 ):8\r\n";
        assertThat(result, is(expected));

    }

}