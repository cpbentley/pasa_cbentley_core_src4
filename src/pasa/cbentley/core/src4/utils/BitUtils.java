package pasa.cbentley.core.src4.utils;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.StringBBuilder;

/**
 * Pure utility methods are static.
 * 
 * <br>
 * 
 * Other methods that relate to the context are class members.
 * <li>create a new object
 * <li>
 * @author Charles Bentley
 *
 */
public class BitUtils {

   /**
    * 1 << 0
    * <br>
    * BIT_MASK_XX are simple bit flags.
    * <br>
    * Side note: 0b1; binary literals only for src lvl 7+
    */
   public static int BIT_MASK_01  = 1;

   /**
    * 1 << 1
    */
   public static int BIT_MASK_02  = 2;

   /**
    * 1 << 2
    */
   public static int BIT_MASK_03  = 4;

   /**
    * 1 << 3
    */
   public static int BIT_MASK_04  = 8;

   /**
    * 1 << 4
    */
   public static int BIT_MASK_05  = 16;

   /**
    * 1 << 5
    */
   public static int BIT_MASK_06  = 32;

   /**
    * 1 << 6
    */
   public static int BIT_MASK_07  = 64;

   /**
    * 1 << 7
    */
   public static int BIT_MASK_08  = 128;

   /**
    * 1 << 8
    */
   public static int BIT_MASK_09  = 256;

   public static int BIT_MASK_10  = 512;

   public static int BIT_MASK_11  = 1024;

   public static int BIT_MASK_12  = 2048;

   public static int BIT_MASK_13  = 4096;

   public static int BIT_MASK_14  = 8192;

   public static int BIT_MASK_15  = 16384;

   public static int BIT_MASK_16  = 32768;

   public static int BIT_MASK_17  = 65536;

   public static int BIT_MASK_18  = 131072;

   public static int BIT_MASK_19  = 262144;

   public static int BIT_MASK_20  = 524288;

   public static int BIT_MASK_21  = 1048576;

   public static int BIT_MASK_22  = 2097152;

   public static int BIT_MASK_23  = 4194304;

   public static int BIT_MASK_24  = 8388608;

   public static int BIT_MASK_25  = 16777216;

   public static int BIT_MASK_26  = 33554432;

   public static int BIT_MASK_27  = 67108864;

   public static int BIT_MASK_28  = 134217728;

   public static int BIT_MASK_29  = 268435456;

   /**
    * 1 << 29
    */
   public static int BIT_MASK_30  = 536870912;

   /**
    * 1 << 30
    */
   public static int BIT_MASK_31  = 1073741824;

   /**
    * In Java, the last bit is the sign bit! So 2147483648 is out of range.
    * <br>
    * 1 << 31 is -2147483648
    * <br>
    * <br>
    * Side note: 0b10000000000000000000000000000000; binary literals only for src lvl 7+
    */
   public static int BIT_MASK_32  = -2147483648;

   /**
    * MASK_XX_BITS are mask with XX bits ON
    */
   public static int MASK_00_BITS = 0x0;

   public static int MASK_01_BITS = 0x1;

   public static int MASK_02_BITS = 0x3;

   /**
    * 4 + 2 + 1 = 8 -1
    */
   public static int MASK_03_BITS = 0x7;

   /**
    * 8 + 4 + 2 + 1 = 15 = 16 -1
    */
   public static int MASK_04_BITS = 0xF;

   /**
    * 31
    */
   public static int MASK_05_BITS = 0x1F;

   /**
    * 63
    */
   public static int MASK_06_BITS = 0x3F;

   /**
    * 127
    */
   public static int MASK_07_BITS = 0x7F;

   /**
    * 128 + 64 + 32 + 16 + 8 + 4 + 2+ 1 = 255 = 256-1
    */
   public static int MASK_08_BITS = 0xFF;

   /**
    * 511
    */
   public static int MASK_09_BITS = 0x1FF;

   /**
    * 1023
    */
   public static int MASK_10_BITS = 0x3FF;

   /**
    * 2047
    */
   public static int MASK_11_BITS = 0x7FF;

   /**
    * 4095
    */
   public static int MASK_12_BITS = 0xFFF;

   /**
    * 8191
    */
   public static int MASK_13_BITS = 0x1FFF;

   /**
    * 16383
    */
   public static int MASK_14_BITS = 0x3FFF;

   /**
    * 32767
    */
   public static int MASK_15_BITS = 0x7FFF;

   /**
    * 65535
    */
   public static int MASK_16_BITS = 0xFFFF;

   public static int MASK_24_BITS = 0xFFFFFF;

   public static boolean areBitsEqual(int val1, int val2, int bit) {
      int mask = 1 << (bit - 1);
      return (val1 & mask) == (val2 & mask);
   }

   /**
    * The number of bytes consumed by the integer.
    * <br>
    * Maximum 4
    * @param i
    * @return
    */
   public static int byteConsumed(int i) {
      if (i % 8 == 0)
         return i / 8;
      else
         return (i / 8) + 1;
   }

   public static int byteSize(int w) {
      if (w < 0)
         return 4;
      if (w < 256)
         return 1;
      if (w < 65536)
         return 2;
      if (w < 16777216)
         return 3;
      return 4;
   }

   public static void copyBit(byte[] b, BitCoordinate c, int bit) {
      c.incrementOne();
      c.setBit(b, bit);
   }

   /**
    * Copy one bit at coordinate c, it will be the i th bit from value.
    * @param b
    * @param c
    * @param value
    * @param numbit
    */
   public static void copyBit(byte[] b, BitCoordinate c, int value, int numbit) {
      c.incrementOne();
      int bit = getBit(numbit, value);
      c.setBit(b, bit);
   }

   /**
    * 
    * @param source
    * @param target
    * @param sourceCoord where to copy the first bit
    * @param targetCoord where to paste the first bit
    * @param sourceSize the number of bits to copy
    */
   public static void copyBits(byte[] source, byte[] target, BitCoordinate sourceCoord, BitCoordinate targetCoord, int sourceSize) {
      for (int i = 1; i <= sourceSize; i++) {
         sourceCoord.forwardOne();
         targetCoord.forwardOne();
         int bit = sourceCoord.getBit(source);
         targetCoord.setBit(target, bit);
      }
   }

   /**
    * Copy in b at coordinate c, numbits of value starting from least significant
    * 
    * example
    * if you have to copy 6 '110' in 00000000 at coordinate 1.0
    * the least significant is 0 copied at first position
    * we will have 01100000 after the copy
    * reading will start 0 then 1 then 1 to build 011
    * <br>
    * <br>
    * @param b
    * @param c copy at bitnum + 1 for the first bit
    * @param value the value to add
    * @param numbit the number of bits to keep
    * @return same array, Coordinate c is updated
    */
   public static byte[] copyBits(byte[] b, BitCoordinate c, int value, int numbit) {
      for (int i = 1; i <= numbit; i++) {
         c.forwardOne();
         int bit = getBit(i, value);
         c.setBit(b, bit);
      }
      return b;
   }

   public static byte[] copyHighBits(byte[] b, BitCoordinate c, int value, int numbit) {
      for (int i = 32; i > 32 - numbit; i--) {
         c.incrementOne();
         int bit = getBit(i, value);
         c.setBit(b, bit);
      }
      return b;
   }

   /**
    * Counts number of 1 bits in a 32 bit unsigned number.
    *
    * @param x unsigned 32 bit number whose bits you wish to count.
    *
    * @return number of 1 bits in x.
    * @author Roedy Green email
    */
   public static int countBits(int x) {
      // collapsing partial parallel sums method
      // collapse 32x1 bit counts to 16x2 bit counts, mask 01010101
      x = (x >>> 1 & 0x55555555) + (x & 0x55555555);
      // collapse 16x2 bit counts to 8x4 bit counts, mask 00110011
      x = (x >>> 2 & 0x33333333) + (x & 0x33333333);
      // collapse 8x4 bit counts to 4x8 bit counts, mask 00001111
      x = (x >>> 4 & 0x0f0f0f0f) + (x & 0x0f0f0f0f);
      // collapse 4x8 bit counts to 2x16 bit counts
      x = (x >>> 8 & 0x00ff00ff) + (x & 0x00ff00ff);
      // collapse 2x16 bit counts to 1x32 bit count
      return (x >>> 16) + (x & 0x0000ffff);
   }

   /**
    * ddddaXbaXbddd becomes dddaYbaYbddd 
    * A chunk is aXb. This method expands the bitsize X to Y for each chunks
    * The first chunk is at Coordinate c.
    * @param data raw data
    * @param BitCoordinate c coordinate of first chunk
    * @param numofchunks the number of chunks
    * @param aBitSize
    * @param xBitSize
    * @param bBitSize
    * @param yBitSize
    * @param lastusedbit the last bit of data to be shifted
    * @return the new array
    * 
    * ddddaXbaXbaXbddd
    * 1)First chunk to be shifted is bddd. shifted up by (Y-X)= diff * numofchunks
    * 2)then baX is shifted up by diff * (numofChunks -1)
    * 3)then baX is shifted up by diff * (numofChunks -2)
    * 4)the last aX is not shifted
    * The shift erases old data in order to provide clean 0 bits for expanded data
    * 
    * PRE: enough free capacity after lastusedbit (diff * numofchunks bits)
    * otherwise data will be overwritten or exception will be thrown
    * POST: reference array data is not changed
    * 
    *  11010
    */
   public void expandBitSize(byte[] data, BitCoordinate c, int numofchunks, int aBitSize, int xBitSize, int bBitSize, int yBitSize, int lastusedbit) {
      //System.out.println(BitMask.toBinString(data, 4));
      int diff = yBitSize - xBitSize;
      int _bitChunkSize = aBitSize + xBitSize + bBitSize;
      //get to the last one
      c.forward(_bitChunkSize * (numofchunks - 1));
      c.forward(aBitSize + xBitSize);
      //+2 = +1 for bit lost in coordindate c
      int bitnum = c.unmap() + 1;
      int lastbit = lastusedbit;
      //System.out.println(c.unmap() + " bitnum="+bitnum);
      //System.out.println(BitMask.toBinString(data, 4));
      //this frees up the memory for the new data.
      shiftBitsUp(data, diff * numofchunks, bitnum, lastbit, true);
      //System.out.println(BitMask.toBinString(data, 4));
      //this loop will shift chunk individually. starting from the last
      for (int i = 1; i < numofchunks; i++) {
         //coord is at b, rewind to get to previous b
         c.rewind(_bitChunkSize);
         //System.out.println(c);
         //shift is decreasing with i
         int bitShift = diff * (numofchunks - i);
         //first bit is first bit of b
         bitnum = c.unmap() + 1;
         lastbit = bitnum + _bitChunkSize - 1;
         //System.out.println("bitnum="+bitnum + " lastbit="+lastbit);
         shiftBitsUp(data, bitShift, bitnum, lastbit, true);
         //System.out.println(BitMask.toBinString(data, 4));

      }
      //the last aX does not move
      //System.out.println("END");
   }

   /**
    * 
    * @param data
    * @param start
    * @param numofchunks
    * @param aBitSize
    * @param xBitSize
    * @param bBitSize
    * @param yBitSize
    * @param lastusedbyte last byte of relevant data
    */
   public void expandByteSize(byte[] data, int start, int numofchunks, int aBitSize, int xBitSize, int bBitSize, int yBitSize, int lastusedbyte) {
      int diff = yBitSize - xBitSize;
      int _bitChunkSize = aBitSize + xBitSize + bBitSize;
      //start by shifting the farthest from start
      start += (_bitChunkSize * (numofchunks - 1));
      start += (aBitSize + xBitSize);
      int lastbit = lastusedbyte;
      if (start <= lastbit)
         shiftBytesUp(data, diff * numofchunks, start, lastbit);
      for (int i = 1; i < numofchunks; i++) {
         start -= (_bitChunkSize);
         int bitShift = diff * (numofchunks - i);
         lastbit = start + _bitChunkSize - 1;
         shiftBytesUp(data, bitShift, start, lastbit);
      }
   }

   public static int getBit(int bitnumber, int value) {
      return (value >> (bitnumber - 1)) & 0x1;
   }

   /**
    * Get 00001100 you do 2,0x03
    * <code> 
    * getBits(x,2)
    * </code>
    * @param val
    * @param pos
    * @param bw
    * @param mask
    * @return
    */
   public static int getBits(int val, int pos, int bw, int mask) {
      return (val >> pos) & mask;
   }

   /**
    * Reads the data in val bit position pos, reads bitwidth bits upwards
    * @param val
    * @param pos
    * @param bitwidth
    * @return
    */
   public static int getData(int val, int pos, int bitwidth) {
      return (val >>> pos) & getMask(bitwidth);
   }

   public static int getDataOutOfBitMask(int data, int singleBitMask) {
      return data & ~singleBitMask;
   }

   /**
    * 
    * @param bitwidth
    * @return 0xFF if 8, 0xFFFFFFFF if 32
    */
   public static int getMask(int bitwidth) {
      int val = 0;
      for (int i = 0; i < bitwidth; i++) {
         val = val + (1 << i);
      }
      return val;
   }

   public static int getMaxByteSize(int v1, int v2, int v3, int v4) {
      int byteSize = 1;
      if (BitUtils.byteSize(v1) > byteSize)
         byteSize = BitUtils.byteSize(v1);
      if (BitUtils.byteSize(v2) > byteSize)
         byteSize = BitUtils.byteSize(v2);
      if (BitUtils.byteSize(v3) > byteSize)
         byteSize = BitUtils.byteSize(v3);
      if (BitUtils.byteSize(v4) > byteSize)
         byteSize = BitUtils.byteSize(v4);
      return byteSize;
   }

   public static int getMaxByteSize(int[] values) {
      if (values == null)
         return 0;
      int byteSize = 1;
      for (int i = 0; i < values.length; i++) {
         int bs = BitUtils.byteSize(values[i]);
         if (bs > byteSize) {
            byteSize = bs;
         }
      }
      return byteSize;
   }

   public static boolean hasFlag(int root, int flag) {
      return (root & flag) == flag;
   }

   /**
    * <li> {@link BitUtils#BIT_MASK_01}
    * <li> {@link BitUtils#BIT_MASK_32}
    * 
    * @param val
    * @param bitMask
    * @return
    */
   public static boolean isBitMaskSet(int val, int bitMask) {
      return (val & bitMask) == bitMask;
   }

   /**
    * 
    * @param val
    * @param bit 1,2,3,4,5,6   32
    * @return
    */
   public static boolean isBitSet(int val, int bit) {
      int mask = 1 << (bit - 1);
      return (val & mask) == mask;
   }

   /**
    * Tells if the first different bit is 1 (true) or 0 (false)
    * @param base
    * @param compare
    * @return
    */
   public static boolean isFirstRight(int base, int compare) {
      if (base == 0)
         return isBitSet(compare, 2);
      int len = widthInBits(base);
      return isBitSet(compare, len + 1);
   }

   public static boolean isSet(int val, int mask) {
      return (val & mask) == mask;
   }

   public static boolean isEven(int val) {
      return (val & 1) == 0;
   }

   public static boolean isOdd(int val) {
      return (val & 1) != 0;
   }

   /**
    * Burn the value in the first bits of data
    * @param data
    * @param value
    * @return
    */
   public static int setByte1(int data, int value) {
      int shifted = ((value & 0xFF) << 24);
      int datan = (data & 0x00FFFFFF);
      return shifted + datan;
   }

   public static int setByte2(int data, int value) {
      return ((value & 0xFF) << 16) + (data & 0xFF00FFFF);
   }

   public static int setByte3(int data, int value) {
      return ((value & 0xFF) << 8) + (data & 0xFFFF00FF);
   }

   public static int setByte4(int data, int value) {
      return ((value & 0xFF) << 0) + (data & 0xFFFFFF00);
   }

   public static int readBit(byte[] b, BitCoordinate c) {
      c.incrementOne();
      return c.getBit(b);
   }

   /**
    * Read numbits number of bits and create a Integer.
    * Little-Endian.
    * @param b
    * @param c
    * @param numbits
    * @return
    */
   public static int readBits(byte[] b, BitCoordinate c, int numbits) {
      int val = 0;
      for (int i = 1; i <= numbits; i++) {
         c.incrementOne();
         val = setBit(val, i, c.getBit(b));
      }
      return val;
   }

   public static int readBits(byte[] b, int bitsnum, int numbits) {
      int val = 0;
      int bytenum = (bitsnum - 1) / 8;
      int bitnum = (bitsnum % 8);
      if (bitnum == 0)
         bitnum = 8;
      for (int i = 1; i <= numbits; i++) {
         if (bitnum + 1 > 8) {
            bytenum++;
            bitnum = 1;
         } else {
            bitnum++;
         }
         val = setBit(val, i, getBit(bitnum, b[bytenum]));
      }
      return val;
   }

   public static int set(int val, int mask) {
      return val | mask;
   }

   /**
    * 
    * @param data
    * @param position 1 for first bit
    * @param bit
    * @return
    */
   public static int setBit(int data, int position, int bit) {
      return setData(data, position - 1, 1, bit);
   }

   public static int setBitMaskOnData(int data, int singleBitMask) {
      return data | singleBitMask;
   }

   /**
    * Sets the data first x (=bithwidth) bits 
    * @param val
    * @param pos
    * @param bitwidth
    * @param data
    * @return
    */
   public static int setBits(int val, int pos, int bitwidth, int mask, int data) {
      return (val & ~(mask << pos)) + ((data & mask) << pos);
   }

   /**
    * PRE: data cannot be higher than 2^bitwidth
    * @param val recipient for the new data 
    * @param pos bit position of the new data
    * @param bitwidth bitwidth of the data
    * @param data the value
    * @return
    */
   public static int setData(int val, int pos, int bitwidth, int data) {
      int mask = getMask(bitwidth);
      return (val & ~(mask << pos)) + ((data & mask) << pos);
   }

   public static void setFlag(byte[] data, int index, int flag, boolean set) {
      data[index] = (byte) setFlag(data[index], flag, set);
   }

   public static int setFlag(int root, int flag, boolean set) {
      if (set)
         root = root | flag;
      else
         root = root & ~flag;
      return root;
   }

   /**
    * Shift shiftsize bits up (if positive, down if negative)
    * @param b
    * @param shiftsize negative is shifted down, positive is shifted up
    * @param offsetbit first bit inclusive to be shifted
    * @param lastbit last bit inclusive to be shifted
    * @param erase
    */
   public void shiftBits(byte[] b, int shiftsize, int offsetbit, int lastbit, boolean erase) {
      if (shiftsize > 0) {
         shiftBitsUp(b, shiftsize, offsetbit, lastbit, erase);
      }
      if (shiftsize < 0) {
         shiftBitsDown(b, 0 - shiftsize, offsetbit, lastbit, erase);
      }
   }

   /**
    * Shift a chunk of bits down
    * If shiftsize is a multiple of 8, data is shifted exactly this multiple of bytes
    * @param b
    * @param shiftsize if shiftsize does too far,offsetbit will have position 0
    * @param offsetbit
    * @param lastbit
    * @param erase
    */
   public void shiftBitsDown(byte[] b, int shiftsize, int offsetbit, int lastbit, boolean erase) {
      int re = shiftsize + 1;
      int f = shiftsize;
      if (offsetbit - re < 0) {
         offsetbit = re;
      }
      BitCoordinate workc = new BitCoordinate(uc);
      workc.map(offsetbit - 1);
      for (int i = offsetbit; i <= lastbit; i++) {
         int bit = BitUtils.readBit(b, workc);
         workc.rewind(re);
         BitUtils.copyBit(b, workc, bit);
         workc.forward(f);
      }
      if (erase) {
         BitCoordinate c = new BitCoordinate(uc);
         c.map(lastbit - shiftsize);
         for (int i = shiftsize; i >= 1; i--) {
            BitUtils.copyBit(b, c, 0);
         }
      }
   }

   /**
    * Most basic shift where shiftsize of bits are shifted shiftsize starting by offsetbit inclusive
    * @param b
    * @param shiftsize
    * @param offsetbit
    */
   public void shiftBitsUp(byte[] b, int shiftsize, int offsetbit) {
      shiftBitsUp(b, shiftsize, offsetbit, offsetbit + shiftsize - 1, false);
   }

   /**
    * Shift by numbits bit on the left
    * offsetbit and lastbit are included in the shift
    * @param b
    * @param shiftsize the stride of the shift
    * @param offsetbit
    * @param lastbit
    * reset to zero 
    */
   public void shiftBitsUp(byte[] b, int shiftsize, int offsetbit, int lastbit) {
      shiftBitsUp(b, shiftsize, offsetbit, lastbit, false);
   }

   /**
    * xxx offsetbit xxx lastbit
    * Shift by numbits bit on the left
    * offsetbit and lastbit are included in the shift
    * @param data array
    * @param shiftsize the number of bits to be shifted to the left
    * @param offsetbit the first bit to be shifted
    * @param lastbit the last bit included. if lastbit=offset bit (say 1), that bit will be shifted up
    * @param erase
    */
   public void shiftBitsUp(byte[] data, int shiftsize, int offsetbit, int lastbit, boolean erase) {
      //	if (offsetbit == lastbit)
      //	   return;
      int f = shiftsize - 1;
      int r = 3 + f;
      //
      if (lastbit + f > data.length * 8) {
         lastbit = lastbit - shiftsize;
      }
      BitCoordinate workc = new BitCoordinate(uc);
      workc.map(lastbit - 1);
      for (int i = lastbit; i >= offsetbit; i--) {
         int bit = BitUtils.readBit(data, workc);
         workc.forward(f);
         BitUtils.copyBit(data, workc, bit);
         workc.rewind(r);
      }
      if (erase) {
         BitCoordinate c = new BitCoordinate(uc);
         c.map(offsetbit - 1);
         for (int i = 1; i <= shiftsize; i++) {
            BitUtils.copyBit(data, c, 0);
         }
      }
   }

   /**
    * Shift [firstbyte - lastbyte] down of shiftsize bytes
    * 
    * erase up shiftsize bytes starting at lastbyte
    * @param data
    * @param shiftsize
    * @param firstbyte
    * @param lastbyte if equal to firstbyte, only one byte is shifted down
    */
   public void shiftBytesDown(byte[] data, int shiftsize, int firstbyte, int lastbyte) {
      lastbyte = Math.min(lastbyte, data.length - 1);
      for (int i = firstbyte; i <= lastbyte; i++) {
         if (i - shiftsize >= 0)
            data[i - shiftsize] = data[i];
      }
      int end = Math.min(shiftsize + lastbyte, data.length);
      for (int i = lastbyte; i < end; i++) {
         data[i] = 0;
      }
   }

   /**
    * Shift bytes up.
    * erase old
    * @param data
    * @param shiftsize
    * @param firstbyte included in the shift
    * @param lastbyte included in the shift
    */
   public void shiftBytesUp(byte[] data, int shiftsize, int firstbyte, int lastbyte) {
      if (firstbyte > lastbyte) {
         throw new RuntimeException("firstbyte " + firstbyte + " > " + " lastybte " + lastbyte);
      }
      for (int i = lastbyte; i >= firstbyte; i--) {
         if (i + shiftsize < data.length)
            data[i + shiftsize] = data[i];
      }
      int end = Math.min(shiftsize + firstbyte, data.length);
      for (int i = firstbyte; i < end; i++) {
         data[i] = 0;
      }
   }

   /** 
    * Rotates array so that ith value goes to i+value.
    */
   public void rotateArray(byte[] array, int value) {
      int j = value;
      int i = value;
      int num = array.length;
      while (i != 0) {
         int m = j;
         j = (j + value) % num;
         byte b = array[m];
         array[m] = array[j];
         array[j] = b;
         if (j == i) {
            j = --i;
         }
      }
   }

   /**
    * From a bit string, get the integer value
    * @param bits a bit string 0010011 
    * @return int value
    */
   public static int toInteger(String bits) {
      int val = 0;
      int expo = 1;
      for (int i = bits.length() - 1; i >= 0; i--) {
         int bit = Integer.parseInt("" + bits.charAt(i));
         if (bit == 1) {
            val += expo;
         }
         expo = expo * 2;
      }
      return val;
   }

   /**
    * Calculate how many bits wide a number is,
    * i.e. position of highest 1 bit.
    * Fully unraveled binary search method.
    * @return p where 2**p is first power of two >= n.
    * e.g. binary 0001_0101 -> 5, 0xffffffff -> 32,
    * 0 -> 0, 1 -> 1, 2 -> 2, 3 -> 2, 4 -> 3
    * @author Dirk Bosmans Dirk.Bosmans@tijd.com */
   public static int widthInBits(int n) {
      if (n < 0)
         return 32;
      if (n > 0x0000ffff) {
         if (n > 0x00ffffff) {
            if (n > 0x0fffffff) {
               if (n > 0x3fffffff) {
                  // if ( n > 0x7fffffff )
                  // return 32
                  // else
                  return 31;
               } else {
                  // !( n > 0x3fffffff )
                  if (n > 0x1fffffff)
                     return 30;
                  else
                     return 29;
               }
            } else {
               // !( n > 0x0fffffff )
               if (n > 0x03ffffff) {
                  if (n > 0x07ffffff)
                     return 28;
                  else
                     return 27;
               } else {
                  // !( n > 0x03ffffff )
                  if (n > 0x01ffffff)
                     return 26;
                  else
                     return 25;
               }
            }
         } else {
            // !( n > 0x00ffffff )
            if (n > 0x000fffff) {
               if (n > 0x003fffff) {
                  if (n > 0x007fffff)
                     return 24;
                  else
                     return 23;
               } else {
                  // !( n > 0x003fffff )
                  if (n > 0x001fffff)
                     return 22;
                  else
                     return 21;
               }
            } else {
               // !( n > 0x000fffff )
               if (n > 0x0003ffff) {
                  if (n > 0x0007ffff)
                     return 20;
                  else
                     return 19;
               } else {
                  // !( n > 0x0003ffff )
                  if (n > 0x0001ffff)
                     return 18;
                  else
                     return 17;
               }
            }
         }
      } else {
         // !( n > 0x0000ffff )
         if (n > 0x000000ff) {
            if (n > 0x00000fff) {
               if (n > 0x00003fff) {
                  if (n > 0x00007fff)
                     return 16;
                  else
                     return 15;
               } else {
                  // !( n > 0x00003fff )
                  if (n > 0x00001fff)
                     return 14;
                  else
                     return 13;
               }
            } else {
               // !( n > 0x00000fff )
               if (n > 0x000003ff) {
                  if (n > 0x000007ff)
                     return 12;
                  else
                     return 11;
               } else {
                  // !( n > 0x000003ff )
                  if (n > 0x000001ff)
                     return 10;
                  else
                     return 9;
               }
            }
         } else {
            // !( n > 0x000000ff )
            if (n > 0x0000000f) {
               if (n > 0x0000003f) {
                  if (n > 0x0000007f)
                     return 8;
                  else
                     return 7;
               } else {
                  // !( n > 0x0000003f )
                  if (n > 0x0000001f)
                     return 6;
                  else
                     return 5;
               }
            } else {
               // !( n > 0x0000000f )
               if (n > 0x00000003) {
                  if (n > 0x00000007)
                     return 4;
                  else
                     return 3;
               } else {
                  // !( n > 0x00000003 )
                  if (n > 0x00000001)
                     return 2;
                  return n;
                  /*
                   else if ( n > 0x00000000 )
                   return 1;
                   else
                   return 0;
                   */
               }
            }
         }
      }
   } // end widthInBits

   private UCtx uc;

   public BitUtils(UCtx uc) {
      this.uc = uc;
   }

   /**
    * Single line binary string of bytes separated by a ' ' every chunkLen bits.
    * @param ar
    * @param offset
    * @param len
    * @param chunkLen number of bits before printing a space
    * @return
    */
   public String toBinStringRightToLeft(byte[] ar, int offset, int len, int chunkLen) {
      //we need a string builder factory
      StringBBuilder sb = new StringBBuilder(uc, len * 8 + 1 + len / chunkLen);
      int count = 0;
      for (int i = offset; i < offset + len; i++) {
         int val = ar[i] & 0xFF;
         for (int j = 1; j <= 8; j++) {
            int bit = BitUtils.getBit(j, val);
            sb.append(bit);
            count++;
            if (count == chunkLen) {
               count = 0;
               sb.append(' ');
            }
         }
      }
      return sb.toString();
   }

   public String toStringBytes(byte[] ar, int cols) {
      return toStringBytes(ar, 0, ar.length, cols);
   }

   /**
    * string of the array with columns
    * @param ar
    * @param offset
    * @param len
    * @param cols
    * @return
    */
   public String toStringBytes(byte[] ar, int offset, int len, int cols) {
      StringBBuilder sb = new StringBBuilder(uc);
      int count = 0;
      for (int i = offset; i < offset + len; i++) {
         sb.append(ar[i]);
         count++;
         if ((count % cols) == 0) {
            sb.nl();
         } else {
            sb.append(' ');
         }
      }
      return sb.toString();
   }

   public int compare(byte[] word, byte[] ar) {
      return compare(word, ar, 0);
   }

   public int compare(byte[] word, byte[] ar, int base) {
      return compare(word, ar, base, base + ar.length);
   }

   /**
    * Compare a sequence of byte one by one
    * return -1 if word is smaller than source at position srcI
    * @param source the main array
    * @param word the word to check
    * @param srcI the starting position of source for the comparison
    * @param wordI start position in word
    * @param len how many bytes to check. starting at 0
    * @return
    */
   public int compareByteArray(byte[] source, byte[] word, int srcI, int wordI, int len) {
      for (int i = 0; i < len; i++) {
         if (source[srcI + i] > word[wordI + i]) {
            return -1;
         } else if (source[srcI + i] == word[wordI + i]) {
            //check next byte
            continue;
         } else {
            return 1;
         }
      }
      return 0;
   }

   /**
    * Shrink array source. Removes "size" bytes starting at index
    * @param source
    * @param dest
    * @param index
    * @return
    * @throws ArrayIndexOutOfBoundsException if argument don't fit
    */
   public byte[] shrinkArrayAtOf(byte[] source, int index, int size) {
      byte[] fi = uc.getMem().createByteArray(source.length - size);
      for (int i = 0; i < index; i++) {
         fi[i] = source[i];
      }
      for (int i = index; i < fi.length; i++) {
         fi[i] = source[i + size];
      }
      return fi;
   }

   /**
    * 
    * @param word
    * @param ar
    * @param base
    *            the offset of ar to begin with
    * @param end
    *            the number of bytes that makes the ar word
    * @return
    */
   public int compare(byte[] word, byte[] ar, final int base, final int end) {
      int compare = 0;
      int wl = word.length;
      for (int i = 0; i < wl; i = i + 2) {
         if (i >= end)
            return 1; // case of xva xv
         int c = (word[i] << 8) + (word[i + 1] << 0);
         int car = (ar[base + i] << 8) + (ar[base + i + 1] << 0);
         if (c > car)
            return 1;
         if (c < car)
            return -1;
      }
      if (wl < end - base)
         return -1; // case of xv xva
      return compare;
   }

   /**
    * 
    * @param ar
    * @return
    */
   public String toBinString(byte[] ar) {
      return toBinString(ar, 0);
   }

   /**
    * Return a bit visualization of the byte array.
    * <br>
    * 
    * @param ar
    * @param space when different than 0, introduce a space after <code>space</code> characters
    * @return
    */
   public String toBinString(byte[] ar, int space) {
      int size = (ar.length * 8);
      if (space != 0) {
         size += ((size / space)); //increase size
      }
      char[] car = new char[size];
      int count = 0;
      int counts = 0;
      for (int i = 0; i < ar.length; i++) {
         for (int k = 1; k <= 8; k++) {
            if (BitUtils.getBit(k, ar[i]) == 0) {
               car[count] = '0';
            } else {
               car[count] = '1';
            }
            count++;
            counts++;
            if (space != 0) {
               if (counts % (space) == 0) {
                  car[count] = ' ';
                  count++;
               }
            }
         }
      }
      return new String(car);
   }

   /**
    * Padds with 0s to reach at least 8 characters
    * @param b
    * @return
    */
   public String toBinString8(int b) {
      String s = Integer.toBinaryString(b);
      int l = s.length();
      for (int i = l; i < 8; i++) {
         s = "0" + s;
      }
      return s;
   }

   /**
    * Padds with 0s to reach at least <code>pad</code> characters
    * @param b
    * @param pad
    * @return
    */
   public String toBinStringPadded(int b, int pad) {
      String s = Integer.toBinaryString(b);
      int l = s.length();
      for (int i = l; i < pad; i++) {
         s = "0" + s;
      }
      return s;
   }

   public String toStringBytes(byte[] ar, int offset, String sep) {
      if (ar == null) {
         return "null";
      }
      StringBBuilder sb = new StringBBuilder(uc);
      for (int i = offset; i < ar.length; i++) {
         if (i != 0)
            sb.append(sep);
         sb.append(ar[i]);
      }
      return sb.toString();
   }

   public String debugString(byte[] ar, String sep) {
      return debugString(ar, 0, sep);
   }

   public String debugString(byte[] ar, int offset, String sep) {
      if (ar == null) {
         return "null";
      }
      StringBBuilder sb = new StringBBuilder(uc);
      for (int i = offset; i < ar.length; i++) {
         if (i != 0)
            sb.append(sep);
         sb.append(ar[i]);
      }
      return sb.toString();
   }

   public String toStringBytes(byte[] ar, String sep) {
      return toStringBytes(ar, 0, sep);
   }

   /**
    * Prints the bytes of the array using {@link BitUtils#toBinString8(int)}
    * @param ar
    * @param offset
    * @param len
    * @return
    */
   public String toBiString8(byte[] ar, int offset, int len) {
      StringBBuilder sb = new StringBBuilder(uc, 8 * len + 10);
      for (int i = offset; i < offset + len; i++) {
         int val = ar[i] & 0xFF;
         sb.append(toBinString8(val));
         sb.nl();
      }
      return sb.toString();
   }
}
