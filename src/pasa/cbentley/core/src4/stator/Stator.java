/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.stator;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.BasicPrefs;
import pasa.cbentley.core.src4.interfaces.IPrefs;
import pasa.cbentley.core.src4.io.BADataIS;
import pasa.cbentley.core.src4.io.BADataOS;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.IntToInts;
import pasa.cbentley.core.src4.structs.IntToObjects;

public class Stator implements IStringable {

   private IPrefs         prefs;

   protected final UCtx   uc;

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

   private BADataOS       writer;

   private BADataIS       reader;

   private byte[]         data;

   public Stator(UCtx uc) {
      this.uc = uc;
   }

   public long getTimestamp() {
      return timestamp;
   }

   public void setTimestamp(long timestamp) {
      this.timestamp = timestamp;
   }

   public boolean hasData() {
      return getData() != null;
   }

   public BADataIS getDataReader() {
      if (reader == null) {
         reader = uc.createNewBADataIS(getData());
      }
      return reader;
   }

   public BADataOS getDataWriter() {
      if (writer == null) {
         writer = uc.createNewBADataOS();
      }
      return writer;
   }

   /**
    * Add key-value data to this {@link Stator}
    * @return
    */
   public IPrefs getKeyValuePairs() {
      if (prefs == null) {
         prefs = new BasicPrefs(uc);
      }
      return prefs;
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

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "State");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   public byte[] getData() {
      return data;
   }

   public void setData(byte[] data) {
      this.data = data;
   }

   //#enddebug

}
