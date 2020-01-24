package pasa.cbentley.core.src4.io;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.UTFDataFormatException;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * 
 * {@link BADataIS} stands for ByteArrayDataInputStream.
 * 
 * It only accepts {@link BADataOS}
 * 
 * Not thread safe. synchronize externally.
 * 
 * Why overriding the {@link java.io.DataInputStream} ?
 * <br>
 *  We wanted a {@link DataInputStream} and {@link DataOutputStream} without checked exceptions
 *  and low bytes first for non char data. Litte Endian! While {@link DataOutputStream} is big endian.
 *  <br>
 *  <br>
 *  ==
 *  <br>
 *  Therefore, DataBA streams are not compatible
 *  <br>
 *  The checked exception model says you are supposed to try to recover from a InputStream read. How?
 *  <br>
 *  It is never possible to recover an {@link IOException} except outputing an error message to the User.
 * @author Charles Bentley
 *
 */
public class BADataIS implements DataInput, IStringable {

   /**
    * Reads from the
    * stream <code>in</code> a representation
    * of a Unicode  character string encoded in
    * <a href="DataInput.html#modified-utf-8">modified UTF-8</a> format;
    * this string of characters is then returned as a <code>String</code>.
    * The details of the modified UTF-8 representation
    * are  exactly the same as for the <code>readUTF</code>
    * method of <code>DataInput</code>.
    *
    * @param      in   a data input stream.
    * @return     a Unicode string.
    * @exception  EOFException            if the input stream reaches the end
    *               before all the bytes.
    * @exception  IOException   the stream has been closed and the contained
    *             input stream does not support reading after close, or
    *             another I/O error occurs.
    * @exception  UTFDataFormatException  if the bytes do not represent a
    *               valid modified UTF-8 encoding of a Unicode string.
    * @see        java.io.DataInputStream#readUnsignedShort()
    */
   private final static String readUTF(BADataIS in) {
      int utflen = in.readUnsignedShort();
      byte[] bytearr = null;
      char[] chararr = null;
      if (in instanceof BADataIS) {
         BADataIS dis = (BADataIS) in;
         if (dis.bytearr.length < utflen) {
            dis.bytearr = new byte[utflen * 2];
            dis.chararr = new char[utflen * 2];
         }
         chararr = dis.chararr;
         bytearr = dis.bytearr;
      } else {
         bytearr = new byte[utflen];
         chararr = new char[utflen];
      }

      int c, char2, char3;
      int count = 0;
      int chararr_count = 0;

      in.readFully(bytearr, 0, utflen);

      while (count < utflen) {
         c = (int) bytearr[count] & 0xff;
         if (c > 127)
            break;
         count++;
         chararr[chararr_count++] = (char) c;
      }

      while (count < utflen) {
         c = (int) bytearr[count] & 0xff;
         switch (c >> 4) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
               /* 0xxxxxxx*/
               count++;
               chararr[chararr_count++] = (char) c;
               break;
            case 12:
            case 13:
               /* 110x xxxx   10xx xxxx*/
               count += 2;
               if (count > utflen)
                  throw new IllegalArgumentException("malformed input: partial character at end");
               char2 = (int) bytearr[count - 1];
               if ((char2 & 0xC0) != 0x80)
                  throw new IllegalArgumentException("malformed input around byte " + count);
               chararr[chararr_count++] = (char) (((c & 0x1F) << 6) | (char2 & 0x3F));
               break;
            case 14:
               /* 1110 xxxx  10xx xxxx  10xx xxxx */
               count += 3;
               if (count > utflen)
                  throw new IllegalArgumentException("malformed input: partial character at end");
               char2 = (int) bytearr[count - 2];
               char3 = (int) bytearr[count - 1];
               if (((char2 & 0xC0) != 0x80) || ((char3 & 0xC0) != 0x80))
                  throw new IllegalArgumentException("malformed input around byte " + (count - 1));
               chararr[chararr_count++] = (char) (((c & 0x0F) << 12) | ((char2 & 0x3F) << 6) | ((char3 & 0x3F) << 0));
               break;
            default:
               /* 10xx xxxx,  1111 xxxx */
               throw new IllegalArgumentException("malformed input around byte " + count);
         }
      }
      // The number of chars produced may be less than utflen
      return new String(chararr, 0, chararr_count);
   }

   private byte         bytearr[]    = new byte[80];

   private char         chararr[]    = new char[80];

   BAByteIS             in;

   private byte         readBuffer[] = new byte[8];

   protected final UCtx uc;

   /**
    * Creates a DataInputStream that uses the specified
    * underlying InputStream.
    * @param uc 
    * @param  in   the specified input stream
    */
   public BADataIS(UCtx uc, BAByteIS in) {
      this.uc = uc;
      this.in = in;
   }

   /**
    * Create a {@link BAByteIS} from {@link BADataOS}
    * @param uc 
    * @param out
    */
   public BADataIS(UCtx uc, BADataOS out) {
      this.uc = uc;
      this.in = new BAByteIS(uc, out.getOut().toByteArray());
   }

   /**
    * A reference to the array in the {@link BAByteIS}
    * @return
    */
   public byte[] getArray() {
      return in.buf;
   }

   public int getPosition() {
      return in.pos;
   }

   public int read() {
      return in.read();
   }

   /**
    * @see java.io.DataInputStream#read(byte[])
    */
   public int read(byte b[]) {
      return in.read(b, 0, b.length);
   }

   /**
    */
   public int read(byte b[], int off, int len) {
      return in.read(b, off, len);
   }

   /**
    */
   public boolean readBoolean() {
      int ch = in.read();
      return (ch != 0);
   }

   /**
    */
   public byte readByte() {
      int ch = in.read();
      return (byte) (ch);
   }

   public byte[] readByteArray() {
      int num = readInt();
      if (num > in.available()) {
         throw new IllegalStateException(num + " Chars for " + in.available());
      }
      byte[] ar = new byte[num];
      read(ar);
      return ar;
   }

   /**
    */
   public char readChar() {
      int ch1 = in.read();
      int ch2 = in.read();
      return (char) ((ch1 << 8) + (ch2 << 0));
   }

   /**
    */
   public double readDouble() {
      return Double.longBitsToDouble(readLong());
   }

   /**
    */
   public float readFloat() {
      return Float.intBitsToFloat(readInt());
   }

   /**
    */
   public void readFully(byte b[]) {
      readFully(b, 0, b.length);
   }

   /**
    */
   public void readFully(byte b[], int off, int len) {
      if (len < 0)
         throw new IndexOutOfBoundsException();
      int n = 0;
      while (n < len) {
         int count = in.read(b, off + n, len - n);
         n += count;
      }
   }

   //remove this stupid method. not part of the light embedded DataInput interface

   /**
    */
   public int readInt() {
      int ch1 = in.read();
      int ch2 = in.read();
      int ch3 = in.read();
      int ch4 = in.read();
      return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
   }

   public int readInt24() {
      int ch1 = in.read();
      int ch2 = in.read();
      int ch3 = in.read();
      return ((ch1 << 16) + (ch2 << 8)) + (ch3 << 0);
   }

   public String readLine() {
      throw new RuntimeException("Not supported. This method is not supposed to be in DataInput");
   }

   /**
    * See the general contract of the <code>readLong</code>
    * method of <code>DataInput</code>.
    * <p>
    * Bytes
    * for this operation are read from the contained
    * input stream.
    *
    * @return     the next eight bytes of this input stream, interpreted as a
    *             <code>long</code>.
    * @exception  EOFException  if this input stream reaches the end before
    *               reading eight bytes.
    * @exception  IOException   if an I/O error occurs.
    * @see        java.io.FilterInputStream#in
    */
   public long readLong() {
      readFully(readBuffer, 0, 8);
      return (((long) readBuffer[0] << 56) + ((long) (readBuffer[1] & 255) << 48) + ((long) (readBuffer[2] & 255) << 40) + ((long) (readBuffer[3] & 255) << 32) + ((long) (readBuffer[4] & 255) << 24) + ((readBuffer[5] & 255) << 16) + ((readBuffer[6] & 255) << 8) + ((readBuffer[7] & 255) << 0));
   }

   /**
    */
   public short readShort() {
      int ch1 = in.read();
      int ch2 = in.read();
      return (short) ((ch1 << 8) + (ch2 << 0));
   }

   public char[] readChars() {
      int num = readInt();
      //before creating, check if num is not too big for our array
      if (num > in.available()) {
         throw new IllegalArgumentException(num + " Chars for " + in.available());
      }
      char[] ar = new char[num];
      for (int i = 0; i < num; i++) {
         ar[i] = readChar();
      }
      return ar;
   }

   /**
    * Read the string written by {@link BADataOS#writeChars(String)}
    * <br>
    * <br>
    * 
    * @return
    */
   public String readString() {
      char[] ar = readChars();
      return new String(ar);
   }

   public int[] readIntArrayByteLong() {
      int num = in.read();
      return readArrayInt(num);
   }

   private int[] readArrayInt(int num) {
      int[] ar = uc.getMem().createIntArray(num);
      for (int i = 0; i < num; i++) {
         ar[i] = readInt();
      }
      return ar;
   }

   public int[] readIntArrayIntLong() {
      int num = readInt();
      return readArrayInt(num);
   }

   /**
    */
   public int readUnsignedByte() {
      int ch = in.read();
      return ch;
   }

   /**
    */
   public int readUnsignedShort() {
      int ch1 = in.read();
      int ch2 = in.read();
      return (ch1 << 8) + (ch2 << 0);
   }

   public String readUTF() {
      return readUTF(this);
   }

   /**
    */
   public int skipBytes(int n) {
      int total = 0;
      int cur = 0;

      while ((total < n) && ((cur = (int) in.skip(n - total)) > 0)) {
         total += cur;
      }

      return total;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BADataIS");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BADataIS");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
