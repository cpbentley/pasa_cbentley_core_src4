package pasa.cbentley.core.src4.interfaces;

public interface ITechThread extends ITech {

   /**
    * Run now without concern for the thread
    */
   public static final int THREAD_MODE_0_POST_NOW   = 0;

   public static final int THREAD_MODE_1_MAIN_NOW   = 1;

   public static final int THREAD_MODE_2_MAIN_LATER = 2;

   /**
    * create a new thread from a pool just
    */
   public static final int THREAD_MODE_3_WORKER     = 3;
}
