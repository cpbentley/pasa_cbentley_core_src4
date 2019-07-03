package pasa.cbentley.core.src4.interfaces;

/**
 * Cheap collection of int enumerations.
 * <br>
 * Cost nothing in memory once inlined.
 * <br>
 * Note: Single letter interfaces don't use the I prefix convention.
 * Since by convention, you cannot have single letter classes.
 * 
 * @author Charles Bentley
 *
 */
public interface C extends ITech {

   public static final int ANGLE_LEFT_180          = 180;

   /**
    * Equals to angle 90 degree
    */
   public static final int ANGLE_UP_90             = 90;

   public static final int ANGLE_DOWN_270          = 270;

   public static final int ANGLE_RIGHT_0           = 0;

   public static final int DIAG_DIR_0TOP_LEFT      = 0;

   public static final int DIAG_DIR_1TOP_RIGHT     = 1;

   public static final int DIAG_DIR_2BOT_LEFT      = 2;

   public static final int DIAG_DIR_3BOT_RIGHT     = 3;

   /**
    * Compatible with the first 8 TYPE
    */
   public static final int DIR_0TOP                = 0;

   public static final int DIR_1BOTTOM             = 1;

   public static final int DIR_2LEFT               = 2;

   public static final int DIR_3RIGHT              = 3;

   public static final int DIR_4TopLeft            = 4;

   public static final int DIR_5TopRight           = 5;

   public static final int DIR_6BotLeft            = 6;

   public static final int DIR_7BotRight           = 7;

   public static final int TYPE_00TOP              = 0;

   public static final int TYPE_01BOTTOM           = 1;

   public static final int TYPE_02LEFT             = 2;

   public static final int TYPE_03RIGHT            = 3;

   public static final int TYPE_04TopLeft          = 4;

   public static final int TYPE_05TopRight         = 5;

   public static final int TYPE_06BotLeft          = 6;

   public static final int TYPE_07BotRight         = 7;

   public static final int TYPE_08MID_TopLeft      = 8;

   public static final int TYPE_09MID_TopRight     = 9;

   public static final int TYPE_10MID_BotLeft      = 10;

   public static final int TYPE_11MID_BotRight     = 11;

   public static final int TYPE_12TopLeftDiagBot   = 12;

   public static final int TYPE_13TopLeftDiagRight = 13;

   public static final int TYPE_14TopRightDiagBot  = 14;

   public static final int TYPE_15TopRightDiagLeft = 15;

   public static final int TYPE_16BotLeftDiagTop   = 16;

   public static final int TYPE_17BotLeftDiagRight = 17;

   public static final int TYPE_18BotRightDiagTop  = 18;

   public static final int TYPE_19BotRightDiagLeft = 19;

   /**
    * Clock wise incremental position on the diag borders
    */
   public static final int POSDIAG_0_TOP_LEFT      = 0;

   public static final int POSDIAG_1_TOP_RIGHT     = 1;

   public static final int POSDIAG_2_BOT_RIGHT     = 2;

   public static final int POSDIAG_3_BOT_LEFT      = 3;

   public static final int POS_0_TOP               = 0;

   public static final int POS_1_BOT               = 1;

   public static final int POS_2_LEFT              = 2;

   public static final int POS_3_RIGHT             = 3;

   public static final int POS_4_CENTER            = 4;

   public static final int LOGIC_0_UNDEFINED       = 0;

   public static final int LOGIC_1_TOP_LEFT        = 1;

   public static final int LOGIC_2_CENTER          = 2;

   public static final int LOGIC_3_BOTTOM_RIGHT    = 3;

   /**
    * When Anchor is computed, it can be outside a rectangle.
    * However
    */
   public static final int ANC_MINUS1_OUTSIDE      = -1;

   public static final int ANC_0_TOP_LEFT          = 0;

   public static final int ANC_1_TOP_CENTER        = 1;

   public static final int ANC_2_TOP_RIGHT         = 2;

   public static final int ANC_3_CENTER_LEFT       = 3;

   public static final int ANC_4_CENTER_CENTER     = 4;

   public static final int ANC_5_CENTER_RIGHT      = 5;

   public static final int ANC_6_BOT_LEFT          = 6;

   public static final int ANC_7_BOT_CENTER        = 7;

   public static final int ANC_8_BOT_RIGHT         = 8;

}
