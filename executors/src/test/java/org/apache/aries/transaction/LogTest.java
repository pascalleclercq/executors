
package org.apache.aries.transaction;

import org.junit.Test;

public class LogTest
{

    int minThreads = 50;
    int maxThreads = 50;
    int minTxPerThread = 50;
    int maxTxPerThread = 50;
    @Test
    public void testOnLongTaskImpl() throws Exception {
        System.err.println("LongTaskImpl");
        LongTask longTask = new LongTaskImpl();
        perform(longTask);
    }

    @Test
    public void testOnSingleThreadExecutorLongTaskImpl() throws Exception {
        System.err.println("SingleThreadExecutorLongTaskImpl");
        LongTask longTask = new SingleThreadExecutorLongTaskImpl();
        perform(longTask);
    }

    private void perform(LongTask longTask) throws Exception {
        for (int i = minThreads; i <= maxThreads; i *= 10) {
            for (int j = minTxPerThread; j <= maxTxPerThread; j *= 10) {
                long ms = testThroughput(longTask, i, j);
                System.err.println("TPS (" + i + " threads, " + j + " tx) = " + ((i * j) / (ms / 1000.0)));
            }
        }

        System.err.println();
        System.err.flush();
    }

    public long testThroughput(final LongTask longTask, final int nbThreads,
            final int nbTxPerThread) throws Exception {
        Thread[] threads = new Thread[nbThreads];
        for (int thIdx = 0; thIdx < nbThreads; thIdx++) {
            threads[thIdx] = new Thread() {

                @Override
                public void run() {
                    try {
                        for (int txIdx = 0; txIdx < nbTxPerThread; txIdx++) {

                            longTask.doALongTask( this);
                        }
                    } catch (Throwable t) {
                        System.out.println(t);
                    }
                }
            };
        }
        long t0 = System.currentTimeMillis();
        for (int thIdx = 0; thIdx < nbThreads; thIdx++) {
            threads[thIdx].start();
        }
        for (int thIdx = 0; thIdx < nbThreads; thIdx++) {
            threads[thIdx].join();
        }
        long t1 = System.currentTimeMillis();
        return t1 - t0;
    }

}
