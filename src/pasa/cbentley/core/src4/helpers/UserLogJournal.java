package pasa.cbentley.core.src4.helpers;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IUserLog;

/**
 * Log list of user logs to list user logs in the absence of logger
 * @author Charles Bentley
 *
 */
public class UserLogJournal implements IUserLog {

   protected final UCtx uc;

   public UserLogJournal(UCtx uc) {
      this.uc = uc;
   }

   public void consoleLog(String str) {

   }

   public void consoleLogError(String str) {

   }

   public void consoleLogGreen(String str) {

   }

   public void consoleLogDate(String str) {

   }

   public void consoleLogDateGreen(String str) {

   }

   public void consoleLogDateRed(String str) {

   }

   public void processOld(IUserLog log) {

   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "UserLogJournal");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "UserLogJournal");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug

}
