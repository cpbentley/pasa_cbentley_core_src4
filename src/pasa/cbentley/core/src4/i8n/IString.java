package pasa.cbentley.core.src4.i8n;

import pasa.cbentley.core.src4.logging.IStringable;

/**
 * 
 * A String function that computes a String based on its {@link LocaleID} and the semantical
 * components to provide the a final semantics
 * <br>
 * <li> Move up will have 2 keys. One for move, for Up.
 * <li> But in russian or french, it is one word, monter/descendre
 * <li> But you can also say Naviguer vers le haut/vers le bas
 * <li>
 * As such, a file may contain extra links to find a String not expressed in the Dev Language English.
 * <br>
 * Key for Move (34) and Key for Up (40) are matched to a new key.. possibly dynamic.
 * <br>
 * This system exists to improve the translation and provide a framework.
 * <br>
 * Different languages also means longer words. Thus a Font used in English
 * might be too big for the Other languages. Provided the Host allows it,
 * a Locale can be associated with a Font setup.
 * So there is a Global Font setup. And a Locale based set up.
 * <br>
 * Every time an {@link IString} is to be displayed, it must be computed.
 * <br>
 * Might be a compound.
 * Eve
 * <br>
 * Implementation tips.
 * Strings displayed fleetingly on the screen should not register for the local change event.
 * <br>
 * <br>
 * Only object with a long life and know when they die so they can remove
 * Event hooks.
 * <br>
 * <br>
 * A Locale string can be a combination of several strings.
 * Navigate Up. Navigate Down
 * <br>
 * A language that use only one word for Navigate Up?
 * <br>
 * LocalID several local used
 * 
 * @author Charles Bentley
 *
 * TODO rename to I8nString
 */
public interface IString extends IStringable {

   /**
    * Fetches the String
    * @return
    */
   public String getStr();
}
