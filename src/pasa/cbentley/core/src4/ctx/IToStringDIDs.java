package pasa.cbentley.core.src4.ctx;

import pasa.cbentley.core.src4.interfaces.ITechThread;
import pasa.cbentley.core.src4.logging.IDebugStringable;

public interface IToStringDIDs {
   /**
    * 
    */
   public static final int A_DID_OFFSET_A_UC      = 50;

   public static final int A_DID_OFFSET_Z_UC      = 200;

   public static final int DID_09_IMAGE_TRANSFORM = A_DID_OFFSET_A_UC + 9;

   public static final int DID_10_TRANSFORMATION  = A_DID_OFFSET_A_UC + 10;

   public static final int DID_11_DIAG_TOP_DIR    = A_DID_OFFSET_A_UC + 11;

   /**
    * {@link ITechThread#STATE_0_ON}
    */
   public static final int DID_15_THREAD_STATE    = A_DID_OFFSET_A_UC + 15;

   /**
    * {@link ITechThread#THREAD_MODE_0_POST_NOW}
    */
   public static final int DID_16_THREAD_MODE     = A_DID_OFFSET_A_UC + 16;

   public static final int DID_DYNAMIC_MAX_VALUE  = 9;

   /**
    * {@link IDebugStringable#toStringSetDynamicDID(int, String[])}.
    * 
    */
   public static final int DYN_1                  = 1;

   public static final int DYN_2                  = 3;

   public static final int DYN_3                  = 3;

   public static final int DYN_4                  = 4;

   public static final int DYN_5                  = 5;

   public static final int DYN_6                  = 6;

   public static final int DYN_7                  = 7;

   public static final int DYN_8                  = 8;

   /**
    * Last Dynamic DID
    */
   public static final int DYN_9                  = 9;

}
