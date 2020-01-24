package pasa.cbentley.core.src4.ctx;

import pasa.cbentley.core.src4.interfaces.IFlags;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * ToString flags are set directly on {@link UCtx#toStringSetToStringFlag(int, boolean)} or
 * {@link ACtx#toStringSetToStringFlag(int, boolean)}
 * 
 * Tagging interface
 * 
 * Flags set on a module context instance, 
 * 
 * Those flags are dynamically set by code to steer {@link IStringable} behavior of toString methods.
 * 
 * Those toString methods query their context for those flags.
 * 
 * Flags set on {@link Dctx#setFlag(int, boolean)}
 * @author Charles Bentley
 *
 */
public interface IFlagsToString extends IFlags {

   //#mdebug
   public static final int D_FLAG_31_IGNORE_SERIALIZE = 0;

   /**
    * When {@link Dctx} methods have a choice betwen 1line and full
    */
   public static final int FLAG_1_EXPAND              = 1;

   public static final int FLAG_DATA_01_SUCCINT       = 1 << 0;

   /**
    * Hints to hide cache data of {@link IStringable}.
    * <br>
    * Default cache info is written
    */
   public static final int FLAG_DATA_02_HIDE_CACHE    = 1 << 1;

   /**
    * Hints to prevent the printing of large byte arrays
    * <br>
    * By default large arrays are trimmed.
    */
   public static final int FLAG_DATA_03_BIG_DATA      = 1 << 2;

   public static final int FLAG_DATA_03_SUCCINT       = 3;

   /**
    * Never writes absolutes like array length in byte Object
    */
   public static final int FLAG_DATA_05_NO_ABSOLUTES  = 1 << 4;

   public static final int FLAG_DATA_06_SHOW_NULLS    = 1 << 5;

   //#enddebug
}
