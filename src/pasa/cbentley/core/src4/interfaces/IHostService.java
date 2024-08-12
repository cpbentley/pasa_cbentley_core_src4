package pasa.cbentley.core.src4.interfaces;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Interface to active features that the host migh support
 * 
 * {@link UCtx} does not understand those concepts. They are represented as integer IDs by the client code that does 
 * understand the concepts
 * <p>
 * Examples of services
 * <li>Clipboard
 * <li>hardware image scaling service.
 * </p>
 * @author Charles Bentley
 *
 */
public interface IHostService extends IStringable {

   /**
    * 
    * @param serviceID
    * @param isActive
    * @return
    */
   public boolean setHostServiceActive(int serviceID, boolean isActive);

   public boolean setHostServiceOn(int serviceID);
   public boolean setHostServiceOff(int serviceID);

   /**
    * Query Host for support of features
    * <li> {@link ITechHostCore#SUP_ID_02_POINTERS}

    * <br>
    * <br>
    * 
    * @param serviceID
    * @return
    */
   public boolean isHostServiceSupported(int serviceID);

   /**
    * Return true when the service is currently active/enabled.
    * 
    * @param serviceID
    * @return
    */
   public boolean isHostServiceActive(int serviceID);
}
