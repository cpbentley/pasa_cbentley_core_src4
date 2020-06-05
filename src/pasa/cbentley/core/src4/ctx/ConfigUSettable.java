package pasa.cbentley.core.src4.ctx;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;

public class ConfigUSettable implements IConfigU {

   private boolean isEraseSettings;

   private boolean isEraseSettingsAll;

   private boolean isForceExceptions;

   private boolean isHardcoded;

   private boolean isIgnoreSettings;

   private boolean isIgnoreSettingsAll;

   private boolean toStringIsUsingClassLinks;

   protected UCtx  uc;

   private String  encoding;

   public ConfigUSettable() {
      encoding = "UTF-8";
   }

   public String getDefaultEncoding() {
      return encoding;
   }

   public void setDefaultEncoding(String encoding) {
      this.encoding = encoding;
   }

   public boolean isEraseSettings() {
      return isEraseSettings;
   }

   public boolean isEraseSettingsAll() {
      return isEraseSettingsAll;
   }

   public boolean isForceExceptions() {
      return isForceExceptions;
   }

   public boolean isHardcoded() {
      return isHardcoded;
   }

   public boolean isIgnoreSettings() {
      return isIgnoreSettings;
   }

   public boolean isIgnoreSettingsAll() {
      return isIgnoreSettingsAll;
   }

   public void setEraseSettings(boolean isEraseSettings) {
      this.isEraseSettings = isEraseSettings;
   }

   public void setEraseSettingsAll(boolean isEraseSettingsAll) {
      this.isEraseSettingsAll = isEraseSettingsAll;
   }

   public void setForceExceptions(boolean isForceExceptions) {
      this.isForceExceptions = isForceExceptions;
   }

   public void setHardcoded(boolean isHardcoded) {
      this.isHardcoded = isHardcoded;
   }

   public void setIgnoreSettings(boolean isIgnoreSettings) {
      this.isIgnoreSettings = isIgnoreSettings;
   }

   public void setIgnoreSettingsAll(boolean isIgnoreSettingsAll) {
      this.isIgnoreSettingsAll = isIgnoreSettingsAll;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, ConfigUSettable.class);
      toStringPrivate(dc);
      dc.appendVarWithSpace("isEraseSettings", isEraseSettings);
      dc.appendVarWithSpace("isEraseSettingsAll", isEraseSettingsAll);
      dc.appendVarWithSpace("isForceExceptions", isForceExceptions);
      dc.appendVarWithSpace("isHardcoded", isHardcoded);
      dc.appendVarWithSpace("isIgnoreSettings", isIgnoreSettings);
      dc.appendVarWithSpace("isIgnoreSettingsAll", isIgnoreSettingsAll);
      dc.appendVarWithSpace("toStringIsUsingClassLinks", toStringIsUsingClassLinks);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ConfigUSettable.class);
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   public boolean toStringIsUsingClassLinks() {
      return toStringIsUsingClassLinks;
   }

   //#enddebug

   private void toStringPrivate(Dctx dc) {

   }

   public void toStringSetDebugUCtx(UCtx uc) {
      this.uc = uc;
   }

   public void ToStringSetUsingClassLinks(boolean toStringIsUsingClassLinks) {
      this.toStringIsUsingClassLinks = toStringIsUsingClassLinks;
   }

}
