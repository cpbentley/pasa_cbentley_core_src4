/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.stator;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.BasicPrefs;
import pasa.cbentley.core.src4.interfaces.IPrefsReader;
import pasa.cbentley.core.src4.io.BADataIS;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.core.src4.utils.BitUtils;

public class StatorReader implements IStringable, ITechStator {

   /**
    * there is a table of ids at the start,
    * the prefs reader is un marshalled from the data as well.
    */
   protected byte[]       data;

   private IntToObjects   factories;

   protected int          flags;

   private IntToObjects   map;

   private int            mapMax;

   protected IPrefsReader prefs;

   private BADataIS       reader;

   /**
    * When it was created
    */
   private long           timestamp;

   protected final UCtx   uc;

   public StatorReader(UCtx uc) {
      this.uc = uc;
      factories = new IntToObjects(uc);

   }

   public void addFactory(IStatorFactory factory) {
      factories.addUnique(factory);
   }

   public Object createObject(Class type) {
      return createObject(type, null);
   }

   /**
    * Class type is implicit when creating back. The caller must know the class type
    * @param type
    * @param currentInstance
    * @return
    */
   public Object createObject(Class type, Object currentInstance) {
      int nullByte = getDataReader().read();
      if (nullByte == 0) {
         return null;
      }
      int objectID = getDataReader().readInt();
      if (objectID >= mapMax) {
         throw new IllegalArgumentException(" objectID " + objectID + " > mapMax " + mapMax);
      }
      Object o = map.getObjectAtIndex(objectID);
      if (o == null) {
         //create it in the factories
         for (int i = 0; i < factories.getSize(); i++) {
            IStatorFactory factory = (IStatorFactory) factories.getObjectAtIndex(i);
            if (factory.isTypeSupported(type)) {
               o = factory.createObject(this, type);
               map.setObject(o, objectID);
               return o;
            }
         }
      }
      //#debug
      toDLog().pNull("No factory for class " + type.getName(), this, StatorReader.class, "createObject", LVL_05_FINE, true);
      return null;
   }

   public byte[] getData() {
      return data;
   }

   /**
    * 
    * @return
    */
   public BADataIS getDataReader() {
      if (data == null) {
         throw new IllegalStateException("No state data to read");
      }
      if (reader == null) {
         reader = uc.createNewBADataIS(getData());
      }
      return reader;
   }

   /**
    * Add key-value data to this {@link StatorReader}
    * @return non null
    */
   public IPrefsReader getKeyValuePairs() {
      if (prefs == null) {
         prefs = new BasicPrefs(uc);
      }
      return prefs;
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

   public long getTimestamp() {
      return timestamp;
   }

   public boolean hasData() {
      return getData() != null;
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

   /**
    * equivalent of {@link StatorWriter#serialize()}
    * @param data
    */
   public void importFrom(byte[] data) {
      this.data = data;
      BADataIS dis = uc.createNewBADataIS(data);
      if (dis.readInt() != MAGIC_WORD_STATOR) {
         throw new IllegalArgumentException();
      }
      int numObjects = dis.readInt();
      this.mapMax = numObjects;
      map = new IntToObjects(uc, numObjects);
      int nullByte = dis.read();
      if (nullByte != 0) {
         //read reader
         byte[] readerData = dis.readByteArray();
         reader = uc.createNewBADataIS(readerData);
      }
      int nullByteKeys = dis.read();
      if (nullByteKeys != 0) {
         getKeyValuePairs().importPrefs(dis);
      }
   }

   public void setFlag(int flag, boolean b) {
      flags = BitUtils.setFlag(flags, flag, b);
   }

   public void setObject(Object o, int objectID) {
      // TODO Auto-generated method stub

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
