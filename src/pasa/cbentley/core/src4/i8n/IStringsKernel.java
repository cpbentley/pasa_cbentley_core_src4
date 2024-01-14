/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.i8n;

import pasa.cbentley.core.src4.ctx.CtxManager;
import pasa.cbentley.core.src4.ctx.ICtx;

/**
 * Root interface for all IString code contexts.
 * 
 * Equivalent of R.strings in android.
 * 
 * However user compiles ids. 
 * 
 * IDs are static in definition but will be computed by the 
 * 
 * String static ranges are registered with {@link CtxManager#registerStaticRange(ICtx, int, int, int)}
 * 
 * 1 based Index
 * 
 * @author Charles Bentley
 *
 */
public interface IStringsKernel {

   /**
    * Lower bound of the range. We use 1based index to match line numbers in a file
    * which is easier for human to think about.
    * <p>
    * The zero slot is currently not used.
    * </p>
    */
   public static final int ASID_STRINGS_1_A = 0;

   /**
    * Higher bound of the range
    */
   public static final int ASID_STRINGS_1_Z = 100;

   /**
    * We use 1based index to match line numbers in a file
    */
   public static final int SID_STRINGS_001_ = ASID_STRINGS_1_A + 1;

   public static final int SID_STRINGS_002_ = ASID_STRINGS_1_A + 2;

   public static final int SID_STRINGS_003_ = ASID_STRINGS_1_A + 3;

}
