package tdd;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TemplateTest {
    Pair[] params;

    @Test
    public void whenGetNameAndSubjectShouldGetGreetingText() throws Exception {
        Template template = new SimpleGenerator();
        String text = "I am a ${name}, Who are ${subject}?";
        params = new Pair[2];
        params[0] = new Pair("name", "Petr");
        params[1] = new Pair("subject", "you");
        String expected = "I am a Petr, Who are you?";

        String result = template.generate(text, params);

        assertThat(result, is(expected));
    }

    @Test
    public void whenGetSosWordShouldGetAskingHelpText() throws Exception {
        Template template = new SimpleGenerator();
        String text = "Help, ${sos}, ${sos}, ${sos}";
        params = new Pair[1];
        params[0] = new Pair("sos", "Aaa");
        String expected = "Help, Aaa, Aaa, Aaa";

        String result = template.generate(text, params);

        assertThat(result, is(expected));
    }

    @Test(expected = Exception.class)
    public void whenParamsNotExistAtTextShouldException() throws Exception {
        Template template = new SimpleGenerator();
        String text = "Help, ${sos}, ${sos}, ${sos}";
        params = new Pair[2];
        params[0] = new Pair("sos", "Aaa");
        params[1] = new Pair("mayday", "mayday");
        template.generate(text, params);
    }

}