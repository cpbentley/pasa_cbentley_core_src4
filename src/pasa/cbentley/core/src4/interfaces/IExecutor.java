package pasa.cbentley.core.src4.interfaces;

import pasa.cbentley.core.src4.event.IEventBus;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Must be set on {@link IEventBus#setExecutor(IExecutor)}
 * 
 * @author Charles Bentley
 *
 */
public interface IExecutor extends ITechThread, IStringable {

   /**
    * Executes the {@link Runnable} in a worker thread.
    * @param run
    */
   public void executeWorker(Runnable run);

   /**
    * Executes the {@link Runnable} the main UI thread right now synchronously if on the main thread.
    * @param run
    */
   public void executeMainNow(Runnable run);

   /**
    * Executes the {@link Runnable} the main UI thread asynchronously.
    * @param run
    */
   public void executeMainLater(Runnable run);
}
