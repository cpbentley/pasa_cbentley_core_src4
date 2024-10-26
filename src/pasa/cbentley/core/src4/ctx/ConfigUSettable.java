/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.ctx;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.ILogConfigurator;
import pasa.cbentley.core.src4.logging.LogConfiguratorAllFinest;

public class ConfigUSettable implements IConfigU {

   private String           encoding;

   private boolean          isEraseSettings;

   private boolean          isEraseSettingsAll;

   private boolean          isForceExceptions;

   private boolean          isHardcoded;

   private boolean          isIgnoreSettings;

   private boolean          isIgnoreSettingsAll;

   private boolean          isStatorRead;

   private boolean          isStatorWrite;

   private ILogConfigurator logConfigurator;

   private int              toStringBytesOn1Line;

   private boolean          toStringIsUsingClassLinks;

   protected UCtx           uc;

   protected String         name;

   public ConfigUSettable() {
      encoding = "UTF-8";
      toStringBytesOn1Line = 20;
      toStringIsUsingClassLinks = true;
   }

   public String getDefaultEncoding() {
      return encoding;
   }

   public String getUCtxName() {
      return name;
   }

   public void setNameUCtx(String name) {
      this.name = name;
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

   public boolean isStatorRead() {
      return isStatorRead;
   }

   public boolean isStatorWrite() {
      return isStatorWrite;
   }

   public void setDefaultEncoding(String encoding) {
      this.encoding = encoding;
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

   public void setLogConfigurator(ILogConfigurator logConfigurator) {
      this.logConfigurator = logConfigurator;
   }

   public void setStatorRead(boolean isStatorRead) {
      this.isStatorRead = isStatorRead;
   }

   public void setStatorWrite(boolean isStatorWrite) {
      this.isStatorWrite = isStatorWrite;
   }

   public void setToStringBytesOn1Line(int toStringBytesOn1Line) {
      this.toStringBytesOn1Line = toStringBytesOn1Line;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, ConfigUSettable.class, 136);
      toStringPrivate(dc);
      dc.appendVarWithNewLine("encoding", encoding);
      dc.appendVarWithNewLine("isEraseSettings", isEraseSettings);
      dc.appendVarWithNewLine("isEraseSettingsAll", isEraseSettingsAll);
      dc.appendVarWithNewLine("isForceExceptions", isForceExceptions);

      dc.appendVarWithNewLine("isStatorRead", isStatorRead);
      dc.appendVarWithNewLine("isStatorWrite", isStatorWrite);

      dc.appendVarWithNewLine("isIgnoreSettings", isIgnoreSettings);
      dc.appendVarWithNewLine("isIgnoreSettingsAll", isIgnoreSettingsAll);

      dc.appendVarWithNewLine("isHardcoded", isHardcoded);

      dc.appendVarWithNewLine("toStringBytesOn1Line", toStringBytesOn1Line);
      dc.appendVarWithNewLine("toStringIsUsingClassLinks", toStringIsUsingClassLinks);

      dc.appendClassNameWithNewLine("logConfigurator", logConfigurator);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ConfigUSettable.class);
      toStringPrivate(dc);
   }

   public int toStringGetBytesOn1Line() {
      return toStringBytesOn1Line;
   }

   public ILogConfigurator toStringGetLogConfigurator() {
      if (logConfigurator == null) {
         logConfigurator = new LogConfiguratorAllFinest();
      }
      return logConfigurator;
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   public boolean toStringIsUsingClassLinks() {
      return toStringIsUsingClassLinks;
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toStringSetDebugUCtx(UCtx uc) {
      this.uc = uc;
   }

   public void toStringSetLogConfigurator(ILogConfigurator logConfigurator) {
      this.logConfigurator = logConfigurator;
   }

   public void ToStringSetUsingClassLinks(boolean toStringIsUsingClassLinks) {
      this.toStringIsUsingClassLinks = toStringIsUsingClassLinks;
   }
   //#enddebug
}
