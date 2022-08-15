package by.morka.effective.java.concurrency.synchronizeaccesstomutable;

import java.util.concurrent.TimeUnit;

/*
Synchronization is not guaranteed in case both (r/w) operations
are not synchronized.
 */
public class StopThreadCorrect {

    private static boolean stopRequested;

    private static synchronized void requestStop() {
        stopRequested = true;
    }

    private static synchronized boolean stopRequested() {
        return stopRequested;
    }

    public static void main(String[] args) throws InterruptedException {
        final Thread backgroundThread = new Thread(() -> {
            int i = 0;
            while (!stopRequested())
                i++;
        });

        backgroundThread.start();
        TimeUnit.SECONDS.sleep(1);
        requestStop();
    }
}
