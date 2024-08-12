/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.stator;

import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.interfaces.IPrefsReader;
import pasa.cbentley.core.src4.io.BADataIS;
import pasa.cbentley.core.src4.io.BADataOS;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.core.src4.utils.BitUtils;

/**
 * Read bytes from a store and transform it into application state.
 * 
 * <p>
 * {@link IStatorFactory} is the interface through which object instances are created
 * When creating a {@link StatorReader}, application must register enough {@link IStatorFactory} instances
 * with {@link StatorReader#addFactory(IStatorFactory)} for all its classes stored
 * 
 * {@link IStatorable} is the interface
 * </p>
 * <p>
 * To write application state, use {@link StatorWriter}
 * </p>
 * 
 * @author Charles Bentley
 *
 */
public class StatorReader extends ObjectU implements IStringable, ITechStator {

   protected int        flags;

   private IntToObjects map;

   /**
    * Maximum objectID. used to prevent bugs
    */
   private int          mapMax;

   private BADataIS     reader;

   private Stator       stator;

   /**
    * When it was created
    */
   private long         timestamp;

   private int          type;

   public StatorReader(Stator stator, int type) {
      super(stator.getUC());
      this.stator = stator;
      this.type = type;
   }

   public void checkInt(int v) {
      int i = readInt();
      if (i != v) {
         throw new IllegalArgumentException(v + "!=" + i);
      }
   }

   public int getNumObjects() {
      return mapMax;
   }

   public Object getObject(int objectID) {
      if (objectID < map.getSize()) {
         return map.getObjectAtIndex(objectID);
      }
      return null;
   }

   /**
    * Add key-value data to this {@link StatorWriter}
    * @return
    */
   public IPrefsReader getPrefs() {
      return stator.getPrefsReader();
   }

   /**
    * 
    * @return
    */
   public BADataIS getReader() {
      return reader;
   }

   /**
    * The byte array being used by this {@link StatorReader}.
    * @return
    */
   public byte[] getReaderByte() {
      return this.reader.getArray();
   }

   /**
    * Current reader position. Will change when reader is read
    * @return
    */
   public int getReaderByteOffset() {
      return this.reader.getPosition();
   }

   public Stator getStator() {
      return stator;
   }

   public long getTimestamp() {
      return timestamp;
   }

   public int getType() {
      return type;
   }

   /**
    * Reader flags
    * 
    * @param flag
    * @return
    */
   public boolean hasFlag(int flag) {
      return BitUtils.hasFlag(flags, flag);
   }

   public boolean hasMore() {
      return reader.hasMore();
   }

   /**
    * One way to init it
    * called by {@link Stator}
    * 
    * Inverse of {@link StatorWriter#serializeData(BADataOS)}
    * @param reader
    */
   public void init(BADataIS dis) {
      mapMax = dis.readInt();
      map = new IntToObjects(uc, mapMax);
      int ctrlInt = dis.read();
      //now the actual reader
      if (ctrlInt == 1) {
         this.reader = dis.readIs();
      } else {
         //badly constructed
         throw new IllegalArgumentException("bad ctrl byte");
      }

      initSub(dis);
   }

   protected void initSub(BADataIS dis) {

   }

   private void manageErrorNullFactory(int ctxID, int classID, ICtx ctx) {
      //because of
      String msg = "Could not create Factory for ctxID=" + ctxID + " " + ctx.getClass().getName();
      //#debug
      toDLog().pNull(msg, null, StatorReader.class, "readObject");
      throw new IllegalArgumentException(msg);
   }

   private void manageErrorNullObject(int ctxID, int classID, ICtx ctx, IStatorFactory fac) {
      //because of
      //#debug
      toDLog().pNull("Could not create object ", this, StatorReader.class, "readObject", LVL_05_FINE, true);
      throw new IllegalArgumentException("Class ID not supported " + classID + " for " + fac.getClass());
   }

   /**
    * When null, cannot do anything
    * 
    * <p>
    * Inverse of {@link StatorWriter#writerToStatorable(IStatorable)}
    * </p>
    * @param statorable
    */
   public void readerToStatorable(IStatorable statorable) {
      int magicToken = getReader().readInt();
      if (magicToken == MAGIC_WORD_OBJECT) {
         //read object ID and put it in the map
         int objectID = getReader().readInt();
         int ctxID = getReader().readInt();
         int classID = getReader().readInt();
         map.setObject(statorable, objectID);
         statorable.stateReadFrom(this);
      } else if (magicToken == MAGIC_WORD_OBJECT_POINTER) {
         int objectID = getReader().readInt();
         //already read. .nothing
      } else if (magicToken == MAGIC_WORD_OBJECT_NULL) {
      } else {
         throw new IllegalArgumentException();
      }

   }

   public int readInt() {
      return getReader().readInt();
   }

   public Object readObject() {
      return readObject(null, null);
   }

   /**
    * Faster link with {@link ICtx}
    * @param ctx
    * @return
    */
   public Object readObject(ICtx ctx) {
      return readObject(ctx, null);
   }

   /**
    * Inverse of {@link StatorWriter#writerToStatorable(IStatorable)}
    * @param ctx
    * @param currentInstance
    * @return
    */
   public Object readObject(ICtx ctx, Object currentInstance) {
      int magicToken = reader.readInt();
      if (magicToken == MAGIC_WORD_OBJECT_NULL) {
         return null;
      } else if (magicToken == MAGIC_WORD_OBJECT_POINTER) {
         //when pointer
         int objectID = reader.readInt();
         IStatorable o = (IStatorable) map.getObjectAtIndex(objectID);
         if (o == null) {
            throw new IllegalArgumentException("Could not find objectID" + objectID + " for");
         }
         //sanity check
         if (currentInstance != null) {
            //it must be euaql
            if (currentInstance != map.getObjectAtIndex(objectID)) {
               throw new IllegalArgumentException();
            }
         }
         return o;
      } else if (magicToken == MAGIC_WORD_OBJECT) {
         //index written
         int objectID = reader.readInt();
         if (objectID >= mapMax) {
            throw new IllegalArgumentException(" objectID " + objectID + " > mapMax " + mapMax);
         }
         int ctxID = reader.readInt();
         int classID = reader.readInt();
         IStatorable o = (IStatorable) map.getObjectAtIndex(objectID);
         if (o != null) {
            return o;
         } else {
            if (currentInstance != null) {
               //no need to create it.. keep the same reference
               ((IStatorable) currentInstance).stateReadFrom(this);
               //set the index for future calls
               map.setObject(currentInstance, objectID);
               return currentInstance;
            } else {
               if (ctx == null) {
                  ctx = stator.getUC().getCtxManager().getCtx(ctxID);
                  if (ctx == null) {
                     throw new IllegalArgumentException("No Ctx for ctxID=" + ctxID);
                  }
               }
               IStatorFactory fac = ctx.getStatorFactory();
               if (fac == null) {
                  manageErrorNullFactory(ctxID, classID, ctx);
               }
               o = (IStatorable) fac.createObject(this, classID);
               if (o == null) {
                  manageErrorNullObject(ctxID, classID, ctx, fac);
               }
               //set it before reading because stateRead might need it because of a child parent link.
               map.setObject(o, objectID);

               o.stateReadFrom(this);
               return o;
            }
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   /**
    * Finds the ctx thx to the ctx id written
    * @param currentInstance
    * @return
    */
   public Object readObject(Object currentInstance) {
      return readObject(null, currentInstance);
   }

   public int readStartIndex() {
      return reader.readInt();
   }

   public void setFlag(int flag, boolean b) {
      flags = BitUtils.setFlag(flags, flag, b);
      if (flag == ITechStator.FLAG_1_FAILED) {
         if (b) {
            this.stator.setFailedTrue();
         }
      }
   }

   /**
    * Once an object has been created, the Factory sets it here so that it can easily be returned
    * if asked by further deserialization process down the road
    * @param o
    * @param objectID
    */
   public void setObject(Object o, int objectID) {
      map.setObjectAndInt(o, objectID, objectID);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, StatorReader.class, 230);
      toStringPrivate(dc);
      super.toString(dc.sup());
      dc.nlLvl(map, "map");
      dc.nlLvl(reader, "reader");
      dc.nlLvl(stator, "stator");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, StatorReader.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());

   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("type", type);
      dc.appendVarWithSpace("mapMax", mapMax);
   }

   //#enddebug

}
