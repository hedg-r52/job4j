package notify;

import java.util.concurrent.locks.Lock;

/**
 * Cобственный механизм блокировок Lock
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Locker {
    private Thread owner;
    private boolean isLocked = false;

    public synchronized void lock() {
        while (isLocked) {
            if (this.owner.equals(Thread.currentThread())) {
                break;
            }
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!isLocked) {
            this.owner = Thread.currentThread();
            this.isLocked = true;
        }
    }

    public boolean isLocked() {
        return this.isLocked;
    }

    public synchronized void unlock() {
        while (isLocked && this.owner.equals(Thread.currentThread())) {
            this.isLocked = false;
            this.owner = null;
            notifyAll();
        }
    }

}
