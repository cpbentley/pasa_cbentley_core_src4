/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.structs;

import java.io.Serializable;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.IStrComparator;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.memory.IMemory;
import pasa.cbentley.core.src4.utils.StringUtils;

/**
 * Utility class mapping int literals to Strings and Image IDs (also strings). 
 * <br>
 * <br>
 * Origin comes from the need to display RMS store content.
 * <br>
 * Model Item generates display Strings and put them along with the record store ID in the IntToStrings.<br>
 * When a String is visually selected by user, Model takes selected index of IntToStrings, retrieve the
 * integer at that row.<br>
 * The app has a way to make a user select a record store.<br>
 * 
 * The {@link Serializable} is not port of the Bentley framework. ?
 * 
 * Used for compatiblity with J2SE. J2ME driver must define a {@link java.io.Serializable} marker interface. 
 * 
 * @author Charles Bentley
 *
 */
public class IntToStrings implements IStringable, Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = -8080875409242843475L;

   private String[]          imgs;

   public int[]              ints;

   /**
    * The size of IntToStrings
    */
   public int                nextempty        = 0;

   public String[]           strings;

   protected UCtx            uc;

   public IntToStrings(UCtx uc) {
      this(uc, 0);
   }

   public IntToStrings(UCtx uc, int size) {
      if (uc == null) {
         throw new NullPointerException();
      }
      this.uc = uc;
      ints = new int[size];
      strings = new String[size];
      imgs = new String[size];
   }

   public IntToStrings(UCtx uc, String[] str) {
      if (uc == null) {
         throw new NullPointerException();
      }
      this.uc = uc;
      strings = str;
      ints = new int[str.length];
      nextempty = str.length;
   }

   /**
    * Adds the integer and string together at the same new index
    * <br>
    * <br>
    * TODO Can we add Nulls?
    * @param integer
    * @param s
    */
   public void add(int integer, String s) {
      if (nextempty >= ints.length) {
         growArray();
      }
      ints[nextempty] = integer;
      strings[nextempty] = s;
      nextempty++;
   }

   public void add(IntToStrings its) {
      growArray(its.nextempty);
      for (int i = 0; i < its.nextempty; i++) {
         add(its.ints[i], its.strings[i]);
      }
   }

   public void add(String s) {
      add(0, s);
   }

   private void addNoCheck(int integer, String s) {
      ints[nextempty] = integer;
      strings[nextempty] = s;
      nextempty++;
   }

   /**
    * 0 if nothing added
    * @param its
    * @return
    */
   public int addReturn(IntToStrings its) {
      growArray(its.nextempty);
      for (int i = 0; i < its.nextempty; i++) {
         addNoCheck(its.ints[i], its.strings[i]);
      }
      return its.nextempty;
   }

   /**
    * 
    * @param its
    * @return number of elements added
    */
   public int addReturn(String[] its) {
      ensureRoomForAddSize(its.length);
      //checks have been done
      for (int i = 0; i < its.length; i++) {
         addNoCheck(0, its[i]);
      }
      return its.length;
   }

   public void clear() {
      nextempty = 0;
      for (int i = 0; i < strings.length; i++) {
         strings[i] = null;
         ints[i] = 0;
      }
   }

   /**
    * Makes the size value a valid index.
    * <br>
    * <br>
    * When size is negative does nothing
    * @param size
    */
   public void ensureRoomFor(int size) {
      if (size >= ints.length) {
         growArray(size + 1);
      }
   }

   public void ensureRoomForAddSize(int size) {
      if (nextempty + size >= ints.length) {
         growArray(size + 1);
      }
   }

   /**
    * Returns the index of the first occurence of integer.
    * <br>
    * <br>
    * 
    * @param mint
    * @return
    */
   public int findInt(int mint) {
      for (int i = 0; i < nextempty; i++) {
         if (ints[i] == mint)
            return i;
      }
      return -1;
   }

   /**
    * Search for a String associated with id in the int array.
    * @param id
    * @return
    */
   public String findStringWithID(int id) {
      for (int i = 0; i < ints.length; i++) {
         if (ints[i] == id) {
            return strings[i];
         }
      }
      return id + " unknown";
   }

   /**
    * Return index of first string s. Uses the strict {@link Object#equals(Object)} method.
    * <br>
    * <br>
    * @param s
    * @return -1 if not found
    */
   public int getFirstStringIndex(String s) {
      return getFirstStringIndex(s, false);
   }

   /**
    * Return the Int value for a string
    * <br>
    * <br>
    * @param s when String is null, returns -1.
    * @param ignorecase when true, use {@link StringUtils#isSimilar(String, String)} method.
    * @return -1 if not found
    */
   public int getFirstStringIndex(String s, boolean ignorecase) {
      if (s == null)
         return Integer.MAX_VALUE;
      for (int i = 0; i < nextempty; i++) {
         if (ignorecase) {
            if (uc.getStrComparator().isSimilar(strings[i], s)) {
               return i;
            }
         } else {
            if (strings[i].equals(s))
               return i;

         }
      }
      return -1;
   }

   /**
    * Returns the integer at index
    * @param id
    * @return
    */
   public int getInt(int index) {
      return ints[index];
   }

   /**
    * Iterates over all strings and returns the maximum length
    * @return
    */
   public int getMaxStringSize() {
      int max = 0;
      for (int i = 0; i < nextempty; i++) {
         if (strings[i].length() > max) {
            max = strings[i].length();
         }
      }
      return max;
   }

   public IMemory getMem() {
      return uc.getMem();
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

   /**
    * Returns the string at index
    * @param id
    * @return
    */
   public String getString(int index) {
      return strings[index];
   }

   /**
    * Trimmed new String array.
    * <br>
    * <br>
    * 
    * @return
    */
   public String[] getStrings() {
      String[] ar = new String[nextempty];
      for (int i = 0; i < nextempty; i++) {
         ar[i] = strings[i];
      }
      return ar;
   }

   /**
    * Grow array by 1
    */
   public void growArray() {
      growArray(1);
   }

   public void growArray(int increment) {
      int incr = ints.length + increment;
      ints = getMem().increaseCapacity(ints, incr);
      strings = getMem().increaseCapacity(strings, incr);
      imgs = getMem().increaseCapacity(imgs, incr);
   }

   public boolean hasStringEquals(String s) {
      for (int i = 0; i < nextempty; i++) {
         if (strings[i].equals(s)) {
            return true;
         }
      }
      return false;
   }

   /**
    * Find String using the == comparator
    * @param s
    * @return
    */
   public boolean hasStringRef(String s) {
      for (int i = 0; i < nextempty; i++) {
         if (strings[i] == s) {
            return true;
         }
      }
      return false;
   }

   /**
    * Sets the String at position <code>pos</code>.
    * <br>
    * Grows the array if necessary. 
    * <br>
    * <br>
    * If position is negative, method throws nothing.
    * @param pos 0 based position
    * @param s
    * @throw ArrayIndexOutOfBoundsException if pos is negative
    */
   public void setSafe(int pos, String s) {
      if (pos < 0)
         throw new ArrayIndexOutOfBoundsException(pos);
      ensureRoomFor(pos);
      if (pos > nextempty) {
         nextempty = pos + 1;
      }
      setUnsafe(pos, s);
   }

   /**
    * Sets the String and int value at position <code>pos</code>.
    * <br>
    * Grows the array if necessary. 
    * <br>
    * <br>
    * If position is negative, method throws nothing.
    * @param pos 0 based position
    * @param s
    * @throw ArrayIndexOutOfBoundsException if pos is negative
    */
   public void setSafe(int pos, String s, int integer) {
      if (pos < 0)
         throw new ArrayIndexOutOfBoundsException(pos);
      ensureRoomFor(pos);
      if (pos > nextempty) {
         nextempty = pos + 1;
      }
      setUnsafe(pos, s, integer);
   }

   /**
    * Sets the String at position <code>pos</code>
    * <br>
    * No checks are made and structure size is not updated.
    * <br>
    * This method does not update the integer array.
    * @param pos
    * @param s
    * @throws ArrayIndexOutOfBoundsException if pos is not valid
    */
   public void setUnsafe(int pos, String s) {
      strings[pos] = s;
   }

   /**
    * Sets the String and int value at position <code>pos</code>
    * <br>
    * No checks are made and structure size is not updated.
    * @param pos
    * @param s
    * @throws ArrayIndexOutOfBoundsException if pos is not valid
    */
   public void setUnsafe(int pos, String s, int value) {
      ints[pos] = value;
      strings[pos] = s;
   }

   /**
    * shift by one any integer
    * @param thre
    */
   public void shiftAbove(int thre) {
      for (int i = 0; i < nextempty; i++) {
         if (ints[i] >= thre)
            ints[i]++;
      }
   }

   /**
    * Sort the int/String according to the String value with a String comparator
    * provided by the UtilsCtx clas. StringUtils.compare method
    * @param asc 
    */
   public void sort(boolean asc) {
      int tempint = 0;
      String tempstr = "";
      if (strings.length == 1)
         return;
      int val = 0;
      IStrComparator strc = uc.getStrComparator();
      for (int i = 0; i < nextempty; i++) {
         for (int j = i + 1; j < nextempty; j++) {
            //-1 if i < j
            val = strc.compare(strings[i], strings[j]);
            if ((asc && val > 0) || (!asc && val < 0)) {
               tempstr = strings[i];
               tempint = ints[i];
               strings[i] = strings[j];
               ints[i] = ints[j];
               strings[j] = tempstr;
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
      String tempstr = "";
      if (ints.length == 1)
         return;
      int val = 0;
      for (int i = 0; i < nextempty; i++) {
         for (int j = i + 1; j < nextempty; j++) {
            //-1 if i < j
            val = (ints[i] > ints[j]) ? 1 : -1;
            if ((asc && val > 0) || (!asc && val < 0)) {
               tempstr = strings[i];
               tempint = ints[i];
               strings[i] = strings[j];
               ints[i] = ints[j];
               strings[j] = tempstr;
               ints[j] = tempint;
            }
         }
      }
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "IntToString");
      dc.appendVarWithSpace("size", getSize());
      for (int i = 0; i < nextempty; i++) {
         dc.nl();
         dc.append(ints[i]);
         dc.append('\t');
         dc.append(strings[i]);
      }
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "IntToString");
      dc.appendVarWithSpace("size", getSize());
      //show max 10 of them on the line
      int num = Math.min(nextempty, 10);
      for (int i = 0; i < num; i++) {
         dc.append(ints[i]);
         dc.append('\t');
         dc.append(strings[i]);
      }

   }
   //#enddebug

   public UCtx toStringGetUCtx() {
      return uc;
   }

}
