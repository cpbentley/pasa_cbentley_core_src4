/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

import java.util.Enumeration;
import java.util.Hashtable;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.ToStringStaticUc;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.CounterInt;
import pasa.cbentley.core.src4.utils.BitUtils;

/**
 * A Given config which tells whethere a {@link LogEntryType} is
 * <li>accepted.
 * <li>1line
 * <li>stack traced
 * 
 * Defines DCtx format flag.
 * Those flags are set to the DCtx fed to the toString method.
 * The class can be include or exlucde some data points from the String returned.
 * <br>
 * When a LogEntry is live, LogEntry string can be updated with new flags.
 * <br>
 * <br>
 * 
 * @author Charles Bentley
 *
 */
public class DLogConfig extends ObjectU implements IDLogConfig, ITechTags, ITechDLogConfig {

   private Hashtable all                 = new Hashtable();

   protected int     cmdflags;

   protected int     flagsFormat;

   protected int     flagsMaster;

   /**
    * <li> {@link ITechTags#FLAG_07_PRINT_EVENT}
    */
   protected int     flagsTag;

   private int       flagsTagNeg;

   private String flagToStringSeparator = "-";

   private Hashtable fullPositives       = new Hashtable();

   private int       logLevel            = ITechLvl.LVL_05_FINE;

   /**
    * Class object that are required to be ignored.
    * We use a hashtable because src4 only has hashtable for hashing.
    * No hashset
    * Implement our own?
    */
   private Hashtable negatives           = new Hashtable();

   private Hashtable positives1LineClass = new Hashtable();

   private Hashtable positives1LineInt   = new Hashtable();

   /**
    * 
    */
   private Hashtable positivesClasses    = new Hashtable();

   private Hashtable positivesTraceClass = new Hashtable();

   private Hashtable positivesTraceTags  = new Hashtable();

   private String    stackPrefix;

   private int       threshold;

   public DLogConfig(UCtx uc) {
      super(uc);
   }

   /**
    * a config integer flags for the {@link LogEntryType}.
    * <br>
    * Tell if 1line, stack trace 
    * @param type LogEntryType to flag according to our logging configuration
    * @return
    */
   public DLogEntryOfConfig getEntryConfig(DLogEntry type) {
      //first check if accepted
      DLogEntryOfConfig entryOfConf = new DLogEntryOfConfig(uc, this, type);
      int flags = 0;
      boolean isAccepted = isAccepted(type);
      
      entryOfConf.setConfigResFlag(FORMAT_FLAG_01_ACCEPTED, isAccepted);

      if (this.hasFlagMaster(MASTER_FLAG_12_LEVEL)) {
         entryOfConf.setConfigResFlag(FORMAT_FLAG_07_LEVEL, true);
      }
      if (this.hasFlagMaster(MASTER_FLAG_07_THREAD_DATA)) {
         entryOfConf.setConfigResFlag(FORMAT_FLAG_04_THREAD, true);
      }
      if (this.hasFlagMaster(MASTER_FLAG_10_OWNER_NAME_UC)) {
         entryOfConf.setConfigResFlag(FORMAT_FLAG_08_OWNER_NAME, true);
      }
      //Dev flags override master flags and are processed last
      if (type.hasDevFlag(DEV_4_THREAD)) {
         entryOfConf.setConfigResFlag(FORMAT_FLAG_04_THREAD, true);
      }
      //dev flags override the config?
      if (type.hasDevFlag(DEV_2_ONELINE)) {
         entryOfConf.setConfigResFlag(FORMAT_FLAG_02_1LINE, true);
      }
      if (type.hasDevFlag(DEV_3_STACK)) {
         entryOfConf.setConfigResFlag(FORMAT_FLAG_03_STACK, true);
      }
      if (type.hasDevFlag(DEV_6_BIG)) {
         entryOfConf.setConfigResFlag(FORMAT_FLAG_06_BIG, true);
      }
      
      return entryOfConf;
   }

   public int getLevel() {
      return getLogLevel();
   }

   public int getLogLevel() {
      return logLevel;
   }

   /**
    * Return the stackprefix.
    * <br>
    * This allows to only print stack trace up to your framework and ignore java packages
    * which are irrelevant.
    * @return
    */
   public String getStackTraceBreak() {
      return stackPrefix;
   }

   /**
    * A Config can have several data flags.
    * <br>
    * It controls what the toString outputs. It is passed along the {@link Dctx}
    * @param flag
    * @return
    */
   public boolean hasFlagData(int flag) {
      return false;
   }

   /**
    * Flags set with {@link DLogConfig#setFlagFormat(int, boolean)}
    */
   public boolean hasFlagFormat(int flag) {
      return BitUtils.hasFlag(flagsFormat, flag);
   }

   /**
    * Sets
    * <li> {@link ITechDLogConfig#MASTER_FLAG_01_BLOCK_ALL_PRINT}
    * <li> {@link ITechDLogConfig#MASTER_FLAG_02_OPEN_ALL_PRINT}
    * <li> {@link ITechDLogConfig#MASTER_FLAG_03_ONLY_POSITIVES}
    * <li> {@link ITechDLogConfig#MASTER_FLAG_04_IGNORE_CLASSES}
    * <li> {@link ITechDLogConfig#MASTER_FLAG_05_IGNORE_FLAGS}
    * <li> {@link ITechDLogConfig#MASTER_FLAG_06_CLASS_INSTANCES}
    * @param flag
    * @param b
    */
   public boolean hasFlagMaster(int flag) {
      return BitUtils.hasFlag(flagsMaster, flag);
   }

   /**
    * <li> {@link ITechTags#FLAG_01_PRINT_ALWAYS}
    * <li> {@link ITechTags#FLAG_05_PRINT_UI}
    * <li> {@link ITechTags#FLAG_06_PRINT_WORK}
    * <li> {@link ITechTags#FLAG_07_PRINT_EVENT}
    * <li> {@link ITechTags#FLAG_08_PRINT_EXCEPTION}
    * <li> {@link ITechTags#FLAG_09_PRINT_FLOW}
    * <li> {@link ITechTags#FLAG_19_PRINT_BRIDGE}
    * @param flag
    * @return
    */
   public boolean hasFlagTag(int flag) {
      return BitUtils.hasFlag(flagsTag, flag);
   }

   /**
    * <li> {@link ITechTags#FLAG_01_PRINT_ALWAYS}
    * <li> {@link ITechTags#FLAG_05_PRINT_UI}
    * <li> {@link ITechTags#FLAG_06_PRINT_WORK}
    * <li> {@link ITechTags#FLAG_07_PRINT_EVENT}
    * <li> {@link ITechTags#FLAG_08_PRINT_EXCEPTION}
    * <li> {@link ITechTags#FLAG_09_PRINT_FLOW}
    * <li> {@link ITechTags#FLAG_19_PRINT_BRIDGE} 
    * @param flag
    * @return
    */
   public boolean hasFlagTagNeg(int flag) {
      return BitUtils.hasFlag(flagsTagNeg, flag);
   }

   /**
    * Forces 1 line formatting on the key LogEntryType 
    * @param flag
    * @param c
    * @return
    */
   public boolean is1Lined(int flag, Class c) {
      if (c != null) {
         if (positives1LineClass.contains(c)) {
            if (positives1LineInt.containsKey(new Integer(flag))) {
               return true;
            }
         }
      }
      return false;
   }

   /**
    * By default an entry is not 1 line.
    * <br>
    * User can configure so that some log entries are 1 lined
    * <br>
    * @param type
    * @return
    */
   public boolean is1Lined(LogEntryType type) {

      return false;
   }

   public boolean isAccepted(DLogEntry type) {
      boolean isClassFullDebug = isClassFullPositive(type.getClassL());
      if (isClassFullDebug) {
         return true;
      }

      //fail fast
      if (type.getLevel() < logLevel) {
         return false;
      }
      int tagID = type.getTagID();
      boolean isAcceptedFlag = isAcceptedFlags(tagID);
      if (isAcceptedFlag) {

         boolean isClassAccepted = isClassAccepted(type.getClassL());
         if (isClassAccepted) {
            return true;
         }
         if (hasFlagMaster(MASTER_FLAG_09_TREAT_STRINGABLE_CLASS)) {
            boolean isClassStringableAccepted = isClassAccepted(type.getClassStringable());
            if (isClassStringableAccepted) {
               return true;
            }
         }
         return false;
      }
      return false;
   }

   public boolean isAccepted(LogEntryType type) {
      //fail fast
      if (type.getLevel() < logLevel) {
         return false;
      }

      return true;
   }

   public boolean isAcceptedFlags(int flagTag) {
      if (hasFlagMaster(MASTER_FLAG_02_OPEN_ALL_PRINT)) {
         return true;
      }
      if (hasFlagMaster(MASTER_FLAG_01_BLOCK_ALL_PRINT)) {
         return false;
      }
      if (hasFlagMaster(MASTER_FLAG_05_IGNORE_FLAGS)) {
         return true;
      } else {
         if (flagTag == FLAG_01_PRINT_ALWAYS) {
            return true;
         }
         if (hasFlagMaster(MASTER_FLAG_08_OPEN_ALL_BUT_FALSE)) {
            //accept it unless flag as negative
            boolean hasFlagTagNeg = hasFlagTagNeg(flagTag);
            if (hasFlagTagNeg) {
               return false;
            } else {
               return true;
            }
         } else {
            //only accept if tag is set to true
            boolean hasFlagTag = hasFlagTag(flagTag);
            return hasFlagTag;
         }
      }
   }

   /**
    * true if class is null.
    * 
    * <li> By default all classes are accepted except negative classes
    * <li> {@link ITechDLogConfig#MASTER_FLAG_03_ONLY_POSITIVES} hides classes not explicitely positive
    * @param c
    * @return
    */
   public boolean isClassAccepted(Class c) {
      if (c == null) {
         return true;
      }
      if (hasFlagMaster(MASTER_FLAG_04_IGNORE_CLASSES)) {
         return true;
      }

      boolean val = true;
      if (fullPositives.containsKey(c)) {
         //we have an override of nevatives
      } else {
         if (negatives.containsKey(c)) {
            val = false;
         } else if (hasFlagMaster(MASTER_FLAG_03_ONLY_POSITIVES)) {
            //val is true for default
            if (positivesClasses.containsKey(c)) {
               val = true;
            } else {
               if (hasFlagMaster(MASTER_FLAG_06_CLASS_INSTANCES)) {
                  val = isInsanceOf(positivesClasses, c);
               } else {
                  val = false;
               }
            }
         }
      }

      if (val) {
         //count classes occurences
         Object o = all.get(c);
         if (o == null) {
            o = new CounterInt(uc, 1);
            all.put(c, o);
         } else {
            ((CounterInt) o).increment();
         }
      }
      return val;
   }

   public boolean isClassFullPositive(Class c) {
      return fullPositives.containsKey(c);
   }

   public boolean isClassFullPostive(Class c) {
      return fullPositives.contains(c);
   }

   public boolean isClassNegative(Class c) {
      return negatives.contains(c);
   }

   private boolean isInsanceOf(Hashtable htClasses, Class c) {
      Enumeration enumf = positivesClasses.keys();
      while (enumf.hasMoreElements()) {
         Class cl = (Class) enumf.nextElement();
         if (c.isInstance(cl)) {
            return true;
         }
      }
      return false;
   }

   /**
    * Is the class and type stack trace.
    * <br>
    * Set by {@link IDLog#setStackTraced(int, Class, boolean)}
    * @param type
    * @param c
    * @return
    */
   public boolean isStackTraced(int type, Class c) {
      if (c != null) {
         if (positivesTraceClass.contains(c)) {
            if (positivesTraceTags.containsKey(new Integer(type))) {
               return true;
            }
         }
      }
      return false;
   }

   /**
    * @param method
    */
   public void onlyAcceptMethod(String method) {

   }

   public void removeAllNegatives() {
      negatives.clear();
   }

   public void removeAllPositives() {
      positivesClasses.clear();
   }

   /**
    * Sets the threshold (bytes) at which we print messages of type memory.
    * Let's say we want to log every array creation above 500 bytes
    * @param t
    */
   public void setArrayMemoryThreshold(int t) {
      threshold = t;
   }

   public void setClassFullPositive(Class c, boolean v) {
      if (v) {
         fullPositives.put(c, c);
      } else {
         fullPositives.remove(c);
      }
   }

   public void setClassNegative(Class c, boolean v) {
      if (v) {
         negatives.put(c, c);
      } else {
         negatives.remove(c);
      }

   }

   public void setClassPositives(Class c, boolean v) {
      if (v) {
         positivesClasses.put(c, c);
      } else {
         positivesClasses.remove(c);
      }
   }

   public void setFlagFormat(int flag, boolean b) {
      flagsFormat = BitUtils.setFlag(flagsFormat, flag, b);
   }

   public void setFlagMaster(int flag, boolean v) {
      flagsMaster = BitUtils.setFlag(flagsMaster, flag, v);
   }

   public void setFlagPrint(int[] flags, boolean v) {
      for (int i = 0; i < flags.length; i++) {
         setFlagMaster(flags[i], v);
      }
   }

   /**
    * Enable/Disable logs with that tagID
    * 
    * {@link ITechTags#FLAG_05_PRINT_UI}
    * {@link ITechTags#FLAG_07_PRINT_EVENT}
    * {@link ITechTags#FLAG_10_PRINT_MODEL}
    * 
    * by default all flags not explicitely set are refused
    * 
    * @param tagID
    * @param b
    */
   public void setFlagTag(int tagID, boolean b) {
      flagsTag = BitUtils.setFlag(flagsTag, tagID, b);
   }

   public void setFlagTag(int[] flags, boolean v) {
      for (int i = 0; i < flags.length; i++) {
         setFlagTag(flags[i], v);
      }
   }

   public void setFlagTagNeg(int tagID, boolean b) {
      flagsTagNeg = BitUtils.setFlag(flagsTagNeg, tagID, b);
   }

   /**
    * Only prints logs equal or above... globally.
    * 
    * But if tag is disable, none will appear
    */
   public void setLevelGlobal(int lvl) {
      this.logLevel = lvl;
   }

   /**
    * Set the level even for disabled tags.
    * 
    * Usually you want to set it to either Severe or Warning.
    * 
    * 
    * @param lvl
    */
   public void setLevelGlobalTag(int lvl) {
      this.logLevel = lvl;
   }

   /**
    * Specific level for tag that overrides the global level setting.
    * 
    * Useful when you want to see finer grained logs for a given tag.
    * 
    * @param lvl
    * @param tag
    */
   public void setLevelTag(int lvl, int tag) {
      this.logLevel = lvl;
   }

   /**
    * Force one lines one class tag
    */
   public void setOneLines(int tag, Class c) {
      positives1LineInt.put(new Integer(tag), Boolean.TRUE);
      positives1LineClass.put(c, Boolean.TRUE);
   }

   /**
    * Computes the stack trace for a class and a tag
    */
   public void setStackTraced(int tag, Class c, boolean b) {
      positivesTraceTags.put(new Integer(tag), Boolean.TRUE);
      positivesTraceClass.put(c, Boolean.TRUE);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, DLogConfig.class, 534);

      dc.appendVarWithNewLine("logLevel", logLevel);
      dc.appendWithSpace(ToStringStaticUc.toStringLogLevel(logLevel));
      dc.appendVarWithSpace("threshold", threshold);
      dc.appendVarWithSpace("stackPrefix", stackPrefix);

      dc.nl();
      dc.append("Master Trues  = ");
      toStringFlagMasterTrue(MASTER_FLAG_01_BLOCK_ALL_PRINT, STRING_M_01_BLOCK_ALL_PRINT, dc);
      toStringFlagMasterTrue(MASTER_FLAG_02_OPEN_ALL_PRINT, STRING_M_02_OPEN_ALL_PRINT, dc);
      toStringFlagMasterTrue(MASTER_FLAG_03_ONLY_POSITIVES, STRING_M_03_ONLY_POSITIVES, dc);
      toStringFlagMasterTrue(MASTER_FLAG_04_IGNORE_CLASSES, STRING_M_04_IGNORE_CLASSES, dc);
      toStringFlagMasterTrue(MASTER_FLAG_05_IGNORE_FLAGS, STRING_M_05_IGNORE_FLAGS, dc);
      toStringFlagMasterTrue(MASTER_FLAG_06_CLASS_INSTANCES, STRING_M_06_CLASS_INSTANCES, dc);
      toStringFlagMasterTrue(MASTER_FLAG_07_THREAD_DATA, STRING_M_07_THREAD_DATA, dc);
      toStringFlagMasterTrue(MASTER_FLAG_08_OPEN_ALL_BUT_FALSE, STRING_M_08_OPEN_ALL_BUT_FALSE, dc);
      toStringFlagMasterTrue(MASTER_FLAG_09_TREAT_STRINGABLE_CLASS, STRING_M_09_TREAT_STRINGABLE_CLASS, dc);
      toStringFlagMasterTrue(MASTER_FLAG_10_OWNER_NAME_UC, STRING_M_10_OWNER_NAME, dc);
      toStringFlagMasterTrue(MASTER_FLAG_11_IGNORES_BIGS, STRING_M_11_IGNORE_BIGS, dc);

      dc.nl();
      dc.append("Format Trues  = ");
      toStringFlagFormatTrue(FORMAT_FLAG_01_ACCEPTED, STRING_F_01_ACCEPTED, dc);
      toStringFlagFormatTrue(FORMAT_FLAG_02_1LINE, STRING_F_02_1LINE, dc);
      toStringFlagFormatTrue(FORMAT_FLAG_03_STACK, STRING_F_03_STACK, dc);
      toStringFlagFormatTrue(FORMAT_FLAG_04_THREAD, STRING_F_04_THREAD, dc);
      toStringFlagFormatTrue(FORMAT_FLAG_05_TIMESTAMP, STRING_F_05_TIMESTAMP, dc);
      toStringFlagFormatTrue(FORMAT_FLAG_06_BIG, STRING_F_06_BIG, dc);
      toStringFlagFormatTrue(FORMAT_FLAG_07_LEVEL, STRING_F_07_LEVEL, dc);
      toStringFlagFormatTrue(FORMAT_FLAG_08_OWNER_NAME, STRING_F_08_OWNER_NAME, dc);

      dc.nl();
      dc.append("Tags Trues = ");
      toStringFlagTagTrue(FLAG_01_PRINT_ALWAYS, STRING_01_ALWAYS, dc);
      toStringFlagTagTrue(FLAG_02_PRINT_NULL, STRING_02_NULL, dc);
      toStringFlagTagTrue(FLAG_04_PRINT_STATOR, STRING_04_STATOR, dc);
      toStringFlagTagTrue(FLAG_05_PRINT_UI, STRING_05_UI, dc);
      toStringFlagTagTrue(FLAG_06_PRINT_WORK, STRING_06_WORK, dc);
      toStringFlagTagTrue(FLAG_07_PRINT_EVENT, STRING_07_EVENT, dc);
      toStringFlagTagTrue(FLAG_08_PRINT_EXCEPTION, STRING_08_EX, dc);
      toStringFlagTagTrue(FLAG_09_PRINT_FLOW, STRING_09_FLOW, dc);
      toStringFlagTagTrue(FLAG_10_PRINT_MODEL, STRING_10_MODEL, dc);
      toStringFlagTagTrue(FLAG_11_PRINT_COMMANDS, STRING_11_CMD, dc);
      toStringFlagTagTrue(FLAG_12_PRINT_BUSINESS, STRING_12_BUSINESS, dc);
      toStringFlagTagTrue(FLAG_13_PRINT_SOUND, STRING_13_SOUND, dc);
      toStringFlagTagTrue(FLAG_14_PRINT_TEMP, STRING_14_TEMP, dc);
      toStringFlagTagTrue(FLAG_15_PRINT_DATA, STRING_15_DATA, dc);
      toStringFlagTagTrue(FLAG_16_PRINT_TAG, STRING_16_TAG, dc);
      toStringFlagTagTrue(FLAG_17_PRINT_TEST, STRING_17_TEST, dc);
      toStringFlagTagTrue(FLAG_18_PRINT_MEMORY, STRING_18_MEMORY, dc);
      toStringFlagTagTrue(FLAG_19_PRINT_BRIDGE, STRING_19_BRIDGE, dc);
      toStringFlagTagTrue(FLAG_20_PRINT_INIT, STRING_20_INIT, dc);
      toStringFlagTagTrue(FLAG_21_PRINT_BIP, STRING_21_BIP, dc);
      toStringFlagTagTrue(FLAG_22_PRINT_STATE, STRING_22_STATE, dc);
      toStringFlagTagTrue(FLAG_23_PRINT_ANIM, STRING_23_ANIM, dc);
      toStringFlagTagTrue(FLAG_24_PRINT_DRAW, STRING_24_DRAW, dc);
      toStringFlagTagTrue(FLAG_25_PRINT_CREATE, STRING_25_CREATE, dc);
      toStringFlagTagTrue(FLAG_26_PRINT_CONFIG, STRING_26_CONFIG, dc);
      toStringFlagTagTrue(FLAG_27_PRINT_SIMULATION, STRING_27_SIMULATION, dc);
      toStringFlagTagTrue(FLAG_28_PRINT_LOOP, STRING_28_LOOP, dc);

      dc.nl();
      dc.append("TagNegs Trues = ");
      toStringFlagTagNegTrue(FLAG_01_PRINT_ALWAYS, STRING_01_ALWAYS, dc);
      toStringFlagTagNegTrue(FLAG_02_PRINT_NULL, STRING_02_NULL, dc);
      toStringFlagTagNegTrue(FLAG_04_PRINT_STATOR, STRING_04_STATOR, dc);
      toStringFlagTagNegTrue(FLAG_05_PRINT_UI, STRING_05_UI, dc);
      toStringFlagTagNegTrue(FLAG_06_PRINT_WORK, STRING_06_WORK, dc);
      toStringFlagTagNegTrue(FLAG_07_PRINT_EVENT, STRING_07_EVENT, dc);
      toStringFlagTagNegTrue(FLAG_08_PRINT_EXCEPTION, STRING_08_EX, dc);
      toStringFlagTagNegTrue(FLAG_09_PRINT_FLOW, STRING_09_FLOW, dc);
      toStringFlagTagNegTrue(FLAG_10_PRINT_MODEL, STRING_10_MODEL, dc);
      toStringFlagTagNegTrue(FLAG_11_PRINT_COMMANDS, STRING_11_CMD, dc);
      toStringFlagTagNegTrue(FLAG_12_PRINT_BUSINESS, STRING_12_BUSINESS, dc);
      toStringFlagTagNegTrue(FLAG_13_PRINT_SOUND, STRING_13_SOUND, dc);
      toStringFlagTagNegTrue(FLAG_14_PRINT_TEMP, STRING_14_TEMP, dc);
      toStringFlagTagNegTrue(FLAG_15_PRINT_DATA, STRING_15_DATA, dc);
      toStringFlagTagNegTrue(FLAG_16_PRINT_TAG, STRING_16_TAG, dc);
      toStringFlagTagNegTrue(FLAG_17_PRINT_TEST, STRING_17_TEST, dc);
      toStringFlagTagNegTrue(FLAG_18_PRINT_MEMORY, STRING_18_MEMORY, dc);
      toStringFlagTagNegTrue(FLAG_19_PRINT_BRIDGE, STRING_19_BRIDGE, dc);
      toStringFlagTagNegTrue(FLAG_20_PRINT_INIT, STRING_20_INIT, dc);
      toStringFlagTagNegTrue(FLAG_21_PRINT_BIP, STRING_21_BIP, dc);
      toStringFlagTagNegTrue(FLAG_22_PRINT_STATE, STRING_22_STATE, dc);
      toStringFlagTagNegTrue(FLAG_23_PRINT_ANIM, STRING_23_ANIM, dc);
      toStringFlagTagNegTrue(FLAG_24_PRINT_DRAW, STRING_24_DRAW, dc);
      toStringFlagTagNegTrue(FLAG_25_PRINT_CREATE, STRING_25_CREATE, dc);
      toStringFlagTagNegTrue(FLAG_26_PRINT_CONFIG, STRING_26_CONFIG, dc);
      toStringFlagTagNegTrue(FLAG_27_PRINT_SIMULATION, STRING_27_SIMULATION, dc);
      toStringFlagTagNegTrue(FLAG_28_PRINT_LOOP, STRING_28_LOOP, dc);

      toStringHashClass(dc, "Negative Classes", negatives);
      toStringHashClass(dc, "Positive Classes", positivesClasses);
      toStringHashClass(dc, "FullPositive Classes", fullPositives);
      toStringHashClass(dc, "Trace Classes", positivesTraceClass);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, DLogConfig.class);
   }

   private void toStringFlagFormatTrue(int flag, String msg, Dctx dc) {
      if (hasFlagFormat(flag)) {
         dc.appendIgnoreChar(msg, ' ');
         dc.append(flagToStringSeparator);
      }
   }

   private void toStringFlagMasterTrue(int flag, String msg, Dctx dc) {
      if (hasFlagMaster(flag)) {
         dc.appendIgnoreChar(msg, ' ');
         dc.append(flagToStringSeparator);
      }
   }

   private void toStringFlagTagNegTrue(int flag, String msg, Dctx dc) {
      if (hasFlagTagNeg(flag)) {
         dc.appendIgnoreChar(msg, ' ');
         dc.append(flagToStringSeparator);
      }
   }

   private void toStringFlagTagTrue(int flag, String msg, Dctx dc) {
      if (hasFlagTag(flag)) {
         dc.appendIgnoreChar(msg, ' ');
         dc.append(flagToStringSeparator);
      }
   }

   private void toStringHashClass(Dctx dc, String title, Hashtable ht) {
      dc.nl();
      dc.append(title);
      dc.appendVarWithSpace("#", ht.size());
      dc.tab();
      Enumeration e = ht.keys();
      while (e.hasMoreElements()) {
         Class c = (Class) e.nextElement();
         dc.nl();
         dc.append(c.getName());
      }
      dc.tabRemove();
   }
   //#enddebug

}
