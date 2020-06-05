/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.interfaces;

import pasa.cbentley.core.src4.utils.NavUtils;

/**
 * Codes for the navigation.
 * 
 * 
 * Default functions on {@link NavUtils}
 * 
 * @author Charles Bentley
 *
 */
public interface ITechNav extends ITech {

   
   public static final int FLAG_1_HORIZ      = 1 << 0;

   public static final int FLAG_2_VERTI      = 1 << 1;

   public static final int FLAG_3_SELECTABLE = 1 << 2;

   public static final int FLAG_4_NAV8       = 1 << 3;

   public static final int NAV_0_REFRESH     = 0;

   /**
    */
   public static final int NAV_1_UP          = 1;

   public static final int NAV_2_DOWN        = 2;

   public static final int NAV_3_LEFT        = 3;

   public static final int NAV_4_RIGHT       = 4;

   /**
    * Select gives the focus 
    */
   public static final int NAV_5_SELECT      = 5;

   /**
    * Code for unselecting the entity
    */
   public static final int NAV_6_UNSELECT    = 6;

   /**
    * give back focus to the parent, the parent knowing focus is coming from down under.
    * therefore does not fire up init state due to first life focus.
    */
   public static final int NAV_7_SELECT_FROM = 7;

   public static final int NAV_8_MOVE        = 8;

   public static final int NAV8_1_TOP_TOP    = 1;

   public static final int NAV8_2_TOP_LFT    = 2;

   public static final int NAV8_3_LFT_LFT    = 3;

   public static final int NAV8_4_LFT_BOT    = 4;

   public static final int NAV8_5_BOT_BOT    = 5;

   public static final int NAV8_5_BOT_RIT    = 6;

   public static final int NAV8_7_RIT_RIT    = 7;

   public static final int NAV8_8_RIT_TOP    = 8;
}
