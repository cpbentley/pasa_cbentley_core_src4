package pasa.cbentley.core.src4.utils;

import java.io.IOException;
import java.io.InputStream;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.io.BAByteOS;

public class IOUtils {

   private UCtx uc;

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
   
   public byte[] streamToByte(InputStream is) throws IOException {
      return convert(is).toByteArray();
   }
}
