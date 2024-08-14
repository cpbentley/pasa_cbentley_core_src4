/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.ctx;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import pasa.cbentley.core.src4.api.ApiManager;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.EventBusArray;
import pasa.cbentley.core.src4.event.IEventBus;
import pasa.cbentley.core.src4.helpers.UserLogJournal;
import pasa.cbentley.core.src4.i8n.IStringProducer;
import pasa.cbentley.core.src4.interfaces.C;
import pasa.cbentley.core.src4.interfaces.IHost;
import pasa.cbentley.core.src4.interfaces.IStrComparator;
import pasa.cbentley.core.src4.interfaces.ITech;
import pasa.cbentley.core.src4.io.BAByteIS;
import pasa.cbentley.core.src4.io.BAByteOS;
import pasa.cbentley.core.src4.io.BADataIS;
import pasa.cbentley.core.src4.io.BADataOS;
import pasa.cbentley.core.src4.logging.BaseDLogger;
import pasa.cbentley.core.src4.logging.DIDManager;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IDLogConfig;
import pasa.cbentley.core.src4.logging.ILogConfigurator;
import pasa.cbentley.core.src4.logging.ILogEntryAppender;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.logging.IUserLog;
import pasa.cbentley.core.src4.logging.ToStringStaticBase;
import pasa.cbentley.core.src4.logging.UserLogSystemOut;
import pasa.cbentley.core.src4.memory.IMemory;
import pasa.cbentley.core.src4.memory.MemorySimpleCreator;
import pasa.cbentley.core.src4.stator.IStatorFactory;
import pasa.cbentley.core.src4.stator.StatorFactoryUC;
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
import pasa.cbentley.core.src4.utils.MathUtils;
import pasa.cbentley.core.src4.utils.ShortUtils;
import pasa.cbentley.core.src4.utils.StringUtils;
import pasa.cbentley.core.src4.utils.URLUtils;

/**
 * {@link UCtx} (Universal context), along with {@link ICtx} and {@link CtxManager},  provide the core basis for the Code Context Pattern.
 * <p>
 * <b>src4</b> suffixed code contexts means all code must be src4 compatible for Java embedded. 
 * </p>
 * 
 * <p>
 * <b>General conventions</b> :
 * <li>Java Code is the Law. Code is written to easy step over debug. Good and up to date comments surely helps but you must be able to step into the code and read it
 * to know what is going on.
 * <li>No reflection as it prevents efficient tree shaking. Pluggable Object factories shall be used instead.
 * <li>Because Code is Law, Annotations are not used.
 * <li>Because Code is Law, XML files are not used.
 * <li>Naming Convention 1: All interfaces start with I, except one letter interfaces such as {@link C}.
 * <li>Convention: All methods, fields and static constants are sorted alphabetically with constant at the top then fields then methods.
 * <li> Conventions Prefixed Auto completion. Good IDEs will provide you a list of possiblities usually with Ctrl+Space
 * <ul>
 * <li>set prefix on methods that modifiy the state of a class. Setters allow for more than 1 parameters. Want to know settingAll methods, fields and static constants are sorted alphabetically with constant at the top then fields then methods.
 * <li>get prefix on methods that read. its allows  methods for settingAll methods, fields and static constants are sorted alphabetically with constant at the top then fields then methods.
 * <li>Example of prefixed naming convention {@link java.awt.event.WindowListener} where all methods are prefixed with window .
 * </ul>
 * <li>Lazy suffix when the getter creating from a null. This leaks implementation details and that's ok in many cases as we want to make it known
 * the reference returned is always the same.
 * <ul>
 * <li>factory.getImageRootLazy() check if factory field imageRoot is null and initialize the instance.
 * </ul>
 * <li> One Class. One File. Corollary: avoid anynomyous classes.
 * <li> Its 90% always more readable to remove anonymous classes. You are forced to acknowledge the state required and it is easier to {@link IStringable} it.
 * </p>
 * 
 * <br>
 * 
 * <p>
 * <b>Debug Framework and Conventions</b> :
 * <li>We use //#debug //#mdebug //#enddebug directives to remove all debugging code from production code. 
 * <li>All debug methods and fields are prefixed with toString
 * <li>Method names prefixed with <code>to</code> are reserved for debugging. 
 * <li>Avoid method names that start with letters u,v,w,x,y,z. This allows debug code to be easily visible at the end of a file.
 * <li>One Class One File. All debug methods are easily visible at the end of a file.
 * <li>toString() method outputs the debug visualization of the class. This must be implemented.
 * <li>All toString and miscellanous debug code must be enclosed by antenna preprocessing directives #debug #mdebug/#enddebug. 
 * <li>Code should not ever rely on toString() methods for making logic decisions. It always was and always will be fragile.
 * </p>
 * 
 * <p>
 * <br>
 * Historically, {@link UCtx} was born out of the need to access the Debug Context (name {@link Dctx}) in a non static way.
 * All debug frameworks/libraries that uses static references had to be scrapped, because even debug static state had to disappear.
 * So every single object has to have a reference to an {@link UCtx} instance to access at least the default configuration of {@link Dctx}.
 * the toString() method of every {@link IStringable} uses the default configuration from {@link UCtx} to create its {@link Dctx}.
 * This setup allows you to create custom Debug Context {@link Dctx} and ask a class to debug itself using it, without messing with the defaults.
 * </p>
 * 
 * <br>
 * 
 * <p>
 * <b>Requirements</b> of the <b>Code Context Pattern</b>:
 * <li>Each corpus/module/project of code must define a <code>.ctx</code> package in which a ProjectNameCtx class is defined. Name is suffixed with Ctx.
 * <li>Schematically, the ProjectNameCtx class provides access to all previously static objects/singletons. Those objects become instances of this class.
 * <li>At minimum, the ProjectNameCtx class provides a Java-based code description of its dependencies to other code contexts in its constructor.
 * <li>A TemplateNameCtx can be abstract so as to provide a template for more specific code contexts, hiding implementation to code contexts using it. 
 * <li>It looks like a God object pattern, but applied to a corpus of code. So Code Context Pattern introduces a clean hierarchy.
 * <li>Rule #3 The constructor of the <code>ProjectNameCtx</code> has a <code>IConfigProjectName</code> extends {@link IConfig} as first parameter. An implementation of <code>IConfigProjectName</code> is used to configure the code context.
 * <ul>
 * <li>This rule allows you to configure a very complex hierarchy with a clean set of Java classes, respecting the convention that Java Code is Law. 
 * <li>Yet provides the flexibility to implement the {@link IConfig} interfaces using text file or XML files.
 * </ul>
 * <li>Rule #4 The constructor of the ProjectNameCtx contains all the dependencies to other code contexts.
 * <li> As a corollary of rule #4, the constructor of a Code Ctx cannot instantiate another Code Ctx.
 * <li>Field references of code contexts are saved as <code>protected final</code> as a class field. 
 * <li>What does the .ctx package contains?
 * <ul>
 * <li> {@link IConfig} and possibly a default Java implementation of it.
 * <li>IEvents prefixed interfaces such as {@link IEventsCore} are located in ctx package.
 * <li>{@link IToStringFlags} prefixed interfaces used to dynamically configure a {@link Dctx} (Debug Context)
 * <li>{@link ToStringStaticUc} for debugging integer constant object of the code context
 * <li> The <code>.ctx</code> contains an IEvents interface and IStrings and any interface defining
 * 
 * </ul>
 * 
 * <li>Interfaces without a clear root in a package are put in a <code>.interfaces</code> package of the code context.
 * <li>A contrario, package specific interfaces such a {@link IDLog} are located in their respective packages.
 *
 * <i>static</i> unique int/long ids that are registered in the {@link CtxManager} at runtime.
 * <li> every set of constants is defined in a ITech prefixed interface
 * that extends {@link ITech}. {@link ITech} works as a marker. 
 * <li>Debug of those values are managed by {@link ToStringStaticBase} sub classes.
 * </li>
 * </p>
 * 
 * 
 * <p>
 * <b>Code Context Pattern Conventions</b> :
 * <li>Since the core code is written to be src4 compatible, every constant is declared <code>public static final</code>
 * <li>Naming: Inverse Completion i.e. specific to less specific.. SwingCtx, YourAppSwingCtx, AndroidCtx. 
 * <li>Instance variables most often will be short like uc, sc, bac,
 * <li>Getters for code context instances can be of the following 2 forms. 
 *  <ul>
 *  <li>SwingCtx -> getSC() / getSwingCtx 
 *  <li>UCtx -> getUC / getUCtx()
 *  <li>JavaGameCtx -> getJGC() / getJavaGameCtx() 
 *  </ul>
 * </p>
 * <p>
 * <b>Consequences</b> of the <b>Code Context Pattern</b>:
 * <li>The static keyword is only allowed for public constants and methods that <i>will</i> be inlined. This rule prevents any static state.
 * <li>By looking at the constructor of a ProjectNameCtx, you know its dependencies to other code contexts.
 * <li>Rule #4 prevent dependency injections
 * </p>
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

   public static final int  CTX_ID = 1;

   private ApiManager       apiManager;

   private ArrayUtils       au;

   private BitUtils         bu;

   private ColorUtils       coloru;

   private IConfigU         config;

   private final CtxManager ctxManager;

   private CharUtils        cu;

   private DIDManager       didManager;

   //#debug
   private IDLog            dlog;

   private String           encoding;

   private EventBusArray    eventBusRoot;

   private Geo2dUtils       geo2dU;

   private IHost            host;

   private IOUtils          iou;

   private IntUtils         iu;

   private LongUtils        lu;

   private MathUtils        mathUtils;

   private IMemory          mem;

   private String           name;

   private Random           random = null;

   private int              registrationID;

   private StatorFactoryUC  statorFactoryUC;

   private IStrComparator   strc;

   /**
    * Might be null.
    */
   private IStringProducer  stringProducer;

   private StringUtils      stru;

   private ShortUtils       su;

   //#debug
   private int              toStringFlags;

   private ILogConfigurator toStringLogConfigurator;

   private URLUtils         urlu;

   private IUserLog         userLog;

   private WorkerThread     workerThread;

   /**
    * Assume a simple Java Host 
    */
   public UCtx() {
      this(null, null);
   }

   /**
    * 
    * @param config when null defaults to {@link ConfigUDef}
    */
   public UCtx(IConfigU config) {
      this(config, null);
   }

   public UCtx(IConfigU config, String name) {
      if (config == null) {
         config = new ConfigUDef();
      }

      //#debug
      config.toStringSetDebugUCtx(this);

      this.name = name;
      this.config = config;
      ctxManager = new CtxManager(this);
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

      //#mdebug
      BaseDLogger dlog = new BaseDLogger(this);
      if (name != null) {
         dlog.setName(name);
      }
      this.dlog = dlog;

      toStringLogConfigurator = config.toStringGetLogConfigurator(this);
      //TODO there might be different appenders with different configurations
      //Files etc etc Here we deal with default appender
      ILogEntryAppender logAppender = dlog.getDefault();
      IDLogConfig configOfAppender = logAppender.getConfig();
      toStringLogConfigurator.apply(configOfAppender); //apply config

      //String message = "Very First Log Message; Using LogConfigurator:" + toStringLogConfigurator.getClass().getName();
      String message = "Very First Log Message; Using (" + stru.getNameClass(toStringLogConfigurator.getClass()) + ".java:20)";
      dlog.pAlways(message, null, UCtx.class, "[" + +this.hashCode() + "]");
      dlog.pAlways("configOfAppender", configOfAppender, UCtx.class, "constructor@360");
      dlog.pAlways("IConfigU", config, UCtx.class, "constructor@361");
      //#enddebug

      //this is not for debug purposes. its the application log for the application user.
      this.userLog = new UserLogSystemOut(this);

      registrationID = ctxManager.registerCtx(this);

      ctxManager.registerStaticID(this, IStaticIDs.SID_STRINGS);
      ctxManager.registerStaticID(this, IStaticIDs.SID_EVENTS);

      ctxManager.registerStaticRange(this, IStaticIDs.SID_EVENTS, IEventsCore.A_SID_CORE_EVENT_A, IEventsCore.A_SID_CORE_EVENT_Z);
      eventBusRoot = new EventBusArray(this, this, getEventBaseTopology(), IEventsCore.A_SID_CORE_EVENT_A);

      encoding = config.getDefaultEncoding();

      //#mdebug
      ctxManager.registerStaticID(this, IStaticIDs.SID_DIDS);
      ctxManager.registerStaticRange(this, IStaticIDs.SID_DIDS, IToStringDIDs.A_DID_OFFSET_A_UC, IToStringDIDs.A_DID_OFFSET_Z_UC);
      didManager = new DIDManager(this);
      config.toStringSetDebugUCtx(this);
      toDLog().pCreate("", this, UCtx.class, "Created@382", LVL_05_FINE, true);
      //#enddebug
   }

   public void bip(Exception e, long bip) {

   }

   public BADataIS createNewBADataIS(byte[] data) {
      BAByteIS bis = new BAByteIS(this, data);
      BADataIS dis = new BADataIS(this, bis);
      return dis;
   }

   public BADataIS createNewBADataIS(byte[] data, int offset) {
      BAByteIS bis = new BAByteIS(this, data, offset, data.length);
      BADataIS dis = new BADataIS(this, bis);
      return dis;
   }

   public BADataIS createNewBADataIS(InputStream is) throws IOException {
      BAByteIS bis = new BAByteIS(this, this.getIOU().streamToByte(is));
      BADataIS dis = new BADataIS(this, bis);
      return dis;
   }

   public BADataOS createNewBADataOS() {
      BAByteOS bos = new BAByteOS(this);
      BADataOS bada = new BADataOS(this, bos);
      return bada;
   }

   public ApiManager getApiManager() {
      if (apiManager == null) {
         apiManager = new ApiManager(this);
      }
      return apiManager;
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

   /**
    * Master config that can impact the behavior of the whole ctx hierarchy
    * @return
    */
   public IConfig getConfig() {
      return config;
   }

   public IConfigU getConfigU() {
      return config;
   }

   public int getCtxID() {
      return CTX_ID;
   }

   public CtxManager getCtxManager() {
      return ctxManager;
   }

   public ICtx[] getCtxSub() {
      return null;
   }

   public CharUtils getCU() {
      return cu;
   }

   /**
    * This field used to be a statically defined variable.
    * 
    * Now it is an instance configurable by {@link IConfigU} and settable
    * on the context.
    * @return
    */
   public String getDefaultEncoding() {
      return encoding;
   }

   public int[] getEventBaseTopology() {
      int[] events = new int[BASE_EVENTS];
      events[PID_00] = PID_00_XX;
      events[PID_01] = PID_01_XX;
      events[PID_02] = PID_02_XX;
      events[PID_03] = PID_03_XX;
      events[PID_04] = PID_04_XX;
      events[PID_05] = PID_05_XX;
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

   /**
    * Null unless set with {@link UCtx#setHost(IHost)}
    * @return
    */
   public IHost getHost() {
      if (host == null) {
         throw new NullPointerException("Host must be set externaly with setHost(IHost)");
      }
      return host;
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

   public MathUtils getMathUtils() {
      if (mathUtils == null) {
         mathUtils = new MathUtils(this);
      }
      return mathUtils;
   }

   public IMemory getMem() {
      return mem;
   }

   public IEventBus getOrCreateEventBus(ICtx ctx, int sidA, int sidZ, int[] topo) {
      //depending on configuration. use one or several buses
      EventBusArray bus = new EventBusArray(this, ctx, topo, sidA);
      return bus;
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
      return registrationID;
   }

   public byte[] getSettings() {
      return null;
   }

   public int getStaticKeyRegistrationID(int type, int key) {
      // TODO Auto-generated method stub
      return 0;
   }

   /**
    * By default no factory for Ctx
    */
   public IStatorFactory getStatorFactory() {
      if (statorFactoryUC == null) {
         statorFactoryUC = new StatorFactoryUC(this);
      }
      return statorFactoryUC;
   }

   public IStrComparator getStrComparator() {
      if (strc == null) {
         strc = new StringComparator(this);
      }
      return strc;
   }

   public IStringProducer getStringProducer() {
      return stringProducer;
   }

   public IStringProducer getStringProducerNotNull() {
      if (stringProducer == null) {
         throw new IllegalStateException("IStringProducer cannot be null. Initialize with setter");
      }
      return stringProducer;
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
      if (userLog == null) {
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

   public void setConfigU(IConfigU configU) {
      if (configU == null) {
         throw new NullPointerException();
      }
      this.config = configU;
   }

   /**
    * 
    * @param encoding
    */
   public void setDefaultEncoding(String encoding) {
      this.encoding = encoding;
   }

   /**
    * Sets the {@link IHost}
    * 
    * {@link IOUtils} can work without one.
    * 
    * But some platform will implement {@link IHost#getResourceAsStream(String)}
    * more efficiently
    * @param host
    */
   public void setHost(IHost host) {
      this.host = host;
   }

   public void setRandom(Random rnd) {
      random = rnd;
   }

   public void setSettings(byte[] data) {
      //we do not save any data yet
   }

   /**
    * Can be null. if null resets to default comparator
    * @param c
    */
   public void setStrComparator(IStrComparator c) {
      this.strc = c;
   }

   public void setStringProducer(IStringProducer stringProducer) {
      this.stringProducer = stringProducer;
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
      if (userLogNew != null && this.userLog != null) {
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
      dc.root(this, UCtx.class, 690);
      toStringPrivate(dc);
      dc.nlLvl(config, IConfigU.class);
      dc.nlLvl(eventBusRoot, "eventBusRoot");
      dc.nlLvl(workerThread, "workerThread", WorkerThread.class);
      dc.nlLvl(mem, "IMemory");
      dc.appendVarWithSpace("stringProducer", stringProducer);
      dc.nlLvl(userLog, "IUserlog");
      dc.nlLvl(dlog, "IDLog");

      //the manager is never to Stringed by a Ctx
      //if you need Manager, you will get the whole application toStringed
      //dc.nlLvl(ctxManager, "ctxManager");
   }

   public boolean toString(Dctx dctx, Object o) {
      //we don't have any unknown object from this code module
      //all our objects implements IStringable
      return false;
   }

   public boolean toString1Line(Dctx dctx, Object o) {
      //we don't have any unknown object from this code module
      //all our objects implements IStringable
      return false;
   }

   /**
    * Find an {@link ICtx} able to toString the object instance class
    * @param dctx
    * @param o
    * @param title
    */
   public boolean toString(Dctx dctx, Object o, String title) {
      if (o instanceof String) {
         dctx.append(title);
         dctx.append(" = ");
         dctx.append((String) o);
         return true;
      }
      return false;
   }

   public boolean toString1(Dctx dc, Object o, String title) {
      return false;
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, UCtx.class);
      toStringPrivate(dc);
   }

   public void toStringCheckNull(Object o) {
      if (o == null) {
         throw new NullPointerException();
      }
   }

   public String toStringEventID(int pid, int eid) {
      switch (pid) {
         case PID_00_ANY:
            return "Any";
         case PID_01_FRAMEWORK:
            switch (eid) {
               case PID_01_FRAMEWORK_0_ANY:
                  return "Any";
               case PID_01_FRAMEWORK_1_CTX_CREATED:
                  return "CtxCreated";
               case PID_01_FRAMEWORK_2_LANGUAGE_CHANGED:
                  return "LanguageChanged";
               default:
                  return "UnknownEID" + eid;
            }
         case PID_02_HOST:
            switch (eid) {
               case PID_02_HOST_0_ANY:
                  return "Any";
               default:
                  return "UnknownEID" + eid;
            }
         case PID_03_MEMORY:
            switch (eid) {
               case PID_03_MEMORY_0_ANY:
                  return "Any";
               case PID_03_MEMORY_1_OUT_OF_MEMORY_GC:
                  return "OutOfMemoryGC";
               case PID_03_MEMORY_2_USER_REQUESTED_GC:
                  return "UserRequestedGC";
               case PID_03_MEMORY_3_OBJECT_DESTROY:
                  return "ObjectDestroy";
               default:
                  return "UnknownEID" + eid;
            }
         case PID_04_LIFE:
            switch (eid) {
               case PID_04_LIFE_0_ANY:
                  return "Any";
               case PID_04_LIFE_1_STARTED:
                  return "Started";
               case PID_04_LIFE_2_PAUSED:
                  return "Paused";
               case PID_04_LIFE_3_RESUMED:
                  return "Resumed";
               case PID_04_LIFE_4_STOPPED:
                  return "Stopped";
               case PID_04_LIFE_5_DESTROYED:
                  return "Destroyed";
               default:
                  return "UnknownEID" + eid;
            }
         case PID_05_THREAD:
            switch (eid) {
               case PID_05_THREAD_0_ANY:
                  return "Any";
               case PID_05_THREAD_1_PULSE_ON:
                  return "PulseOn";
               case PID_05_THREAD_2_PULSE_OFF:
                  return "PulseOff";
               default:
                  return "UnknownEID" + eid;
            }
         default:
            return null;
      }
   }

   public void toStringFlagSetOn(int flag, boolean b, Dctx dctx) {
      //we don't do anything by default
   }

   public DIDManager toStringGetDIDManager() {
      return didManager;
   }

   public ILogConfigurator toStringGetLogConfigurator() {
      return toStringLogConfigurator;
   }

   public int toStringGetToStringFlags() {
      return toStringFlags;
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
      dc.appendVarWithSpace("name", name);
      dc.appendVarWithSpace("ctxid", getCtxID());
      dc.appendVarWithSpace("RegistrationID", getRegistrationID());
      dc.appendVarWithSpace("encoding", encoding);
   }

   public String toStringProducerID(int pid) {
      switch (pid) {
         case PID_00_ANY:
            return "Any";
         case PID_01_FRAMEWORK:
            return "Framework";
         case PID_02_HOST:
            return "Host";
         case PID_03_MEMORY:
            return "Memory";
         case PID_04_LIFE:
            return "Life";
         case PID_05_THREAD:
            return "Thread";
         default:
            return null;
      }
   }

   public void toStringSetDLog(IDLog log) {
      if (log != null) {
         dlog = log;
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

   public String toStringStaticID(int staticID) {
      switch (staticID) {
         case IStaticIDs.SID_STRINGS:
            return "Strings";
         case IStaticIDs.SID_EVENTS:
            return "Events";
         case IStaticIDs.SID_DIDS:
            return "DIDs";
         default:
            return null;
      }
   }

   //#enddebug

}
