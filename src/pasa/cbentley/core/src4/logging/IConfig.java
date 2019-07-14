package pasa.cbentley.core.src4.logging;

import pasa.cbentley.core.src4.ctx.UCtx;

/**
 * Interface to configure the logger
 * 
 * <li>{@link UCtx#toDLog()}
 * <li> {@link IDLog#getDefault()}
 * <li> I
 * 
 * 
 * @author Charles Bentley
 *
 */
public interface IConfig extends IStringable, ITechConfig {

   //#mdebug

   /**
    * Compute {@link ITechConfig} flags of the {@link DLogEntry}.
    * 
    * It will use its config and any dev flags {@link ITechDev} of the {@link DLogEntry}
    * @param type
    * @return
    */
   public DLogEntryOfConfig getEntryConfig(DLogEntry type);

   /**
    * 
    * @return
    */
   public String getStackTraceBreak();

   /**
    * <li> {@link ITechConfig#CONFIG_FLAG_04_SHOW_THREAD}
    * @param flag
    * @return
    */
   public boolean hasFlagFormat(int flag);

   /**
    * 
    */
   public void removeAllNegatives();

   /**
    * 
    */
   public void removeAllPositives();

   /**
    * Switch on the Full Positive Print flag for this class.
    * <br>
    * This means that all print statements will go through for that class
    * except for the {@link IDLog#MASTER_FLAG_01_BLOCK_ALL_PRINT}.<br>
    * <br>
    * This method is usefull to debug a specific class without affecting other tags in other classes.
    * @param c
    * @param v
    */
   public void setClassFullPositive(Class c, boolean v);

   /**
    * Mute or unMute the class from the default setting
    * @param c
    * @param v true to set put it in negative list. false to remove it from negative list
    */
   public void setClassNegative(Class c, boolean v);

   /**
    * Adds the class C to positive print list. The Class will print true flags.
    * Only those classes will be able to print when flag is set to only 
    *  {@link IDLog#MASTER_FLAG_03_ONLY_POSITIVES}
    * @param c
    * @param v
    */
   public void setClassPositives(Class c, boolean v);

   /**
    * <li> {@link ITechConfig#CONFIG_FLAG_04_SHOW_THREAD}
    * @param flag
    * @param b
    */
   public void setFlagFormat(int flag, boolean b);

   /**
    * <li> {@link ITechConfig#MASTER_FLAG_01_BLOCK_ALL_PRINT}
    * <li> {@link ITechConfig#MASTER_FLAG_02_OPEN_ALL_PRINT}
    * <li> {@link ITechConfig#MASTER_FLAG_03_ONLY_POSITIVES}
    * <li> {@link ITechConfig#MASTER_FLAG_04_IGNORE_CLASSES}
    * <li> {@link ITechConfig#MASTER_FLAG_05_IGNORE_FLAGS}
    * <li> {@link ITechConfig#MASTER_FLAG_06_CLASS_INSTANCES}
    * @param flags
    * @param v
    */
   public void setFlagPrint(int[] flags, boolean v);

   /**
    * Config flags
    * <li> {@link ITechConfig#MASTER_FLAG_01_BLOCK_ALL_PRINT}
    * <li> {@link ITechConfig#MASTER_FLAG_02_OPEN_ALL_PRINT}
    * <li> {@link ITechConfig#MASTER_FLAG_03_ONLY_POSITIVES}
    * <li> {@link ITechConfig#MASTER_FLAG_04_IGNORE_CLASSES}
    * <li> {@link ITechConfig#MASTER_FLAG_05_IGNORE_FLAGS}
    * <li> {@link ITechConfig#MASTER_FLAG_06_CLASS_INSTANCES}
    * @param flag
    * @param v
    */
   public void setFlagPrint(int flag, boolean v);

   /**
    * <li> {@link ITechTags#FLAG_05_PRINT_UI}
    * <li> {@link ITechTags#FLAG_11_PRINT_COMMANDS}
    * <li> {@link ITechTags#FLAG_20_PRINT_INIT}
    * 
    * @param flags
    * @param v
    */
   public void setFlagTag(int flag, boolean v);

   /**
    * When true, sets the Tag as a negative. The tag is then ignored if {@link ITechConfig#MASTER_FLAG_08_OPEN_ALL_BUT_FALSE}
    * is set.
    * @param flag
    * @param v
    */
   public void setFlagTagNeg(int flag, boolean v);

   /**
    * 
    * @param flags
    * @param v
    */
   public void setFlagTag(int[] flags, boolean v);

   /**
    * 
    * @param lvl
    */
   public void setLevelGlobal(int lvl);

   /**
    * Force one line for all debug entries of that class.
    * <br>
    * 
    * @param flag
    * @param c
    */
   public void setOneLines(int flag, Class c);

   /**
    * 
    * @param type
    * @param c
    * @param b
    */
   public void setStackTraced(int type, Class c, boolean b);

   //#enddebug
}
