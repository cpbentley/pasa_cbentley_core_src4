/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.structs;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.utils.IntUtils;

/**
 * A array of {@link IntBuffer} giving a Matrix of integers.
 * <br>
 * <br>
 * @author Charles Bentley
 *
 */
public class IntBufferMatrix {

   /**
    * must be 1 or higher
    */
   private int         increment = 5;

   /**
    * index 0 starts at 0 and gives the last used value
    * therefore it also gives the number of elements
    * buffer size = ints.length - 1 - int[0]
    */
   private IntBuffer[] rows;

   /**
    * In order to behave like a tree, rowsize tells the number of relevant ints in the IntBuffer
    * <br>
    * <br>
    * It should be smaller than buffer length at index 0
    * 
    */
   private int[]       rowsize;

   private int         size;

   private UCtx        uc;

   public IntBufferMatrix(UCtx uc) {
      this.uc = uc;
      rows = new IntBuffer[1];
      rowsize = new int[1];
   }

   /**
    * matrix with max rows
    * @param max
    */
   public IntBufferMatrix(int max) {
      if (max < 1)
         max = 1;
      rows = new IntBuffer[max];
      rowsize = new int[max];
   }

   public IntBufferMatrix(IntBuffer[] ar) {
      rows = ar;
      rowsize = new int[ar.length];
   }

   /**
    * Copy content of IntBuffer to non null buffer or create a buffer
    * @param row
    */
   public void addCopyRow(IntBuffer row) {
      ensureCapacity();
      if (rows[getSize()] == null) {
         rows[getSize()] = new IntBuffer(uc,row.getSize() + 1);
      }
      //copy
      rows[getSize()].copyClone(row);
      setSize(getSize() + 1);
   }

   public void addRow(IntBuffer row) {
      ensureCapacity();
      rows[getSize()] = row;
      setSize(getSize() + 1);
   }

   public void addRow(IntBuffer row, int rowsize) {
      addRow(row);
      this.rowsize[getSize() - 1] = rowsize;
   }

   /**
    * Resets the matrix to (0,0). All {@link IntBuffer}s are cleared.
    */
   public void clear() {
      for (int i = 0; i < getSize(); i++) {
         if (rows[i] != null) {
            rows[i].clear();
         }
      }
      setSize(0);
   }

   /**
    * Does the row contains
    * @param row
    * @param i
    * @return
    */
   public boolean contains(int row, int i) {
      return IntUtils.contains(rows[row].getIntsRef(), i, 1, rows[row].getIntsRef()[0]);
   }

   private void ensureCapacity() {
      if (getSize() < rows.length)
         return;
      IntBuffer[] oldData = rows;
      rows = new IntBuffer[oldData.length + increment];
      rowsize = uc.getMem().increaseCapacity(rowsize, increment);
      for (int i = 0; i < oldData.length; i++) {
         System.arraycopy(oldData, 0, rows, 0, oldData.length);
      }
   }

   /**
    * 0 based index;
    * <br>
    * <br>
    * index check returns null if row is invalid.
    * @param row
    * @return
    */
   public IntBuffer getRow(int row) {
      if (row < 0 || row >= rows.length) {
         return null;
      }
      return rows[row];
   }

   /**
    * append 
    * @param row
    * @param col
    */
   public void setInt(int row, int col) {
      IntBuffer brow = rows[row];
      brow.setInts(uc.getMem().ensureCapacity(brow.getIntsRef(), brow.getIntsRef()[0] + 1, increment));
      brow.getIntsRef()[brow.getIntsRef()[0] + 1] = col;
      brow.getIntsRef()[0]++;
   }

   public int getSize() {
      return size;
   }

   public void setSize(int size) {
      this.size = size;
   }
}
