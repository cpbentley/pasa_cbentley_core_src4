/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.ctx;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.ILogConfigurator;
import pasa.cbentley.core.src4.logging.LogConfiguratorAllFinest;

public class ConfigUDef extends ConfigAbstract implements IConfigU {

   public ConfigUDef(UCtx uc) {
      super(uc);
      isIgnoreSettings = false;
      isEraseSettings = false;
   }

   public String getDefaultEncoding() {
      return "UTF-8";
   }

   public boolean isEraseSettingsAll() {
      return false;
   }

   public boolean isForceExceptions() {
      return false;
   }

   public boolean isIgnoreSettingsAll() {
      return false;
   }
   

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, ConfigUDef.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());

      dc.nl();
      dc.appendVarWithSpace("toStringIsUsingClassLinks", toStringIsUsingClassLinks());
      dc.appendVarWithSpace("isForceExceptions", isForceExceptions());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ConfigUDef.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   public boolean toStringIsUsingClassLinks() {
      return true;
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("isIgnoreSettingsAll", isIgnoreSettingsAll());
      dc.appendVarWithSpace("isEraseSettingsAll", isEraseSettingsAll());
   }


   //#enddebug

}
