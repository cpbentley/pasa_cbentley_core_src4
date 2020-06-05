/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.utils;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Encapsulates the position of Bit in a byte array, i.e. the coordinate of a bit value in a byte array
 * <br>
 * <br>
 * <li> x = bytenum i.e. the index on the byte array
 * <li> y = bitnum i.e. the bit number: bit number of 1 = first bit, 2 = 2nd bit .. 8 = last bit. 1 based index
 * <br>
 * @author Charles Bentley
 *
 */
public class BitCoordinate implements IStringable {

   /** 
    * y  is never 0 as it is a one based index
    */
   private int  bitnum;

   /** 
    * x can be 0 
    */
   private int  bytenum;

   private int  saveBitNum;

   private int  saveByteNum;

   private final UCtx uc;

   /**
    * Start at 0,0
    */
   public BitCoordinate(UCtx uc) {
      this.uc = uc;
   }

   /**
    * Start coordinate in bytes/bit
    * @param bytes
    * @param bit
    */
   public BitCoordinate(UCtx uc, int bytes, int bit) {
      this.uc = uc;
      bytenum = bytes;
      bitnum = bit;
   }

   public int getBitnum() {
      return bitnum;
   }

   public void reset() {
      bitnum = 0;
      bytenum = 0;
   }

   public int getBytenum() {
      return bytenum;
   }

   /**
    * Returns the bit at the current coordinate position.
    * @param b
    * @return
    */
   public int getBit(byte[] b) {
      return BitUtils.getBit(bitnum, b[bytenum]);
   }

   public void forwardOne() {
      if (bitnum + 1 > 8) {
         bytenum++;
         bitnum = 1;
      } else {
         bitnum++;
      }
   }

   public Object clone() {
      BitCoordinate c = new BitCoordinate(uc);
      c.bitnum = bitnum;
      c.bytenum = bytenum;
      c.saveBitNum = saveBitNum;
      c.saveByteNum = saveByteNum;
      return c;
   }

   public void incrementOne() {
      if (bitnum + 1 > 8) {
         bytenum++;
         bitnum = 1;
      } else {
         bitnum++;
      }
   }

   /**
    * Sets the current bit to 0 or 1 if bit is different 0
    * @param bit
    * @return
    */
   public void setBit(byte[] data, int bit) {
      data[bytenum] = (byte) BitUtils.setData(data[bytenum], bitnum - 1, 1, bit);
   }

   public boolean isLastBit() {
      return bitnum + 1 > 8;
   }

   /**
    * Write the next bit of this coordinat
    * @param b
    * @param bit
    */
   public void copyBit(byte[] b, int bit) {
      incrementOne();
      setBit(b, bit);
   }

   /**
    * Copy one bit at coordinate c, it will be the i th bit from value.
    * @param b
    * @param c
    * @param value
    * @param numbit
    */
   public void copyBit(byte[] b, int value, int numbit) {
      incrementOne();
      int bit = BitUtils.getBit(numbit, value);
      setBit(b, bit);
   }

   /**
    * 
    * @param c
    * @return 
    * -1 if c is <
    *  0 if equal
    *  1 if bigger
    */
   public int compare(BitCoordinate c) {
      if (this.bytenum < c.bytenum)
         return -1;
      if (this.bytenum == c.bytenum) {
         if (this.bitnum < c.bitnum)
            return -1;
         if (this.bitnum == c.bitnum)
            return 0;
      }
      //bigger
      return 1;
   }

   /**
    * Saves the current pointer positions
    */
   public void tick() {
      saveBitNum = bitnum;
      saveByteNum = bytenum;
   }

   /**
    * Sets the saved tick positions back the current
    */
   public void tack() {
      bitnum = saveBitNum;
      bytenum = saveByteNum;
   }

   /**
    * return the byte address of a bit address
    * @param bitnum
    * @return
    */
   public static int getByte(int bitnum) {
      if (bitnum == 0)
         return 0;
      return (bitnum - 1) / 8;
   }

   public void map(int bitnum) {
      if (bitnum == 0) {
         this.bitnum = 0;
         bytenum = 0;
         return;
      }
      // For the RECORD:old implementation because we think it less performant
      //	   int count = 0;
      //	   int left = bitnum;
      //	   while ((left - 8) > 0) {
      //		count++;
      //		left -= 8;
      //	   }
      //	   this.bytenum = count;
      //	   this.bitnum = left;
      this.bytenum = (bitnum - 1) / 8;
      this.bitnum = (bitnum % 8);
      if (this.bitnum == 0)
         this.bitnum = 8;
   }

   /**
    * Current bit position
    * @return
    * After a read/write operation, return the last bit copied
    * Remember:reads/writes start at unmap() + 1 
    */
   public int unmap() {
      return (bytenum * 8) + bitnum;
   }

   /**
    * Move Coordinate foward (right) of bits
    * <br>
    * <br>
    * @param familyForward
    */
   public void forward(int bits) {
      if (bits == 0)
         return;
      if (bits < 0) {
         rewind(-bits);
         return;
      }
      int bytes = (bits - 1) / 8;
      int bit = (bits % 8);
      if (bit == 0)
         bit = 8;
      // value ranges from [2 to 16]
      int addbit = this.bitnum + bit;
      if (addbit > 8) {
         bytes++;
         if (addbit == 16)
            this.bitnum = 8;
         else
            this.bitnum = addbit - 8;
      } else {
         this.bitnum = addbit;
      }
      this.bytenum += bytes;
   }

   public void rewind(int bits) {
      if (bits == 0)
         return;
      if (bits < 0) {
         forward(-bits);
         return;
      }
      int bytes = (bits - 1) / 8;
      int bit = (bits % 8);
      if (bit == 0)
         bit = 8;
      // value ranges from [2 to 16]
      int rembit = this.bitnum - bit;
      if (rembit < 1) {
         if (bytenum == 0 || bytenum - bytes == 0) {
            this.bytenum = 0;
            this.bitnum = 0;
            return;
         }
         bytes++;
         if (rembit == 0)
            this.bitnum = 8;
         else
            this.bitnum = 8 + rembit;
      } else {
         this.bitnum = rembit;
      }
      this.bytenum -= bytes;
      if (this.bytenum < 0)
         this.bytenum = 0;
   }

   public void setByteAndBit(int bytenum, int bitnum) {
      this.bytenum = bytenum;
      this.bitnum = bitnum;
   }

   /**
    * Returns interval between saved and current position in bits
    * <br>
    * <br>
    * 
    * @return negative if current position is before saved
    */
   public int getInterval() {
      int bitlen = (this.bytenum * 8) + this.bitnum - ((saveByteNum * 8) + saveBitNum);
      return bitlen;
   }

   /**
    * Any bording bits are counting for 1 byte
    * <br>
    * <br>
    * 
    * @return
    */
   public int getIntervalBytes() {
      int byteLen = this.bytenum - saveByteNum;
      if (bitnum != 0) {
         byteLen++;
      }
      return byteLen;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BitCoordinate");
      toStringPrivate(dc);
      dc.appendVarWithSpace("saveByteNum", saveByteNum);
      dc.appendVarWithSpace("saveBitNum", saveBitNum);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {
      dc.append(this.bytenum + ":" + this.bitnum);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BitCoordinate");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug
}