/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.stator;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.BasicPrefs;
import pasa.cbentley.core.src4.interfaces.IPrefsWriter;
import pasa.cbentley.core.src4.io.BADataOS;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.IntToInts;
import pasa.cbentley.core.src4.structs.IntToObjects;

/**
 * 
 * @author Charles Bentley
 *
 */
public class StatorWriter implements IStringable, ITechStator {

   private byte[]       data;

   private IPrefsWriter prefs;

   private IntToInts    table;

   /**
    * When it was created
    */
   private long         timestamp;

   protected final UCtx uc;

   /**
    * Current writer
    */
   private BADataOS     writer;

   private IntToObjects writtenObjects;

   public StatorWriter(UCtx uc) {
      this.uc = uc;
      table = new IntToInts(uc);
      writtenObjects = new IntToObjects(uc);
   }

   public byte[] getData() {
      return data;
   }

   /**
    * Current position, so continue to write
    * @return
    */
   public BADataOS getDataWriter() {
      if (writer == null) {
         writer = uc.createNewBADataOS();
      }
      return writer;
   }

   public int getNumWrittenObject() {
      return writtenObjects.getSize();
   }
   /**
    * Creates a new entry position for the id,
    * 
    * it entry already exists
    * @param id
    * @return
    */
   public BADataOS getDataWriter(int id) {
      if (writer == null) {
         writer = uc.createNewBADataOS();
      }

      return writer;
   }

   /**
    * Add key-value data to this {@link StatorWriter}
    * @return
    */
   public IPrefsWriter getKeyValuePairs() {
      if (prefs == null) {
         prefs = new BasicPrefs(uc);
      }
      return prefs;
   }

   public long getTimestamp() {
      return timestamp;
   }

   /**
    * Assign an id
    * @param canvasAppliAbstract
    * @return
    */
   public boolean isObjectNotWritten(IStatorable statorable) {
      int index = writtenObjects.findObjectRef(statorable);
      if (index == -1) {
         //create a new one
         int objectID = writtenObjects.nextempty;
         writtenObjects.add(statorable);
         getDataWriter().writeInt(objectID);
         return true;
      } else {
         getDataWriter().writeInt(index);
         return false;
      }
   }

   /**
    * Call this when the stator needs to be written to disk.
    * 
    * Serialize everything stored in this stator to byte array.
    * 
    * this will be fed to the {@link StatorReader}
    * @return
    */
   public byte[] serialize() {
      BADataOS out = uc.createNewBADataOS();
      out.writeInt(MAGIC_WORD_STATOR); //magic
      out.writeInt(writtenObjects.getSize()); //max number of objects
      if (writer != null) {
         out.write(1);
         out.writeByteArray(writer.getOut().getArrayRef(), 0, writer.getOut().getByteWrittenCount());
      } else {
         out.write(0);
      }
      if (prefs != null) {
         out.write(1);
         prefs.export(out);
      } else {
         out.write(0);
      }
      return out.getByteCopy();
   }

   public void setTimestamp(long timestamp) {
      this.timestamp = timestamp;
   }

   public void stateWriteOf(IStatorable statorable) {
      if (statorable == null) {
         getDataWriter().write(0);
      } else {
         getDataWriter().write(1);
         if (isObjectNotWritten(statorable)) {
            statorable.stateWriteTo(this);
         }
      }
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "State");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "State");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
