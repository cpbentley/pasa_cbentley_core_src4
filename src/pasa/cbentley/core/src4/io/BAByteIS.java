/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Based on {@link ByteArrayInputStream} of JDK1.0
 * 
 * Tweaked 
 * <li> desynchronized
 * <li> no {@link IOException} since its a pure byte array.
 * 
 * @author Charles-Philip Bentley
 */
public class BAByteIS extends InputStream implements IStringable {

   /**
    * An array of bytes that was provided
    * by the creator of the stream. Elements <code>buf[0]</code>
    * through <code>buf[count-1]</code> are the
    * only bytes that can ever be read from the
    * stream;  element <code>buf[pos]</code> is
    * the next byte to be read.
    */
   protected byte       buf[];

   /**
    * The index one greater than the last valid character in the input
    * stream buffer.
    * This value should always be nonnegative
    * and not larger than the length of <code>buf</code>.
    * It  is one greater than the position of
    * the last byte within <code>buf</code> that
    * can ever be read  from the input stream buffer.
    */
   protected int        count;

   /**
    * The currently marked position in the stream.
    * ByteArrayInputStream objects are marked at position zero by
    * default when constructed.  They may be marked at another
    * position within the buffer by the <code>mark()</code> method.
    * The current buffer position is set to this point by the
    * <code>reset()</code> method.
    * <p>
    * If no mark has been set, then the value of mark is the offset
    * passed to the constructor (or 0 if the offset was not supplied).
    *
    * @since   JDK1.1
    */
   protected int        mark = 0;

   /**
    * The index of the next character to read from the input stream buffer.
    * This value should always be nonnegative
    * and not larger than the value of <code>count</code>.
    * The next byte to be read from the input stream buffer
    * will be <code>buf[pos]</code>.
    */
   protected int        pos;

   protected final UCtx uc;

   /**
    * Creates a <code>ByteArrayInputStream</code>
    * so that it  uses <code>buf</code> as its
    * buffer array.
    * The buffer array is not copied.
    * The initial value of <code>pos</code>
    * is <code>0</code> and the initial value
    * of  <code>count</code> is the length of
    * <code>buf</code>.
    * @param uc TODO
    * @param   buf   the input buffer.
    */
   public BAByteIS(UCtx uc, byte buf[]) {
      this.uc = uc;
      this.buf = buf;
      this.pos = 0;
      this.count = buf.length;
   }

   /**
    * Creates <code>ByteArrayInputStream</code>
    * that uses <code>buf</code> as its
    * buffer array. The initial value of <code>pos</code>
    * is <code>offset</code> and the initial value
    * of <code>count</code> is the minimum of <code>offset+length</code>
    * and <code>buf.length</code>.
    * The buffer array is not copied. The buffer's mark is
    * set to the specified offset.
    * @param uc TODO
    * @param   buf      the input buffer.
    * @param   offset   the offset in the buffer of the first byte to read.
    * @param   length   the maximum number of bytes to read from the buffer.
    */
   public BAByteIS(UCtx uc, byte buf[], int offset, int length) {
      this.uc = uc;
      this.buf = buf;
      this.pos = offset;
      this.count = Math.min(offset + length, buf.length);
      this.mark = offset;
   }

   /**
    * Returns the number of remaining bytes that can be read (or skipped over)
    * from this input stream.
    * <p>
    * The value returned is <code>count&nbsp;- pos</code>,
    * which is the number of bytes remaining to be read from the input buffer.
    *
    * @return  the number of remaining bytes that can be read (or skipped
    *          over) from this input stream without blocking.
    */
   public int available() {
      return count - pos;
   }

   /**
    * Closing a <tt>ByteArrayInputStream</tt> has no effect. The methods in
    * this class can be called after the stream has been closed without
    * generating an <tt>IOException</tt>.
    * <p>
    */
   public void close() {
   }

   /**
    * Set the current marked position in the stream.
    * ByteArrayInputStream objects are marked at position zero by
    * default when constructed.  They may be marked at another
    * position within the buffer by this method.
    * <p>
    * If no mark has been set, then the value of the mark is the
    * offset passed to the constructor (or 0 if the offset was not
    * supplied).
    *
    * <p> Note: The <code>readAheadLimit</code> for this class
    *  has no meaning.
    *
    * @since   JDK1.1
    */
   public void mark(int readAheadLimit) {
      mark = pos;
   }

   /**
    * Tests if this <code>InputStream</code> supports mark/reset. The
    * <code>markSupported</code> method of <code>ByteArrayInputStream</code>
    * always returns <code>true</code>.
    *
    * @since   JDK1.1
    */
   public boolean markSupported() {
      return true;
   }

   /**
    * {@link ByteArrayInputStream#read()}
    * 
    * but not synchronized and
    * @throws IllegalStateException if called with not enough data
    */
   public int read() {
      if (pos < count) {
         return (buf[pos++] & 0xff);
      }
      throw new IllegalStateException("pos[" + pos + "] >= count[" + count + "]. Full lenght=" + buf.length);
   }

   /**
    * Same as {@link ByteArrayInputStream#read(byte[], int, int)}
    * but not synchronized and throws {@link IllegalStateException} if called with not enough
    * data
    */
   public int read(byte b[], int off, int len) {
      if (b == null) {
         throw new NullPointerException();
      } else if (off < 0 || len < 0 || len > b.length - off) {
         throw new IndexOutOfBoundsException();
      }

      if (pos >= count) {
         throw new IllegalStateException();
      }

      int avail = count - pos;
      if (len > avail) {
         len = avail;
      }
      if (len <= 0) {
         return 0;
      }
      System.arraycopy(buf, pos, b, off, len);
      pos += len;
      return len;
   }

   /**
    * Resets the buffer to the marked position.  The marked position
    * is 0 unless another position was marked or an offset was specified
    * in the constructor.
    */
   public void reset() {
      pos = mark;
   }

   /**
    * Skips <code>n</code> bytes of input from this input stream. Fewer
    * bytes might be skipped if the end of the input stream is reached.
    * The actual number <code>k</code>
    * of bytes to be skipped is equal to the smaller
    * of <code>n</code> and  <code>count-pos</code>.
    * The value <code>k</code> is added into <code>pos</code>
    * and <code>k</code> is returned.
    *
    * @param   n   the number of bytes to be skipped.
    * @return  the actual number of bytes skipped.
    */
   public long skip(long n) {
      long k = count - pos;
      if (n < k) {
         k = n < 0 ? 0 : n;
      }

      pos += k;
      return k;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, BAByteIS.class, 250);
      toStringPrivate(dc);
      dc.nl();
      uc.getBU().toStringBytes(dc, buf, 0, count, 15);
      
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("count", count);
      dc.appendVarWithSpace("mark", mark);
      dc.appendVarWithSpace("pos", pos);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BAInputS");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug

}