/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.ctx;

import pasa.cbentley.core.src4.i8n.IStringsKernel;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.ILogConfigurator;
import pasa.cbentley.core.src4.logging.ILogConfiguratorCtx;
import pasa.cbentley.core.src4.stator.IStatorFactory;
import pasa.cbentley.core.src4.utils.BitUtils;

/**
 * Abstract context class for other modules that want to participate with {@link UCtx} in the context pattern.
 * <p>
 * Each module ctx class is a god like object that lives in peace with other gods thanks to the {@link CtxManager}.
 * 
 * Gods are configured by a master {@link IConfig} interface. Any God with specific configuration extends that interface.
 * Since Gods are configurable, it is logical that they also have state data if only for user-modified configuration values.
 * Are those modified configuration values to be persist for the next run ? User wants that. Or does he ? 
 * If he wants a change, why not simple using different configuration then ? And have a mechanism a given config file is chosen
 * Also most of the time, if an application wants to save some settings, it can use an ad-hoc class just for that.
 * 
 * Why poluting {@link ACtx} with state data ? So it was decided not to include state here.
 * But the {@link CtxManager} provides a way for supporting ctx that want to have their state anyways.
 * 
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

   protected IConfig          config;

   /**
    * Uses the default {@link CtxManager} from {@link UCtx}
    * @param uc
    */
   public ACtx(UCtx uc) {
      this(null, uc, uc.getCtxManager());
   }

   public ACtx(IConfig config, UCtx uc) {
      this(config, uc, uc.getCtxManager());
   }

   /**
    * 
    * @param uc
    * @param cm
    */
   public ACtx(UCtx uc, CtxManager cm) {
      this(null, uc, cm);
   }

   public ACtx(IConfig config, UCtx uc, CtxManager cm) {
      this.config = config;
      this.uc = uc;
      this.cm = cm;

      id = cm.registerCtx(this);
      
      //#mdebug
      ILogConfigurator logConfig = uc.toStringGetLogConfigurator();
      if(logConfig instanceof ILogConfiguratorCtx) {
         ((ILogConfiguratorCtx)logConfig).configureCtx(this);
      }
      //#enddebug
      
   }

   public IConfig getConfig() {
      return config;
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
    * @return 1 index based ID that was provided by the {@link CtxManager}
    */
   public int getRegistrationID() {
      return id;
   }

   /**
    * Returns the data that was read from the store by the {@link CtxManager}.
    * and set using {@link ACtx#setSettings(byte[])}
    * 
    * <p>
    * It is then used by the implementation to read its settings
    * </p>
    */
   public byte[] getSettings() {
      return data;
   }

   /**
    * Context implementation returns the 0 based value from key based on the type of static domains.
    * 
    * @param type.. for example {@link IStringsKernel#SID_STRINGS}
    * @param key.. a key
    */
   public int getStaticKeyRegistrationID(int type, int key) {
      throw new RuntimeException("class must implement getStaticKeyRegistrationID "+ getClass().getName());
   }

   /**
    * By default no factory for Ctx
    */
   public IStatorFactory getStatorFactory() {
      return null;
   }


   /**
    * 
    * @return
    */
   public UCtx getUC() {
      return uc;
   }

   /**
    * true when ctx has saved data from a previous run saved by {@link CtxManager}
    * @return
    */
   public boolean hasCtxData() {
      return data != null;
   }

   /**
    * Global method all ctxs
    */
   public void implementationProblem() {
      throw new RuntimeException();
   }

   public void registerRemove() {
      uc.getCtxManager().registerRemoveCtx(this);
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
      dc.root(this, ACtx.class, 200);
      toStringPrivate(dc);
      dc.appendVarWithSpace("toStringFlags", toStringFlags);
      dc.nlLvlArrayBytes("data", data);
      dc.nlLvl(config, "config");
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
      dc.root1Line(this, ACtx.class);
      toStringPrivate(dc);
   }

   public void toStringAssert(boolean b, String msg) {
      if (!b) {
         throw new IllegalStateException("assertion failed : " + msg);
      }
   }

   public void toStringCheckNull(Object o) {
      if (o == null) {
         throw new NullPointerException();
      }
   }

   public String toStringEventID(int pid, int eid) {
      return null;
   }

   public void toStringFlagSetOn(int flag, boolean b, Dctx dctx) {
      //we don't do anything by default
   }

   public String toStringGetDIDString(int did, int value) {
      return uc.toStringGetDIDManager().toStringGetDIDString(did, value);
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

   /**
    * @param flag
    * @param v
    */
   public void toStringSetToStringFlag(int flag, boolean v) {
      toStringFlags = BitUtils.setFlag(toStringFlags, flag, v);
   }

   public String toStringStaticID(int staticID) {
      return null;
   }

   //#enddebug
}
