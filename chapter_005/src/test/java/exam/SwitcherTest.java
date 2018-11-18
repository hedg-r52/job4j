package exam;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SwitcherTest {

    @Test
    public void whenSwitchShouldReturnNumberSequenceByTenElements() throws Exception {
        Switcher sw = new Switcher();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Adder(sw, sw.sem1, sw.sem2, 1));
        exec.execute(new Adder(sw, sw.sem2, sw.sem1, 2));
        exec.shutdown();
        TimeUnit.SECONDS.sleep(1);
        String expected = "1111111111222222222211111111112222222222111111111122222222221111111111222222222211111111112222222222";
        assertThat(sw.getValue(), is(expected));
    }

}