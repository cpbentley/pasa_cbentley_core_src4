package pasa.cbentley.core.src4.interfaces;

public interface IExecutor {

   
   public void executeWorker(Runnable run);
   public void executeMainNow(Runnable run);
   public void executeMainLater(Runnable run);
}
