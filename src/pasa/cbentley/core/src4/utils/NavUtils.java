/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.utils;

import pasa.cbentley.core.src4.interfaces.ITechNav;

public class NavUtils implements ITechNav {

   public static int navInverse(int navEvent) {
      switch (navEvent) {
         case NAV_00_REFRESH:
            return NAV_00_REFRESH;
         case NAV_01_UP:
            return NAV_02_DOWN;
         case NAV_02_DOWN:
            return NAV_01_UP;
         case NAV_03_LEFT:
            return NAV_04_RIGHT;
         case NAV_04_RIGHT:
            return NAV_03_LEFT;
         case NAV_05_SELECT:
            return NAV_06_UNSELECT;
         case NAV_06_UNSELECT:
            return NAV_05_SELECT;
         default:
            return NAV_00_REFRESH;
      }
   }
}
