package nonblockingalgo;

import org.apache.commons.lang.SerializationUtils;
import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class NonBlockingCacheTest {

    NonBlockingCache nbc;
    Base first, second, third;

    @Before
    public void setUp() {
        nbc = new NonBlockingCache();
        first  = new Base("model1", 1);
        second = new Base("model2", 2);
        third  = new Base("model3", 3);
        nbc.add(first);
        nbc.add(second);
        nbc.add(third);
    }
    @Test(expected = OptimisticException.class)
    public void whenGetOptimisticException() {
        Base modelCopyOne = (Base) SerializationUtils.clone(nbc.getModel(1));
        Base modelCopyTwo = (Base) SerializationUtils.clone(nbc.getModel(1));
        modelCopyOne.edit("model1:one");
        nbc.update(modelCopyOne);
        modelCopyOne.edit("model1:two");
        nbc.update(modelCopyTwo);
    }

    @Test
    public void whenDeleteValue() {
        nbc.delete(first);
        assertNull(nbc.getModel(1));
    }

    @Test
    public void whenNotDeleteValue() {
        assertNotNull(nbc.getModel(1));
    }

    @Test
    public void whenTwoThreadsUpdateValue() throws InterruptedException {
        Thread threadOne = new Thread() {
            @Override
            public void run() {
                Base test = (Base) SerializationUtils.clone(nbc.getModel(1));
                test.edit("threadOne");
                nbc.update(test);
            }
        };
        Thread threadTwo = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Base test = (Base) SerializationUtils.clone(nbc.getModel(1));
                test.edit("threadTwo");
                nbc.update(test);
            }
        };

        threadOne.start();
        threadTwo.start();
        threadOne.join();
        threadTwo.join();

        assertThat(nbc.getModel(1).getData(), is("threadTwo"));
    }

}