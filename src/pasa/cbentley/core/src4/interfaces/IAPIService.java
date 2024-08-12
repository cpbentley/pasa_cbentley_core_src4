package pasa.cbentley.core.src4.interfaces;

import pasa.cbentley.core.src4.ctx.ACtx;
import pasa.cbentley.core.src4.event.ILifeListener;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * All method use serviceID to differentiate services calls within a class implementing different services.
 * 
 * @author Charles Bentley
 *
 */
public interface IAPIService extends IStringable, ILifeListener {

   /**
    * Sets the {@link ACtx} after the construction. Because src4 cannot instantiate a class from its name 
    * using parameters in the constructor.
    * 
    * @param context
    * @throws IllegalArgumentException if context type is not expected
    */
   public void setCtx(ACtx context) throws IllegalArgumentException;

   /**
    * 
    * @param id ID for the service. Differentiate from different services within a single class implementing different services
    * @param param possibly null param. It depends on the service
    * @return
    */
   public boolean startService(int serviceID, Object param);

   public boolean isServiceRunning(int serviceID, Object param);

   public boolean stopService(int serviceID, Object param);

}
