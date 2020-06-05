/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.ctx;

import pasa.cbentley.core.src4.logging.Dctx;

public interface IConfigU extends IConfig {

   /**
    * Master control flag for all sub code contexts.
    * <br>
    * When true, system assumes all configuration return true for {@link IConfig#isIgnoreSettings()}
    * 
    * 
    * @return
    */
   public boolean isIgnoreSettingsAll();
   /**
    * When true, clean up everything upon start
    * @return
    */
   public boolean isEraseSettingsAll();

   /**
    * Force exceptions in grey case areas
    * @return
    */
   public boolean isForceExceptions();
   
   public String getDefaultEncoding();
   
   //#mdebug
   public void toStringSetDebugUCtx(UCtx uc);
   
   /**
    * is {@link Dctx} using class links instead of #
    * @param uc
    */
   public boolean toStringIsUsingClassLinks();

   //#enddebug
}
