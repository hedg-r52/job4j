package netmanager;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RequestParserTest {
    private RequestParser requestParser = RequestParser.getRequestParser();
    private final static String CMD = "Command";
    private final static String PARAM = "Parameter";

    @Test
    public void whenNoParamAtRequestThenReturnUpperCaseCommandAndEmptyParam() {
        requestParser.parse(CMD);
        assertThat(requestParser.getCommand(), is(CMD.toUpperCase()));
        assertThat(requestParser.getParam(), is(""));
    }

    @Test
    public void whenRequestWithParamThenReturnUpperCaseCommandAndSameParam() {
        requestParser.parse(String.format("%s %s", CMD, PARAM));
        assertThat(requestParser.getCommand(), is(CMD.toUpperCase()));
        assertThat(requestParser.getParam(), is(PARAM));
    }
}