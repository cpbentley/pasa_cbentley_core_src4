/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

import java.io.OutputStream;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.IPrefs;
import pasa.cbentley.core.src4.io.BADataIS;
import pasa.cbentley.core.src4.structs.IntToStrings;

/**
 * Wrapper to log calls the Jascal preferences
 * <br>
 * Mainly used for debug purposes.
 * 
 * @author Charles Bentley
 *
 */
public class PreferencesSpyLogger implements IPrefs {

   private IPrefs       prefs;

   protected final UCtx uc;

   public PreferencesSpyLogger(UCtx uc, IPrefs prefs) {
      this.uc = uc;
      this.prefs = prefs;

      //#debug
      toDLog().pFlow("", this, PreferencesSpyLogger.class, "constructor", LVL_05_FINE, true);
   }

   public void clear() {
      //#debug
      toDLog().pFlow("", this, PreferencesSpyLogger.class, "clear", LVL_05_FINE, true);

      prefs.clear();
   }

   public void export(OutputStream os) {
      //#debug
      toDLog().pFlow("", this, PreferencesSpyLogger.class, "export", LVL_05_FINE, true);
      prefs.export(os);
   }

   public String get(String key, String def) {
      String value = prefs.get(key, def);
      //#debug
      toDLog().pFlow("returns " + value + " for key=" + key + " def=" + def, this, PreferencesSpyLogger.class, "get", ITechLvl.LVL_04_FINER, true);
      return value;
   }

   public boolean getBoolean(String key, boolean def) {
      boolean value = prefs.getBoolean(key, def);
      //#debug
      toDLog().pFlow("returns " + value + " for key=" + key + " def=" + def, this, PreferencesSpyLogger.class, "getBoolean", ITechLvl.LVL_04_FINER, true);
      return value;
   }

   public double getDouble(String key, double d) {
      double value = prefs.getDouble(key, d);
      //#debug
      toDLog().pFlow("returns " + value + " for key=" + key + " value=" + d, this, PreferencesSpyLogger.class, "getDouble", ITechLvl.LVL_04_FINER, true);
      return value;
   }

   public int getInt(String key, int def) {
      int value = prefs.getInt(key, def);
      //#debug
      toDLog().pFlow("returns " + value + " for key=" + key + " def=" + def, this, PreferencesSpyLogger.class, "getInt", ITechLvl.LVL_04_FINER, true);
      return value;
   }

   public IntToStrings getKeys() {
      return prefs.getKeys();
   }

   public String[] getStrings(String key, char separator) {
      String[] values = prefs.getStrings(key, separator);
      //#debug
      toDLog().pFlow("returns " + uc.getStrU().getString(values, separator) + " for key=" + key + " separator=" + separator, this, PreferencesSpyLogger.class, "get", ITechLvl.LVL_04_FINER, true);
      return values;
   }

   public void importPrefs(BADataIS dis) {
      //#debug
      toDLog().pFlow("", this, PreferencesSpyLogger.class, "importPrefs", LVL_05_FINE, true);
      prefs.importPrefs(dis);
   }

   public void put(String key, String value) {
      //#debug
      toDLog().pFlow("key=" + key + " value=" + value, this, PreferencesSpyLogger.class, "put", ITechLvl.LVL_04_FINER, true);
      prefs.put(key, value);
   }

   public void put(String key, String[] strs, char separator) {
      //#debug
      toDLog().pFlow("key=" + key + " strs=" + uc.getStrU().getString(strs, separator) + " separator=" + separator, this, PreferencesSpyLogger.class, "put", ITechLvl.LVL_05_FINE, true);
      prefs.put(key, strs, separator);
   }

   public void putBoolean(String key, boolean value) {
      //#debug
      toDLog().pFlow("key=" + key + " value=" + value, this, PreferencesSpyLogger.class, "putBoolean", ITechLvl.LVL_05_FINE, true);
      prefs.putBoolean(key, value);
   }

   public void putDouble(String key, double value) {
      //#debug
      toDLog().pFlow("key=" + key + " value=" + value, this, PreferencesSpyLogger.class, "putDouble", ITechLvl.LVL_04_FINER, true);
      prefs.putDouble(key, value);
   }

   public void putInt(String key, int value) {
      //#debug
      toDLog().pFlow("key=" + key + " value=" + value, this, PreferencesSpyLogger.class, "putInt", ITechLvl.LVL_05_FINE, true);
      prefs.putInt(key, value);
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "JascalPref");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "JascalPref");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
