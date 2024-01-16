package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.IToStringDIDs;
import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.ToStringStaticUc;
import pasa.cbentley.core.src4.ctx.UCtx;

public class DIDManager extends ObjectU implements IDebugStringable, IToStringDIDs {

   /**
    * Hosts the dynamics DID
    */
   private String[][]         dynamicDID = new String[IToStringDIDs.DID_DYNAMIC_MAX_VALUE + 1][];

   private IDebugStringable[] diders;

   private int                nextEmpty;

   public DIDManager(UCtx uc) {
      super(uc);
      diders = new IDebugStringable[5];
   }

   public void registerDIDer(IDebugStringable dider) {
      diders[nextEmpty] = dider;
   }

   public String toStringGetDIDString(int did, int value) {
      if (did <= IToStringDIDs.DYN_9) {
         if (dynamicDID[did] != null) {
            if (value < 0 || value >= dynamicDID[did].length) {
               return "BOModulesManager Dynamic DID Error " + value;
            }
            return dynamicDID[did][value];
         }
      }
      //check the dids of its own module.. no need for registration
      switch (did) {
         case DID_09_IMAGE_TRANSFORM:
            return ToStringStaticUc.toStringTransform(value);
         case DID_10_TRANSFORMATION:
            return ToStringStaticUc.toStringTransform(value);
         case DID_11_DIAG_TOP_DIR:
            return ToStringStaticUc.toStringDiagDir(value);
         case DID_15_THREAD_STATE:
            return ToStringStaticUc.toStringThreadState(value);
         case DID_16_THREAD_MODE:
            return ToStringStaticUc.toStringThreadMode(value);
      }
      for (int i = 0; i < diders.length; i++) {
         String idString = diders[i].toStringGetDIDString(did, value);
         if (idString != null) {
            return idString;
         }
      }
      return null;
   }

   public int toStringGetDynamicDIDMax() {
      return IToStringDIDs.DID_DYNAMIC_MAX_VALUE;
   }

   /**
    * 
    * @param did
    * @param strings
    */
   public void toStringSetDynamicDID(int did, String[] strings) {
      dynamicDID[did] = strings;
   }

}
