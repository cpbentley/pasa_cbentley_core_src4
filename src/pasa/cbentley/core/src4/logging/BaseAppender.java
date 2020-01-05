package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.UCtx;

public abstract class BaseAppender implements ILogEntryAppender {

   //#mdebug
   protected IDLogConfig config;

   protected UCtx uc;

   public BaseAppender(UCtx uc) {
      this.uc = uc;
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
   //#enddebug
   
}
