/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.ctx;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.ILogConfigurator;
import pasa.cbentley.core.src4.logging.LogConfiguratorAllFinest;

/**
 * Base core implemention class for {@link IConfig}
 * 
 * <li> {@link IConfig#isHardcoded()}
 * <li> {@link IConfig#isIgnoreSettings()}
 * 
 * @author Charles Bentley
 *
 */
public abstract class ConfigAbstract implements IConfig {

   protected boolean        isEraseSettings;

   protected boolean        isIgnoreSettings;

   protected UCtx           uc;

   private ILogConfigurator logConfigurator;

   public ConfigAbstract(UCtx uc) {
      this.uc = uc;
   }

   public ConfigAbstract() {
   }

   /**
    * When true, clean up everything upon start.
    * 
    * Erase app level settings and framework settings.
    * 
    * @return
    */
   public boolean isEraseSettings() {
      return isEraseSettings;
   }

   /**
    * Return false by default. Override with true if you want hardcoded
    */
   public boolean isHardcoded() {
      return false;
   }


   /**
    * Ignores the saved settings
    * @return
    */
   public boolean isIgnoreSettings() {
      return isIgnoreSettings;
   }

   /**
    * 
    * @param b
    */
   public void setEraseSettings(boolean b) {
      this.isEraseSettings = b;
   }

   /**
    * 
    * @param b
    */
   public void setIgnoreSettings(boolean b) {
      this.isIgnoreSettings = b;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, ConfigAbstract.class, 91);
      toStringPrivate(dc);
      dc.appendVarWithNewLine("hashcode", this.hashCode());
      dc.appendVarWithNewLine("isEraseSettings", isEraseSettings);
      dc.appendVarWithNewLine("isIgnoreSettings", isIgnoreSettings);
      dc.appendClassNameWithNewLine("logConfigurator", logConfigurator);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ConfigAbstract.class, 126);
      toStringPrivate(dc);
      dc.appendVarWithSpace("isEraseSettings", isEraseSettings);
      dc.appendVarWithSpace("isIgnoreSettings", isIgnoreSettings);
   }

   public ILogConfigurator toStringGetLogConfigurator(UCtx uc) {
      if (logConfigurator == null) {
         logConfigurator = new LogConfiguratorAllFinest();
      }
      return logConfigurator;
   }

   public void toStringSetDebugUCtx(UCtx uc) {
      //no need to set it here since it already done in constructor
      this.uc = uc;
   }

   public void toStringSetLogConfigurator(ILogConfigurator logConfigurator) {
      this.logConfigurator = logConfigurator;
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   private void toStringPrivate(Dctx dc) {
    
   }

   //#enddebug

}
