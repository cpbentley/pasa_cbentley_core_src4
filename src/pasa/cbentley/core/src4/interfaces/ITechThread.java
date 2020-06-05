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
}
