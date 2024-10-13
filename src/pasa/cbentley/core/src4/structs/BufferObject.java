/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.structs;

import java.util.Enumeration;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.utils.ArrayUtils;

/**
 * Double ended buffer. You can append in front or at the end.
 * 
 * Src 4 compatible.
 * 
 * core.src5 has a BufferObject for generics.
 * 
 * @author Charles Bentley
 *
 */
public class BufferObject extends ObjectU implements IStringable {

   private int      count     = 0;

   /**
    * must be 1 or higher
    */
   private int      increment = 5;

   /**
    * index 0 starts at 0 and gives the last used value.
    * Therefore it also gives the number of elements
    * buffer size = ints.length - 1 - int[0]
    */
   private Object[] objects;

   private int      offset    = 0;

   /**
    * 1,1
    */
   public BufferObject(UCtx uc) {
      this(uc, 1, 1, 0);
   }

   public BufferObject(UCtx uc, int num) {
      this(uc, num, 3, 0);
   }

   /**
    * 
    * @param startSize
    * @param increment few adds take a small increment., lots take a big increment
    */
   public BufferObject(UCtx uc, int startSize, int increment, int bufferFront) {
      super(uc);
      set(new Object[startSize]);
      this.increment = increment;
      this.offset = bufferFront;
   }

   /**
    * Uses the reference!
    * @param ar
    */
   public BufferObject(UCtx uc, String[] ar) {
      super(uc);
      set(ar);
   }

   /**
    * append Object to the {@link BufferObject}
    * @param str
    */
   public void add(Object obj) {
      set(uc.getMem().ensureCapacity(objects, offset, count, increment));
      objects[offset + count] = obj;
      count += 1;
   }

   /**
    * append Object to the {@link BufferObject} if not null.
    * when null does nothing
    * @param str
    */
   public void addNotNull(Object obj) {
      if (obj != null) {
         this.add(obj);
      }
   }

   public void addNotNull(Object[] objs) {
      if (objs != null) {
         for (int i = 0; i < objs.length; i++) {
            this.addNotNull(objs[i]);
         }
      }
   }

   public void add(Object[] objs) {
      int nums = objs.length;
      set(uc.getMem().ensureCapacity(objects, offset, count + nums, increment + nums));
      for (int i = 0; i < nums; i++) {
         objects[offset + count + i] = objs[i];
      }
      count += nums;
   }

   private void ensureCapacityForNewLoad(int sizeLoad) {
      set(uc.getMem().ensureCapacity(objects, offset, count + sizeLoad, increment + sizeLoad));
   }

   public void add(Object obj1, Object obj2) {
      set(uc.getMem().ensureCapacity(objects, offset, count + 2, increment));
      objects[offset + count] = obj1;
      objects[offset + count + 1] = obj2;
      count += 2;
   }

   /**
    * Add the Object in front of the array
    * @param obj
    */
   public void addLeft(Object obj) {
      if (offset == 0) {
         set(uc.getMem().ensureCapacity(objects, offset, count + 2, increment));
         //shift them all up by 2 and
         ArrayUtils.shiftUp(objects, 2, offset, offset + count - 1, false);
         offset += 2;
      }
      offset--;
      objects[offset] = obj;
      count++;
   }

   /**
    * Copies the references of this {@link BufferObject} into the array starting at offset
    * @param array
    * @param offset
    */
   public void appendBufferToArrayAt(Object[] array, int offset) {
      int start = this.offset;
      int end = start + count;
      for (int j = start; j < end; j++) {
         array[offset] = objects[j];
         offset++;
      }
   }

   public void appendBufferToArrayAt(Object[] array, int offset, int startOffset, int skip) {
      int start = this.offset;
      int end = start + count;
      for (int j = start; j < end; j++) {
         array[offset] = objects[j];
         offset++;
      }
   }

   /**
    * O(1)
    */
   public void clear() {
      count = 0;
   }

   /**
    * Clone tit for tat
    * @return
    */
   public BufferObject cloneMe() {
      BufferObject ib = new BufferObject(uc);
      ib.increment = increment;
      ib.set(new Object[objects.length]);
      for (int i = 0; i < objects.length; i++) {
         ib.objects[i] = this.objects[i];
      }
      ib.offset = this.offset;
      ib.count = this.count;
      return ib;
   }

   /**
    * 0 based index
    * <br>
    * <br>
    * 
    * @param index
    * @return
    * @throws ArrayIndexOutOfBoundsException
    */
   public Object get(int index) {
      return objects[offset + index];
   }

   /**
    * Returns a truncated copy of the int array with only the integer data.
    * <br> 
    * The size header and trailing buffer are removed.
    * @return
    */
   public Object[] getClonedTrimmed() {
      Object[] ar = new Object[count];
      return getClonedTrimmed(ar);
   }

   public Object[] getClonedTrimmed(Object[] ar) {
      int size = count;
      int index = 0;
      for (int i = offset; i < offset + size; i++) {
         ar[index] = objects[i];
         index++;
      }
      return ar;
   }

   /**
    * Returns the last element in the {@link BufferObject}.
    * <br>
    * <br>
    * Does not remove it.
    * @return null if no elements
    */
   public Object getFirst() {
      if (count > 0) {
         return objects[offset];
      }
      return null;
   }
   
   public Enumeration getEnumeration() {
      EnumerationBase eb = new EnumerationBase(uc);
      eb.setArray(objects, offset, count);
      return eb;
   }

   /**
    * Returns the reference to the integer array. This mean the int[0] still means the number of elements.
    * <br>
    * <br>
    * A direct reference should be used with caution and is inherently unsafe unless
    * you know the implementation details of {@link BufferObject}
    * @return
    */
   public Object[] getIntsRef() {
      return objects;
   }

   public boolean isEmpty() {
      return count == 0;
   }

   /**
    * Returns the last element in the {@link BufferObject}.
    * <br>
    * <br>
    * Does not remove it.
    * @return null if no elements
    */
   public Object getLast() {
      if (count > 0) {
         return objects[offset + count - 1];
      }
      return null;
   }

   public int getLength() {
      return count;
   }

   /**
    * 
    * @param o
    * @return -1 if not found
    */
   public int getObjectIndex(Object o) {
      int start = this.offset;
      int end = start + count;
      for (int j = start; j < end; j++) {
         if (objects[j] == o) {
            return j;
         }
      }
      return -1;
   }

   /**
    * Check if index is right
    * 
    * O(n) complexity
    * 
    * @param index
    * @param o
    * @throws IllegalArgumentException
    */
   public void insertAt(int index, Object o) {
      if (index < 0 || index >= count) {
         throw new IllegalArgumentException("out of bounds " + index);
      }
      set(uc.getMem().ensureCapacity(objects, count, increment));
      ArrayUtils.shiftUp(objects, 1, offset + index, offset + count - 1, false);
      objects[index] = o;
      count++;
   }

   /**
    * Number of elements.
    * @return
    */
   public int getSize() {
      return count;
   }

   /**
    * 
    * @param i
    * @param j
    */
   public void swap(int i, int j) {
      Object temp = objects[offset + i];
      objects[offset + i] = objects[offset + j];
      objects[offset + j] = temp;
   }

   public boolean hasReference(Object o) {
      return getObjectIndex(o) != -1;
   }

   /**
    * Remove size elements starting at index
    * @param index
    * @param size
    */
   public void removeAtIndexFor(int index, int size) {
      int start = offset + index + size;
      int end = offset + count;
      ArrayUtils.shiftDown(objects, size, start, end, false);
      count -= size;
   }

   public BufferObject removeAllForIndex(int index) {
      if (index > count) {
         throw new IllegalArgumentException("index=" + index + " > count=" + count);
      }
      int size = count - index;
      return removeAllForIndex(index, size);
   }

   /**
    * Unsafe. no array array check
    * @param index
    * @param size
    * @param bo
    * @param destIndex
    */
   public void copyFromTo(int index, int size, BufferObject bo, int destIndex) {
      for (int i = 0; i < size; i++) {
         int offsetSrc = this.offset + index + i;
         Object toAdd = this.objects[offsetSrc];
         bo.objects[destIndex + i] = toAdd;
      }
      bo.count += size;
   }

   /**
    * Removes all object from index
    * @param index
    * @param size
    * @return
    */
   public BufferObject removeAllForIndex(int index, int size) {
      BufferObject bo = new BufferObject(uc, size);
      this.copyFromTo(index, size, bo, 0);
      //effectively remve them
      for (int i = offset + index; i < offset + count; i++) {
         this.objects[i] = null;
      }
      count -= size;
      return bo;
   }

   /**
    * Gets object at index and removes it.
    * @param index
    * @return
    */
   public Object removeAtIndex(int index) {
      Object v = get(index);
      removeAtIndexFor(index, 1);
      return v;
   }

   /**
    * 
    * @return null if size is zero
    */
   public Object removeFirst() {
      if (count != 0) {
         Object v = objects[offset];
         objects[offset] = null;
         offset += 1;
         count--;
         return v;
      } else {
         return null;
      }
   }

   /**
    * Shuffle all the content of the buffer
    */
   public void shuffle() {
      uc.getAU().shuffle(objects, offset, count);
   }

   /**
    * Removes the last element and returns it.
    * <br>
    * Returns 0 when {@link BufferObject} is emptys
    * @return
    */
   public Object removeLast() {
      if (count != 0) {
         int index = offset + count - 1;
         Object v = objects[index];
         objects[index] = null;
         count--;
         return v;
      } else {
         return null;
      }
   }

   /**
    * Sets the reference of the buffer.
    * @param ints
    */
   public void set(Object[] objects) {
      this.objects = objects;
   }

   /**
    * Replaces the object/null at index with obj.
    * No other checks are made relative to the size. if index is out of size or not
    * @param index
    * @param obj
    * @throws ArrayIndexOutOfBoundsException
    */
   public void setUnsafe(int index, Object obj) {
      objects[offset + index] = obj;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, BufferObject.class, 380);
      toStringPrivate(dc);
      super.toString(dc.sup());

      dc.append(':');
      for (int i = 0; i < objects.length; i++) {
         dc.nlLvlObject("i=" + i, objects[i]);
      }
   }

   private void toStringPrivate(Dctx dc) {
      dc.append(" offset=" + offset);
      dc.append(" increment=" + increment);
      dc.append(" count=" + count);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, BufferObject.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
