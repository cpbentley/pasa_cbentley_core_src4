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
 * Class that handles inserting new int values in a sorted array.
 * 
 * 
 * @author Charles Bentley
 *
 */
public class IntSorter implements IStringable {

   public static final int CAPACITY_INCREASE_0_ONE        = 0;

   public static final int CAPACITY_INCREASE_1_HALF       = 1;

   /**
    * basic insert of values in front of array
    */
   public static final int SORT_ORDER_0_NONE_APPEND_SUFFIX = 0;

   /**
    * No sorting. but integers are added on the top of the array
    */
   public static final int SORT_ORDER_1_NONE_APPEND_PREFIX = 1;

   /**
    * Sort from small to tall.
    * <br>
    * <br>
    * The algo for sorting is decided by the implementation
    */
   public static final int SORT_ORDER_2_ASC                = 2;

   /**
    * Sorted from tall to small
    */
   public static final int SORT_ORDER_3_DESC               = 3;

   public static final int VALUE_ADDED                    = 0;

   public static final int VALUE_ADDED_AND_ARRAY_MODIFIED = 1;

   public static final int VALUE_NOT_ADDED                = -1;

   private int[]           array;

   private int             arrayInsertIndex;

   private int             capacityIncreaseType            = 0;

   private boolean         isUnique;

   private int[]           tupleOrderSearch;

   private int             type;

   private UCtx            uc;

   public IntSorter(UCtx uc, int type, boolean isUnique) {
      this.uc = uc;
      this.type = type;
      this.isUnique = isUnique;
   }

   public int addToArray(int value, int[] array) {
      return this.addToArray(value, array, 0, array.length);
   }

   /**
    * Sorter assumes array 
    * we want to offset at which the value was inserted, 
    * <li>1 if value was added and array reference was changed
    * <li>0 if value was added
    * <li>-1 if value was not added. i.e. a duplicate
    * 
    * Reminder: This is not thread safe
    * @param value
    * @param array
    * @param offset
    * @param len
    * @throws throw {@link IllegalArgumentException} if sorter type is unknown
    */
   public int addToArray(int value, int[] array, int offset, int len) {
      //first get the index of the first value
      int indexSearch = -1;
      if (type == SORT_ORDER_2_ASC) {
         tupleOrderSearch = IntUtils.getSearchInASC(value, array, offset, len);
      } else if (type == SORT_ORDER_3_DESC) {
         tupleOrderSearch = IntUtils.getSearchInDESC(value, array, offset, len);
      } else {
         tupleOrderSearch = null;
         if (isUnique) {
            indexSearch = getFirstIndexOfValue(value, array, offset, len);
            if (indexSearch != -1) {
               return -1;
            }
         }
      }

      if (tupleOrderSearch != null) {
         if (isUnique && tupleOrderSearch[IntUtils.TUPLE_SEARCH_0_FIRST_INDEX] != -1) {
            return -1;
         } else {
            indexSearch = tupleOrderSearch[IntUtils.TUPLE_SEARCH_1_INSERT_INDEX];
         }
      }

      //now that we know we can add, check if there is enough array space after offset+len
      int returnValue = 0;
      //make sure offset+len is a valid index
      if (array.length <= offset + len) {
         int sizeIncrease = 1; //capacity increases
         if (capacityIncreaseType == CAPACITY_INCREASE_1_HALF) {
            sizeIncrease = array.length / 2;
         }
         array = uc.getMem().increaseCapacity(array, sizeIncrease);
         returnValue = 1; //set value indicating the array reference has been modified
         this.array = array;
      }

      //Do the insertion
      if (type == SORT_ORDER_0_NONE_APPEND_SUFFIX) {
         arrayInsertIndex = offset + len;
         array[arrayInsertIndex] = value;
      } else if (type == SORT_ORDER_1_NONE_APPEND_PREFIX) {
         arrayInsertIndex = offset;
         IntUtils.shiftIntUp(array, 1, offset, len, false);
         array[arrayInsertIndex] = value;
      } else if (type == SORT_ORDER_2_ASC) {
         //we already have our inserting index if isUnique check
         IntUtils.shiftIntUp(array, 1, indexSearch, offset + len, false);
         array[indexSearch] = value;
      } else if (type == SORT_ORDER_3_DESC) {
         IntUtils.shiftIntUp(array, 1, indexSearch, offset + len, false);
         array[indexSearch] = value;
      } else {
         throw new IllegalArgumentException();
      }
      return returnValue;
   }

   public void addToIntArray(int value, IntArray intArray) {
      int v = addToArray(value, intArray.getArray(), intArray.getOffset(), intArray.getLen());
      if (v == VALUE_ADDED_AND_ARRAY_MODIFIED) {
         intArray.setArray(getArray());
         intArray.incrementLen();
      } else if (v == VALUE_ADDED) {
         intArray.incrementLen();
      }
   }

   public boolean contains(int value, int[] array, int offset, int len) {
      return getFirstIndexOfValue(value, array, offset, len) != -1;
   }

   /**
    * Array when added by {@link IntSorter#addToArray(int, int[], int, int)}
    * <br>
    * When int returned by 
    * @return
    */
   public int[] getArray() {
      return array;
   }

   /**
    * Offset at which value was last added by {@link IntSorter#addToArray(int, int[], int, int)}
    * @return
    */
   public int getArrayInsertIndex() {
      return arrayInsertIndex;
   }

   public int getFirstIndexOfValue(int value, int[] array, int offset, int len) {
      int index = -1;
      if (type == SORT_ORDER_2_ASC) {
         //logn search
         index = IntUtils.getOrderIndexAsc(array, value, isUnique, offset, len);
      } else if (type == SORT_ORDER_3_DESC) {
         index = IntUtils.getOrderIndexDesc(array, value, isUnique, offset, len);
      } else {
         //do a linear check. 
         index = IntUtils.getFirstIndex(value, array, offset, len);
      }
      return index;
   }

   public int getType() {
      return type;
   }

   public boolean isUnique() {
      return isUnique;
   }

   /**
    * Sort the int array using the sorter settings.
    * 
    * @param array
    * @param offset
    * @param len
    * @throws ArrayIndexOutOfBoundsException if offset and len are invalid
    */
   public void sortArray(int[] array, int offset, int len) {

   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "IntSorter");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "IntSorter");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
