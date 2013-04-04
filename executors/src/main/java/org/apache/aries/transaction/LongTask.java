
package org.apache.aries.transaction;

public interface LongTask
{

    public abstract void doALongTask(Thread caller) throws Exception;

}
