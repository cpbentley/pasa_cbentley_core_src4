package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.UCtx;

public abstract class BaseAppender implements ILogEntryAppender {

   //#mdebug
   private IConfig config;

   protected UCtx uc;

   public BaseAppender(UCtx uc) {
      this.uc = uc;
      config = new Config(uc); //defautl config
   }

   public IConfig getConfig() {
      return config;
   }

   public void setConfig(IConfig c) {
      if (c != null) {
         config = c;
      }
   }
   //#enddebug
   
}
