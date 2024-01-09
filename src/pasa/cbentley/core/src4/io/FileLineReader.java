package pasa.cbentley.core.src4.io;

import java.io.InputStream;

import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.UCtx;

/**
 * Reads a file and call {@link ILineReader} every line.
 * @author Charles Bentley
 *
 */
public class FileLineReader {

   private char        avoid    = '#';

   private String      encoding = "UTF-8";

   private String      file;

   private ILineReader reader;

   private UCtx        uc;


   public FileLineReader(UCtx uc, ILineReader reader, String file) {
      this.uc = uc;
      this.reader = reader;
      this.file = file;
   }

   public FileLineReader(UCtx uc,ILineReader reader) {
      this(uc, reader, null);
   }

   public String getEncoding() {
      return encoding;
   }

   public int read() {
      return read(file, encoding);
   }

   public int read(String file) {
      return read(file, encoding);
   }

   public int read(String file, String encoding) {
      Object cl = uc;
      FileReader fr = new FileReader(uc);
      XString cp = null;
      InputStream is = uc.getIOU().getStream(cl, file);
      if (is == null) {
         throw new NullPointerException("Class " + cl.getClass().getName() + " could not read file " + file);
      }
      fr.open(is, encoding);
      //there might be a problem with the first character
      //problem with The byte order mark (BOM) is a Unicode character, there a lie with nothing
      int numA = 0;
      int numB = 0;
      while ((cp = fr.readCharLine()) != null) {
         numA++;
         char[] chars = cp.getChars();
         int offset = cp.getOffset();
         char firstChar = chars[offset];
         if (firstChar == avoid) {
            continue;
         }
         numB++;
         reader.lineRead(this, cp, numA, numB);
      }
      fr.close();
      return numA;
   }

   public void setCharAvoid(char c) {
      this.avoid = c;
   }

   public void setEncoding(String encoding) {
      this.encoding = encoding;
   }

   public void setFileBackUp(String file) {

   }
}
