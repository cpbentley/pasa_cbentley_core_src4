package pasa.cbentley.core.src4.ctx;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.utils.BitUtils;

/**
 * Abstract context class for other modules that want to participate with {@link UCtx} in the context pattern.
 * <p>
 * Each module ctx class is a god like object that lives in peace with other gods thanks to the {@link CtxManager}.
 * </p>
 * <p>
 * Javadoc of the context provides all the starting information on how to use the module.
 * </p>
 * 
 * @see UCtx
 * @author Charles Bentley
 *
 */
public abstract class ACtx implements ICtx {

   private byte[]       data;

   private int          id;

   //#debug
   private int          toStringFlags;

   /**
    * Each context as a reference to the root {@link UCtx}
    */
   protected final UCtx uc;

   /**
    * 
    * @param uc
    */
   public ACtx(UCtx uc) {
      this.uc = uc;
   }

   /**
    * 
    */
   public int getCtxID() {
      return 0;
   }

   /**
    * Domain: TODO starts at 0 or 1?
    */
   public int getRegistrationID() {
      return id;
   }

   /**
    * Context manager
    */
   public byte[] getSettings() {

      return data;
   }

   /**
    * 
    */
   public int getStaticKeyRegistrationID(int type, int key) {
      // TODO Auto-generated method stub
      return 0;
   }

   /**
    * 
    * @return
    */
   public UCtx getUCtx() {
      return uc;
   }

   /**
    * 
    */
   public void register() {
      id = uc.getCtxManager().registerCtx(this);
   }

   public void setSettings(byte[] data) {
      this.data = data;
   }

   //#mdebug
   public IDLog toDLog() {
      return uc.toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "ACtx");
      toStringPrivate(dc);
   }

   public boolean toString(Dctx dctx, Object o) {
      return false;
   }

   public boolean toString(Dctx dc, Object o, String title) {
      return false;
   }

   public boolean toString1(Dctx dc, Object o, String title) {
      return false;
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "ACtx");
      toStringPrivate(dc);
   }

   public String toStringEventID(int pid, int eid) {
      return null;
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   /**
    * Check module-static wise if flag.
    * <br>
    * Each {@link Dctx} should have its own specific flag overriding
    * @param flag
    * @return
    */
   public boolean toStringHasToStringFlag(int flag) {
      return BitUtils.hasFlag(toStringFlags, flag);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public String toStringProducerID(int pid) {
      return null;
   }

   /**
    * @param flag
    * @param v
    */
   public void toStringSetToStringFlag(int flag, boolean v) {
      toStringFlags = BitUtils.setFlag(toStringFlags, flag, v);
   }

   //#enddebug
}
