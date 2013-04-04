
package fr.opensagres.executordemo;

import org.junit.Test;

import fr.opensagres.executordemo.LongTask;
import fr.opensagres.executordemo.SimpleLongTask;
import fr.opensagres.executordemo.SingleThreadExecutorLongTaskIgnoreException;
import fr.opensagres.executordemo.SingleThreadExecutorLongTaskWithException;

public class LogTest
{

    
    int nbThreads = 30;
    int minTxPerThread = 1;
    int maxTxPerThread = 100;
    @Test
    public void simpleLongTask() throws Exception {
        System.err.println("SimpleLongTask");
        LongTask longTask = new SimpleLongTask();
        perform(longTask);
    }

    @Test
    public void singleThreadExecutorLongTaskWithException() throws Exception {
        System.err.println("SingleThreadExecutorLongTaskWithException");
        LongTask longTask = new SingleThreadExecutorLongTaskWithException();
        perform(longTask);
    }

    @Test
    public void singleThreadExecutorLongTaskIgnoreException() throws Exception {
        System.err.println("SingleThreadExecutorLongTaskIgnoreException");
        LongTask longTask = new SingleThreadExecutorLongTaskIgnoreException();
        perform(longTask);
    }
    
    private void perform(LongTask longTask) throws Exception {
        
        	
            for (int j = minTxPerThread; j <= maxTxPerThread; j *= 10) {
                long ms = testThroughput(longTask, nbThreads, j);
                System.err.println("TPS (" + nbThreads + " threads, " + j + " tx) = " + ((nbThreads * j) / (ms / 1000.0)));
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
