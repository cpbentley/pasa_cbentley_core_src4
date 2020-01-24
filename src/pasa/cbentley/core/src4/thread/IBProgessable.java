package pasa.cbentley.core.src4.thread;

import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Interface to a UI component able to display messages and incremental progress.
 * <br>
 * <br>
 * Use this when many tasks are to be completed and the UI has no idea about them.
 * <br>
 * {@link IBRunnable} will be able to communicate titles, labels through this interface.
 * <br>
 * <br>
 * The implementation is responsible to synchronize  to the GUI thread.
 * <br>
 * A Swing implementation will have to call {@link SwingUtilities#invokeLater(Runnable)}
 * with a runnable that updates the UI component.
 * @author Charles Bentley
 *
 */
public interface IBProgessable extends IStringable,ITechRunnable {

   /**
    * Most Generic.
    */
   public static final int LVL_0_USER        = 0;

   /**
    * Tester
    */
   public static final int LVL_1_TESTER      = 1;

   /**
    * Tester Debug
    */
   public static final int LVL_2_DEBUG       = 1;

   /**
    * 
    * @return
    */
   public String getTitle();

   /**
    * Sets the High Level Title
    * @param title
    */
   public void setTitle(String title);

   /**
    * Sets the Maximum value reachable
    * @param mv unknown if 0, means the progress is only visual
    * no percentage indicator
    */
   public void setMaxValue(int mv);

   /**
    * 
    * @param title {@link IBProgessable#setTitle(String)}
    * @param info {@link IBProgessable#setTitle(String)}
    * @param label {@link IBProgessable#setTitle(String)}
    * @param maxValue {@link IBProgessable#setTitle(String)}
    * @param level {@link IBProgessable#setTitle(String)}
    */
   public void set(String title, String info, String label, int maxValue, int level);

   /**
    * The label is the string that will right next to the gauge.
    * It should be short
    * @param s the string to display as the label
    */
   public void setLabel(String s);

   /**
    * 
    * @return
    */
   public int getLvl();

   /**
    * Sets the level of progress information.
    * 
    * <li> {@link IBProgessable#LVL_0_USER}
    * <li> {@link IBProgessable#LVL_1_TESTER}
    * <li> {@link IBProgessable#LVL_2_DEBUG}
    * @param lvl
    * @return
    */
   public void setLvl(int lvl);

   /**
    * Sets the value 
    * if > than the max value, no effects
    * @param value
    */
   public void setValue(int value);

   /**
    * Set timing value computed.
    * <br>
    * Time elapsed and estimated time left
    * @param value
    */
   public void setTimeLeft(long value);

   public void error(String msg);

   /**
    * Sets the information above the gauge.
    * <br>
    * This can be much longer than the label
    * <br>
    * @param info
    */
   public void setInfo(String info);

   /**
    * increment the value
    * if the progress has reached the max value, the call has no effect
    */
   public void increment(int value);

   /**
    * The implementation is free in how it implements sub task visual progress.
    * @return
    */
   public IBProgessable getChild();

   /**
    * Create a child {@link IBProgessable} 
    * @param runnable Task that will update the Progress and may
    * be controlled (paused, canceled) by the UI
    * @return a child or this if cannot procreate
    * @post Visually, a new progress is created subordinated to the parent.
    */
   public IBProgessable getChild(IBRunnable runnable);

   /**
    * Tells the UI that the task is finished. 
    * User may close it. etc etc
    * @param msg a msg for the end
    */
   public void close(String msg);

}
