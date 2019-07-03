package pasa.cbentley.core.src4.thread;

import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Listens to state changes of an {@link IBRunnable}
 * <br>
 * <br>
 * 
 * @author Charles Bentley
 *
 */
public interface IBRunnableListener extends IStringable, ITechRunnable {

   /**
    * When task by {@link IBRunnable} is finished.
    * <br>
    * Typically, you cast {@link IBRunnable} to class and.
    * <br>
    * Called in the task thread. 
    * <br>
    * For GUI application, it must synch on the GUI thread to update its state.
    * <br>
    * 
    * @param runner
    * @param newState TODO
    */
   public void runnerNewState(IBRunnable runner, int newState);

   /**
    * When task failed to complete.
    * <br>
    * <br>
    * @param runner
    * @param e
    */
   public void runnerException(IBRunnable runner, Throwable e);

}
