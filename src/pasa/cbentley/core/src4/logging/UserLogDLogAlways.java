package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.UCtx;

public class UserLogDLogAlways implements IUserLog {

   private UCtx uc;

   public UserLogDLogAlways(UCtx uc) {
      this.uc = uc;
   }

   public void consoleLog(String str) {
      //#debug
      uc.toDLog().pAlways(str, null, UserLogDLogAlways.class, "consoleLog", ITechLvl.LVL_05_FINE, true);
   }

   public void consoleLogDate(String str) {
      //#debug
      uc.toDLog().pAlways(str, null, UserLogDLogAlways.class, "consoleLogDate", ITechLvl.LVL_05_FINE, true);
   }

   public void consoleLogDateGreen(String str) {
      //#debug
      uc.toDLog().pAlways(str, null, UserLogDLogAlways.class, "consoleLogDateGreen", ITechLvl.LVL_05_FINE, true);
   }

   public void consoleLogDateRed(String str) {
      //#debug
      uc.toDLog().pAlways(str, null, UserLogDLogAlways.class, "consoleLogDateRed", ITechLvl.LVL_05_FINE, true);
   }

   public void consoleLogError(String str) {
      //#debug
      uc.toDLog().pAlways(str, null, UserLogDLogAlways.class, "consoleLogError", ITechLvl.LVL_05_FINE, true);
   }

   public void consoleLogGreen(String str) {
      //#debug
      uc.toDLog().pAlways(str, null, UserLogDLogAlways.class, "consoleLogGreen", ITechLvl.LVL_05_FINE, true);
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "UserLogDLogAlways");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "UserLogDLogAlways");
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug

}
