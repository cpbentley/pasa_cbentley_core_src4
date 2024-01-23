/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.stator;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.interfaces.IPrefsWriter;
import pasa.cbentley.core.src4.io.BADataIS;
import pasa.cbentley.core.src4.io.BADataOS;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.IntToInts;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.core.src4.utils.BitUtils;

/**
 * 
 * For simple write methods use
 * 
 * <li> {@link StatorWriter#getWriter()} {@link BADataOS}
 * <ol>
 * <li> {@link BADataOS#writeInt(int)}
 * <li> {@link BADataOS#writeIntArrayByteLong(int[])}
 * <li> {@link BADataOS#writeIntArrayIntLong(int[])}
 * <li> {@link BADataOS#writeString(String)}
 * <li>
 * </ol>
 * <p>
 * 
 * For writing to a key-object hashtable use
 *  {@link StatorWriter#getKeyValuePairs()}. It acutally uses the single one from the Stator
 * </p>
 * Provides 
 * @author Charles Bentley
 *
 */
public class StatorWriter extends ObjectU implements IStringable, ITechStator {

   private Stator       stator;

   private IntToInts    table;

   private int          type;

   /**
    * Current writer
    */
   private BADataOS     writer;

   private IntToObjects writtenObjects;

   private int          flags;

   public StatorWriter(Stator stator, int type) {
      super(stator.getUC());
      this.stator = stator;
      this.type = type;
      table = new IntToInts(uc);
      writtenObjects = new IntToObjects(uc);
      writer = uc.createNewBADataOS();
   }

   /**
    * Current position, so continue to write
    * @return
    */
   public BADataOS getWriter() {
      return writer;
   }

   public void setFlag(int flag, boolean b) {
      flags = BitUtils.setFlag(flags, flag, b);
   }

   /**
    * Add key-value data to this {@link StatorWriter}
    * @return
    */
   public IPrefsWriter getPrefs() {
      return stator.getPrefsWriter();
   }

   public int getNumWrittenObject() {
      return writtenObjects.getSize();
   }

   public Stator getStator() {
      return stator;
   }

   public int getType() {
      return type;
   }

   /**
    * Provides byte array snapshot of the current data stored in this {@link StatorWriter}
    * 
    * Serialize everything stored in this stator to byte array.
    * 
    * this will be fed to the {@link StatorReader}
    * @return
    */
   public byte[] serialize() {
      BADataOS out = uc.createNewBADataOS();
      serialize(out);
      return out.getByteCopy();
   }

   /**
    * Inverse of {@link StatorReader#init(BADataIS)}
    * @param out
    */
   public void serialize(BADataOS out) {
      out.writeInt(MAGIC_WORD_WRITER); //magic
      out.writeInt(type); //type so unwrap can create it
      //we need to know its length
      int sizeObjects = writtenObjects.getSize();
      out.writeInt(sizeObjects); //max number of objects

      if (writer != null) {
         out.write(1);
         out.writeOS(writer);
      } else {
         out.write(0);
      }

   }

   //#mdebug

   /**
    * Called when a {@link IStatorable} asks its {@link IStatorable} children to writeselves.
    * 
    * The Parent knows which type of {@link StatorWriter} to use.
    * Preferred method to use.. it also to deal with null and to see whether that object
    * reference has already been written to this {@link StatorWriter}.
    * <br>
    * You should not use directly {@link IStatorable#stateWriteTo(StatorWriter)}
    * 
    * <p>
    * Inverse of {@link StatorReader#readerToStatorable(IStatorable)}
    * </p>
    * @param statorable
    */
   public void writerToStatorable(IStatorable statorable) {
      BADataOS dataWriter = getWriter();
      if (statorable == null) {
         dataWriter.writeInt(MAGIC_WORD_OBJECT_NULL);
      } else {
         int index = writtenObjects.findObjectRef(statorable);
         if (index == -1) {
            getWriter().writeInt(MAGIC_WORD_OBJECT);

            //create a new one
            int objectID = writtenObjects.nextempty;
            writtenObjects.add(statorable);
            getWriter().writeInt(objectID);
            int ctxID = statorable.getCtxOwner().getCtxID();
            getWriter().writeInt(ctxID);
            int classID = statorable.getStatorableClassID();
            getWriter().writeInt(classID);
            //now ask the statorable to write its object and children
            statorable.stateWriteTo(this);
         } else {
            //write object id
            getWriter().writeInt(MAGIC_WORD_OBJECT_POINTER);
            getWriter().writeInt(index);
         }
      }
   }

   public void toString(Dctx dc) {
      dc.root(this, StatorWriter.class, "@line170");
      toStringPrivate(dc);

      dc.nlLvl(writtenObjects, "writtenObjects");
      dc.nlLvl(writer, "writer");
      dc.nlLvl(stator, "stator");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, StatorWriter.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("type", type);
   }

   public void writeStartIndex(int len) {
      writer.writeInt(len);
   }

   //#enddebug

}
