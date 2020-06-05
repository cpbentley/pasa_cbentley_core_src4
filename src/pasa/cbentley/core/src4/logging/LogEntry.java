/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.UCtx;

public class LogEntry implements IStringable {
   
   //#mdebug
   
   Class   c;

   int     flag;

   boolean is1Line = false;

   String  method;

   String  msg;

   String  stringable;

   String  stringable1Line;

   private final UCtx uc;

    int lvl;

   public LogEntry(UCtx uc, String msg, IStringable d, Class c, String m, int flag) {
      this(uc, msg, d != null ? d.toString() : null, d != null ? d.toString1Line() : null, c, m, flag, false, ITechLvl.LVL_05_FINE);
   }

   public LogEntry(UCtx uc, String msg, IStringable d, Class c, String m, int flag, boolean line1) {
      this(uc, msg, d != null ? d.toString() : null, d != null ? d.toString1Line() : null, c, m, flag, line1, ITechLvl.LVL_05_FINE);
   }

   public LogEntry(UCtx uc, String msg, String s, String s1, Class c, String m, int flag, boolean line1, int lvl) {
      this.uc = uc;
      this.msg = msg;
      this.c = c;
      this.method = m;
      this.flag = flag;
      this.stringable = s;
      this.stringable1Line = s1;
      this.is1Line = line1;
      this.lvl = lvl;
   }

   public void toString(Dctx dc) {
      dc.append(stringable);
   }

   public String toString(String nl) {
      return stringable;
   }

   public String toString1Line() {
      return stringable1Line;
   }

   public void toString1Line(Dctx dc) {
      dc.append(stringable1Line);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }
   
   //#enddebug
}