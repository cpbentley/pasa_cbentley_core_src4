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

   protected boolean    isEraseSettings;

   protected boolean    isIgnoreSettings;

   protected final UCtx uc;

   public ConfigAbstract(UCtx uc) {
      this.uc = uc;
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
      dc.root(this, ConfigAbstract.class, 54);
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "ConfigAbstract");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("isEraseSettings", isEraseSettings);
      dc.appendVarWithSpace("isIgnoreSettings", isIgnoreSettings);
   }

   //#enddebug

}
