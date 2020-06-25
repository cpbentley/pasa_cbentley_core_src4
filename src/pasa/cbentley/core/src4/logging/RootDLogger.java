/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.UCtx;

/**
 * @author Charles Bentley
 *
 */
public abstract class RootDLogger implements IDLog {
   //#mdebug

   private UCtx                uc;

   /**
    * Count will increment and then reset to 0.
    */
   private long                count;

   private ILogEntryAppender[] appenders;

   /**
    * By default create a default {@link SystemOutAppender}
    * @param uc
    */
   public RootDLogger(UCtx uc) {
      this.uc = uc;
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

   public ILogEntryAppender getDefault() {
      return appenders[0];
   }

   public int getLevelDefault() {
      return ITechLvl.LVL_08_INFO;
   }

   /**
    * Array of non null appenders
    * @return
    */
   private ILogEntryAppender[] getAppenders() {
      return appenders;
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
    * This is used for quick debugging without having to change configuration of the logger.
    * <br>
    * The call should always be changed back to something else
    * @param msg
    * @param str
    * @param c
    * @param method
    */
   public void pAlways(String msg, IStringable str, Class c, String method) {
      ptPrint(msg, str, c, method, ITechTags.STRING_ALWAYS, ITechTags.FLAG_01_PRINT_ALWAYS, getLevelDefault(), false);
   }

   public void pAlways(String msg, IStringable str, Class c, String method, int lvl, boolean oneLine) {
      ptPrint(msg, str, c, method, ITechTags.STRING_ALWAYS, ITechTags.FLAG_01_PRINT_ALWAYS, lvl, oneLine);
   }

   private String methodIndent = null;

   public synchronized void methodStart(Class c, String method, int lvl) {
      methodIndent = c.getName() + "#" + method;
   }

   public synchronized void methodEnd(Class c, String method, int lvl) {
      methodIndent = null;
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
         devFlags = ITechConfig.CONFIG_FLAG_2_1LINE;
      }
      this.ptPrint(msg, stringable, c, method, tagString, tagID, lvl, devFlags);
   }

   protected synchronized void ptPrint(String msg, IStringable stringable, Class c, String method, String tagString, int tagID, int lvl, int flags) {
      //thread separation 
      Thread t = Thread.currentThread();
      count++;
      ILogEntryAppender[] appenders = getAppenders();
      for (int i = 0; i < appenders.length; i++) {
         DLogEntry entry = new DLogEntry();
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

   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, RootDLogger.class, "@line151");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("count", count);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, RootDLogger.class);
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug

}
