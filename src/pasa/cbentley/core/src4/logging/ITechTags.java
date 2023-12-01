/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.interfaces.ITech;

/**
 * Define a set of default tags.
 * 
 * @author Charles Bentley
 *
 */
public interface ITechTags extends ITech {

   public static final int    FLAG_01_PRINT_ALWAYS    = 1 << 0;

   public static final int    FLAG_05_PRINT_UI        = 1 << 4;

   public static final int    FLAG_06_PRINT_WORK      = 1 << 5;

   public static final int    FLAG_07_PRINT_EVENT     = 1 << 6;

   public static final int    FLAG_08_PRINT_EXCEPTION = 1 << 7;

   public static final int    FLAG_09_PRINT_FLOW      = 1 << 8;

   public static final int    FLAG_10_PRINT_MODEL     = 1 << 9;

   public static final int    FLAG_11_PRINT_COMMANDS  = 1 << 10;

   public static final int    FLAG_12_PRINT_BUSINESS  = 1 << 11;

   public static final int    FLAG_13_PRINT_SOUND     = 1 << 12;

   public static final int    FLAG_14_PRINT_TEMP      = 1 << 13;

   public static final int    FLAG_15_PRINT_DATA      = 1 << 14;

   public static final int    FLAG_16_PRINT_TAG       = 1 << 15;

   public static final int    FLAG_17_PRINT_TEST      = 1 << 16;

   public static final int    FLAG_18_PRINT_MEMORY    = 1 << 17;

   public static final int    FLAG_19_PRINT_BRIDGE    = 1 << 18;

   public static final int    FLAG_20_PRINT_INIT      = 1 << 19;

   public static final int    FLAG_21_PRINT_BIP       = 1 << 20;

   public static final int    FLAG_22_PRINT_STATE     = 1 << 21;

   public static final int    FLAG_23_PRINT_ANIM      = 1 << 22;

   public static final int    FLAG_24_PRINT_DRAW      = 1 << 23;

   /**
    * Message that should not occur, generating a NPE if not catching
    */
   public static final int    FLAG_25_PRINT_NULL      = 1 << 24;

   public static final int    FLAG_ALL                = -1;

   public static final String STRING_01_ALWAYS        = "Always";

   public static final String STRING_05_UI            = "Ui    ";

   public static final String STRING_06_WORK          = "Work  ";

   public static final String STRING_07_EVENT         = "Event ";

   public static final String STRING_08_EX            = "Except";

   public static final String STRING_09_FLOW          = "Flow  ";

   public static final String STRING_10_MODEL         = "Model ";

   public static final String STRING_11_CMD           = "Cmd   ";

   public static final String STRING_12_BUSINESS      = "Business";

   public static final String STRING_13_SOUND         = "Sound ";

   public static final String STRING_14_TEMP          = "Temp  ";

   public static final String STRING_15_DATA          = "Data  ";

   public static final String STRING_16_TAG           = "Tag   ";

   public static final String STRING_17_TEST          = "Test  ";

   public static final String STRING_18_MEMORY        = "Mem   ";

   public static final String STRING_19_BRIDGE        = "Bridge";

   public static final String STRING_20_INIT          = "Init  ";

   public static final String STRING_21_BIP           = "BIP   ";

   public static final String STRING_22_STATE         = "State ";

   public static final String STRING_23_ANIM          = "Anim  ";

   public static final String STRING_24_DRAW          = "Draw  ";

   public static final String STRING_25_NULL          = "Null";

}
