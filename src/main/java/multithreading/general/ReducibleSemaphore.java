package multithreading.general;

import java.util.concurrent.Semaphore;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class ReducibleSemaphore extends Semaphore {

    public ReducibleSemaphore(int i) {
        super(i);
    }

    @Override
    protected void reducePermits(int i) {
        super.reducePermits(i);
    }

}
