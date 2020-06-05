/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.structs;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.utils.IntUtils;

/**
 * Links an int to another int at an index position
 * <br>
 * Implementation trick uses only 1 integer array. It stores Uno:Duo pairs together. <br>
 * By default structure acts like a bag, Unos and Duos may have duplicates. 
 * But they can be ordered according to Unos. then Unos are unique.
 * Keys may be made unique. Values as well. Values may become keys and keys values.
 * <br>
 * Get methods may either return a default value when not found or throw an execption
 * <br>
 * <br>
 * <b> 5 differents cases:</b>
 * <br>
 * <li>non ordered
 * <li>Uno ordered unique
 * <li>Uno ordered, sub order on Duo
 * <li>Duo ordered unique
 * <li>Duo ordered, sub order on Uno
 * <br>
 * Can be used
 * @author Charles Bentley
 *
 */
public class IntToInts implements IStringable {

   public static final int GROW_INT_DEFAULT                   = 2;

   /**
    * bag key
    */
   public static final int TYPE_0NON_ORDERED                  = 0;

   /**
    * Unique Uno order
    */
   public static final int TYPE_1UNO_ORDER                    = 1;

   /**
    * No order but Unos are unique.
    */
   public static final int TYPE_2NON_ORDERED_UNIQUE           = 3;

   private int             growInt                            = 2;

   /**
    * 0 means the size
    */
   private int[]           ints;

   private int             returnKeyNotFoundValue;

   private boolean         throwEx;

   public static final int DEFAULT_RETURN_VALUE_KEY_NOT_FOUND = Integer.MAX_VALUE;

   private int             type                               = 0;

   private UCtx            uc;

   /**
    * Returns {@link Integer#MAX_VALUE} when key not found.
    */
   public IntToInts(UCtx uc) {
      this(uc, 0, GROW_INT_DEFAULT, false, Integer.MAX_VALUE);
   }

   public IntToInts(UCtx uc, int type) {
      this(uc, type, GROW_INT_DEFAULT, false, Integer.MAX_VALUE);
   }

   public IntToInts(UCtx uc, int type, int growInt, boolean ex, int rv) {
      this(uc, type, growInt, ex, rv, 0);
   }

   public IntToInts(UCtx uc, int type, int growInt, boolean ex, int rv, int initBufSize) {
      this.uc = uc;
      this.type = type;
      this.growInt = growInt;
      ints = new int[1 + initBufSize];
      throwEx = ex;
      returnKeyNotFoundValue = rv;
   }

   /**
    * Add key as being unique.
    * @param key
    * @param value
    */
   public void add(int key, int value) {

      int len = manageNewIndex();
      switch (type) {
         case TYPE_2NON_ORDERED_UNIQUE:
            int index = getUnoIndex(key);
            if (index != -1) {
               ints[index * 2 + 2] = value;
               return;
            }
         case TYPE_0NON_ORDERED:
            ints[len] = key;
            ints[len + 1] = value;
            ints[0] += 1;
            return;
         case TYPE_1UNO_ORDER:
            //most cases when keys are added from smallest to biggest
            if (ints[0] == 0 || key > ints[len - 2]) {
               ints[len] = key;
               ints[len + 1] = value;
               ints[0] += 1;
               return;
            }
            for (int i = 1; i < len; i += 2) {
               if (ints[i] == key) {
                  ints[i + 1] = value;
                  return;
               }
               if (ints[i] > key) {
                  //insert just before
                  uc.getIU().shiftIntUp(ints, 2, i, len, false);
                  ints[i] = key;
                  ints[i + 1] = value;
                  ints[0] += 1;
                  return;
               }
            }
            break;

         default:
            break;
      }

   }

   /**
    * 
    * Adds an Uno with its Duo
    * If structure is order, the add method 
    * @param Uno
    * @param Duo
    */
   public void addUnoDuo(int Uno, int Duo) {
      add(Uno, Duo);
   }

   public int[] getAllDuos() {
      int[] d = new int[getNumKeys()];
      int offset = 0;
      for (int i = 0; i < d.length; i++) {
         d[offset] = ints[1 + 1 + i + offset];
         offset++;
      }
      return d;
   }

   public int[] getAllUnos() {
      int[] d = new int[getNumKeys()];
      int offset = 0;
      for (int i = 0; i < d.length; i++) {
         d[offset] = ints[1 + i + offset];
         offset++;
      }
      return d;
   }

   public int[] getAllUnosDuos() {
      int num = getNumKeys();
      int[] d = new int[num * 2];
      int offset = 0;
      for (int i = 0; i < num; i++) {
         d[offset] = ints[1 + offset];
         offset++;
         d[offset] = ints[1 + offset];
         offset++;
      }
      return d;
   }

   /**
    * Get Index of first duo value.
    * <br>
    * <br>
    * @param duo
    * @return
    */
   public int getDuoIndex(int duo) {
      int len = getLenData();
      int count = 0;
      for (int i = 1; i < len; i += 2) {
         if (ints[i + 1] == duo)
            return count;
         count++;
      }
      return -1;
   }

   /**
    * Value at position i
    * @param index
    * @return
    */
   public int getIndexedDuo(int index) {
      return ints[index * 2 + 2];
   }

   /**
    * Uno key at index i
    * @param i
    * @return
    */
   public int getIndexedUno(int i) {
      return ints[i * 2 + 1];
   }

   /**
    * Don't use this outside.
    * For iteration use {@link IntToInts#getNumKeys()}
    * @return
    */
   public int getLenData() {
      return ints[0] * 2 + 1;
   }

   /**
    * Number of key-value pairs
    * @return
    */
   public int getNumKeys() {
      return ints[0];
   }

   /**
    * Reset length index. Does not erase previous data
    */
   public void clear() {
      ints[0] = 0;
   }

   /**
    * 
    * @param unoXpos starts at 0 for the first 
    * @return
    */
   public int getXthUno(int unoXpos) {
      return ints[1 + (unoXpos * 2)];
   }

   /**
    * 
    * @param unoXpos starts at 0 for the first duo
    * @return
    */
   public int getXthDuo(int duoXpos) {
      return ints[1 + (duoXpos * 2) + 1];
   }

   /**
    * virtual Index position of Uno
    * @param uno
    * @return -1 if not there
    */
   public int getUnoIndex(int uno) {
      int len = getLenData();
      int count = 0;
      for (int i = 1; i < len; i += 2) {
         if (ints[i] == uno)
            return count;
         count++;
      }
      return -1;
   }

   /**
    * 
    * 
    * Fast get because ints are sorted in ASC order
    * <br>
    * if key is not found, behavior depends on constructor
    * either throw an exception or return a given value
    *
    * @param uno
    * @return
    */
   public int getDuoFromUno(int uno) {
      int len = ints[0] * 2 + 1;
      for (int i = 1; i < len; i += 2) {
         if (ints[i] == uno)
            return ints[i + 1];
      }
      if (throwEx)
         throw new IllegalArgumentException("Key Not Found " + uno);
      return returnKeyNotFoundValue;
   }

   public int getUnoFromDuo(int duo) {
      int len = ints[0] * 2 + 1;
      for (int i = 2; i < len; i += 2) {
         if (ints[i] == duo)
            return ints[i - 1];
      }
      if (throwEx)
         throw new IllegalArgumentException("Key Not Found " + duo);
      return returnKeyNotFoundValue;
   }

   /**
    * Add size for a uno-duo
    * @return
    */
   public int manageNewIndex() {
      int len = ints[0] * 2 + 1;
      if (len + 2 >= ints.length) {
         ints = uc.getMem().increaseCapacity(ints, growInt);
      }
      return len;
   }

   /**
    * Removes all lines with Uno, along with their duos
    * @param uno
    */
   public void removeUno(int uno) {
      int len = getLenData();
      for (int i = 1; i < len; i += 2) {
         if (ints[i] == uno) {
            //start at +2
            IntUtils.shiftIntDown(ints, 2, i + 2, len - 1, true);
            ints[0]--;
            return;
         }
      }
   }

   public void setNotFound(boolean ex, int returnValue) {
      throwEx = ex;
      returnKeyNotFoundValue = returnValue;
   }

   public int[] getArrayReference() {
      return ints;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "IntToInts");
      toStringPrivate(dc);
      dc.append("Type=" + type);
      dc.append(" ");
      int len = ints[0] * 2 + 1;
      for (int i = 1; i < len; i += 2) {
         dc.append("(");
         dc.append(ints[i]);
         dc.append(",");
         dc.append(ints[i + 1]);
         dc.append(")");
      }
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("pairs=", ints[0]);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "IntToInts");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }
   //#enddebug

}
