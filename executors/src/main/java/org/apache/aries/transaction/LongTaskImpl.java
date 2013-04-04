package org.apache.aries.transaction;


public class LongTaskImpl implements LongTask 
{

    public void doALongTask(Thread caller) throws Exception{
        simulateBottleNeck();
    }
    
    private volatile int counter=0;

    private synchronized void simulateBottleNeck() throws Exception{
        counter++;
            Thread.sleep(20);
        if(counter%1000==0) throw new NullPointerException();
    }
}
