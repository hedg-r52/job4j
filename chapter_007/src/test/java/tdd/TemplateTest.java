package tdd;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TemplateTest {
    @Test
    public void whenGetNameAndSubjectShouldGetGreetingText() throws Exception {
        Template template = new SimpleGenerator();
        String text = "I am a ${name}, Who are ${subject}?";
        List<Pair> params = new ArrayList<>();
        params.add(new Pair("name", "Petr"));
        params.add(new Pair("subject", "you"));
        String expected = "I am a Petr, Who are you?";
        String result = template.generate(text, params);
        assertThat(result, is(expected));
    }

    @Test
    public void whenGetSosWordShouldGetAskingHelpText() throws Exception {
        Template template = new SimpleGenerator();
        String text = "Help, ${sos}, ${sos}, ${sos}";
        List<Pair> params = new ArrayList<>();
        params.add(new Pair("sos", "Aaa"));
        String expected = "Help, Aaa, Aaa, Aaa";
        String result = template.generate(text, params);
        assertThat(result, is(expected));
    }

    @Test(expected = Exception.class)
    public void whenParamsNotExistAtTextShouldException() throws Exception {
        Template template = new SimpleGenerator();
        String text = "Help, ${sos}, ${sos}, ${sos}";
        List<Pair> params = new ArrayList<>();
        params.add(new Pair("sos", "Aaa"));
        params.add(new Pair("mayday", "mayday"));
        template.generate(text, params);
    }

    @Test(expected = Exception.class)
    public void whenParamsAtTextNotReplacedShouldException() throws Exception {
        Template template = new SimpleGenerator();
        String text = "I am a ${name}, Who are ${subject}?";
        List<Pair> params = new ArrayList<>();
        params.add(new Pair("name", "Petr"));
        template.generate(text, params);
    }
}
