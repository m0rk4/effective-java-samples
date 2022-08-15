package by.morka.effective.java.concurrency.synchronizeaccesstomutable;

import java.util.concurrent.TimeUnit;

public class StopThread {
    /*
    Even though read/write ops on non-double/long fields
    are atomic we may face problems.
     */
    private static boolean stopRequested;

    public static void main(String[] args) throws InterruptedException {
        /*
        There is no guaranteed time when this thread will notice change in
        stopRequested variable.
        The JVM may translate the code to following:
        if (!stopRequested)
            while(true)
                i++;
         */
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
