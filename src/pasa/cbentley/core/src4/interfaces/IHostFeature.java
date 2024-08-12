package pasa.cbentley.core.src4.interfaces;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Interface to active features that the host migh support
 * 
 * {@link UCtx} does not understand those concepts. They are represented as integer IDs by the client code that does 
 * understand the concepts
 * <p>
 * Examples of features: 
 * <li>Alias drawing
 * <li>Custom Font
 * <li> OpenGL setting on Canvases.
 * </p>
 * 
 * @see IHostData
 * @see IHostService 
 * @author Charles Bentley
 *
 */
public interface IHostFeature extends IStringable {

   /**
    * Enable/Disable a feature. 
    * 
    * <li> {@link ITechHostUI#SUP_ID_28_ALWAYS_ON_TOP}
    * 
    * <li> {@link ITechHostCore#SUP_ID_20_CLIPBOARD}
    * 
    * <li> {@link ITechFeaturesDraw#SUP_ID_06_CUSTOM_FONTS}
    * <li> {@link ITechFeaturesDraw#SUP_ID_07_IMAGE_SCALING}
    * 
    * @param featureID
    * @param b
    * @return true if the change was successful
    */
   public boolean setHostFeatureEnabled(int featureID, boolean b);

   public boolean setHostFeatureOn(int featureID);

   public boolean setHostFeatureOff(int featureID);

   /**
    * Sets the Feature at the factory settings. overides and forces the reset of all user defined.
    * 
    * Every object created after this call, will have the feature enable/disabled
    * @param featureID
    * @param b
    * @return
    */
   public boolean setHostFeatureEnabledFactory(int featureID, boolean b);

   public boolean setHostFeatureFactoryOn(int featureID);

   public boolean setHostFeatureFactoryOff(int featureID);

   /**
    * Query Host for support of features
    * <li> {@link ITechHostCore#SUP_ID_02_POINTERS}
    * <li> {@link ITechHostCore#SUP_ID_03_OPEN_GL}
    * <br>
    * <br>
    * 
    * @param featureID
    * @return
    */
   public boolean isHostFeatureSupported(int featureID);

   /**
    * Return true when the service is currently active/enabled.
    * 
    * @param featureID
    * @return
    */
   public boolean isHostFeatureEnabled(int featureID);
   
   public boolean isHostFeatureFactoryEnabled(int featureID);
}
