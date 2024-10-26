/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.ctx;

import pasa.cbentley.core.src4.i8n.LocaleID;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.ILogConfigurator;

/**
 * Configures this {@link UCtx} module.
 * 
 * <li> Default encoding,
 * <li> How to use saved settings. i.e. configuration values modified and saved by the user
 * <ol>Force their erasur reset them
 * <ol>ignore them.. for debug purposes usually
 * <li> Local -> {@link LocaleID}
 * 
 * <p>
 * {@link IConfigU} do not have {@link UCtx} in the constructor parameter. It is set later 
 * by {@link UCtx} using {@link IConfigU#toStringSetDebugUCtx(UCtx)}
 * </p>
 * @author Charles Bentley
 *
 */
public interface IConfigU extends IConfig {

   /**
    * 
    * @return
    */
   public String getDefaultEncoding();

   /**
    * When true, clean up everything upon start..
    * @return
    */
   public boolean isEraseSettingsAll();

   /**
    * Force exceptions in grey case areas
    * @return
    */
   public boolean isForceExceptions();

   /**
    * Master control flag for all sub code contexts.
    * <br>
    * When true, system assumes all configuration return true for {@link IConfig#isIgnoreSettings()}
    * 
    * 
    * @return
    */
   public boolean isIgnoreSettingsAll();

   public String getUCtxName();

   //#mdebug
   /**
    * A non null {@link ILogConfigurator}.
    * Create a default one if necessary
    * @return
    */
   public ILogConfigurator toStringGetLogConfigurator();

   /**
    * is {@link Dctx} using class links instead of #
    * @param uc
    */
   public boolean toStringIsUsingClassLinks();

   /**
    * Sets the {@link UCtx} on the config for debug purposes.
    * The config needs a reference to {@link UCtx} to acces toString methods and the logger.
    * @param uc
    */
   public void toStringSetDebugUCtx(UCtx uc);

   /**
    * Sets the {@link ILogConfigurator} to be retrieved with {@link IConfigU#toStringGetLogConfigurator()}
    * @param logConfigurator
    * @return
    */
   public void toStringSetLogConfigurator(ILogConfigurator logConfigurator);

   public int toStringGetBytesOn1Line();

   //#enddebug
}
