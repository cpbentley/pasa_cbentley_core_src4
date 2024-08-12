/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.interfaces;

import java.io.IOException;
import java.io.InputStream;

import pasa.cbentley.core.src4.api.ApiManager;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * The KernelHost provides a unified interface to core services.
 * <li> Reading a file from a path is host dependent.
 * <li> Computing time values 
 * <li> String internationalization
 * 
 * 
 * Provides access to Java platform services (J2SE,Android,Embedded)
 * <br>
 * When constructing {@link UCtx}, an {@link IHost} is optional. It uses
 * the default Java implementation
 * <br>
 * The core module does not need any 
 * 
 * @see {@link UCtx#setHost(IHost)}
 * 
 * @author Charles Bentley
 *
 */
public interface IHost extends IStringable {

   /**
    * Returns the {@link InputStream} for the file. The path for the file
    * 
    * Different hosts will have different ways of loading a file from the jarfile. Android comes to mind
    * @param file
    * @return
    * @throws IOException
    */
   public InputStream getResourceAsStream(String file) throws IOException;

   public ITimeCtrl getTimeCtrl();

   public IExecutor getExecutor();

   public IHostData getHostData();

   public IHostFeature getHostFeature();

   /**
    * Related to {@link ApiManager}
    * @return
    */
   public IHostService getHostService();
}
