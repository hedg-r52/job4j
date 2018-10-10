package bomberman.version2.blocks;

import java.util.concurrent.locks.ReentrantLock;

public class Wall extends ReentrantLock {
    public Wall() {
        this.lock();
    }
}
