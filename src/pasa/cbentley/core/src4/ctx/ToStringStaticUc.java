/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.ctx;

import pasa.cbentley.core.src4.interfaces.C;
import pasa.cbentley.core.src4.interfaces.ITechThread;
import pasa.cbentley.core.src4.interfaces.ITechTransform;
import pasa.cbentley.core.src4.logging.ITechTags;
import pasa.cbentley.core.src4.logging.ToStringStaticBase;
import pasa.cbentley.core.src4.thread.ITechRunnable;

public class ToStringStaticUc extends ToStringStaticBase {

   public static String toStringTrans(int key) {
      switch (key) {
         case ITechTransform.TRANSFORM_0_NONE:
            return "None";
         case ITechTransform.TRANSFORM_1_FLIP_H_MIRROR_ROT180:
            return "Flip H = MirrorRot180";
         case ITechTransform.TRANSFORM_2_FLIP_V_MIRROR:
            return "VMirror";
         case ITechTransform.TRANSFORM_3_ROT_180:
            return "Rot180";
         case ITechTransform.TRANSFORM_4_MIRROR_ROT270:
            return "MirrorRot270";
         case ITechTransform.TRANSFORM_5_ROT_90:
            return "Rot90";
         case ITechTransform.TRANSFORM_6_ROT_270:
            return "Rot270";
         case ITechTransform.TRANSFORM_7_MIRROR_ROT90:
            return "MirrorRot90";
         default:
            return "ToStringTrans Unknown" + key;
      }
   }

   public static String toStringTransform(int trans) {
      switch (trans) {
         case ITechTransform.TRANSFORM_0_NONE:
            return "No Transform";
         case ITechTransform.TRANSFORM_1_FLIP_H_MIRROR_ROT180:
            return "Flip_H";
         case ITechTransform.TRANSFORM_2_FLIP_V_MIRROR:
            return "Flip_V";
         case ITechTransform.TRANSFORM_3_ROT_180:
            return "Rotation_180";
         case ITechTransform.TRANSFORM_4_MIRROR_ROT270:
            return "Mirrot_Rotation270";
         case ITechTransform.TRANSFORM_5_ROT_90:
            return "Rotation_90";
         case ITechTransform.TRANSFORM_6_ROT_270:
            return "Rotation_270";
         case ITechTransform.TRANSFORM_7_MIRROR_ROT90:
            return "Mirror_Rotation90";
         default:
            return "Unknown Transform " + trans;
      }
   }

   public static String toStringDiagDir(int type) {
      switch (type) {
         case C.DIAG_DIR_0_TOP_LEFT:
            return "TopLeft";
         case C.DIAG_DIR_1_TOP_RIGHT:
            return "TopRight";
         case C.DIAG_DIR_2_BOT_LEFT:
            return "BotLeft";
         case C.DIAG_DIR_3_BOT_RIGHT:
            return "BotRight";
         default:
            return "UnknownDiagDir";
      }
   }

   public static String toStringThreadMode(int mode) {
      switch (mode) {
         case ITechThread.THREAD_MODE_0_POST_NOW:
            return "PostNow";
         case ITechThread.THREAD_MODE_1_MAIN_NOW:
            return "MainNow";
         case ITechThread.THREAD_MODE_2_MAIN_LATER:
            return "MainLater";
         case ITechThread.THREAD_MODE_3_WORKER:
            return "Worker";
         default:
            return "Unknown " + mode;
      }
   }

   public static String toStringThreadState(int state) {
      switch (state) {
         case ITechThread.STATE_0_ON:
            return "On";
         case ITechThread.STATE_1_OFF:
            return "Off";
         case ITechThread.STATE_2_SHUT_DOWN:
            return "Shutdown";
         case ITechThread.STATE_3_PAUSED:
            return "Paused";
         case ITechThread.STATE_4_KEEP_ON:
            return "KeepOn";
         default:
            return "Unknown " + state;
      }
   }

   public static String toStringState(int state) {
      switch (state) {
         case ITechRunnable.STATE_0_RUNNING:
            return "Running";
         case ITechRunnable.STATE_1_PAUSED:
            return "Pause";
         case ITechRunnable.STATE_2_CANCELED:
            return "Canceled";
         case ITechRunnable.STATE_3_STOPPED:
            return "Stopped";
         case ITechRunnable.STATE_4_CANCELED_ERROR:
            return "Errored";
         case ITechRunnable.STATE_5_INTERRUPTED:
            return "Interrupted";
         case ITechRunnable.STATE_6_FINISHED:
            return "Finished";
         default:
            return "UnknownState" + state;
      }
   }
   
   public static String toStringDebugTag(int tag) {
      switch (tag) {
         case ITechTags.FLAG_01_PRINT_ALWAYS:
            return ITechTags.STRING_01_ALWAYS;
         case ITechTags.FLAG_02_PRINT_NULL:
            return ITechTags.STRING_02_NULL;
         case ITechTags.FLAG_04_PRINT_STATOR:
            return ITechTags.STRING_04_STATOR;
         case ITechTags.FLAG_05_PRINT_UI:
            return ITechTags.STRING_05_UI;
         case ITechTags.FLAG_06_PRINT_WORK:
            return ITechTags.STRING_06_WORK;
         case ITechTags.FLAG_07_PRINT_EVENT:
            return ITechTags.STRING_07_EVENT;
         case ITechTags.FLAG_08_PRINT_EXCEPTION:
            return ITechTags.STRING_08_EX;
         case ITechTags.FLAG_09_PRINT_FLOW:
            return ITechTags.STRING_09_FLOW;
         case ITechTags.FLAG_10_PRINT_MODEL:
            return ITechTags.STRING_10_MODEL;
         case ITechTags.FLAG_11_PRINT_COMMANDS:
            return ITechTags.STRING_11_CMD;
         case ITechTags.FLAG_12_PRINT_BUSINESS:
            return ITechTags.STRING_12_BUSINESS;
         case ITechTags.FLAG_13_PRINT_SOUND:
            return ITechTags.STRING_13_SOUND;
         case ITechTags.FLAG_14_PRINT_TEMP:
            return ITechTags.STRING_14_TEMP;
         case ITechTags.FLAG_15_PRINT_DATA:
            return ITechTags.STRING_15_DATA;
         case ITechTags.FLAG_16_PRINT_TAG:
            return ITechTags.STRING_16_TAG;
         case ITechTags.FLAG_17_PRINT_TEST:
            return ITechTags.STRING_17_TEST;
         case ITechTags.FLAG_18_PRINT_MEMORY:
            return ITechTags.STRING_18_MEMORY;
         case ITechTags.FLAG_19_PRINT_BRIDGE:
            return ITechTags.STRING_19_BRIDGE;
         case ITechTags.FLAG_20_PRINT_INIT:
            return ITechTags.STRING_20_INIT;
         case ITechTags.FLAG_21_PRINT_BIP:
            return ITechTags.STRING_21_BIP;
         case ITechTags.FLAG_22_PRINT_STATE:
            return ITechTags.STRING_22_STATE;
         case ITechTags.FLAG_23_PRINT_ANIM:
            return ITechTags.STRING_23_ANIM;
         case ITechTags.FLAG_24_PRINT_DRAW:
            return ITechTags.STRING_24_DRAW;
         default:
            return "Unknown " + tag;
      }
   }
}
