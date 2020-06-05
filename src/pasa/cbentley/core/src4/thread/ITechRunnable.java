/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.thread;

import pasa.cbentley.core.src4.interfaces.ITech;

/**
 * 
 * @author Charles Bentley
 *
 */
public interface ITechRunnable extends ITech {

   /**
    * The UI progress bar will not be hidable by the user.
    * <br>
    * It will only accept Stop/Pause/Cancel commands when applicable.
    */
  public static final int FLAG_01_BLOCKING       = 1;
   /**
    * Allows the user to stop the task at the current step.
    * <br>
    * Clean but does not undo what has already been done. I.e updates in a database
    */
  public static final int FLAG_02_STOPPABLE      = 2;
   /**
    * Allows the user to pause/unpause
    */
  public static final int FLAG_03_PAUSABLE       = 4;
   /**
    * Allows the user to cleanly cancel the task in a clean way
    */
  public static final int FLAG_04_CANCELABLE     = 8;
   /**
    * Show the command to hide the Progress showing this Runnable
    */
  public static final int FLAG_05_UI_HIDABLE     = 16;
   /**
    * High Priority if set, the ThreadPool must not queue the task
    * and will set to the highest priority possible.
    * <br>
    * When set a new {@link Thread} is created for Task and run immediately
    */
  public static final int FLAG_06_NEW_THREAD     = 32;
   /**
    * When set, 
    */
  public static final int FLAG_07_UI_THREAD      = 64;
   /**
    * By Default it is running
    */
  public static final int STATE_0_RUNNING        = 0;
   /**
    * Stops the task without trying to undo previous work
    */
  public static final int STATE_3_STOPPED        = 3;
   /**
    * Undo previous work
    * If not possible,
    */
  public static final int STATE_2_CANCELED       = 2;
   /**
    * Runner crashed out because of exceptions
    */
  public static final int STATE_4_CANCELED_ERROR = 4;
   /**
    * State pause is special. Requesting a pause with
    * {@link IBRunnable#requestNewState(int)} will 
    */
  public static final int STATE_1_PAUSED         = 1;
   /**
    * Someone called {@link Thread#interrupt()}
    * while pausing
    * <br>
    * Cases:
    * <li>  worker is sleeping (animation).
    *  GUI wants to move on faster. So itpublic static final interrupts
    * <li> thread is paused. set this state request
    * so that thread continues its run state afterpublic static final interruption
    * <br>
    * If this state request is not provided. we have to
    * wind down and finish asap. 
    */
  public static final int STATE_5_INTERRUPTED    = 5;
   /**
    * State reached if thread finish on its own without external
    * calls to {@link IBRunnable#requestNewState(int)} to stop it.
    */
  public static final int STATE_6_FINISHED       = 6;

}
