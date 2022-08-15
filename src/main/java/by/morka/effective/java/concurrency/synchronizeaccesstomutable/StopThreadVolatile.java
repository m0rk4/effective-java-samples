package by.morka.effective.java.concurrency.synchronizeaccesstomutable;

import java.util.concurrent.TimeUnit;

public class StopThreadVolatile {
    /*
     `volatile` guarantees that any reading thread
     will get last written value
     */
    private static volatile boolean stopRequested;

    public static void main(String[] args) throws InterruptedException {
        final Thread backgroundThread = new Thread(() -> {
            int i = 0;
            while (!stopRequested)
                i++;
        });

        backgroundThread.start();
        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;
    }
}
