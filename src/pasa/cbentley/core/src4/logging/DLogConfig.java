/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.logging;

import java.util.Enumeration;
import java.util.Hashtable;

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
public class DLogConfig implements IDLogConfig, ITechTags, ITechConfig {

   //#mdebug

   private Hashtable all                 = new Hashtable();

   protected int     cmdflags;

   protected int     flagsFormat;

   protected int     flagsPrint;

   /**
    * <li> {@link ITechTags#FLAG_07_PRINT_EVENT}
    */
   protected int     flagsTag;

   private int       flagsTagNeg;

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

   private UCtx      uc;

   public DLogConfig(UCtx uc) {
      this.uc = uc;

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
      entryOfConf.setConfigResFlag(CONFIG_FLAG_1_ACCEPTED, isAccepted(type));

      //dev flags override the config?
      if (type.hasDevFlag(DEV_2_1LINE)) {
         entryOfConf.setConfigResFlag(CONFIG_FLAG_2_1LINE, true);
      }
      if (type.hasDevFlag(DEV_3_STACK)) {
         entryOfConf.setConfigResFlag(CONFIG_FLAG_3_STACK, true);
      }

      if (hasFlagPrint(MASTER_FLAG_07_THREAD_DATA)) {
         entryOfConf.setConfigResFlag(CONFIG_FLAG_04_SHOW_THREAD, true);
      }
      //Dev override master flag
      if (type.hasDevFlag(DEV_4_THREAD)) {
         entryOfConf.setConfigResFlag(CONFIG_FLAG_04_SHOW_THREAD, true);
      }

      return entryOfConf;
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

   public boolean hasFlagFormat(int flag) {
      return BitUtils.hasFlag(flagsFormat, flag);
   }

   /**
    * Sets
    * <li> {@link ITechConfig#MASTER_FLAG_01_BLOCK_ALL_PRINT}
    * <li> {@link ITechConfig#MASTER_FLAG_02_OPEN_ALL_PRINT}
    * <li> {@link ITechConfig#MASTER_FLAG_03_ONLY_POSITIVES}
    * <li> {@link ITechConfig#MASTER_FLAG_04_IGNORE_CLASSES}
    * <li> {@link ITechConfig#MASTER_FLAG_05_IGNORE_FLAGS}
    * <li> {@link ITechConfig#MASTER_FLAG_06_CLASS_INSTANCES}
    * @param flag
    * @param b
    */
   public boolean hasFlagPrint(int flag) {
      return BitUtils.hasFlag(flagsPrint, flag);
   }

   /**
    * <li> {@link ITechTags#FLAG_07_PRINT_EVENT}
    * @param flag
    * @return
    */
   public boolean hasFlagTag(int flag) {
      return BitUtils.hasFlag(flagsTag, flag);
   }

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
      //fail fast
      if (type.getLevel() < logLevel) {
         return false;
      }
      boolean isAcceptedFlag = isAcceptedFlags(type.getTagID());
      if (isAcceptedFlag) {

         boolean isClassAccepted = isClassAccepted(type.getClassL());
         if (isClassAccepted) {
            return true;
         }
         if (hasFlagPrint(MASTER_FLAG_09_TREAT_STRINGABLE_CLASS)) {
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
      if (hasFlagPrint(MASTER_FLAG_02_OPEN_ALL_PRINT)) {
         return true;
      }
      if (hasFlagPrint(MASTER_FLAG_01_BLOCK_ALL_PRINT)) {
         return false;
      }
      if (hasFlagPrint(MASTER_FLAG_05_IGNORE_FLAGS)) {
         return true;
      } else {
         if (flagTag == FLAG_01_PRINT_ALWAYS) {
            return true;
         }
         if (hasFlagPrint(MASTER_FLAG_08_OPEN_ALL_BUT_FALSE)) {
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
    * <li> {@link ITechConfig#MASTER_FLAG_03_ONLY_POSITIVES} hides classes not explicitely positive
    * @param c
    * @return
    */
   public boolean isClassAccepted(Class c) {
      if (c == null) {
         return true;
      }
      if (hasFlagPrint(MASTER_FLAG_04_IGNORE_CLASSES)) {
         return true;
      }

      boolean val = true;
      if (fullPositives.containsKey(c)) {
         //we have an override of nevatives
      } else {
         if (negatives.containsKey(c)) {
            val = false;
         } else if (hasFlagPrint(MASTER_FLAG_03_ONLY_POSITIVES)) {
            //val is true for default
            if (positivesClasses.containsKey(c)) {
               val = true;
            } else {
               if (hasFlagPrint(MASTER_FLAG_06_CLASS_INSTANCES)) {
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

   public void setFlagPrint(int flag, boolean v) {
      flagsPrint = BitUtils.setFlag(flagsPrint, flag, v);
   }

   public void setFlagPrint(int[] flags, boolean v) {
      for (int i = 0; i < flags.length; i++) {
         setFlagPrint(flags[i], v);
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

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "Config");

      dc.appendVarWithSpace("logLevel", logLevel);

      dc.append("Print Trues = ");
      toStringFlag(MASTER_FLAG_01_BLOCK_ALL_PRINT, "BlockAll", dc);
      toStringFlag(MASTER_FLAG_02_OPEN_ALL_PRINT, "AllPrint", dc);
      toStringFlag(MASTER_FLAG_03_ONLY_POSITIVES, "OnlyPositives", dc);
      toStringFlag(MASTER_FLAG_04_IGNORE_CLASSES, "IgnoreClasses", dc);
      toStringFlag(MASTER_FLAG_05_IGNORE_FLAGS, "IgnoreFlags", dc);
      toStringFlag(MASTER_FLAG_06_CLASS_INSTANCES, "Instances", dc);

      dc.append("Tags Trues = ");
      toStringFlag(FLAG_07_PRINT_EVENT, STRING_EVENT, dc);
      toStringFlag(FLAG_08_PRINT_EXCEPTION, "Exception", dc);
      toStringFlag(FLAG_09_PRINT_FLOW, STRING_FLOW, dc);
      toStringFlag(FLAG_10_PRINT_MODEL, STRING_MODEL, dc);
      toStringFlag(FLAG_11_PRINT_COMMANDS, "Commands", dc);
      toStringFlag(FLAG_12_PRINT_BUSINESS, STRING_BUSINESS, dc);
      toStringFlag(FLAG_13_PRINT_SOUND, STRING_SOUND, dc);
      toStringFlag(FLAG_14_PRINT_TEMP, "Temp", dc);
      toStringFlag(FLAG_16_PRINT_TAG, "Tag", dc);
      toStringFlag(FLAG_17_PRINT_TEST, "Test", dc);
      toStringFlag(FLAG_18_PRINT_MEMORY, "Memory", dc);
      toStringFlag(FLAG_19_PRINT_BRIDGE, STRING_BRIDGE, dc);
      toStringFlag(FLAG_20_PRINT_INIT, STRING_INIT, dc);
      toStringFlag(FLAG_21_PRINT_BIP, "Bip", dc);
      toStringFlag(FLAG_22_PRINT_STATE, STRING_STATE, dc);
      toStringFlag(FLAG_23_PRINT_ANIM, STRING_ANIM, dc);
      toStringFlag(FLAG_24_PRINT_DRAW, STRING_DRAW, dc);

      toStringHashClass(dc, "Negative Classes", negatives);
      toStringHashClass(dc, "Positive Classes", positivesClasses);
      toStringHashClass(dc, "Trace Classes", positivesTraceClass);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "Config");
   }

   private void toStringFlag(int flag, String msg, Dctx dc) {
      if (hasFlagPrint(flag)) {
         dc.append(msg + ";");
      }
   }

   public UCtx toStringGetUCtx() {
      return uc;
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
