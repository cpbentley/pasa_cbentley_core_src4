/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;

/**
 * @author Charles Bentley
 *
 */
public abstract class RootDLogger extends ObjectU implements IDLog {

   private ILogEntryAppender[] appenders;

   /**
    * Count will increment and then reset to 0.
    */
   private long                count;

   private String              name;

   /**
    * By default create a default {@link SystemOutAppender}
    * @param uc
    */
   public RootDLogger(UCtx uc) {
      super(uc);
      appenders = new ILogEntryAppender[] { new SystemOutAppender(uc) };
   }

   /**
    * Thread safe
    * @param appender
    */
   public void addAppender(ILogEntryAppender appender) {
      ILogEntryAppender[] nappenders = new ILogEntryAppender[appenders.length + 1];
      uc.getAU().copy1stTo2nd(appenders, nappenders);
      appenders = nappenders;
      appenders[appenders.length - 1] = appender;
   }

   public ILogEntryAppender getAppender(int index) {
      return appenders[index];
   }

   public boolean getTag(int tag) {
      ILogEntryAppender ap = getAppender(0);
      return ap.getConfig().hasFlagTag(tag);
   }

   public boolean toggleTag(int tag) {
      ILogEntryAppender ap = getAppender(0);
      boolean v = ap.getConfig().hasFlagTag(tag);
      ap.getConfig().setFlagTag(tag, !v);
      return !v;
   }

   /**
    * Array of non null appenders
    * @return
    */
   private ILogEntryAppender[] getAppenders() {
      return appenders;
   }

   public long getCount() {
      return count;
   }

   public ILogEntryAppender getDefault() {
      return appenders[0];
   }

   public int getLevelDefault() {
      return ITechLvl.LVL_08_INFO;
   }

   public String getName() {
      return name;
   }

   /**
    * This is used for quick debugging without having to change configuration of the logger.
    * <br>
    * The call should always be changed back to something else
    * @param msg
    * @param str
    * @param c
    * @param method
    */
   public void pAlways(String msg, IStringable str, Class c, String method) {
      ptPrint(msg, str, c, method, ITechTags.STRING_01_ALWAYS, ITechTags.FLAG_01_PRINT_ALWAYS, getLevelDefault(), false);
   }

   public void pAlways(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      ptPrint(msg, str, c, method, ITechTags.STRING_01_ALWAYS, ITechTags.FLAG_01_PRINT_ALWAYS, lvl, oneLine);
   }

   /**
    * 
    * @param msg
    * @param str
    * @param c
    * @param method
    * @param m
    * @param flag
    */
   public void ptPrint(String msg, IStringable str, Class c, String method, String m, int flag) {
      ptPrint(msg, str, c, method, m, flag, getLevelDefault(), false);
   }

   /**
    * 
    * @param msg
    * @param stringable
    * @param c
    * @param method
    * @param tagString
    * @param tagID
    */
   protected void ptPrint(String msg, IStringable stringable, Class c, String method, String tagString, int tagID, int lvl, boolean oneLine) {
      int devFlags = 0;
      if (oneLine) {
         devFlags = ITechConfig.FORMAT_FLAG_02_1LINE;
      }
      this.ptPrint(msg, stringable, c, method, tagString, tagID, lvl, devFlags);
   }

   protected void ptPrintBig(String msg, IStringable stringable, Class c, String method, String tagString, int tagID, int lvl) {
      this.ptPrint(msg, stringable, c, method, tagString, tagID, lvl, ITechDev.DEV_6_BIG);
   }

   protected synchronized void ptPrint(String msg, IStringable stringable, Class c, String method, String tagString, int tagID, int lvl, int flags) {
      //thread separation 
      Thread t = Thread.currentThread();
      count++;
      ILogEntryAppender[] appenders = getAppenders();
      for (int i = 0; i < appenders.length; i++) {
         DLogEntry entry = new DLogEntry();
         entry.setNameOwner(name);
         entry.setCount(count);
         entry.setMethod(method);
         entry.setClassL(c);

         if (stringable != null) {
            entry.setClassStringable(stringable.getClass());
         }
         entry.setMsg(msg);
         entry.setTagString(tagString);
         entry.setTagID(tagID);
         entry.setStringable(stringable);
         entry.setDevFlags(flags);
         entry.setLevel(lvl);
         appenders[i].processLogEntry(entry);
      }
   }

   public void setName(String name) {
      this.name = name;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, RootDLogger.class, 144);
      toStringPrivate(dc);
      super.toString(dc.sup());
      dc.nlLvlArray("Appenders", appenders);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, RootDLogger.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("count", count);
   }

   //#enddebug

}
