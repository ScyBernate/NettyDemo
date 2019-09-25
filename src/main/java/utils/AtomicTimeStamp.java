package utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 返回多线程下唯一时间戳
 */
public class AtomicTimeStamp {

    private static AtomicLong timeMills = new AtomicLong(0);

    public static long getTimeStamp() {
        for (; ; ) {
            long currentMillis = System.nanoTime();
            long timeMillsInAtl = timeMills.get();
            if (currentMillis > timeMillsInAtl && timeMills.compareAndSet(timeMillsInAtl, currentMillis)) {
                return currentMillis;
            }
        }
    }
}


