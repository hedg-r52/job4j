package notify;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class LockerTest {

    Locker locker;

    @Before
    public void setUp() throws Exception {
        locker = new Locker();
    }

    @Test
    public void whenLockedThenIsLockedTrue() {
        locker.lock();
        assertThat(locker.isLocked(), is(true));
    }

    @Test
    public void whenLockedAndUnlockThenIsLockedFalse() {
        locker.lock();
        locker.unlock();
        assertThat(locker.isLocked(), is(false));
    }

}