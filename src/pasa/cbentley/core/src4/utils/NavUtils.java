/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.utils;

import pasa.cbentley.core.src4.interfaces.ITechNav;

public class NavUtils implements ITechNav {

   public static int navInverse(int navEvent) {
      switch (navEvent) {
         case NAV_0_REFRESH:
            return NAV_0_REFRESH;
         case NAV_1_UP:
            return NAV_2_DOWN;
         case NAV_2_DOWN:
            return NAV_1_UP;
         case NAV_3_LEFT:
            return NAV_4_RIGHT;
         case NAV_4_RIGHT:
            return NAV_3_LEFT;
         case NAV_5_SELECT:
            return NAV_6_UNSELECT;
         case NAV_6_UNSELECT:
            return NAV_5_SELECT;
         default:
            return NAV_0_REFRESH;
      }
   }
}
