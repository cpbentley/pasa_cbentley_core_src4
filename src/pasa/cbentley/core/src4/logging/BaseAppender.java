/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;

public abstract class BaseAppender extends ObjectU implements ILogEntryAppender {

   //#mdebug
   protected IDLogConfig config;

   public BaseAppender(UCtx uc) {
      super(uc);
      config = new DLogConfig(uc); //defautl config
   }

   public IDLogConfig getConfig() {
      return config;
   }

   public void setConfig(IDLogConfig c) {
      if (c != null) {
         config = c;
      }
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, BaseAppender.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
      dc.nlLvl(config, "IDLogConfig");
   }

   private void toStringPrivate(Dctx dc) {
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, BaseAppender.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

   //#enddebug

}
