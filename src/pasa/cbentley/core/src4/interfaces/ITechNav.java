/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.interfaces;

import pasa.cbentley.core.src4.utils.NavUtils;

/**
 * Codes for the navigation in space and time.
 * 
 * 
 * Default functions on {@link NavUtils}
 * 
 * @author Charles Bentley
 *
 */
public interface ITechNav extends ITech {

   public static final int FLAG_1_HORIZ                = 1 << 0;

   public static final int FLAG_2_VERTI                = 1 << 1;

   public static final int FLAG_3_SELECTABLE           = 1 << 2;

   /**
    * Up to 8 directions possible from one position
    */
   public static final int FLAG_4_NAV8                 = 1 << 3;

   public static final int NAV_00_REFRESH              = 0;

   /**
    */
   public static final int NAV_01_UP                   = 1;

   public static final int NAV_02_DOWN                 = 2;

   public static final int NAV_03_LEFT                 = 3;

   public static final int NAV_04_RIGHT                = 4;

   /**
    * Select gives the focus 
    */
   public static final int NAV_05_SELECT               = 5;

   /**
    * Code for unselecting the entity
    */
   public static final int NAV_06_UNSELECT             = 6;

   public static final int NAV_20_PRESELECT            = 20;

   /**
    * give back focus to the parent, the parent knowing focus is coming from down under.
    * therefore does not fire up init state due to first life focus.
    */
   public static final int NAV_07_SELECT_FROM          = 7;

   public static final int NAV_08_MOVE                 = 8;

   /**
    * In the structure of, move the focus to the next element
    */
   public static final int NAV_09_FOCUS_CYCLE_FORWARD  = 9;

   /**
    * move back in the currency historical context
    */
   public static final int NAV_10_FOCUS_CYCLE_BACKWARD = 10;

   public static final int NAV_11_SCROLL_UP            = 11;

   public static final int NAV_12_SCROLL_DOWN          = 12;

   public static final int NAV_13_SCROLL_LEFT          = 13;

   public static final int NAV_14_SCROLL_RITE          = 14;

   public static final int NAV_21_PAGE_UP              = 21;

   public static final int NAV_22_PAGE_DOWN            = 22;

   public static final int NAV_23_PAGE_LEFT            = 23;

   public static final int NAV_24_PAGE_RITE            = 24;

   /**
    * 
    */
   public static final int NAV8_1_TOP_TOP              = 1;

   public static final int NAV8_2_TOP_LFT              = 2;

   public static final int NAV8_3_LFT_LFT              = 3;

   public static final int NAV8_4_LFT_BOT              = 4;

   public static final int NAV8_5_BOT_BOT              = 5;

   public static final int NAV8_5_BOT_RIT              = 6;

   public static final int NAV8_7_RIT_RIT              = 7;

   public static final int NAV8_8_RIT_TOP              = 8;
}
