/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.structs;

import java.util.Enumeration;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;

import pasa.cbentley.core.src4.interfaces.IStrComparator;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.utils.BitUtils;
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
public class IntToObjects extends ObjectU implements IStringable {

   public int[]    ints;

   public int      nextempty = 0;

   /**
    * Cannot be null
    */
   public Object[] objects;

   public IntToObjects(UCtx uc) {
      this(uc, 5);
   }

   /**
    * This sets 
    * @param uc
    * @param i
    */
   public IntToObjects(UCtx uc, int i) {
      this(uc, i, false);
   }

   /**
    * 
    * @param uc
    * @param initialCapacity
    * @param fixed when true, structure is given the initialCapacity in length
    */
   public IntToObjects(UCtx uc, int initialCapacity, boolean emptyFill) {
      super(uc);
      objects = new Object[initialCapacity];
      ints = new int[initialCapacity];
      if (emptyFill) {
         nextempty = initialCapacity;
      }
   }

   /**
    * 
    * @param uc
    * @param os
    */
   public IntToObjects(UCtx uc, Object[] os) {
      super(uc);
      if (os == null) {
         throw new NullPointerException();
      }
      objects = os;
      nextempty = os.length;
      ints = new int[os.length];
   }
   
   /**
    * Arrays are not copied
    * @param uc
    * @param str
    * @param ints
    */
   public IntToObjects(UCtx uc, String[] objects, int[] ints) {
      super(uc);
      if (uc == null) {
         throw new NullPointerException();
      }
      if (objects == null || ints == null) {
         throw new NullPointerException();
      }
      if (objects.length != ints.length) {
         throw new IllegalArgumentException();
      }
      this.objects = objects;
      this.ints = ints;
      nextempty = objects.length;
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
    * Appends object and its int value.
    * 
    * @param obj
    * @param value
    * @return the index of the object added
    */
   public int addReturnIndex(Object obj, int value) {
      nextempty++; //atomic. thread safety
      int index = nextempty - 1; //atomic. no 
      if (index >= ints.length) {
         growArray();
      }
      ints[index] = value;
      objects[index] = obj;
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

   public void addUnique(Object o, int value) {
      if (findObjectRef(o) == -1) {
         this.add(o, value);
      }
   }

   /**
    * Nullify all object references
    */
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

   public void delete(int index) {
      this.delete(index, 1);
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
    * Ensure the number of slots. Make the nextEmpty at least +1
    * <br>
    * <br>
    * Increment will be half the size.
    * @param size index that should not throw an {@link ArrayIndexOutOfBoundsException}.
    */
   public void ensureRoom(int size) {
      ensureRoom(size, objects.length >> 1);
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

   public boolean hasIntFlag(int index, int flag) {
      return BitUtils.hasFlag(ints[index], flag);
   }

   public boolean hasInt(int mint) {
      return findInt(mint) != -1;
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

   /**
    * Returns the index of the first int value.
    * @param val
    * @return
    */
   public int findIntFirstIndex(int val) {
      for (int i = 0; i < nextempty; i++) {
         if (ints[i] == val)
            return i;
      }
      return -1;
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
    * Returns the index of the first object whose reference == <code>o</code> .
    * 
    * -1 if Object reference is not found
    * @param o
    * @return [0-size[
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
    * Returns the integer value of the first object whose reference == <code>o</code> .
    * 
    * Integer.MAX if Object reference is not found
    * @param o
    * @return [0-size[
    */
   public int findObjectInt(Object o) {
      for (int i = 0; i < nextempty; i++) {
         if (objects[i] == o) {
            return ints[i];
         }
      }
      return Integer.MAX_VALUE;
   }

   /**
    * Treating the int value as flags, set the bit flag to b value
    * @param index
    * @param flag
    * @param b
    */
   public void setIntFlag(int index, int flag, boolean b) {
      ints[index] = BitUtils.setFlag(ints[index], flag, b);
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

   public void incrementInt(int index, int val) {
      ints[index] += val;
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
    * Returns the number of elements.
    * <br>
    * 0 based length. So returns 0 when no items.
    * @return
    */
   public int getSize() {
      return nextempty;
   }

   public Enumeration getEnumeration() {
      EnumerationBase eb = new EnumerationBase(uc);
      eb.setArray(objects, nextempty);
      return eb;
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
    * Clone this by incrementing backing array size
    * @param num
    * @return
    */
   public IntToObjects cloneIncrement(int num) {
      IntToObjects cloneAr = new IntToObjects(uc, objects.length + num);
      for (int i = 0; i < objects.length; i++) {
         cloneAr.ints[i] = this.ints[i];
         cloneAr.objects[i] = this.objects[i];
      }
      cloneAr.nextempty = this.nextempty;
      return cloneAr;
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

   public Object getLast() {
      return objects[nextempty - 1];
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

   /**
    * Insert object at index, moving all other objects up
    * @param o
    * @param index
    */
   public void insertObject(Object o, int index) {
      insertObject(o, 0, index);
   }

   /**
    * Insert object at index, moving all other objects up
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
   public void toString(Dctx dc) {
      dc.root(this, IntToObjects.class, 691);
      toStringPrivate(dc);
      super.toString(dc.sup());

      for (int i = 0; i < nextempty; i++) {
         dc.nl();
         dc.append((i + 1) + "=");
         dc.append(ints[i]);
         dc.append('\t');
         Object o = objects[i];
         if (o != null) {
            if (o instanceof IStringable) {
               if (dc.isExpand()) {
                  ((IStringable) o).toString(dc);
               } else {
                  ((IStringable) o).toString1Line(dc);
               }
            } else {
               String str = o.toString();
               if (!dc.isExpand()) {
                  //cut after the first line
                  int in = str.indexOf('\n');
                  if (in != -1) {
                     str = str.substring(0, in);
                  }
               }
               dc.append(str);
            }
         } else {
            dc.append("null");
         }
      }
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, IntToObjects.class, 691);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("size", nextempty);
   }

   public void toStringForLine(Dctx dc) {
      dc.root(this, "IntToObjects");
      dc.append(" [size=" + nextempty + "]");
      String total = String.valueOf(nextempty);
      int numChars = total.length();
      for (int i = 0; i < nextempty; i++) {
         dc.nl();
         int num = (i + 1);
         String strNum = uc.getStrU().prettyIntPaddStr(num, numChars, " ");
         dc.append(strNum);
         dc.append("=");
         dc.append(ints[i]);
         dc.append('\t');
         Object o = objects[i];
         if (o != null) {
            if (o instanceof IStringable) {
               if (dc.isExpand()) {
                  ((IStringable) o).toString(dc.Level());
               } else {
                  ((IStringable) o).toString1Line(dc.Level());
               }
            } else {
               String str = o.toString();
               if (!dc.isExpand()) {
                  //cut after the first line
                  int in = str.indexOf('\n');
                  if (in != -1) {
                     str = str.substring(0, in);
                  }
               }
               dc.append(str);
            }
         } else {
            dc.append("null");
         }
      }
   }
   //#enddebug

}
