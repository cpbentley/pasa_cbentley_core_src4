package pasa.cbentley.core.src4.structs;

import pasa.cbentley.core.src4.ctx.UCtx;

import pasa.cbentley.core.src4.interfaces.IStrComparator;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.utils.StringUtils;

/**
 * Utility class mapping int literals to Objects, used as a collection of objects
 * <br>
 * <br>
 * {@link IntToInts}, {@link IntToStrings}, {@link IntToObjects}.
 * 
 * This class public fields because this class purpose is not to encapsulate but provide
 * merely support methods that are supposed to be pruned
 * Was decided to "test" public access.
 * If ported to src8 with generics, we would encapsulate all fields.
 * 
 * @author Charles Bentley
 *
 */
public class IntToObjects implements IStringable {

   public int[]    ints;

   public int      nextempty = 0;

   /**
    */
   public Object[] objects;

   private final UCtx    uc;

   public IntToObjects(UCtx uc) {
      this.uc = uc;
      objects = new Object[5];
      ints = new int[5];
   }

   /**
    * This sets 
    * @param uc
    * @param i
    */
   public IntToObjects(UCtx uc, int i) {
      this.uc = uc;
      ints = new int[i];
      objects = new Object[i];
   }

   /**
    * 
    * @param uc
    * @param initialCapacity
    * @param fixed when true, structure is given the initialCapacity in length
    */
   public IntToObjects(UCtx uc, int initialCapacity, boolean emptyFill) {
      this.uc = uc;
      ints = new int[initialCapacity];
      objects = new Object[initialCapacity];
      if (emptyFill) {
         nextempty = initialCapacity;
      }
   }

   public IntToObjects(UCtx uc, Object[] os) {
      this.uc = uc;
      if (os == null) {
         objects = new Object[5];
         ints = new int[5];
      } else {
         objects = os;
         nextempty = os.length;
         ints = new int[os.length];
      }
   }

   public void add(int i, int o) {
      this.add(new Integer(o), i);
   }

   /**
    * 
    * @param i
    * @param o
    */
   public void add(int i, Object o) {
      this.add(o, i);
   }

   public void add(Object o) {
      this.add(o, 0);
   }

   /**
    * Appends object and its int value
    * <br>
    * <br>
    * 
    * @param o
    * @param i
    */
   public void add(Object o, int i) {
      if (nextempty >= ints.length) {
         growArray();
      }
      ints[nextempty] = i;
      objects[nextempty] = o;
      nextempty++;
   }

   /**
    * Adds the content of the array
    * @param o
    */
   public void addArray(Object[] o) {
      for (int i = 0; i < o.length; i++) {
         this.add(o[i], 0);
      }
   }

   /**
    * Appends object and its int value
    * <br>
    * <br>
    * @param o
    * @param i
    * @return the index of the object added
    */
   public int addR(Object o, int i) {
      nextempty++; //atomic. thread safety
      int index = nextempty - 1; //atomic. no 
      if (index >= ints.length) {
         growArray();
      }
      ints[index] = i;
      objects[index] = o;
      return index;
   }

   /**
    * 
    * @param o
    * @param i
    * @throws IllegalArgumentException if i already there
    */
   public void addThrow(Object o, int i) {
      if (findInt(i) != -1) {
         throw new IllegalArgumentException("" + i);
      }
      add(o, i);
   }

   /**
    * Adds integer i to the structure if not found.
    * <br>
    * @param i
    * @return index of first i
    */
   public int addUnique(int i) {
      int index = findInt(i);
      if (index == -1) {
         index = nextempty;
         this.add(i, null);
      }
      return index;
   }

   /**
    * Only add if Object reference is not found
    * @param o
    */
   public void addUnique(Object o) {
      if (findObjectRef(o) == -1) {
         this.add(o, 0);
      }
   }

   public void clear() {
      nextempty = 0;
      for (int i = 0; i < objects.length; i++) {
         objects[i] = null;
         ints[i] = 0;
      }
   }
   public void clearNewTo(int newSize) {
      nextempty = 0;
      objects = new Object[newSize];
      ints = new int[newSize];
   }
   /**
    * Copy all objects in array at offset i, starting at i
    * @param ar
    * @param i
    */
   public void copy(Object[] ar, int i) {
      for (int j = 0; j < nextempty; j++) {
         ar[i + j] = objects[j];
      }
   }

   /**
    * Deletes num entries starting at index (inclusive) and shifts back values above.
    * <br>
    * <br>
    * Not synchronized.
    * <br>
    * <br>
    * 
    * @param index
    * @param nums must be 1 or more. if bigger, throw an excepionts
    */
   public void delete(int index, int nums) {
      if (nums <= 0)
         throw new IllegalArgumentException();
      if (index + nums > nextempty) {
         throw new IllegalArgumentException();
      }
      for (int i = index + nums; i < nextempty; i++) {
         objects[i - nums] = objects[i];
         ints[i - nums] = ints[i];
      }
      //clear object references
      for (int i = nextempty - nums; i < nextempty; i++) {
         objects[i] = null;
         ints[i] = 0;
      }
      nextempty -= nums;
   }

   /**
    * Ensure the number of slots. Make the nextEmpty at least +1
    * <br>
    * <br>
    * @param size index that should not throw an {@link ArrayIndexOutOfBoundsException}.
    * @param increment the incrementing size of the array
    */
   public void ensureRoom(int size, int increment) {
      if (size >= objects.length) {
         ints = uc.getMem().ensureCapacity(ints, size, increment);
         objects = uc.getMem().ensureCapacity(objects, size, increment);
      }
      if (nextempty <= size) {
         nextempty = size;
      }
   }

   /**
    * Search for the first line with integer mint
    * <br>
    * <br>
    * 
    * @param mint
    * @return -1 if none.
    */
   public int findInt(int mint) {
      for (int i = 0; i < nextempty; i++) {
         if (ints[i] == mint)
            return i;
      }
      return -1;
   }

   /**
    * Finds the first line with integer i and returns the object.
    * <br>
    * <br>
    * @param i
    * @return null if integer is not found or when null is stored
    */
   public Object findIntObject(int val) {
      for (int i = 0; i < nextempty; i++) {
         if (ints[i] == val)
            return objects[i];
      }
      return null;
   }

   public int findObjectEquals(Object o) {
      for (int i = 0; i < nextempty; i++) {
         if (o.equals(objects[i])) {
            return i;
         }
      }
      return -1;
   }

   /**
    * Index [0-size[ of object reference.
    * -1 if Object reference is not found
    * @param o
    * @return
    */
   public int findObjectRef(Object o) {
      for (int i = 0; i < nextempty; i++) {
         if (objects[i] == o) {
            return i;
         }
      }
      return -1;
   }

   /**
    * 
    * @param o
    * @return {@link Integer#MAX_VALUE} if not found.
    */
   public int findRefObjectInt(Object o) {
      int val = findObjectRef(o);
      if (val != -1) {
         return ints[val];
      }
      return Integer.MAX_VALUE;
   }

   /**
    * Return the Int value for a string
    * @param s
    * @param ignorecase
    * @return INT max value of not found
    */
   public int findString(String s, boolean ignorecase) {
      if (s == null)
         return Integer.MAX_VALUE;
      StringUtils su = uc.getStrU();
      for (int i = 0; i < nextempty; i++) {
         if (ignorecase) {
            if (su.isSimilar(objects[i].toString(), s)) {
               return i;
            }
         } else {
            if (objects[i].equals(s))
               return i;
         }
      }
      return -1;
   }

   public int findStringObject(String s, boolean ignorecase) {
      int val = findString(s, ignorecase);
      if (val != -1)
         return ints[val];
      return Integer.MAX_VALUE;
   }

   public int getInt(int index) {
      return ints[index];
   }

   public int[] getInts() {
      int[] ar = new int[nextempty];
      for (int i = 0; i < nextempty; i++) {
         ar[i] = ints[i];
      }
      return ar;
   }

   /**
    * Size. Nextempty field
    * <br>
    * <br>
    * 0 based length. So returns 0 when no items.
    * @return
    */
   public int getLength() {
      return nextempty;
   }

   /**
    * Starts at 0 and copies objects to array starting at offset
    * @param ar
    */
   public void copyToArray(Object[] ar, int offset) {
      int min = Math.min(ar.length - offset, nextempty);
      for (int i = 0; i < min; i++) {
         ar[offset + i] = objects[i];
      }
   }

   public void copyToArray(Object[] ar) {
      copy(ar, 0);
   }

   /**
    * 
    * @param index
    * @return
    * @throws IllegalArgumentException
    */
   public Object getObjectAtIndex(int index) {
      return objects[index];
   }

   public Object[] getObjects() {
      Object[] ar = new Object[nextempty];
      for (int i = 0; i < nextempty; i++) {
         ar[i] = objects[i];
      }
      return ar;
   }

   public void growArray() {
      growArray(1);
   }

   public void growArray(int increment) {
      int incr = ints.length + increment;
      ints = uc.getMem().increaseCapacity(ints, incr);
      objects = uc.getMem().increaseCapacity(objects, incr);
   }

   public boolean hasObject(Object o) {
      for (int i = 0; i < nextempty; i++) {
         if (objects[i] == o) {
            return true;
         }
      }
      return false;
   }

   public int getObjectIndex(Object o) {
      for (int i = 0; i < nextempty; i++) {
         if (objects[i] == o) {
            return i;
         }
      }
      return -1;
   }

   public boolean hasObjectEquals(Object o) {
      for (int i = 0; i < nextempty; i++) {
         if (objects[i].equals(o)) {
            return true;
         }
      }
      return false;
   }

   public void insertObject(Object o, int index) {
      insertObject(o, 0, index);
   }

   /**
    * 
    * @param o
    * @param index
    */
   public void insertObject(Object o, int val, int index) {
      if (index >= nextempty) {
         setObject(o, index);
      } else {
         ensureRoom(index + 1, 10);
         for (int i = nextempty; i >= index; i--) {
            objects[i + 1] = objects[i];
            ints[i + 1] = ints[i];
         }
         objects[index] = o;
         ints[index] = val;
      }
   }

   public boolean removeRef(Object o) {
      int index = findObjectRef(o);
      if (index != -1) {
         delete(index, 1);
         return true;
      }
      return false;
   }

   /**
    * Sets the object at index. Does not update the integer value.
    * <br>
    * <br>
    * Update next empty.
    * 
    * @param o
    * @param index
    * @throws ArrayIndexOutOfBoundsException if index has not be ensured.
    */
   public void setObject(Object o, int index) {
      objects[index] = o;
      if (index >= nextempty) {
         nextempty = index + 1;
      }
   }

   /**
    * Updates the value,
    * @param val
    * @param index
    * @throws ArrayIndexOutOfBoundsException if index has not be ensured.
    */
   public void setInt(int val, int index) {
      ints[index] = val;
      if (index >= nextempty) {
         nextempty = index + 1;
      }
   }

   /**
    * 
    * @param o
    * @param val
    * @param index
    * @throws ArrayIndexOutOfBoundsException if index has not be ensured.
    */
   public void setObjectAndInt(Object o, int val, int index) {
      objects[index] = o;
      ints[index] = val;
      if (index >= nextempty) {
         nextempty = index + 1;
      }
   }

   /**
    * Sort according to the String value with a {@link StringUtils#compare(char[], char[])}
    * on the {@link Object#toString()} 
    * @param asc 
    */
   public void sort(boolean asc) {
      int tempint = 0;
      Object tempstr = null;
      if (objects.length == 1)
         return;
      int val = 0;
      IStrComparator comparator = uc.getStrComparator();
      for (int i = 0; i < nextempty; i++) {
         for (int j = i + 1; j < nextempty; j++) {
            //-1 if i < j
            val = comparator.compare(objects[i].toString(), objects[j].toString());
            if ((asc && val > 0) || (!asc && val < 0)) {
               tempstr = objects[i];
               tempint = ints[i];
               objects[i] = objects[j];
               ints[i] = ints[j];
               objects[j] = tempstr;
               ints[j] = tempint;
            }
         }
      }
   }

   /**
    * Sort the int values... String matches follow
    * @param asc
    */
   public void sortInt(boolean asc) {
      int tempint = 0;
      Object tempstr = null;
      if (ints.length == 1)
         return;
      int val = 0;
      for (int i = 0; i < nextempty; i++) {
         for (int j = i + 1; j < nextempty; j++) {
            //-1 if i < j
            val = (ints[i] > ints[j]) ? 1 : -1;
            if ((asc && val > 0) || (!asc && val < 0)) {
               tempstr = objects[i];
               tempint = ints[i];
               objects[i] = objects[j];
               ints[i] = ints[j];
               objects[j] = tempstr;
               ints[j] = tempint;
            }
         }
      }
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   /**
    * 
    */
   public void toString(Dctx sb) {
      sb.root(this, "IntToObjects");
      sb.append(" [size=" + nextempty + "]");
      for (int i = 0; i < nextempty; i++) {
         sb.nl();
         sb.append((i + 1) + "=");
         sb.append(ints[i]);
         sb.append('\t');
         Object o = objects[i];
         if (o != null) {
            if (o instanceof IStringable) {
               if (sb.isExpand()) {
                  ((IStringable) o).toString(sb);
               } else {
                  ((IStringable) o).toString1Line(sb.nLevel());
               }
            } else {
               String str = o.toString();
               if (!sb.isExpand()) {
                  //cut after the first line
                  int in = str.indexOf('\n');
                  if (in != -1) {
                     str = str.substring(0, in);
                  }
               }
               sb.append(str);
            }
         } else {
            sb.append("null");
         }
      }
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "IntToObjects");
      dc.append(" [size=" + nextempty + "]");
   }

   public void toStringForLine(Dctx sb) {
      sb.root(this, "IntToObjects");
      sb.append(" [size=" + nextempty + "]");
      for (int i = 0; i < nextempty; i++) {
         sb.nl();
         sb.append((i + 1) + "=");
         sb.append(ints[i]);
         sb.append('\t');
         Object o = objects[i];
         if (o != null) {
            if (o instanceof IStringable) {
               if (sb.isExpand()) {
                  ((IStringable) o).toString(sb.Level());
               } else {
                  ((IStringable) o).toString1Line(sb.Level());
               }
            } else {
               String str = o.toString();
               if (!sb.isExpand()) {
                  //cut after the first line
                  int in = str.indexOf('\n');
                  if (in != -1) {
                     str = str.substring(0, in);
                  }
               }
               sb.append(str);
            }
         } else {
            sb.append("null");
         }
      }
   }
   //#enddebug

}
