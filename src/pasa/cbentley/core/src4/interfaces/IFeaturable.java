package pasa.cbentley.core.src4.interfaces;

import pasa.cbentley.core.src4.logging.IStringable;

public interface IFeaturable extends IStringable {

   /**
    * True when {@link IFeaturable} has implementation
    * @param featureID
    * @return
    */
   public boolean hasFeatureSupport(int featureID);

   /**
    * 
    * @param featureID
    * @return
    */
   public int getFeatureInt(int featureID);

   /**
    * Returns the object associated with the given feature, identfied by its ID.
    * @param featureID
    * @return null if that feature has no object for this {@link IFeaturable}
    */
   public Object getFeatureObject(int featureID);

   /**
    * 
    * @param featureID
    * @param b
    * @return false if change could not happen
    */
   public boolean featureEnable(int featureID, boolean b);

   /**
    * True when feature is enabled.
    * <br>
    * 
    * False is disabled or does not support the feature.
    * @param featureID
    * @return
    */
   public boolean isFeatureEnabled(int featureID);

}
