/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.interfaces.ITech;

public interface ITechConfig extends ITech {

   public static final int CONFIG_FLAG_04_SHOW_THREAD            = 1 << 3;

   /**
    * Log entry records the timestamp.
    * Can be used to print difference between
    */
   public static final int CONFIG_FLAG_05_TIMESTAMP              = 1 << 4;

   /**
    * 
    */
   public static final int CONFIG_FLAG_1_ACCEPTED                = 1 << 0;

   public static final int CONFIG_FLAG_2_1LINE                   = 1 << 1;

   /**
    * Show stack trace
    */
   public static final int CONFIG_FLAG_3_STACK                   = 1 << 2;

   /**
    * Lever to block all print. Ignores all other flags except {@link IDLog#MASTER_FLAG_02_OPEN_ALL_PRINT}
    * and the {@link IDLog#pAlways(String, IStringable, Class, String)}
    * 
    * Second most powerful master flag. Blocks prints
    */
   public static final int MASTER_FLAG_01_BLOCK_ALL_PRINT        = 1 << 0;

   /**
    * Ignores the {@link DLogConfig} and print all.
    * <br>
    * Most powerful flags. TODO rename at 1
    * 
    * Override all flags and print all the content when set.
    * <br>
    * This flag will force the ignore of Class Negatives/Positives.
    * 
    */
   public static final int MASTER_FLAG_02_OPEN_ALL_PRINT         = 1 << 1;

   /**
    * Assume all tags are true, will reject only flags that are explicitly false
    */
   public static final int MASTER_FLAG_08_OPEN_ALL_BUT_FALSE     = 1 << 7;

   /**
    * Print only positive classes.
    * 
    * Setting this flag overrides the default which is that all classes are accepted. 
    */
   public static final int MASTER_FLAG_03_ONLY_POSITIVES         = 1 << 2;

   /**
    * Check the {@link IStringable} class for positives or negatives
    */
   public static final int MASTER_FLAG_09_TREAT_STRINGABLE_CLASS = 1 << 8;

   /**
    * When set, class settings of the config are ignored
    */
   public static final int MASTER_FLAG_04_IGNORE_CLASSES         = 1 << 3;

   /**
    * When set, tags are ignored on the config.
    * <br>
    * Setting only Positives and this flag will display everything on the positive classes.
    * 
    * This flag allows to switch off the current tag flag in the config
    */
   public static final int MASTER_FLAG_05_IGNORE_FLAGS           = 1 << 4;

   /**
    * Positive classes if instances in a hierarchy of mother classes. Code check if 
    */
   public static final int MASTER_FLAG_06_CLASS_INSTANCES        = 1 << 5;

   /**
    * 
    */
   public static final int MASTER_FLAG_07_THREAD_DATA            = 1 << 6;

}
