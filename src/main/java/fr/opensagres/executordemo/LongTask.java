
package fr.opensagres.executordemo;

public interface LongTask
{

    public abstract void doALongTask(Thread caller) throws Exception;

}
