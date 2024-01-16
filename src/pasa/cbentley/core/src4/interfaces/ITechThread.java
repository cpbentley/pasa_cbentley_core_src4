/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.interfaces;

public interface ITechThread extends ITech {

   /**
    * Run now without concern for the thread
    */
   public static final int THREAD_MODE_0_POST_NOW   = 0;

   /**
    * If we are in main, run synchronously, otherwise post to main queue
    */
   public static final int THREAD_MODE_1_MAIN_NOW   = 1;

   /**
    * post to main queue
    */
   public static final int THREAD_MODE_2_MAIN_LATER = 2;

   /**
    * create a new thread from a pool just
    */
   public static final int THREAD_MODE_3_WORKER     = 3;

   public static final int STATE_0_ON               = 0;

   public static final int STATE_1_OFF              = 1;

   public static final int STATE_2_SHUT_DOWN        = 2;

   /**
    * TODO when a nav key is pressed or, reset caret back to ON. i.e. it stops waiting 
    * When a thread wants to pause Pulse thread in a given mode
    * <br>
    * Synchronize caret blinking with GUI event thread.
    * <br>
    * Caret thread is paused and caretOn is true when an GUI event modifies the String.
    * 
    */
   public static final int STATE_3_PAUSED           = 3;

   /**
    * 
    */
   public static final int STATE_4_KEEP_ON          = 4;
}
