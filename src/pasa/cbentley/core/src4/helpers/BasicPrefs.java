package pasa.cbentley.core.src4.helpers;

import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.IPrefs;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.structs.IntToStrings;

/**
 * src4 {@link Hashtable} based implementation of {@link IPrefs}
 *  
 * @author Charles Bentley
 *
 */
public class BasicPrefs implements IPrefs {

   protected final Hashtable ht;

   protected final UCtx      uc;

   public BasicPrefs(UCtx uc) {
      this.uc = uc;
      ht = new Hashtable();
   }

   public void clear() {
      ht.clear();
   }

   public void export(OutputStream os) {
      throw new RuntimeException();
   }

   public String get(String key, String def) {
      nullCheckKey(key);
      Object objectValue = ht.get(key);
      String returnValue = def;
      if (objectValue != null) {
         if (objectValue instanceof String) {
            returnValue = (String) objectValue;
         } else {
            returnValue = objectValue.toString();
         }
      }
      return returnValue;
   }

   public boolean getBoolean(String key, boolean def) {
      nullCheckKey(key);
      Object objectValue = ht.get(key);
      boolean returnValue = def;
      if (objectValue != null && objectValue instanceof Boolean) {
         returnValue = ((Boolean) objectValue).booleanValue();
      }
      return returnValue;
   }

   public double getDouble(String key, double def) {
      nullCheckKey(key);
      Object objectValue = ht.get(key);
      double returnValue = def;
      if (objectValue != null && objectValue instanceof Double) {
         returnValue = ((Double) objectValue).doubleValue();
      }
      return returnValue;
   }

   public int getInt(String key, int def) {
      nullCheckKey(key);
      Object objectValue = ht.get(key);
      int returnValue = def;
      if (objectValue != null && objectValue instanceof Integer) {
         returnValue = ((Integer) objectValue).intValue();
      }
      return returnValue;
   }

   public IntToStrings getKeys() {
      String[] keys = new String[ht.size()];
      Enumeration keysEnum = ht.keys();
      int count = 0;
      while(keysEnum.hasMoreElements()) {
         keys[count] = (String)keysEnum.nextElement();
         count++;
      }
      return new IntToStrings(uc, keys);
   }

   public String[] getStrings(String key, char separator) {
      nullCheckKey(key);
      String root1 = this.get(key, "");
      if (root1.equals("")) {
         return new String[0];
      } else {
         String splitter = String.valueOf(separator);
         String[] data = uc.getStrU().getSplitArray(root1, splitter);
         return data;
      }
   }

   private void nullCheckKey(String key) {
      if (key == null) {
         throw new NullPointerException();
      }
   }

   public void put(String key, String value) {
      nullCheckKey(key);
      nullCheckKey(value);

      ht.put(key, value);
   }

   public void put(String key, String[] strs, char separator) {
      nullCheckKey(key);
      StringBBuilder sb = new StringBBuilder(uc);
      for (int i = 0; i < strs.length; i++) {
         if (i != 0) {
            sb.append(separator);
         }
         sb.append(strs[i]);

      }
      ht.put(key, sb.toString());
   }

   public void putBoolean(String key, boolean value) {
      nullCheckKey(key);
      ht.put(key, new Boolean(value));
   }

   public void putDouble(String key, double value) {
      nullCheckKey(key);
      ht.put(key, new Double(value));
   }

   public void putInt(String key, int value) {
      nullCheckKey(key);
      ht.put(key, new Integer(value));
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BasicPrefs");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BasicPrefs");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
