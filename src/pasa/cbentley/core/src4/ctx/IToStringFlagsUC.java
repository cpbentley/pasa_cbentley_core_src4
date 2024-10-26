/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.ctx;

import pasa.cbentley.core.src4.interfaces.IToStringFlags;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * ToString flags are set directly on {@link UCtx#toStringSetToStringFlag(int, boolean)} or
 * {@link ACtx#toStringSetToStringFlag(int, boolean)}
 * 
 * <p>
 * Tagging interface for all prefixed IFlagsToString 
 * </p>
 * 
 * Flags set on a module context instance, 
 * 
 * Those flags are dynamically set by code to steer {@link IStringable} behavior of toString methods.
 * 
 * Those toString methods query their context for those flags.
 * 
 * Flags set on {@link Dctx#setFlag(int, boolean)}
 * 
 * 
 * @author Charles Bentley
 *
 */
public interface IToStringFlagsUC extends IToStringFlags {

   //#mdebug


   /**
    * Some ToString methods have 2 choice.
    * 
    *  When succint, the pruning is done.
    */
   public static final int FLAG_UC_01_SUCCINT         = 1 << 0;

   /**
    * Tells to print cache data of {@link IStringable}.
    * 
    */
   public static final int FLAG_UC_02_CACHE_PRINT     = 1 << 1;

   /**
    * Tells ToString methods to print big data such as large byte arrays
    * <br>
    * By default large arrays are trimmed.
    */
   public static final int FLAG_UC_04_BIG_DATA        = 1 << 3;


   /**
    * Never writes absolutes like array length in byte Object
    */
   public static final int FLAG_UC_05_NO_ABSOLUTES    = 1 << 4;

   /**
    * Sometimes we want to hide nulls.. this forces showing those nulls
    */
   public static final int FLAG_UC_06_SHOW_NULLS      = 1 << 5;


   /**
    * When set, display data about non {@link IStringable} from java package
    */
   public static final int FLAG_UC_10_HOST_OBJECTS      = 1 << 9;

   /**
    * When set, serialization data is included.
    * This differentiate datastructure with different source
    * 
    * toString data will not equal another exactly equal except for serialize details
    * 
    */
   public static final int FLAG_UC_11_SERIALIZE_DETAILS = 1 << 10;

   //#enddebug
}
