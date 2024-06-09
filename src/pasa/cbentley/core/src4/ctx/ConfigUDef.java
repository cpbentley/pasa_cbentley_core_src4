/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.ctx;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.ILogConfigurator;
import pasa.cbentley.core.src4.logging.LogConfiguratorAllFinest;

/**
 * Default implementation of the {@link IConfigU} config.
 * 
 * @author Charles Bentley
 *
 */
public class ConfigUDef implements IConfigU {

   private ILogConfigurator logConfigurator;

   private UCtx             uc;

   public ConfigUDef() {
   }

   public String getDefaultEncoding() {
      return "UTF-8";
   }

   public boolean isEraseSettings() {
      return false;
   }

   public boolean isEraseSettingsAll() {
      return false;
   }

   public boolean isForceExceptions() {
      return false;
   }

   public boolean isHardcoded() {
      return true;
   }

   public boolean isIgnoreSettings() {
      return false;
   }

   public boolean isStatorWrite() {
      return true;
   }

   public boolean isStatorRead() {
      return true;
   }

   public boolean isIgnoreSettingsAll() {
      return false;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, ConfigUDef.class, 70);
      toStringPrivate(dc);

      dc.nl();
      dc.appendVarWithSpace("toStringIsUsingClassLinks", toStringIsUsingClassLinks());
      dc.appendVarWithSpace("isForceExceptions", isForceExceptions());
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ConfigUDef.class);
      toStringPrivate(dc);
   }

   public int toStringGetBytesOn1Line() {
      return 20;
   }

   public ILogConfigurator toStringGetLogConfigurator(UCtx uc) {
      if (logConfigurator == null) {
         logConfigurator = new LogConfiguratorAllFinest();
      }
      return logConfigurator;
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   public boolean toStringIsUsingClassLinks() {
      return true;
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("isIgnoreSettingsAll", isIgnoreSettingsAll());
      dc.appendVarWithSpace("isEraseSettingsAll", isEraseSettingsAll());
      dc.appendVarWithSpace("isEraseSettings", isEraseSettings());
      dc.appendVarWithSpace("isStatorRead", isStatorRead());
      dc.appendVarWithSpace("isStatorWrite", isStatorWrite());
      dc.appendVarWithSpace("isForceExceptions", isForceExceptions());
   }

   public void toStringSetDebugUCtx(UCtx uc) {
      this.uc = uc;
   }

   public void toStringSetLogConfigurator(ILogConfigurator logConfigurator) {
      this.logConfigurator = logConfigurator;
   }

   //#enddebug

}
