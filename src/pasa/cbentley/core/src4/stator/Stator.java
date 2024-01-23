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
 * 
 * @author Charles Bentley
 *
 */
public class Stator extends ObjectU implements IStringable, ITechStator {


   private BasicPrefs     prefs;

   private StatorReader[] readers;

   /**
    * Holds references of {@link IStatorable} 
    */
   protected IntToObjects references;

   /**
    * 
    */
   protected IntToInts    referencesPointers;

   /**
    * When it was created
    */
   private long           timestamp;

   private StatorWriter[] writers;

   public Stator(UCtx uc) {
      super(uc);
      int numTypes = getNumberOfTypes();
      readers = new StatorReader[numTypes];
      writers = new StatorWriter[numTypes];
   }


   /**
    * Override when using more specalized Stator and reader.
    * @param type
    * @return
    */
   protected StatorReader createReader(int type) {
      return new StatorReader(this, type);
   }

   protected StatorWriter createWriter(int type) {
      return new StatorWriter(this, type);
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
      return readers[type];
   }

   public long getTimestamp() {
      return timestamp;
   }

   public boolean hasReader(int type) {
      return readers[type] != null;
   }

   public boolean hasWriter(int type) {
      return writers[type] != null;
   }

   public StatorWriter getWriter(int type) {
      if (writers[type] == null) {
         writers[type] = createWriter(type);
      }
      return writers[type];
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

   public void checkTypeEx(int type) {
      if (type < 0 || type > TYPE_NUM) {
         throw new IllegalArgumentException("type=" + type);
      }
   }

   protected StatorReader readStatorReader(BADataIS dis) {
      int type = dis.readInt();
      checkTypeEx(type);
      StatorReader statorReader = createReader(type);
      statorReader.init(dis);
      return statorReader;
   }

   protected void importWriterToReader(BADataIS dis) {
      StatorReader readStatorReader = readStatorReader(dis);
      int type = readStatorReader.getType();
      readers[type] = readStatorReader;
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

   private void importTed(BADataIS dis) {
      boolean isContinue = dis.hasMore();
      while (isContinue) {
         int magicToken = dis.readInt();
         boolean success = switchMagic(magicToken, dis);
         isContinue = success && dis.hasMore();
      }
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
            writers[i].serialize(out);
         }
      }
   }

   public void setTimestamp(long timestamp) {
      this.timestamp = timestamp;
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

   //#enddebug

}
