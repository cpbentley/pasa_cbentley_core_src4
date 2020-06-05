package pasa.cbentley.core.src4.interfaces;

import java.io.IOException;
import java.io.InputStream;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * The KernelHost provides a unified interface to core services.
 * <li> Reading a file from a path is host dependent.
 * <li> Computing time values 
 * <li> String internationalization
 * <br>
 * Provides access to Java platform services (J2SE,Android,Embedded)
 * <br>
 * When constructing {@link UCtx}, an {@link IKernelHost} is optional. It uses
 * the default Java implementation
 * <br>
 * The core module does not need any 
 * 
 * @see {@link UCtx#setHost(IKernelHost)}
 * 
 * @author Charles Bentley
 *
 */
public interface IKernelHost extends IStringable {

   /**
    * Returns the {@link InputStream} for the file. The path for the file
    * is
    * @param file
    * @return
    * @throws IOException
    */
   public InputStream getResourceAsStream(String file) throws IOException;
   
   public ITimeCtrl getTimeCtrl();
}
