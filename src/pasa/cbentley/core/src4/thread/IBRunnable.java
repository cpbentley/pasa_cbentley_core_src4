/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.thread;


import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Custom src4 implementation of a Future Task.
 * 
 * <br>
 * {@link Future#cancel(boolean)}
 * Because the TimerTask cancel method does not stop a currently running thread,
 * we have to create a kind of object for tasks that can be canceled,paused by the user
 * <br>
 * <br>
 * 
 * {@link IBProgessable} will check if his runnable is {@link IBRunnable} and check flags to see if cancel can be used
 * <br>
 * <br>
 * @author Charles Bentley
 *
 */
public interface IBRunnable extends Runnable, IStringable, ITechRunnable {

   /**
    * Flags from {@link IMProgessable}
    * 
    * @return bits set for supported method
    */
   public int getState();

   /**
    * Called from another thread to asynchronously requests a state change.
    * Valid parameters are
    * <li> {@link ITechRunnable#STATE_0_RUNNING}
    * <li> {@link ITechRunnable#STATE_1_PAUSED}
    * <li> {@link ITechRunnable#STATE_2_CANCELED}
    * <li> {@link ITechRunnable#STATE_3_STOPPED} Request a stop
    * @param state
    * @throws IllegalArgumentException if state is {@link ITechRunnable#STATE_4_CANCELED_ERROR}
    * @throws IllegalArgumentException if state is {@link ITechRunnable#STATE_5_INTERRUPTED}
    * @throws IllegalArgumentException if state is {@link ITechRunnable#STATE_6_FINISHED}
    */
   public void requestNewState(int state);

   /**
    * Adds the listener
    * @param lis {@link IBRunnableListener}
    */
   public void addListener(IBRunnableListener lis);

   /**
    * Removes the listener if added
    * @param lis
    */
   public void removeListener(IBRunnableListener lis);

   /**
    * <li> {@link ITechRunnable#FLAG_01_BLOCKING}
    * <li> {@link ITechRunnable#FLAG_02_STOPPABLE}
    * <li> {@link ITechRunnable#FLAG_03_PAUSABLE}
    * <li> {@link ITechRunnable#FLAG_04_CANCELABLE}
    * <li> {@link ITechRunnable#FLAG_05_UI_HIDABLE}
    * <li> {@link ITechRunnable#FLAG_06_NEW_THREAD}
    * <li> {@link ITechRunnable#FLAG_07_UI_THREAD}
    * @return
    */
   public boolean hasRunFlag(int flag);

   /**
    * 
    * @param flag
    * @param v
    */
   public void setRunFlag(int flag, boolean v);

}
