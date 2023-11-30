/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.ctx;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;

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

   protected final UCtx uc;

   protected boolean    isIgnoreSettings;

   protected boolean    isEraseSettings;

   public ConfigAbstract(UCtx uc) {
      this.uc = uc;
   }

   /**
    * Return false by default. Override with true if you want hardcoded
    */
   public boolean isHardcoded() {
      return false;
   }

   public void setEraseSettings(boolean b) {
      this.isEraseSettings = b;
   }

   public void setIgnoreSettings(boolean b) {
      this.isIgnoreSettings = b;
   }

   /**
    * Ignores the saved settings
    * @return
    */
   public boolean isIgnoreSettings() {
      return isIgnoreSettings;
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

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, ConfigAbstract.class, 54);
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("isEraseSettings", isEraseSettings);
      dc.appendVarWithSpace("isIgnoreSettings", isIgnoreSettings);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "ConfigAbstract");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug

}
