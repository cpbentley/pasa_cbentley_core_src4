package pasa.cbentley.core.src4.i8n;

import pasa.cbentley.core.src4.ctx.CtxManager;
import pasa.cbentley.core.src4.ctx.ICtx;

/**
 * Root interface for all IString code contexts.
 * 
 * Equivalent of R.strings in android.
 * 
 * However user compiles ids. 
 * IDs are static in definition but will be computed by the 
 * 
 * String static ranges are registered with {@link CtxManager#registerStaticRange(ICtx, int, int, int)}
 * 
 * @author Charles Bentley
 *
 */
public interface IStringsKernel {

   public static final int SID_STRINGS_1 = 1;
}
