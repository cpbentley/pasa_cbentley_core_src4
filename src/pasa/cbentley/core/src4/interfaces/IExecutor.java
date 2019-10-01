package pasa.cbentley.core.src4.interfaces;

import pasa.cbentley.core.src4.logging.IStringable;

public interface IExecutor extends ITechThread, IStringable {

   
   public void executeWorker(Runnable run);
   public void executeMainNow(Runnable run);
   public void executeMainLater(Runnable run);
}
