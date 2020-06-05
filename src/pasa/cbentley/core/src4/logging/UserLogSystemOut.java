/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.UCtx;

public class UserLogSystemOut implements IUserLog {

   private UCtx uc;

   public UserLogSystemOut(UCtx uc) {
      this.uc = uc;
   }

   public void consoleLog(String str) {
      System.out.println(str);
   }

   public void consoleLogDate(String str) {
      System.out.println(str);
   }

   public void consoleLogDateGreen(String str) {
      System.out.println(str);
   }

   public void consoleLogDateRed(String str) {
      System.err.println(str);
   }

   public void consoleLogError(String str) {
      System.err.println(str);
   }

   public void consoleLogGreen(String str) {
      System.out.println(str);
   }

   public void processOld(IUserLog log) {

   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "UserLogSystemOut");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "UserLogSystemOut");
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug

}
