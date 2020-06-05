/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * 
 * Based on {@link ByteArrayOutputStream} of JDK1.0
 * <br>
 * Tweaked 
 * <li> include {@link UCtx}. required to grow the byte array
 * <li> {@link IStringable} thx to {@link UCtx}
 * <li> desynchronized
 * <li> no {@link IOException} since its a pure byte array.
 * <li> a-z sorted.
 *
 * @author  Charles-Philip Bentley
 * @since   JDK1.0
 */

public class BAByteOS extends OutputStream implements IStringable {

   /**
    * The maximum size of array to allocate.
    * Some VMs reserve some header words in an array.
    * Attempts to allocate larger arrays may result in
    * OutOfMemoryError: Requested array size exceeds VM limit
    */
   private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

   private static int hugeCapacity(int minCapacity) {
      if (minCapacity < 0) // overflow
         throw new OutOfMemoryError();
      return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
   }

   /**
    * The buffer where data is stored.
    */
   protected byte buf[];

   /**
    * The number of valid bytes in the buffer.
    */
   protected int  count;

   protected UCtx uc;

   /**
    * Creates a new byte array output stream. The buffer capacity is
    * initially 32 bytes, though its size increases if necessary.
    */
   public BAByteOS(UCtx uc) {
      this(uc, 32);
   }

   /**
    * Creates a new byte array output stream, with a buffer capacity of
    * the specified size, in bytes.
    *
    * @param   size   the initial size.
    * @exception  IllegalArgumentException if size is negative.
    */
   public BAByteOS(UCtx uc, int size) {
      this.uc = uc;
      if (size < 0) {
         throw new IllegalArgumentException("Negative initial size: " + size);
      }
      buf = new byte[size];
   }

   /**
    * Closing a <tt>ByteArrayOutputStream</tt> has no effect. The methods in
    * this class can be called after the stream has been closed without
    * generating an <tt>IOException</tt>.
    * <p>
    *
    */
   public void close() throws IOException {
   }

   private byte[] copyOf(int newCapacity) {
      byte[] copy = new byte[newCapacity];
      System.arraycopy(buf, 0, copy, 0, Math.min(buf.length, newCapacity));
      return copy;
   }

   /**
    * Increases the capacity if necessary to ensure that it can hold
    * at least the number of elements specified by the minimum
    * capacity argument.
    *
    * @param minCapacity the desired minimum capacity
    * @throws OutOfMemoryError if {@code minCapacity < 0}.  This is
    * interpreted as a request for the unsatisfiably large capacity
    * {@code (long) Integer.MAX_VALUE + (minCapacity - Integer.MAX_VALUE)}.
    */
   private void ensureCapacity(int minCapacity) {
      // overflow-conscious code
      if (minCapacity - buf.length > 0)
         grow(minCapacity);
   }

   public byte[] getArrayRef() {
      return buf;
   }

   /**
    * Same as size but with the getter convention.
    * @return
    */
   public int getByteWrittenCount() {
      return count;
   }

   /**
    * Increases the capacity to ensure that it can hold at least the
    * number of elements specified by the minimum capacity argument.
    *
    * @param minCapacity the desired minimum capacity
    */
   private void grow(int minCapacity) {
      // overflow-conscious code
      int oldCapacity = buf.length;
      int newCapacity = oldCapacity << 1;
      if (newCapacity - minCapacity < 0)
         newCapacity = minCapacity;
      if (newCapacity - MAX_ARRAY_SIZE > 0)
         newCapacity = hugeCapacity(minCapacity);
      buf = copyOf(newCapacity);
   }

   /**
    * Resets the <code>count</code> field of this byte array output
    * stream to zero, so that all currently accumulated output in the
    * output stream is discarded. The output stream can be used again,
    * reusing the already allocated buffer space.
    *
    * @see     java.io.ByteArrayInputStream#count
    */
   public void reset() {
      count = 0;
   }

   /**
    * Returns the current size of the buffer.
    *
    * @return  the value of the <code>count</code> field, which is the number
    *          of valid bytes in this output stream.
    * @see     java.io.ByteArrayOutputStream#count
    */
   public int size() {
      return count;
   }

   /**
    * Creates a newly allocated byte array. Its size is the current
    * size of this output stream and the valid contents of the buffer
    * have been copied into it.
    *
    * @return  the current contents of this output stream, as a byte array.
    * @see     java.io.ByteArrayOutputStream#size()
    */
   public byte toByteArray()[] {
      return copyOf(count);
   }


   /**
    * Writes <code>len</code> bytes from the specified byte array
    * starting at offset <code>off</code> to this byte array output stream.
    *
    * @param   b     the data.
    * @param   off   the start offset in the data.
    * @param   len   the number of bytes to write.
    */
   public void write(byte b[], int off, int len) {
      if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) - b.length > 0)) {
         throw new IndexOutOfBoundsException();
      }
      ensureCapacity(count + len);
      System.arraycopy(b, off, buf, count, len);
      count += len;
   }

   /**
    * Writes the specified byte to this byte array output stream.
    *
    * @param   b   the byte to be written.
    */
   public void write(int b) {
      ensureCapacity(count + 1);
      buf[count] = (byte) b;
      count += 1;
   }

   /**
    * Writes the complete contents of this byte array output stream to
    * the specified output stream argument, as if by calling the output
    * stream's write method using <code>out.write(buf, 0, count)</code>.
    *
    * @param      out   the output stream to which to write the data.
    * @exception  IOException  if an I/O error occurs.
    */
   public void writeTo(OutputStream out) throws IOException {
      out.write(buf, 0, count);
   }

   
   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BAOutputS");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }
   
   /**
    * Converts the buffer's contents into a string decoding bytes using the
    * platform's default character set. The length of the new <tt>String</tt>
    * is a function of the character set, and hence may not be equal to the
    * size of the buffer.
    *
    * <p> This method always replaces malformed-input and unmappable-character
    * sequences with the default replacement string for the platform's
    * default character set. The {@linkplain java.nio.charset.CharsetDecoder}
    * class should be used when more control over the decoding process is
    * required.
    *
    * @return String decoded from the buffer's contents.
    * @since  JDK1.1
    */
   private void toStringPrivate(Dctx dc) {
      String str = new String(buf, 0, count);
      dc.appendWithSpace(str);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BAOutputS");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug
   

}