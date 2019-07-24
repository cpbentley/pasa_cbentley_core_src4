package pasa.cbentley.core.src4.memory;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;

/**
 * Does not provide any services
 * @author Charles Bentley
 *
 */
public class MemorySimpleCreator implements IMemory {

   private UCtx uc;

   public MemorySimpleCreator(UCtx uc) {
      this.uc = uc;
   }

   public void addMemFreeable(IMemFreeable free) {

   }

   public byte[] clone(byte[] data) {
      if (data == null) {
         return null;
      }
      byte[] ndata = new byte[data.length];
      System.arraycopy(data, 0, ndata, 0, data.length);
      return ndata;
   }

   public int[] clone(int[] ar) {
      if (ar == null) {
         return null;
      }
      int[] clone = new int[ar.length];
      System.arraycopy(ar, 0, clone, 0, ar.length);
      return clone;
   }

   public byte[] createByteArray(int size) {
      return new byte[size];
   }

   public char[] createCharArray(int len) {
      return new char[len];
   }

   /**
    * 
    */
   public char[][] createCharArrayDouble(int len) {
      return new char[len][];
   }

   public int[] createIntArray(int size) {
      return new int[size];
   }

   /**
    * Removes #addition bytes starting from position
    * 
    * [ b b b b b position r r r c c c] of length 12
    * position = 5, addition = 4
    * [ b b b b b c c c ]
    * size is size - addition
    * @param ar
    * @param addition if <= 0 same array is returned
    * @param position
    * @return
    */
   public byte[] decreaseCapacity(byte[] ar, int addition, int position) {
      if (addition <= 0)
         return ar;
      byte[] oldData = ar;
      int firstLen = position;
      int secondLen = oldData.length - firstLen - addition;
      ar = new byte[oldData.length - addition];
      //copy the first block from 0-position
      System.arraycopy(oldData, 0, ar, 0, firstLen);
      // copy from position+addition to ar.length to position+addition
      System.arraycopy(oldData, firstLen + addition, ar, firstLen, secondLen);
      return ar;
   }

   public char[] ensureCapacity(char[] ar, int size, int incr) {
      if (size < ar.length)
         return ar;

      //#debug
      toDLog().pMemory("chars.length=" + ar.length + " size=" + size + " grow=" + incr, null, MemorySimpleCreator.class, "ensureCapacity", LVL_05_FINE, true);

      char[] oldData = ar;
      ar = new char[size + incr];
      for (int i = 0; i < oldData.length; i++) {
         System.arraycopy(oldData, 0, ar, 0, oldData.length);
      }
      return ar;
   }

   public int[] ensureCapacity(int[] ar, int size) {
      return ensureCapacity(ar, size, 1);
   }

   /**
    * 
    * @param ar
    * @param size
    * @param grow growth increment
    * @return
    */
   public int[] ensureCapacity(int[] ar, int size, int grow) {
      if (ar == null) {
         return new int[size + grow];
      }
      if (size + grow < ar.length)
         return ar;

      //#debug
      toDLog().pMemory("ints.length=" + ar.length + " size=" + size + " grow=" + grow, null, MemorySimpleCreator.class, "ensureCapacity", LVL_05_FINE, true);

      int[] oldData = ar;
      ar = new int[size + grow];
      for (int i = 0; i < oldData.length; i++) {
         System.arraycopy(oldData, 0, ar, 0, oldData.length);
      }
      return ar;
   }

   public Object[] ensureCapacity(Object[] ar, int size, int grow) {
      if (ar == null) {
         //#debug
         toDLog().pMemory("Objects is null. size=" + size + " grow=" + grow, null, MemorySimpleCreator.class, "ensureCapacity", LVL_05_FINE, true);
         return new Object[size + grow];
      }
      if (size + grow < ar.length) {
         return ar;
      }
      //#debug
      toDLog().pMemory("Objects.length=" + ar.length + " size=" + size + " grow=" + grow, null, MemorySimpleCreator.class, "ensureCapacity", LVL_05_FINE, true);
      Object[] oldData = ar;
      ar = new Object[size + grow];
      for (int i = 0; i < oldData.length; i++) {
         System.arraycopy(oldData, 0, ar, 0, oldData.length);
      }
      return ar;
   }

   public String[] ensureCapacity(String[] ar, int size, int incr) {
      if (size < ar.length)
         return ar;
      String[] oldData = ar;
      ar = new String[size + incr];
      for (int i = 0; i < oldData.length; i++) {
         System.arraycopy(oldData, 0, ar, 0, oldData.length);
      }
      return ar;
   }

   /**
    * The size of each "rows" and create a byte array of newSize for the last
    * element in the master byte array
    * Data is copied at 0
    * @param ar array to grow
    * @param size the numb
    */
   public byte[] increaseCapacity(byte[] ar, int addition) {
      byte[] oldData = ar;
      ar = new byte[oldData.length + addition];
      System.arraycopy(oldData, 0, ar, 0, oldData.length);
      return ar;
   }

   public byte[] increaseCapacity(byte[] ar, int addition, int position) {
      byte[] oldData = ar;
      ar = new byte[oldData.length + addition];
      //copy the first block from 0-position
      System.arraycopy(oldData, 0, ar, 0, position);
      if (addition < 0) {
         addition = 0 - addition;
         //copy the difference minus the
         int secondLen = oldData.length - position - addition;
         System.arraycopy(oldData, position + addition, ar, position, secondLen);
      } else {
         int secondLen = oldData.length - position;
         // copy from position to position+addition
         System.arraycopy(oldData, position, ar, position + addition, secondLen);
      }
      return ar;
   }

   /**
    * new entries are nulls
    */
   public byte[][] increaseCapacity(byte[][] ar, int addition) {
      byte[][] oldData = ar;
      ar = new byte[oldData.length + addition][];
      for (int i = 0; i < oldData.length; i++) {
         ar[i] = oldData[i];
      }
      return ar;
   }

   public char[] increaseCapacity(char[] ar, int addition) {
      char[] oldData = ar;
      ar = new char[oldData.length + addition];
      System.arraycopy(oldData, 0, ar, 0, oldData.length);
      return ar;
   }

   public int[] increaseCapacity(int[] ar, int addition) {
      //#debug
      toDLog().pMemory("ints " + ar.length + " add=" + addition, null, MemorySimpleCreator.class, "increaseCapacity", LVL_05_FINE, true);

      int[] oldData = ar;
      ar = new int[oldData.length + addition];
      System.arraycopy(oldData, 0, ar, 0, oldData.length);
      return ar;
   }

   public int[] increaseCapacity(int[] ar, int addition, int position) {
      if (position >= ar.length) {
         return increaseCapacity(ar, addition);
      }
      int[] oldData = ar;
      int firstLen = position;
      int secondLen = oldData.length - firstLen;
      ar = new int[oldData.length + addition];
      //copy the first block from 0-position
      System.arraycopy(oldData, 0, ar, 0, firstLen);
      // copy from position to position+addition
      System.arraycopy(oldData, firstLen, ar, firstLen + addition, secondLen);
      return ar;
   }

   public Object[] increaseCapacity(Object[] ar, int addition) {
      //#debug
      toDLog().pMemory("Objects " + ar.length + " add=" + addition, null, MemorySimpleCreator.class, "increaseCapacity", LVL_05_FINE, true);

      Object[] oldData = ar;
      ar = new Object[oldData.length + addition];
      System.arraycopy(oldData, 0, ar, 0, oldData.length);
      return ar;
   }

   public String[] increaseCapacity(String[] ar, int addition) {
      String[] oldData = ar;
      ar = new String[oldData.length + addition];
      System.arraycopy(oldData, 0, ar, 0, oldData.length);
      return ar;
   }

   /**
    * Create empty arrays instead of null refs
    * @param ar
    * @param addition
    * @return
    */
   public int[][] increaseCapacityNonEmpty(int[][] ar, int addition, int startsize) {
      int[][] oldData = ar;
      ar = new int[oldData.length + addition][startsize];
      System.arraycopy(oldData, 0, ar, 0, oldData.length);
      return ar;
   }

   /**
    * Create a new array of size and copy array values to array
    * @param ar
    * @param size
    * @return
    */
   public int[] resizeTo(int[] ar, int size) {
      int[] oldData = ar;
      ar = new int[size];
      size = Math.min(size, oldData.length);
      System.arraycopy(oldData, 0, ar, 0, size);
      return ar;
   }

   public void softGC() {
      System.gc();
   }

   //#mdebug
   public IDLog toDLog() {
      return uc.toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "MemorySimpleCreator");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "MemorySimpleCreator");
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }
   //#enddebug
}
