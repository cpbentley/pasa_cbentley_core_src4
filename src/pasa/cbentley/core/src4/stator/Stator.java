/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.stator;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.BasicPrefs;
import pasa.cbentley.core.src4.interfaces.IPrefsReader;
import pasa.cbentley.core.src4.interfaces.IPrefsWriter;
import pasa.cbentley.core.src4.io.BAByteIS;
import pasa.cbentley.core.src4.io.BADataIS;
import pasa.cbentley.core.src4.io.BADataOS;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.IntToInts;
import pasa.cbentley.core.src4.structs.IntToObjects;

/**
 * Controls the reading and writing of application state.
 * 
 * <li> {@link StatorWriter} to write
 * <li> {@link StatorReader} to read.
 * 
 * A single stator is used so that object references are not duplicated. Writers share the same {@link Stator}
 * 
 * @author Charles Bentley
 *
 */
public class Stator extends ObjectU implements IStringable, ITechStator {

   private static final int MODE_0_ANY         = 0;

   private static final int MODE_1_ACTIVE_ONLY = 1;

   private StatorReader     activeReader;

   private StatorWriter     activeWriter;

   private boolean          isFailed;

   private int              mode;

   private BasicPrefs       prefs;

   private StatorReader[]   readers;

   /**
    * Holds references of {@link IStatorable} 
    */
   protected IntToObjects   references;

   /**
    * 
    */
   protected IntToInts      referencesPointers;

   /**
    * When it was created
    */
   private long             timestamp;

   private StatorWriter[]   writers;

   public Stator(UCtx uc) {
      super(uc);
      int numTypes = getNumberOfTypes();
      readers = new StatorReader[numTypes];
      writers = new StatorWriter[numTypes];
   }

   public void checkTypeEx(int type) {
      if (type < 0 || type > TYPE_NUM) {
         throw new IllegalArgumentException("type=" + type);
      }
   }

   /**
    * Override when using more specalized Stator and reader.
    * @param type
    * @return
    */
   protected StatorReader createReader(int type) {
      return new StatorReader(this, type);
   }

   public StatorWriter createWriter(int type) {
      return new StatorWriter(this, type);
   }

   public StatorReader getActiveReader() {
      return activeReader;
   }

   public StatorWriter getActiveWriter() {
      if (activeWriter == null) {
         //default writer is
         getWriter(TYPE_0_MASTER);
      }
      return activeWriter;
   }

   public void setActiveReader(StatorReader reader) {
      if (reader.getStator() != this) {
         throw new IllegalArgumentException();
      }
      this.activeReader = reader;
   }

   public void setActiveWriter(StatorWriter sw) {
      if (sw.getStator() != this) {
         throw new IllegalArgumentException();
      }
      this.activeWriter = sw;
   }

   private BasicPrefs getKeyValuePairs() {
      if (prefs == null) {
         prefs = new BasicPrefs(uc);
      }
      return prefs;
   }

   public int getNumberOfTypes() {
      return ITechStator.TYPE_NUM;
   }

   public IPrefsReader getPrefsReader() {
      return getKeyValuePairs();
   }

   public IPrefsWriter getPrefsWriter() {
      return getKeyValuePairs();
   }

   /**
    * A Reader only exists if it has data from an import.
    * @param type
    * @return
    */
   public StatorReader getReader(int type) {
      activeReader = readers[type];
      return readers[type];
   }

   public long getTimestamp() {
      return timestamp;
   }

   public StatorWriter getWriter(int type) {
      if (writers[type] == null) {
         writers[type] = createWriter(type);
      }
      activeWriter = writers[type];
      return writers[type];
   }

   public boolean hasReader(int type) {
      return readers[type] != null;
   }

   public boolean hasWriter(int type) {
      return writers[type] != null;
   }

   /**
    * Import the objects inside the stator serialized byte array.
    * equivalent of {@link StatorWriter#serialize()}
    * @param data
    * @throws IllegalArgumentException when data does not start with MAGIC 4 bytes
    */
   public void importFrom(byte[] data) {
      BADataIS dis = uc.createNewBADataIS(data);
      if (dis.readInt() != MAGIC_WORD_STATOR) {
         throw new IllegalArgumentException();
      }

      importTed(dis);
   }

   private void importPrefs(BADataIS dis) {
      importPrefsFrom(dis);
   }

   /**
    * Replaces prefs with or Add ?
    * @param data
    */
   public void importPrefsFrom(BADataIS dis) {
      int nullByteKeys = dis.read();
      if (nullByteKeys != 0) {
         BasicPrefs bp = new BasicPrefs(uc);
         bp.importPrefs(dis);
         this.prefs = bp;
      }
   }

   public void importPrefsFrom(byte[] data) {
      BADataIS dis = uc.createNewBADataIS(data);
      if (dis.readInt() != MAGIC_WORD_PREFS) {
         throw new IllegalArgumentException();
      }
      importPrefsFrom(dis);
   }

   private void importTed(BADataIS dis) {
      boolean isContinue = dis.hasMore();
      while (isContinue) {
         int magicToken = dis.readInt();
         boolean success = switchMagic(magicToken, dis);
         isContinue = success && dis.hasMore();
      }
   }

   protected void importWriterToReader(BADataIS dis) {
      StatorReader readStatorReader = readStatorReader(dis);
      int type = readStatorReader.getType();
      readers[type] = readStatorReader;
   }

   public boolean isFailed() {
      return isFailed;
   }

   protected StatorReader readStatorReader(BADataIS dis) {
      int type = dis.readInt();
      checkTypeEx(type);
      StatorReader statorReader = createReader(type);
      statorReader.init(dis);
      return statorReader;
   }

   public byte[] serializeAll() {
      BADataOS out = uc.createNewBADataOS();
      out.writeInt(MAGIC_WORD_STATOR); //magic

      serializePrefs(out);
      serializeWriters(out);

      serializeAllSub(out);
      return out.getByteCopy();
   }

   protected void serializeAllSub(BADataOS out) {

   }

   /**
    * Provides byte array snapshot of the current data stored in this {@link StatorWriter}
    * 
    * Serialize everything stored in this stator to byte array.
    * 
    * this will be fed to the {@link StatorReader}
    * @return
    */
   public byte[] serializePrefs() {
      BADataOS out = uc.createNewBADataOS();
      serializePrefs(out);
      return out.getByteCopy();
   }

   public void serializePrefs(BADataOS out) {
      out.writeInt(MAGIC_WORD_PREFS); //magic
      if (prefs != null) {
         out.write(1);
         prefs.export(out);
      } else {
         out.write(0);
      }
   }

   public void serializeWriters(BADataOS out) {

      for (int i = 0; i < writers.length; i++) {
         if (writers[i] != null) {
            writers[i].serializeWhole(out);
         }
      }
   }

   public void setTempActiveReaderWith(BADataIS dis) {
      StatorReader createReader = createReader(TYPE_5_TEMP_CONTAINER);
      createReader.init(dis);
      this.activeReader = createReader;
   }

   public void setActiveReaderWith(byte[] dataCtx, int offset, int length) {
      BAByteIS in = new BAByteIS(uc, dataCtx, offset, length);
      BADataIS dis = new BADataIS(uc, in);
      setTempActiveReaderWith(dis);
   }

   /**
    * All writes after this call from this Stator are made on a single
    * whatever requests are made
    * @param ctxID
    */
   public void setActiveWriteTemp() {
      this.activeWriter = createWriter(TYPE_5_TEMP_CONTAINER);
   }

   public StatorWriter setAndGetActiveWriteTemp() {
      this.activeWriter = createWriter(TYPE_5_TEMP_CONTAINER);
      return this.activeWriter;
   }

   public StatorReader setAndGetActiveReaderTemp() {

      this.activeReader = createReader(TYPE_5_TEMP_CONTAINER);
      return this.activeReader;
   }

   public void setFailedTrue() {
      isFailed = true;
   }

   public void setModeActiveOnly() {
      mode = MODE_1_ACTIVE_ONLY;
   }

   public void setModeAny() {
      mode = MODE_0_ANY;
   }
   //#enddebug

   public void setTimestamp(long timestamp) {
      this.timestamp = timestamp;
   }

   protected boolean switchMagic(int magic, BADataIS dis) {
      if (magic == MAGIC_WORD_PREFS) {
         importPrefs(dis);
         return true;
      } else if (magic == MAGIC_WORD_WRITER) {
         importWriterToReader(dis);
         return true;
      } else {
         return switchMagicSub(magic, dis);
      }
   }

   protected boolean switchMagicSub(int magic, BADataIS dis) {
      //#debug
      toDLog().pAlways("Wrong Magic " + magic, this, Stator.class, "switchMagic", LVL_05_FINE, true);
      return false;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, Stator.class, 110);
      toStringPrivate(dc);
      super.toString(dc.sup());

      dc.nlLvlArray("readers", readers);
      dc.nlLvlArray("writers", writers);

      dc.nlLvl(prefs, "basicPrefs");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, Stator.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

}
