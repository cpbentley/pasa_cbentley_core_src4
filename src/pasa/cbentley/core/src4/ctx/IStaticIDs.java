package pasa.cbentley.core.src4.ctx;

/**
 * Centralize Static ID definition for the module
 * @author Charles Bentley
 *
 */
public interface IStaticIDs {

   /**
    * ID used to register events
    * <li>{@link CtxManager#registerStaticID(ICtx, int)}
    * <li>{@link CtxManager#registerStaticRange(ICtx, int, int, int)}
    */
   public static final int SID_STRINGS = 1;

   public static final int SID_EVENTS  = 2;

   //#debug
   public static final int SID_DIDS    = 3;

}
