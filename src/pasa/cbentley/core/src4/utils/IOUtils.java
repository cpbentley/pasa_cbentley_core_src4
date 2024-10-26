/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.core.src4.interfaces.IFileCallBack;
import pasa.cbentley.core.src4.interfaces.IHost;
import pasa.cbentley.core.src4.io.BAByteOS;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.IntToStrings;

public class IOUtils implements IStringable {

   private final int   BOM_SIZE  = 4;

   /**
    * Field must be initialized for reading {@link InputStream} with the method {@link Class#getResourceAsStream(String)}
    * TODO: remove this.
    */
   public Class        REFERENCE = null;

   private UCtx        uc;

   public IOUtils(UCtx uc) {
      this.uc = uc;
   }

   /**
    * Close InputStream
    * @param is
    * @return
    * @throws IOException
    */
   public BAByteOS convert(InputStream is) throws IOException {
      BAByteOS bos = new BAByteOS(uc);

      byte[] buffer = new byte[1024];
      int numRead = -1;
      while ((numRead = is.read(buffer, 0, buffer.length)) != -1) {
         bos.write(buffer, 0, numRead);
      }
      is.close();
      return bos;
   }

   public void finalClose(InputStream os) {
      if (os != null) {
         try {
            os.close();
         } catch (Exception e) {
            //#debug
            e.printStackTrace();
         }
      }
   }

   public void finalClose(OutputStream os) {
      if (os != null) {
         try {
            os.close();
         } catch (Exception e) {
            //#debug
            e.printStackTrace();
         }
      }
   }

   public String fixFileName(String file) {
      if (file.charAt(0) != '/') {
         return "/" + file;
      }
      return file;
   }

   private Class getClass(Object o) {
      if (REFERENCE != null)
         return REFERENCE;
      else {
         if (o == null) {
            throw new NullPointerException();
         } else {
            if (o instanceof Class) {
               return (Class) o;
            }
            return o.getClass();
         }
      }

   }

   public InputStream getInputStream(Object caller, String fileName) {
      Class c = getClass(caller);
      InputStream is = c.getResourceAsStream(fileName);
      return is;
   }

  

   /**
    * TODO UConfig tells us if / is required for path names
    * @param name
    * @return
    */
   public String getResourcePath(String name) {
      String m = name;
      if (name.charAt(0) != '/')
         m = '/' + name;
      return m;
   }

   /**
    * handle the lack of / automatically
    * @param o
    * @param fileName
    * @return
    */
   public InputStream getStream(Object o, String fileName) {
      //TODO
      fileName = getResourcePath(fileName);
      Class c = getClass(o);
      InputStream is = null;
      IHost kernelHost = uc.getHost();
      if (kernelHost == null) {
         is = c.getResourceAsStream(fileName);
      } else {
         try {
            is = kernelHost.getResourceAsStream(fileName);
         } catch (IOException e) {
            e.printStackTrace();
            return null;
         }
      }
      return is;
   }

   public byte[] readBytes(InputStream is) throws IOException {
      int bufferStart = 512;
      // Create the byte array to hold the data
      byte[] bytes = new byte[bufferStart];
      // Read in the bytes
      int offset = 0;
      int numRead = 0;
      while ((numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
         offset += numRead;
         if (offset >= bytes.length) {
            bytes = uc.getMem().increaseCapacity(bytes, bytes.length + bufferStart);
         }
         //System.out.println(MemPrint.snapShot("offset"));
      }
      //System.out.println(MemPrint.snapShot("before close"));

      is.close();
      //System.out.println(MemPrint.snapShot("close "));

      byte[] data = new byte[offset];
      System.arraycopy(bytes, 0, data, 0, data.length);
      return data;
   }

   /**
    * 
    * @param ui can be null but then REFERENCE must be set
    * @param fileName
    * @return
    */
   public byte[] readBytes(String fileName) {
      fileName = fixFileName(fileName);
      InputStream is = getStream(uc, fileName);
      try {
         if (is == null) {
            return new byte[0];
         }
         return readBytes(is);
      } catch (IOException e) {
         //#debug
         e.printStackTrace();
         //#debug
         toDLog().pTest("msg", this, IOUtils.class, "readBytes", LVL_05_FINE, true);
         return null;
      }
   }

   public void readFile(IFileCallBack cb, InputStream is, String encod) {
      try {
         if (is == null) {
            //#debug
            toDLog().pNull("InputStream is null", this, IOUtils.class, "readFile", LVL_05_FINE, true);
            return;
         }
         InputStreamReader isr = new InputStreamReader(is, encod);
         //TODO parametrize
         int bufferSize = 512;
         char[] ar = new char[bufferSize];
         char[] line = new char[50];
         int offset = 0;
         int length = 0;
         //fill the buffer
         while (cb.isFileContinue() && (length = isr.read(ar)) != -1) {
            for (int i = 0; i < length; i++) {
               if (ar[i] == '\n' || ar[i] == '\r') {
                  if (offset != 0) {
                     cb.lineCallBack(line, 0, offset);
                     offset = 0;
                  }
               } else {
                  line[offset] = ar[i];
                  offset++;
                  if (offset >= line.length) {
                     line = uc.getMem().increaseCapacity(line, line.length);
                  }
               }
            }
         }
      } catch (UnsupportedEncodingException e) {
         //#debug
         e.printStackTrace();
      } catch (IOException e) {
         //#debug
         e.printStackTrace();
      }

   }

   /**
    * Performant file reading with a call back each time a line is found
    * <br>
    * <br>
    * Callback is {@link IFileCallBack#lineCallBack(char[], int, int)}.
    * Called after a "\n\r" is met.
    * @param cb
    * @param fileName
    * @param encod
    * @param maxLineLength perf variable. will be maximum buffer size for char[] array
    */
   public void readFile(IFileCallBack cb, InputStream is, String encod, int maxLineLength) {
      if (true) {
         readFile(cb, is, encod);
      }
      try {
         if (is == null) {
            //#debug
            toDLog().pNull("InputStream is null", this, IOUtils.class, "readFile", LVL_05_FINE, true);
            return;
         }
         InputStreamReader isr = new InputStreamReader(is, encod);
         //TODO parametrize
         int bufferSize = 512;
         char[] ar = new char[bufferSize];
         char[] line = new char[maxLineLength];
         int offset = 0;
         int length = 0;
         //fill the buffer
         while ((length = isr.read(ar)) != -1) {
            for (int i = 0; i < length; i++) {
               if (ar[i] == '\n' || ar[i] == '\r') {
                  if (offset != 0) {
                     cb.lineCallBack(line, 0, offset);
                     offset = 0;
                  }
               } else {
                  line[offset] = ar[i];
                  offset++;
               }
            }
         }
      } catch (UnsupportedEncodingException e) {
         //#debug
         e.printStackTrace();
      } catch (IOException e) {
         //#debug
         e.printStackTrace();
      }

   }

   /**
    * Depending on the platform.. the fileName must have a / in front of its name
    * @param ui
    * @param cb
    * @param fileName
    * @param encod
    */
   public void readFile(IFileCallBack cb, String fileName, String encod) {
      //         if (fileName.charAt(0) != '/') {
      //            fileName = "/" + fileName;
      //         }
      Class c = getClass(cb);
      InputStream is = c.getResourceAsStream(fileName);
      if (is == null) {
         String msg = "Could not open file for reading " + fileName + " Encod:" + encod + "Check leading /";
         //#debug
         toDLog().pNull(msg, this, IOUtils.class, "readFile", LVL_05_FINE, false);

      }
      readFile(cb, is, encod);
   }

   /**
    * Performant file reading with a call back each time a line is found
    * <br>
    * <br>
    * @param cb
    * @param fileName a file name
    * @param encod
    * @param maxLineLength
    */
   public void readFile(IFileCallBack cb, String fileName, String encod, int maxLineLength) {
      //         if (fileName.charAt(0) != '/') {
      //            fileName = "/" + fileName;
      //         }
      Class c = getClass(cb);
      InputStream is = c.getResourceAsStream(fileName);
      if (is == null) {
         //#debug
         toDLog().pNull("InputStream is null for fileName=" + fileName + " encod=" + encod, this, IOUtils.class, "readFile", LVL_05_FINE, true);
      }
      readFile(cb, is, encod, maxLineLength);
   }

   /**
    * 
    * @param ui
    * @param fileName
    * @param encod
    * @return null if fileName could not be found
    */
   public StringBuffer readFile(String fileName, String encod) {
      StringBuffer sb = new StringBuffer();
      try {
         //got to make sure TODO
         //     if (fileName.charAt(0) != '/') {
         //       fileName = "/" + fileName;
         //     }
         InputStream is = getStream(uc, fileName);
         if (is == null) {
            return null;
         }
         InputStreamReader isr = new InputStreamReader(is, encod);
         //TODO buffer not working
         char[] car = new char[512];
         while (isr.read(car) != -1) {
            isr.read(car);
            sb.append(car);
         }
      } catch (UnsupportedEncodingException e) {
         //#debug
         e.printStackTrace();
      } catch (IOException e) {
         sb.append(e.getMessage());
         //#debug
         e.printStackTrace();
      }
      return sb;
   }

   public StringBBuilder readFileAsBuilder(String fileName, String encod) {
      return readFileAsBuilder(fileName, encod, false, ' '); //char is irrevelant while false
   }

   /**
    * 
    * @param fileName
    * @param encod
    * @param isIgnore when true, ignore c while reading
    * @param c
    * @return
    */
   public StringBBuilder readFileAsBuilder(String fileName, String encod, boolean isIgnore, char c) {
      StringBBuilder sb = new StringBBuilder(uc);
      try {
         InputStream is = getStream(uc, fileName);
         if (is == null) {
            return null;
         }
         InputStreamReader isr = new InputStreamReader(is, encod);
         char[] car = new char[256];
         int read = -1;
         while ((read = isr.read(car)) != -1) {
            if (isIgnore) {
               sb.appendIgnore(car, 0, read, c);
            } else {
               sb.append(car, 0, read);
            }
         }
      } catch (UnsupportedEncodingException e) {
         //#debug
         e.printStackTrace();
         return null;
      } catch (IOException e) {
         sb.append(e.getMessage());
         //#debug
         e.printStackTrace();
         return null;
      }
      return sb;
   }

   /**
    * Reads the file as text using given encoding. Removes all \r characters
    * @param fileName
    * @param encod
    * @return
    */
   public StringBBuilder readFileAsBuilderWindows(String fileName, String encod) {
      return readFileAsBuilder(fileName, encod, true, StringUtils.NEW_LINE_CARRIAGE_RETURN);
   }

   /**
    * 
    * @param fileName
    * @param encod
    * @return
    */
   public char[] readFileAsChars(String fileName, String encod) {
      StringBBuilder sb = readFileAsBuilderWindows(fileName, encod);
      sb.trimToSize();
      return sb.getArrayRef();
   }

   /**
    * Read the file as text as such.
    * <br>
    * <br>
    * Returns null if there is an exception
    * @param ui
    * @param fileName
    * @param encod
    * @return
    */
   public String readFileAsString(String fileName, String encod) {
      StringBBuilder sb = readFileAsBuilder(fileName, encod);
      return sb.toString();
   }

   public String readFileAsStringWindows(String fileName, String encod) {
      StringBBuilder sb = readFileAsBuilderWindows(fileName, encod);
      return sb.toString();
   }

   public IntToStrings readFileIts(String fileName, String encod, int maxLineLength, int initSize) {
      final IntToStrings its = new IntToStrings(uc, initSize);
      IFileCallBack cb = new IFileCallBack() {

         public boolean isFileContinue() {
            return true;
         }

         public void lineCallBack(char[] b, int offset, int length) {
            its.add(new String(b, offset, length));
         }
      };
      readFile(cb, fileName, encod);
      return its;
   }

   public int readSetBytes(String fileName, byte[] bytes, int offset) {
      StringBBuilder sb = new StringBBuilder(uc);
      try {
         InputStream is = getStream(uc, fileName);
         int numRead = 0;
         int total = 0;
         while ((numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
            total += numRead;
         }
         return total;
      } catch (IOException e) {
         sb.append(e.getMessage());
         //#debug
         e.printStackTrace();
         return 0;
      }
   }



   public byte[] streamToByte(InputStream is) throws IOException {
      return convert(is).toByteArray();
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "IOUtils");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "IOUtils");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug

}
