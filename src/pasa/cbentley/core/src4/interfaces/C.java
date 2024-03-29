/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.interfaces;

import pasa.cbentley.core.src4.logging.ToStringStaticC;

/**
 * Cheap collection of int enumerations.
 * <br>
 * Cost nothing in memory once inlined.
 * <br>
 * Note: Single letter interfaces don't use the I prefix convention.
 * Since by convention, you cannot have single letter classes.
 * 
 * @see ToStringStaticC
 * @author Charles Bentley
 *
 */
public interface C extends ITech {

   public static final int AXIS_0_VERTICAL          = 0;

   public static final int AXIS_1_HORIZONTAL        = 1;

   public static final int AXIS_2_ASCENDING         = 2;

   public static final int AXIS_3_DESCENDING        = 3;

   public static final int ANC_0_TOP_LEFT           = 0;

   public static final int ANC_1_TOP_CENTER         = 1;

   public static final int ANC_2_TOP_RIGHT          = 2;

   public static final int ANC_3_CENTER_LEFT        = 3;

   public static final int ANC_4_CENTER_CENTER      = 4;

   public static final int ANC_5_CENTER_RIGHT       = 5;

   public static final int ANC_6_BOT_LEFT           = 6;

   public static final int ANC_7_BOT_CENTER         = 7;

   public static final int ANC_8_BOT_RIGHT          = 8;

   /**
    * When Anchor is computed, it can be outside a rectangle.
    * However
    */
   public static final int ANC_MINUS1_OUTSIDE       = -1;

   /**
    * 
    */
   public static final int ANGLE_DOWN_270           = 270;

   /**
    * 
    */
   public static final int ANGLE_LEFT_180           = 180;

   /**
    * 
    */
   public static final int ANGLE_RIGHT_0            = 0;

   /**
    * Equals to angle 90 degree
    */
   public static final int ANGLE_UP_90              = 90;

   public static final int DIAG_DIR_0_TOP_LEFT      = 0;

   public static final int DIAG_DIR_1_TOP_RIGHT     = 1;

   public static final int DIAG_DIR_2_BOT_LEFT      = 2;

   public static final int DIAG_DIR_3_BOT_RIGHT     = 3;

   /**
    * Compatible with the first 8 TYPE
    */
   public static final int DIR_0_TOP                = 0;

   public static final int DIR_1_BOTTOM             = 1;

   public static final int DIR_2_LEFT               = 2;

   public static final int DIR_3_RIGHT              = 3;

   public static final int DIR_4_TopLeft            = 4;

   public static final int DIR_5_TopRight           = 5;

   public static final int DIR_6_BotLeft            = 6;

   public static final int DIR_7_BotRight           = 7;

   public static final int LINE_0_HORIZONTAL        = 0;

   public static final int LINE_1_VERTICAL          = 1;

   public static final int LOGIC_0_UNDEFINED        = 0;

   public static final int LOGIC_1_TOP_LEFT         = 1;

   public static final int LOGIC_2_CENTER           = 2;

   public static final int LOGIC_3_BOTTOM_RIGHT     = 3;

   public static final int POS_0_TOP                = 0;

   public static final int POS_1_BOT                = 1;

   public static final int POS_2_LEFT               = 2;

   public static final int POS_3_RIGHT              = 3;

   public static final int POS_4_CENTER             = 4;

   /**
    * Clock wise incremental position on the diag borders
    */
   public static final int POSDIAG_0_TOP_LEFT       = 0;

   public static final int POSDIAG_1_TOP_RIGHT      = 1;

   public static final int POSDIAG_2_BOT_RIGHT      = 2;

   public static final int POSDIAG_3_BOT_LEFT       = 3;

   public static final int TEST_0_FAIL              = 0;

   public static final int TEST_1_SUCCESS           = 1;

   public static final int TEST_2_UNKNOWN           = 2;

   public static final int TYPE_00_TOP              = 0;

   public static final int TYPE_01_BOTTOM           = 1;

   public static final int TYPE_02_LEFT             = 2;

   public static final int TYPE_03_RIGHT            = 3;

   public static final int TYPE_04_TopLeft          = 4;

   public static final int TYPE_05_TopRight         = 5;

   public static final int TYPE_06_BotLeft          = 6;

   public static final int TYPE_07_BotRight         = 7;

   public static final int TYPE_08_MID_TopLeft      = 8;

   public static final int TYPE_09_MID_TopRight     = 9;

   public static final int TYPE_10_MID_BotLeft      = 10;

   public static final int TYPE_11_MID_BotRight     = 11;

   public static final int TYPE_12_TopLeftDiagBot   = 12;

   public static final int TYPE_13_TopLeftDiagRight = 13;

   public static final int TYPE_14_TopRightDiagBot  = 14;

   public static final int TYPE_15_TopRightDiagLeft = 15;

   public static final int TYPE_16_BotLeftDiagTop   = 16;

   public static final int TYPE_17_BotLeftDiagRight = 17;

   public static final int TYPE_18_BotRightDiagTop  = 18;

   public static final int TYPE_19_BotRightDiagLeft = 19;

}
