package fr.opensagres.executordemo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class SingleThreadExecutorLongTaskWithException implements LongTask 
{

    ExecutorService service= Executors.newSingleThreadExecutor();
    
    LongTask inner= new SimpleLongTask();
    public void doALongTask(final Thread caller) throws Exception {
        
        Future<Void> future =      service.submit(new Callable<Void>() {
            public Void call() throws Exception {
                inner.doALongTask(caller);
                return null;
            }
        });
        
        try {
            future.get();
         } catch (ExecutionException ex) {
           throw (Exception)ex.getCause();
         }
        
    }

    
   

}
