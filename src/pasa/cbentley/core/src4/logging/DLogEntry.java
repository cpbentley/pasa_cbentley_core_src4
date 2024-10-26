/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.ToStringStaticUc;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.utils.BitUtils;

/**
 * Represents a log statement written in the code.
 * 
 * It is filled by the {@link BaseDLogger} with relevant data.
 * 
 * The {@link IStringable} is a reference to the object. As such it cannot be serialized
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
public class DLogEntry extends ObjectU implements ITechLvl {

   private Class       classL;

   private Class       classStringable;

   /**
    * ID counter of log entry. usually incrementing and set by logger
    */
   private long        count;

   private int         flagsDev;

   private int         lvl;

   private String      method;

   private String      msg;

   private String      name;

   private String      string1Line;

   private IStringable stringable;

   private String      stringFull;

   private int         tagID;

   /**
    * Tag string, for the Log message.
    * <li> Event
    * <li> init
    * <li> ...
    */
   private String      tagString;

   private String      threadName;

   private Throwable   throwable;

   /**
    * Time of the log.. not mandatory.
    */
   private long        timeStamp;

   public DLogEntry(UCtx uc) {
      super(uc);
      lvl = LVL_05_FINE;
   }

   /**
    * Compute data for the config.
    * @param config
    */
   public DLogEntryOfConfig computeDLogEntryOfConfig(IDLogConfig config) {
      DLogEntryOfConfig ec = config.getEntryConfig(this);
      //check if this log entry should record the thread name
      if (threadName == null && ec.hasFormatFlag(ITechDLogConfig.FORMAT_FLAG_04_THREAD)) {
         fillThreadName();
      }
      if (timeStamp == 0 && ec.hasFormatFlag(ITechDLogConfig.FORMAT_FLAG_05_TIMESTAMP)) {
         timeStamp = System.currentTimeMillis();
      }
      return ec;
   }

   public void fillThreadName() {
      if (threadName == null) {
         threadName = Thread.currentThread().getName();
      }
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
    * 
    * @return
    */
   public Class getClassL() {
      return classL;
   }

   /**
    * The class being toStringed in the print statement
    * @return
    */
   public Class getClassStringable() {
      return classStringable;
   }

   public long getCount() {
      return count;
   }

   public int getLevel() {
      return lvl;
   }

   public String getMethod() {
      return method;
   }

   public String getMsg() {
      return msg;
   }

   public String getNameOwner() {
      return name;
   }

   public String getString1Line() {
      if (string1Line == null) {
         if (stringable == null) {
            string1Line = "";
         } else {
            //#debug
            string1Line = stringable.toString1Line();
         }
      }
      return string1Line;
   }

   public IStringable getStringable() {
      return stringable;
   }

   public String getStringFull() {
      if (stringFull == null) {
         if (stringable == null) {
            stringFull = "";
         } else {
            //#debug
            stringFull = stringable.toString();
         }
      }
      return stringFull;
   }

   public int getTagID() {
      return tagID;
   }

   public String getTagString() {
      return tagString;
   }

   public String getThreadName() {
      return threadName;
   }

   /**
    * Get the {@link Throwable} if any associated with this 
    * @return
    */
   public Throwable getThrowable() {
      return throwable;
   }

   public LogEntryType getType() {
      return new LogEntryType();
   }

   /**
    * Has the dev set the flag in its call of the method
    * @param flag
    * @return
    */
   public boolean hasDevFlag(int flag) {
      return BitUtils.hasFlag(flagsDev, flag);
   }

   public void setClassL(Class c) {
      this.classL = c;
   }

   public void setClassStringable(Class c) {
      this.classStringable = c;
   }

   public void setCount(long count) {
      this.count = count;
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

   public void setLevel(int lvl) {
      this.lvl = lvl;
   }

   public void setMethod(String method) {
      this.method = method;
   }

   public void setMsg(String msg) {
      this.msg = msg;
   }

   public void setNameOwner(String name) {
      this.name = name;
   }

   public void setStringable(IStringable stringable) {
      this.stringable = stringable;
   }

   public void setTagID(int tagID) {
      this.tagID = tagID;
   }

   public void setTagString(String tagString) {
      this.tagString = tagString;
   }

   public void setThrowable(Throwable throwable) {
      this.throwable = throwable;
   }
   
   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, DLogEntry.class, toStringGetLine(265));
      toStringPrivate(dc);
      super.toString(dc.sup());
      
      
      dc.appendVarWithSpace("lvl", ToStringStaticUc.toStringLogLevel(lvl));
      dc.appendVarWithSpace("owner", name);
      
      
      dc.appendVarWithNewLine("threadName", threadName);
      dc.appendVarWithNewLine("msg", msg);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, DLogEntry.class, toStringGetLine(265));
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("tagString", tagString);
   }
   //#enddebug
   


}
