/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
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
 * <p>
 * The most important method is the {@link ACtx#getCtxID()} method. See its javadoc.
 * </p>
 * @see UCtx
 * @author Charles Bentley
 *
 */
public abstract class ACtx implements ICtx {

   /**
    * The owner of the context
    */
   protected final CtxManager cm;

   /**
    * Data of the context. If it exists, it was set
    * during registering in the constructor.
    */
   private byte[]             data;

   private int                id;

   //#debug
   private int                toStringFlags;

   /**
    * Each context as a reference to the root {@link UCtx}
    */
   protected final UCtx       uc;

   /**
    * Uses the default {@link CtxManager} from {@link UCtx}
    * @param uc
    */
   public ACtx(UCtx uc) {
      this(uc, uc.getCtxManager());
   }

   /**
    * 
    * @param uc
    * @param cm
    */
   public ACtx(UCtx uc, CtxManager cm) {
      this.uc = uc;
      this.cm = cm;
      id = cm.registerCtx(this);
   }

   public IConfig getConfig() {
      return null;
   }

   /**
    * Must be implemented by the subclass of {@link ACtx}.
   
    * <p>
    * Q: <b>What is the purpose</b> ?
    * It prevents the application developer from loading 2 contexts of the same class in the same {@link CtxManager}.
    * It also forces the choice of a unique integer relative to other contexts class.
    * Relative Context Awareness and prevent context dumping.
    * </p>
    * <p>
    * Q: <b>Why is it not Abstract</b> ?
    * To strike the developer's consciousness at runtime. Its no big deal.. Just a simple static value to define
    * </p>
    * <br>
    * This is the weak link in the architecture. This value is statically defined with no compile checks of collisions. Application fail to start
    * when the  
    * 
    * @see ICtx#getCtxID()
    */
   public int getCtxID() {
      return 0;
   }

   /**
    * An array of 
    */
   public ICtx[] getCtxSub() {
      return new ICtx[] { uc };
   }

   /**
    * Domain: TODO starts at 0 or 1?
    */
   public int getRegistrationID() {
      return id;
   }

   /**
    * Global method all ctxs
    */
   public void implementationProblem() {
      throw new RuntimeException();
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
    * true when ctx has saved data from a previous run saved by {@link CtxManager}
    * @return
    */
   public boolean hasCtxData() {
      return data != null;
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
      dc.appendVarWithSpace("toStringFlags", toStringFlags);
      dc.nlLvl(uc, UCtx.class);
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

   public void toStringAssert(boolean b, String msg) {
      if (!b) {
         throw new IllegalStateException("assertion failed : " + msg);
      }
   }

   public String toStringEventID(int pid, int eid) {
      return null;
   }

   /**
    * Return all flags
    * @return
    */
   public int toStringGetToStringFlags() {
      return toStringFlags;
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
      dc.appendVarWithSpace("ctxid", getCtxID());
      dc.appendVarWithSpace("RegistrationID", getRegistrationID());
   }

   public String toStringProducerID(int pid) {
      return null;
   }

   public void toStringSetToStringFlag(int flags) {
      toStringFlags = flags;
   }

   public void toStringCheckNull(Object o) {
      if (o == null) {
         throw new NullPointerException();
      }
   }

   public void toStringFlagSetOn(int flag, boolean b, Dctx dctx) {
      //we don't do anything by default
   }

   /**
    * @param flag
    * @param v
    */
   public void toStringSetToStringFlag(int flag, boolean v) {
      toStringFlags = BitUtils.setFlag(toStringFlags, flag, v);
   }

   public void unregister() {
      uc.getCtxManager().unRegisterCtx(this);
   }

   //#enddebug
}
