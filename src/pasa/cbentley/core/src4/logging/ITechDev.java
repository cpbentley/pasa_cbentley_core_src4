/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.interfaces.ITech;

/**
 * Flags the dev can enter
 * @author Charles Bentley
 *
 */
public interface ITechDev extends ITech {

   /**
    * {@link IStringable} and log statement will not include new lines characters
    */
   public static final int DEV_2_1LINE     = 1 << 1;

   /**
    * Show stack trace of this log statement
    */
   public static final int DEV_3_STACK     = 1 << 2;

   /**
    * Tells the logger to show thread info. 
    */
   public static final int DEV_4_THREAD    = 1 << 3;

   /**
    * Tells the logger to show time info. 
    */
   public static final int DEV_5_TIMESTAMP = 1 << 4;

   
   public static final int DEV_0_1LINE_THREAD = DEV_2_1LINE | DEV_4_THREAD;

   
}
