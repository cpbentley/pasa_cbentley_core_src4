package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.interfaces.C;

public class ToStringStaticC extends ToStringStaticBase implements C {

   public static String toStringCDir(int dir) {
      switch (dir) {
         case DIR_0TOP:
            return "Top";
         case DIR_1BOTTOM:
            return "Bottom";
         case DIR_2LEFT:
            return "Left";
         case DIR_3RIGHT:
            return "Right";
         case DIR_4TopLeft:
            return "TopLeft";
         case DIR_5TopRight:
            return "TopRight";
         case DIR_6BotLeft:
            return "BotLeft";
         case DIR_7BotRight:
            return "BotRight";
         default:
            return "UnknownDir" + dir;
      }
   }

   public static String toStringPos(int pos) {
      switch (pos) {
         case POS_0_TOP:
            return "Top";
         case POS_1_BOT:
            return "Bot";
         case POS_2_LEFT:
            return "Left";
         case POS_3_RIGHT:
            return "Right";
         default:
            return "Unknown " + pos;
      }
   }

   public static String toStringLogicAlignY(int dir) {
      switch (dir) {
         case LOGIC_0_UNDEFINED:
            return "Undefined";
         case LOGIC_1_TOP_LEFT:
            return "Top";
         case LOGIC_2_CENTER:
            return "Center";
         case LOGIC_3_BOTTOM_RIGHT:
            return "Bottom";
         default:
            return "Unknown " + dir;
      }
   }

   public static String toStringLogicAlignX(int dir) {
      switch (dir) {
         case LOGIC_0_UNDEFINED:
            return "Undefined";
         case LOGIC_1_TOP_LEFT:
            return "Left";
         case LOGIC_2_CENTER:
            return "Center";
         case LOGIC_3_BOTTOM_RIGHT:
            return "Right";
         default:
            return "Unknown " + dir;
      }
   }

   public static String toStringLogicAlignXStartEnd(int dir) {
      switch (dir) {
         case LOGIC_0_UNDEFINED:
            return "Undefined";
         case LOGIC_1_TOP_LEFT:
            return "Start";
         case LOGIC_2_CENTER:
            return "Center";
         case LOGIC_3_BOTTOM_RIGHT:
            return "End";
         default:
            return "Unknown " + dir;
      }
   }

   public static String toStringLogicAlign(int dir) {
      switch (dir) {
         case LOGIC_0_UNDEFINED:
            return "Undefined";
         case LOGIC_1_TOP_LEFT:
            return "Top-Left";
         case LOGIC_2_CENTER:
            return "Center";
         case LOGIC_3_BOTTOM_RIGHT:
            return "Bot-Right";
         default:
            return "Unknown " + dir;
      }
   }

   public static String toStringAnc(int ac) {
      switch (ac) {
         case ANC_0_TOP_LEFT:
            return "TopLeft";
         case ANC_1_TOP_CENTER:
            return "TopCenter";
         case ANC_2_TOP_RIGHT:
            return "TopRight";
         case ANC_3_CENTER_LEFT:
            return "CenterLeft";
         case ANC_4_CENTER_CENTER:
            return "Center";
         case ANC_5_CENTER_RIGHT:
            return "CenterRight";
         case ANC_6_BOT_LEFT:
            return "BotLeft";
         case ANC_7_BOT_CENTER:
            return "BotCenter";
         case ANC_8_BOT_RIGHT:
            return "BotRight";
         case ANC_MINUS1_OUTSIDE:
            return "Outside";
         default:
            return "UnknownAnc" + ac;
      }
   }
}
