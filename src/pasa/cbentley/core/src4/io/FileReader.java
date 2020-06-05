/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Optimized {@link InputStream} text reader. Works with package class {@link XString}.
 * 
 * Reads an {@link InputStream} and interprets data as strings, line per line.
 * <br>
 * <br>
 * Uses its own {@link XString} object. Dev must not mess with it.
 * @author Charles Bentley
 *
 */
public class FileReader implements IStringable {
   private int               bufferSize = 512;

   private char[]            ar;

   private InputStreamReader isr;

   private int               lastseen;

   private XString           xstring;

   private int               endofbuffer;

   private boolean           isEndOfStreamReached;

   private int               oldseen;

   private boolean           foundone;

   /**
    * number of characters read
    */
   private int               byteread   = 0;

   private int               bytesread  = 0;

   protected final UCtx      uc;

   /**
    * Create a FileRead with a maximum of 512 characters to be read on a line
    */
   public FileReader(UCtx ui) {
      this(ui, 512);
   }

   public FileReader(UCtx ui, int bsize) {
      this.uc = ui;
      xstring = new XString(ui);
      //bufferSize depends on the amount of memory available
      // 512 = 1024 bytes = 1k memory = 512 characters
      // 
      bufferSize = bsize;
      ar = new char[bufferSize];
   }

   public void openFile(String file) {
      openFile(file, uc.getDefaultEncoding());
   }

   public void close() {
      if (isr != null) {
         try {
            isr.close();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }

   /**
    * Tries to open the Stream with given encoding.
    * When stream is null, returns early and the class will explode
    * <br>
    * <br>
    * It is the responsibility of the caller.
    * @param is cannot null
    * @param encod
    */
   public void open(InputStream is, String encod) {
      try {
         if (is == null) {
            throw new NullPointerException();
         }
         isr = new InputStreamReader(is, encod);
         byteread = 0;
         oldseen = 0;
         lastseen = 0;
         endofbuffer = fillBuffer(0);
         if (endofbuffer != -1) {
            isEndOfStreamReached = false;
         }
      } catch (UnsupportedEncodingException e) {
         //#debug
         e.printStackTrace();
      }
   }

   public void openFile(String file, String encod) {
      InputStream is = this.getClass().getResourceAsStream(file);
      if (is == null) {
         throw new IllegalArgumentException("File " + file + " not found");
      }
      open(is, encod);
   }

   /**
    * Fill the Buffer at position start up to the end if there are enough data to be read
    * <br>
    * <br>
    * @param start
    * @return the number of characters read into the buffer, -1 is no more
    */
   public int fillBuffer(int start) {
      try {
         int len = ar.length - start;
         return isr.read(ar, start, len);
      } catch (IOException e) {
         e.printStackTrace();
      }
      return -1;
   }

   public XString readCharLineIgnore(char c) {
      XString xs = readCharLine();
      while (xs != null && xs.getChar(0) == '#') {
         xs = readCharLine();
      }
      return xs;
   }

   /**
    * 
    * @return CharP should only be read only, null when reached the end 
    */
   public XString readCharLine() {
      if (isEndOfStreamReached) {
         return null;
      }
      xstring.reset();
      oldseen = lastseen;
      //border line case
      boolean border = false;
      //while we have not found a line break
      while (lastseen == oldseen) {
         //search line in the current buffer
         searchLine();
         //if none was found we have foundone = false
         if (!foundone) {
            int startFill = 0;
            //if full buffer does not contain line break, expand and re fill buffer
            if (lastseen == 0) {
               //#debug
               uc.toDLog().pFlow("Buffer is too small. Increasing from " + ar.length + " to " + (ar.length + (ar.length / 2)), null, FileReader.class, "readCharLine");

               startFill = ar.length;
               //extreme case where a full line is too big (Embedded)
               //let the memory manager handle this
               ar = uc.getMem().increaseCapacity(ar, ar.length / 2);
            } else {
               //end of line not found in the middle
               if (lastseen != endofbuffer) {
                  //copy the last portion at the begining of buffer and fill buffer
                  startFill = endofbuffer - lastseen;
                  //lastseen
                  if (ar.length <= lastseen || ar.length <= startFill) {
                     //#debug
                     uc.toDLog().pFlow("ar len=" + ar.length, null, FileReader.class, "readCharLine");
                  }
                  //copy the end at the begining
                  System.arraycopy(ar, lastseen, ar, 0, startFill);
                  //reset start counter
                  setLastSeen(0);
                  oldseen = lastseen;
                  //since the buffer has been moved to the offset 0 
                  endofbuffer = startFill;
               } else {
                  border = true;
               }
            }
            //fillBuffer
            int read = fillBuffer(startFill);
            if (read == -1) {
               if (lastseen == endofbuffer) {
                  xstring.set(ar, lastseen, 0);
               } else {
                  xstring.set(ar, lastseen, endofbuffer - lastseen);
               }
               isEndOfStreamReached = true;
               byteread += xstring.getLen();
               return xstring;
            } else {
               if (border) {
                  //case when lastseen = endofbuffer
                  border = false;
                  setLastSeen(0);
                  oldseen = lastseen;
               }
               //the new end is startFill plus the number of char read
               endofbuffer = startFill + read;
            }
         } else {
            byteread += xstring.getLen();
            return xstring;
         }
      }
      byteread += xstring.getLen();
      return xstring;
   }

   private void setLastSeen(int val) {
      oldseen = lastseen;
      lastseen = val;
   }

   private void searchLine() {
      //check for return
      xstring.reset();
      foundone = false;
      for (int i = lastseen; i < endofbuffer; i++) {
         if (ar[i] == '\r') {
            //windows line break = \r\n
            if (i != ar.length - 1) {
               xstring.set(ar, lastseen, i - lastseen);
               foundone = true;
               setLastSeen(i + 2);
            }
            break;
         } else {
            if (ar[i] == '\n') {
               xstring.set(ar, lastseen, i - lastseen);
               setLastSeen(i + 1);
               foundone = true;
               break;
            }
         }
      }
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "FileReader");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "FileReader");
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }
   //#enddebug

}
