package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.utils.BitUtils;

/**
 * Timing of the Log
 * 
 * <li> message
 * <li> {@link IStringable}
 * <li> Class
 * <li> method in which the log was made
 * <li>
 * <li> Tag ID in the hierarchy and its human readable String. possibly localized
 * <li> Time stamp
 * <li> Tread in which the {@link DLogEntry} was made. Optional
 * 
 * @author Charles Bentley
 *
 */
public class DLogEntry implements ITechLvl {
   //#mdebug
   private String      msg;

   private IStringable stringable;

   private String      string1Line;

   private String      stringFull;

   private Class       classL;

   private String      method;

   private int         tagID;

   /**
    * Tag string, for the Log message.
    * <li> Event
    * <li> init
    * <li> ...
    */
   private String      tagString;

   /**
    * Time of the log.. not mandatory.
    */
   private long        timeStamp;

   private String      threadName;

   private Throwable   throwable;

   /**
    * ID counter of log entry. usually incrementing and set by logger
    */
   private long        count;

   
   private int         lvl;

   private int         flagsDev;

   public DLogEntry() {
      lvl = LVL_05_FINE;
   }

   public void fillThreadName() {
      if (threadName == null) {
         threadName = Thread.currentThread().getName();
      }
   }

   public String getThreadName() {
      return threadName;
   }

   public String getMsg() {
      return msg;
   }

   public void setMsg(String msg) {
      this.msg = msg;
   }

   public LogEntryType getType() {
      return new LogEntryType();
   }

   public Class getClassL() {
      return classL;
   }

   public void setClassL(Class c) {
      this.classL = c;
   }

   public String getMethod() {
      return method;
   }

   public void setMethod(String method) {
      this.method = method;
   }

   public int getTagID() {
      return tagID;
   }

   public int getLevel() {
      return lvl;
   }

   public void setLevel(int lvl) {
      this.lvl = lvl;
   }

   public void setTagID(int tagID) {
      this.tagID = tagID;
   }

   public String getTagString() {
      return tagString;
   }

   public void setTagString(String tagString) {
      this.tagString = tagString;
   }

   public long getCount() {
      return count;
   }

   public void setCount(long count) {
      this.count = count;
   }

   public IStringable getStringable() {
      return stringable;
   }

   public void setStringable(IStringable stringable) {
      this.stringable = stringable;
   }


   public String getString1Line() {
      if (string1Line == null) {
         if (stringable == null) {
            string1Line = "";
         } else {
            string1Line = stringable.toString1Line();
         }
      }
      return string1Line;
   }

   public String getStringFull() {
      if (stringFull == null) {
         if (stringable == null) {
            stringFull = "";
         } else {
            stringFull = stringable.toString();
         }
      }
      return stringFull;
   }



   /**
    * Converts all objects to String. derefence objects linked to application
    * so they will not be garbaged collected.
    */
   public void flatten() {
      stringable = null;
      throwable = null;
   }

   /**
    * Get the {@link Throwable} if any associated with this 
    * @return
    */
   public Throwable getThrowable() {
      return throwable;
   }

   public void setThrowable(Throwable throwable) {
      this.throwable = throwable;
   }

   /**
    * Compute data for the config.
    * @param config
    */
   public DLogEntryOfConfig computeDLogEntryOfConfig(IConfig config) {
      DLogEntryOfConfig ec = config.getEntryConfig(this);
      //check if this log entry should record the thread name
      if (threadName == null && ec.hasConfigFlag(ITechConfig.CONFIG_FLAG_04_SHOW_THREAD)) {
         fillThreadName();
      }
      if (timeStamp == 0 && ec.hasConfigFlag(ITechConfig.CONFIG_FLAG_05_TIMESTAMP)) {
         timeStamp = System.currentTimeMillis();
      }
      return ec;
   }

   /**
    * Has the dev set the flag in its call of the method
    * @param flag
    * @return
    */
   public boolean hasDevFlag(int flag) {
      return BitUtils.hasFlag(flagsDev, flag);
   }

   /**
    * Set by calls such as
    * 
    * {@link IDLog#pFlow(String, IStringable, Class, String, int, int)}
    * 
    * <br>
    * @param flags
    */
   public void setDevFlags(int flags) {
      flagsDev = flags;
   }

   //#enddebug
}
