/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.interfaces.ITech;

/**
 * Flags the dev can enter manually in calls such as
 * 
 * <li> {@link IDLog#pFlow(String, Object, Class, String, int, int)}
 * 
 * @author Charles Bentley
 *
 */
public interface ITechDev extends ITech {

   /**
    * {@link IStringable} and log statement will not include new lines characters
    */
   public static final int DEV_2_ONELINE        = 1 << 1;

   /**
    * Show stack trace of this log statement
    */
   public static final int DEV_3_STACK          = 1 << 2;

   /**
    * Tells the logger to show thread info. 
    */
   public static final int DEV_4_THREAD         = 1 << 3;

   /**
    * Tells the logger to show time info. 
    */
   public static final int DEV_5_TIMESTAMP      = 1 << 4;

   /**
    * It is tagged as a big statement. which will be ignored with master flag
    * 
    * {@link ITechDLogConfig#MASTER_FLAG_11_IGNORES_BIGS}
    * 
    */
   public static final int DEV_6_BIG            = 1 << 5;

   /**
    * All tags must be true so a log entry is accepted
    */
   public static final int DEV_7_AND_TAGS       = 1 << 6;

   /**
    * Mix of {@link ITechDev#DEV_2_ONELINE} and {@link ITechDev#DEV_4_THREAD}
    */
   public static final int DEV_X_ONELINE_THREAD = DEV_2_ONELINE | DEV_4_THREAD;

}
