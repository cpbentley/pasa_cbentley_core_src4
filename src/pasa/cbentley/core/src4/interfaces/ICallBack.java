package pasa.cbentley.core.src4.interfaces;

import pasa.cbentley.core.src4.logging.IStringable;

/**
 * A call back for a worker in the same thread as the worker.
 * 
 * @author Charles Bentley
 *
 */
public interface ICallBack extends IStringable {

   /**
    * If object is instance of {@link Throwable}, can assume, it failed.
    * <br>
    * Typically you check for instance of Object and act on it.
    * <br>
    * The callBack is operating in the caller thread.
    * 
    * @param o Object is often the source on which the callback was provided
    */
   public void callBack(Object o);
}
