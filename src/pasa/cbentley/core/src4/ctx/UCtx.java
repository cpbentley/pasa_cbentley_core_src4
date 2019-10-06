package pasa.cbentley.core.src4.ctx;

import java.io.InputStream;
import java.util.Random;

import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.EventBusArray;
import pasa.cbentley.core.src4.event.IEventBus;
import pasa.cbentley.core.src4.helpers.UserLogJournal;
import pasa.cbentley.core.src4.interfaces.C;
import pasa.cbentley.core.src4.interfaces.IStrComparator;
import pasa.cbentley.core.src4.interfaces.ITech;
import pasa.cbentley.core.src4.io.BAByteIS;
import pasa.cbentley.core.src4.io.BAByteOS;
import pasa.cbentley.core.src4.logging.BaseDLogger;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.ILogEntryAppender;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.logging.IUserLog;
import pasa.cbentley.core.src4.logging.ToStringStaticBase;
import pasa.cbentley.core.src4.logging.UserLogSystemOut;
import pasa.cbentley.core.src4.memory.IMemory;
import pasa.cbentley.core.src4.memory.MemorySimpleCreator;
import pasa.cbentley.core.src4.strings.StringComparator;
import pasa.cbentley.core.src4.thread.WorkerThread;
import pasa.cbentley.core.src4.utils.ArrayUtils;
import pasa.cbentley.core.src4.utils.BitUtils;
import pasa.cbentley.core.src4.utils.CharUtils;
import pasa.cbentley.core.src4.utils.ColorUtils;
import pasa.cbentley.core.src4.utils.Geo2dUtils;
import pasa.cbentley.core.src4.utils.IOUtils;
import pasa.cbentley.core.src4.utils.IntUtils;
import pasa.cbentley.core.src4.utils.LongUtils;
import pasa.cbentley.core.src4.utils.ShortUtils;
import pasa.cbentley.core.src4.utils.StringUtils;
import pasa.cbentley.core.src4.utils.URLUtils;

/**
 * {@link UCtx} (Universal context), along with {@link ICtx} and {@link CtxManager},  provide the core basis for the Module Context Pattern.
 * <p>
 * <b>src4</b> suffixed module means all code must be src4 compatible for Java embedded. 
 * </p>
 * <b>Requirements</b> of the <b>Module Context Pattern</b>:
 * <li>Each module of code must define a <code>.ctx</code> package in which a ModuleNameCtx class is defined.
 * <li>Convention: All methods, fields and static constants are sorted alphabetically with constant at the top then fields the methods.
 * <li>The context module class provides access to all static object/singletons. Those objects become instances of the module context instance.
 * <li>Dependency Injection 1: every class of a module has the module ctx instance in the first parameter of the constructor. It is saved as a <code>protected final</code> as a class field. 
 * <li>Dependency Injection 2: a module ctx class is constructor injected its dependencies of other known module ctxs.
 * <li>Dependency Injection 3: Unknown modules are injected as an interface preferably as final in the constructor or if necessary in a setter.
 * <li>
 * <li>The static keyword is only allowed for public constants and methods that <i>will</i> be inlined. This rule prevents any static state.
 * <li>Naming Convention 1: All interfaces start with I, except one letter interfaces such as {@link C}.
 * <li>Interfaces without a clear root in a package are put in a .interfaces package.
 * <li>A contrario, package specific interfaces such a {@link IDLog} are located in their respective packages.
 * <li>IEvents prefixed interfaces such as {@link IEventsCore} are located in ctx package.
 *
 * <li> The <code>.ctx</code> contains an IEvents interface and IStrings and any interface defining
 * <i>static</i> unique int/long ids that are registered in the {@link CtxManager} at runtime.
 * <li> every set of constants is defined in a ITech prefixed interface
 * that extends {@link ITech}. {@link ITech} works as a marker. 
 * <li>Since the core code is written to be src4 compatible, every constant is declared public static final
 * <li>Debug of those values are managed by {@link ToStringStaticBase} sub classes.
 * <br>
 * <li>Naming Convention: Inverse Completion i.e. specific to less specific.. SwingCtx, YourAppSwingCtx, AndroidCtx. Variables often short like uc, sc, bac,
 * getters getSC() / getSwingCtx, getUC().
 * <li>All debug methods are prefixed with toString
 * <li>All toString and miscellanous debug code must be enclosed by antenna preprocessing directives #debug #mdebug/#enddebug. 
 * </li>
 * 
 * 
 * <p>
 * <b>Module Dependencies</b>
 * <br>
 * All other modules "connect" to this codebase by extending {@link ACtx}. That means they must
 * get an instance of {@link UCtx} in their constructor.
 * A module context also receives a <i>remote</i> module context of every module that will be used by the code of said module.
 * </p>
 * This means that by looking at the constructor of the context of a module, you know which other <i>remote</i> modules this module
 * code depends on.
 * <br>
 * {@link ICtx} defines the contract of module context for which {@link ACtx} provide stub implementation.
 * <br>
 * <br>
 * 
 * Since {@link UCtx} is universal, it is accessible everywhere. This enables access to universal services such as utils and debug logging to every single object instance inside the Module Context Pattern rules.
 * No more statically defined Loggers at the top of every classes.
 * 
 * <br>
 * This module provides the universal service of logging.
 * <br>
 * <br>
 * 
 * <b>Logging</b>
 * <br>
 * See {@link Dctx} and {@link IDLog} which are the front facing classes used in the code for logging.
 * 
 * If there is any, it should 
 * 
 * Platform agnostic
 * Uses
 * <li> String, litteral,
 * <li> Byte arrays, 
 * <li> {@link InputStream} -> simplified to {@link BAByteIS}, {@link BAByteOS}
 * <br>
 * <br>
 * Defines
 * <li> Logging framework with {@link IStringable} and {@link Dctx}
 * <li> {@link IEventBus} framework of {@link BusEvent}
 * <li> Stub framework for an universal language Strings framework
 * <li> {@link Thread} tasking with 
 * <br>
 * <br>
 * 
 * The universal module does not use any of the following concept directly.
 * <li> File is too dependant on the device
 * <li> Path.
 * <li> Network
 * <li> Class.getResourceAsStream is not reliable everywhere. (Android)
 * <li> Another module will have to define a custom {@link ILogEntryAppender}
 * 
 * 
 * It uses a minimal subset of the Java API in order to be easily portable to embedded frameworks.
 * This package is the core of the framework. As with all packages pasa.cbentley.*.src4, all code must be src4 compatible for Java embedded.
 * <br>
 * 
 * <br>
 * Incompatible source code is be located in core.src5 (Java 5) or core.src8 (Java 8)
 * 
 * Most host dependent functions are delegated to another module
 * 
 * <li> File is too dependant on the device
 * <li> 
 * @author Charles Bentley
 *
 */
public class UCtx implements ICtx, IEventsCore {

   public static final int CTX_ID           = 1;

   private ArrayUtils      au;

   private BitUtils        bu;

   private ColorUtils      coloru;

   private CtxManager      ctxManager;

   private CharUtils       cu;

   //Used to be static. not anymore. its a variable of the context
   //so that it can be configured dynamically. to test crashes etc.
   protected String        DEFAULT_ENCODING = "UTF-8";

   //#debug
   private IDLog           dlog;

   private EventBusArray   eventBusRoot;

   private Geo2dUtils      geo2dU;

   private IOUtils         iou;

   private IntUtils        iu;

   private LongUtils       lu;

   private IMemory         mem;

   private int             moduleID;

   private Random          random           = null;

   private IStrComparator  strc;

   private StringUtils     stru;

   private ShortUtils      su;

   //#debug
   private int             toStringFlags;

   private URLUtils        urlu;

   private IUserLog        userLog;

   private WorkerThread    workerThread;

   /**
    * Assume a simple Java Host 
    */
   public UCtx() {
      setCtxManager(new CtxManager(this));
      aInit();
   }

   /**
    * Register new {@link UCtx} with Manager
    * @param cm
    */
   public UCtx(CtxManager cm) {
      if (cm == null) {
         throw new NullPointerException();
      }
      this.setCtxManager(cm);
      aInit();
   }

   private void aInit() {
      mem = new MemorySimpleCreator(this);
      stru = new StringUtils(this);
      cu = new CharUtils(this);
      iu = new IntUtils(this);
      su = new ShortUtils(this);
      urlu = new URLUtils(this);
      lu = new LongUtils(this);
      coloru = new ColorUtils(this);
      bu = new BitUtils(this);
      au = new ArrayUtils(this);
      iou = new IOUtils(this);

      //#debug
      this.userLog = new UserLogSystemOut(this);
      //#debug
      dlog = new BaseDLogger(this);

      eventBusRoot = new EventBusArray(this, this, getEventBaseTopology());
      moduleID = ctxManager.registerCtx(this);
   }

   public void bip(Exception e, long bip) {

   }

   public ArrayUtils getAU() {
      return au;
   }

   public BitUtils getBU() {
      return bu;
   }

   public ColorUtils getColorU() {
      return coloru;
   }

   public int getCtxID() {
      return CTX_ID;
   }

   public CtxManager getCtxManager() {
      return ctxManager;
   }

   public CharUtils getCU() {
      return cu;
   }

   public String getDefaultEncoding() {
      return DEFAULT_ENCODING;
   }

   public int[] getEventBaseTopology() {
      int[] events = new int[BASE_EVENTS];
      events[PID_0_ANY] = EID_MEMORY_X_NUM;
      events[PID_1_FRAMEWORK] = EID_FRAMEWORK_X_NUM;
      events[PID_2_HOST] = EID_HOST_X_NUM;
      events[PID_3_MEMORY] = EID_MEMORY_X_NUM;
      return events;
   }

   /**
    * Get the event relating to the module.
    * each event id is static and must be unique
    * @return
    */
   public IEventBus getEventBusRoot() {
      return eventBusRoot;
   }

   public Geo2dUtils getGeo2dUtils() {
      if (geo2dU == null) {
         geo2dU = new Geo2dUtils(this);
      }
      return geo2dU;
   }

   public IOUtils getIOU() {
      return iou;
   }

   public IntUtils getIU() {
      return iu;
   }

   public LongUtils getLU() {
      return lu;
   }

   public IMemory getMem() {
      return mem;
   }

   /**
    * lazy init if none was set.
    * 
    * Can be set with a fixed seed for testing purposes with {@link UCtx#setRandom(Random)}
    * @return
    */
   public Random getRandom() {
      if (random == null) {
         random = new Random();
      }
      return random;
   }

   public Random getRandom(long seed) {
      return new Random(seed);
   }

   public int getRegistrationID() {
      return moduleID;
   }

   public byte[] getSettings() {
      // TODO Auto-generated method stub
      return null;
   }

   public int getStaticKeyRegistrationID(int type, int key) {
      // TODO Auto-generated method stub
      return 0;
   }

   public IStrComparator getStrComparator() {
      if (strc == null) {
         strc = new StringComparator(this);
      }
      return strc;
   }

   public StringUtils getStrU() {
      return stru;
   }

   public ShortUtils getSU() {
      return su;
   }

   public URLUtils getUrlU() {
      return urlu;
   }

   /**
    * It will never be null
    * @return
    */
   public IUserLog getUserLog() {
      if(userLog == null) {
         userLog = new UserLogJournal(this);
      }
      return userLog;
   }

   /**
    * Lazy init
    * @return
    */
   public WorkerThread getWorkerThread() {
      if (workerThread == null) {
         workerThread = new WorkerThread(this);
      }
      return workerThread;
   }

   public void setCtxManager(CtxManager ctxManager) {
      this.ctxManager = ctxManager;
   }

   //#mdebug
   public void setDlog(IDLog log) {
      if (log != null) {
         dlog = log;
      }
   }
   //#enddebug

   public void setRandom(Random rnd) {
      random = rnd;
   }

   public void setSettings(byte[] data) {
      // TODO Auto-generated method stub
   }

   /**
    * Can be null. if null resets to default comparator
    * @param c
    */
   public void setStrComparator(IStrComparator c) {
      this.strc = c;
   }

   public void setStrU(StringUtils stru) {
      if (stru != null) {
         this.stru = stru;
      }
   }

   /**
    * When null, reset the log to an empty {@link UserLogJournal}
    * @param userLogNew
    */
   public void setUserLog(IUserLog userLogNew) {
      if(userLogNew != null && this.userLog != null) {
         userLogNew.processOld(this.userLog);
      }
      this.userLog = userLogNew;
   }

   //#mdebug
   /**
    * Returns the Facade to the logging facility.
    * <br>
    * You can configure the logger to log thread ownership (by name) of the log entry.
    * <br>
    * 
    * @return
    */
   public IDLog toDLog() {
      return dlog;
   }

   /**
    * Debug method to check for null references.
    * <br>
    * Possibly remove this check in production code... should never happen
    * @param boc
    * @param boCtx
    */
   public void toStrDebugNullCheck(Object o, ICtx ctx) {
      if (o == null) {
         throw new NullPointerException();
      }
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "UCtx");
      toStringPrivate(dc);
   }

   public boolean toString(Dctx dctx, Object o) {
      // TODO Auto-generated method stub
      return false;
   }

   /**
    * Find an {@link ICtx} able to toString the object instance class
    * @param dctx
    * @param o
    * @param title
    */
   public boolean toString(Dctx dctx, Object o, String title) {
      return false;
   }

   public boolean toString1(Dctx dc, Object o, String title) {
      return false;
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "UCtx");
      toStringPrivate(dc);
   }

   public String toStringEventID(int pid, int eid) {
      switch (pid) {
         case PID_0_ANY:
            return "Any";
         case PID_1_FRAMEWORK:
            switch (eid) {
               case EID_FRAMEWORK_0_ANY:
                  return "Any";
               case EID_FRAMEWORK_1_CTX_CREATED:
                  return "CtxCreated";
               case EID_FRAMEWORK_2_LANGUAGE_CHANGED:
                  return "LanguageChanged";
               default:
                  return "UnknownEID"+eid;
            }
         case PID_2_HOST:
            return "Host";
         case PID_3_MEMORY:
            switch (eid) {
               case EID_MEMORY_0_ANY:
                  return "Any";
               case EID_MEMORY_1_OUT_OF_MEMORY_GC:
                  return "OutOfMemoryGC";
               case EID_MEMORY_2_USER_REQUESTED_GC:
                  return "UserRequestedGC";
               case EID_MEMORY_3_OBJECT_DESTROY:
                  return "ObjectDestroy";
               default:
                  return "UnknownEID"+eid;
            }
         default:
            return "UnknownPID"+pid;
      }
   }

   public UCtx toStringGetUCtx() {
      return this;
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
      switch (pid) {
         case PID_0_ANY:
            return "Any";
         case PID_1_FRAMEWORK:
            return "Framework";
         case PID_2_HOST:
            return "Host";
         case PID_3_MEMORY:
            return "Memory";
         default:
            return null;
      }
   }

   /**
    * Debug flag for this context
    * @param flag
    * @param v
    */
   public void toStringSetToStringFlag(int flag, boolean v) {
      toStringFlags = BitUtils.setFlag(toStringFlags, flag, v);
   }

   //#enddebug

}
