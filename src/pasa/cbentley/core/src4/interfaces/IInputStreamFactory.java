package pasa.cbentley.core.src4.interfaces;

import java.io.IOException;
import java.io.InputStream;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.utils.IOUtils;

/**
 * Abstract away a feature for {@link IOUtils}.
 * <br>
 * <br>
 * 
 * At the {@link UCtx}, we cannot make assumptions on how to read resources from jar/exe/war etc.
 * 
 * It will be given by the context
 * @author Charles Bentley
 *
 */
public interface IInputStreamFactory {

   /**
    * Tries reading the file.
    * @param file
    * @return
    * @throws IOException
    */
   public InputStream getResourceAsStream(String name) throws IOException;
}
