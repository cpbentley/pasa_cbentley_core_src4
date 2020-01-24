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

}
