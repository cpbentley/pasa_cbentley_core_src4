/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.ctx;

import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Manifest of a configuration for a code context.
 * 
 * Provides read and sometimes write access to values.
 * 
 * Shouldn't those values be immutable. Changing them requires creating a new context instance?
 * 
 * @author Charles Bentley
 *
 */
public interface IConfig extends IStringable {

   
   /**
    * Ignores the saved settings. Don't erase them.
    * <br>
    * The Config is applied to a byte setting that won't be saved.
    * Useful for testing one setting quickly.
    * @return
    */
   public boolean isIgnoreSettings();
   
   
   /**
    * When true, clean up settings of the Ctx.
    * <br>
    * When true, this essentially erases anything the user has modified in the previous session.
    * <br>
    * When something is corrupted.
    * @return
    */
   public boolean isEraseSettings();
   
   /**
    * When true, configures ctx to not change its settings.
    * They are hardcoded by this config and that's it.
    * @return
    */
   public boolean isHardcoded();
   
   
}
