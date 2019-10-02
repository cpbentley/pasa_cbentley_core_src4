package pasa.cbentley.core.src4.thread;

import java.util.Enumeration;
import java.util.Vector;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.core.src4.structs.synch.MutexSignal;

/**
 * Provides common base for most IMRunnables.
 * <br>
 * Handles thread synchronization.
 * <br>
 * <br>
 * The flag and interrupt() approaches are essentially the same. In both cases, the thread that expects to be interrupted needs to regularly check;
 *   e.g. by calling interrupted() or isInterrupted() or by checking a flag. 
 * <br>
 * The difference is that that the interrupt() mechanism will work when the code is waiting for a notify() or blocked in and IO operation. 
 * Because of that, and because interrupt is an application independent way of doing this, it should be used in preference to an application specific mechanism.
 * @author Charles Bentley
 *
 */
public abstract class AbstractBRunnable implements IBRunnable {

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

   /**
    * 
    */
   private int                  flags        = 0;

   protected IBRunnableListener listener;

   /**
    * Lazy
    */
   protected Vector             listeners    = null;

   /**
    * The lock on which the task will wait when paused
    */
   private Object               lock         = new Integer(0);

   /**
    * Real current state of the {@link IMRunnable}.
    */
   private volatile int         state        = 0;

   /**
    * User request for setting the {@link IMRunnable} in this state.
    * 
    * <li> {@link ITechRunnable#STATE_0_RUNNING}
    * <li> {@link ITechRunnable#STATE_3_STOPPED}
    * <li> {@link ITechRunnable#STATE_2_CANCELED}
    * <li> {@link ITechRunnable#STATE_1_PAUSED}
    * 
    * <br>
    * Since its volatile, GUI changes will be seen.
    */
   private volatile int         stateRequest = 0;

   private UCtx                 uc;

   /**
    * Used for monitoring the Paused state
    */
   private MutexSignal          mutex;

   private boolean              isWaiting    = true;

   public AbstractBRunnable(UCtx uc) {
      this.uc = uc;

   }

   public AbstractBRunnable(UCtx uc, int flag) {
      this.uc = uc;
      flags = flag;
   }

   public Object getLock() {
      return lock;
   }

   public int getState() {
      return state;
   }

   public boolean isStateRunning() {
      return state == STATE_0_RUNNING;
   }

   public boolean isStatePaused() {
      return state == STATE_1_PAUSED;
   }

   /**
    * Implementation of {@link AbstractBRunnable} checks the state request.
    * <br>
    */
   public int getStateRequest() {
      return stateRequest;
   }

   /**
    * Tells the {@link IMProgessable} the capabilities of this {@link Runnable}.
    * <br>
    * <li> {@link ITechRunnable#FLAG_04_CANCELABLE}
    * <li> {@link ITechRunnable#FLAG_02_STOPPABLE}
    * <li> {@link ITechRunnable#FLAG_03_PAUSABLE}
    */
   public boolean hasRunFlag(int flag) {
      return (flags & flag) == flag;
   }

   public MutexSignal getPauseLock() {
      if (mutex == null) {
         mutex = new MutexSignal(uc);
      }
      return mutex;
   }

   /**
    *  Check state change requests.
    *  
    *  Return false if the thread should stop, i.e. the user or an event requested the work to be aborted.
    *  
    *  TODO code the interrupted case when not paused {@link ITechRunnable#STATE_5_INTERRUPTED}
    * @return
    */
   public boolean isContinue() {
      //first check if another thread interrupted us using the Java classic mechanism.
      //its how SwingWorker will ask our thread to stop doing its task.
      //TODO Thread.interrupted() is not supported in src4
      //      if(Thread.interrupted()) {
      //         this.state = ITechRunnable.STATE_2_CANCELED;
      //         //#debug
      //         toDLog().pWork("Thread is interrupted", this, AbstractBRunnable.class, "isContinue", LVL_05_FINE, true);
      //         return false;
      //      }
      //its because this code above does not work that this class was created in the first place.
      //for interrupting a you set the state request and call interrupt
      int methodStateRequest = getStateRequest();
      int stateCurrent = getState();
      if (stateCurrent != methodStateRequest) {
         //#debug
         uc.toDLog().pWork("stateCurrent != stateRequest", this, AbstractBRunnable.class, "isContinue", ITechLvl.LVL_05_FINE, true);

         boolean isContinue = false;
         if (methodStateRequest == ITechRunnable.STATE_1_PAUSED) {
            //when paused,, lock is in wait mode pause the task using wait
            synchronized (lock) { //get the lock 
               notifyNewState(ITechRunnable.STATE_1_PAUSED); //is this dangerous ? no. java locks are reentrants.
               //what if the notified object decides to reset the pause state. he
               //will call request another state which we check here
               int pauseStateRequestAccepted = getStateRequest();
               if (pauseStateRequestAccepted == ITechRunnable.STATE_1_PAUSED) {
                  //ok listeners are ok to proceed in paused state
                  this.state = ITechRunnable.STATE_1_PAUSED;
                  try {
                     //avoir spurring wakeups in linux. set isWaiting to false just before notifying.
                     isWaiting = true;
                     while (this.isWaiting) {
                        lock.wait();
                     }
                     //which state request kicked us out our paused state?
                     int updatedStateRequest = getStateRequest();
                     //at this step.. we have unpaused. we have to check what if the new state
                     if (updatedStateRequest == ITechRunnable.STATE_0_RUNNING) {
                        isContinue = true;
                     }
                     methodStateRequest = updatedStateRequest;
                  } catch (InterruptedException e) {
                     //e.printStackTrace();
                     //interruption is a cooperative mechanism. Another thread interrupted us
                     //requesting that we stop what we are doing.. we were pausing. 
                     // we can close this thread. no problems.
                     //or there is a request to continue running
                     int updatedStateRequest = getStateRequest();
                     if (updatedStateRequest == ITechRunnable.STATE_5_INTERRUPTED) {
                        //was interruupted willingly by the api.
                        //continue looping

                     } else {
                        //interrupt was called by a thread manager wanting to finish it
                        updatedStateRequest = ITechRunnable.STATE_5_INTERRUPTED;
                     }
                     methodStateRequest = updatedStateRequest;
                  }
               } else {
                  if (pauseStateRequestAccepted == ITechRunnable.STATE_0_RUNNING) {
                     isContinue = true;
                  }
                  methodStateRequest = pauseStateRequestAccepted;
               }
            }
         }
         //set new state
         this.state = methodStateRequest;
         // communicate the state change to listeners
         if (state != ITechRunnable.STATE_2_CANCELED && state != ITechRunnable.STATE_4_CANCELED_ERROR && state != ITechRunnable.STATE_5_INTERRUPTED) {
            //those above states will be sent below when the method returns
            notifyNewState(state);
         }
         //returning
         return isContinue;
      }
      return true;
   }

   /**
    * Can be used by sub classes to notify an update of the Run state.
    * @param newState
    */
   protected void notifyNewState(int newState) {
      if (listener != null) {
         listener.runnerNewState(this, newState);
      }
      if (listeners != null) {
         Enumeration it = listeners.elements();
         while (it.hasMoreElements()) {
            IBRunnableListener lisv = (IBRunnableListener) it.nextElement();
            lisv.runnerNewState(this, newState);
         }
      }
   }

   /**
    * Set only once so it is safe as a getter. Or Is it not? Check with another expert
    */
   private Thread thisThread;

   public Thread getThread() {
      return thisThread;
   }

   public void run() {
      try {
         thisThread = Thread.currentThread();
         runAbstract();
         if (state != ITechRunnable.STATE_2_CANCELED && state != ITechRunnable.STATE_5_INTERRUPTED) {
            state = ITechRunnable.STATE_6_FINISHED;
         }
         notifyNewState(state);
      } catch (Exception e) {

         //this exception might be OK.. some libraries may throw some kind of exceptions when interrupted.
         //ask implementation if this exception has the meaning of thread interrupted=true
         //We have to do this because in src4 we don't have access to isInterrupted() on Thread object
         if (isExceptionThreadInterrupted(e)) {
            //this will occur when task is stuck in a low level IO task and UI user interrupts it.
            //Most of the time, this case should not happen anyways, implementation must check isContinue
            //when running this class in a SwingWorker thread for example, you should
            //in src4 we don't have access to isInterrupted on Thread object
            //set state to interrupted manually
            state = STATE_5_INTERRUPTED;
            //tell implementation to cleanly go away. This should n
            interruptedPleaseExit();
         } else {
            //#debug
            toDLog().pEx("Error during task execution", this, AbstractBRunnable.class, "run", e);

            //an exception occured during the task.. what do we do about it?
            exception(e);
         }
      }

   }

   protected void interruptedPleaseExit() {
   }

   /**
    * By default returns false
    * @param e
    * @return
    */
   protected boolean isExceptionThreadInterrupted(Exception e) {
      return false;
   }

   /**
    * Called when an exception prevented the task from finishing.
    * <br>
    * Set the state to Error
    * @param e
    */
   public void exception(Exception e) {
      state = ITechRunnable.STATE_4_CANCELED_ERROR;
      //send it to listeners
      if (listener != null) {
         listener.runnerException(this, e);
      }
      if (listeners != null) {
         Enumeration it = listeners.elements();
         while (it.hasMoreElements()) {
            IBRunnableListener lisv = (IBRunnableListener) it.nextElement();
            lisv.runnerException(this, e);
         }
      }
   }

   /**
    * {@link AbstractBRunnable} will set the state to {@link ITechRunnable#STATE_3_STOPPED} if not canceled
    */
   public abstract void runAbstract();

   public void addListener(IBRunnableListener lis) {
      if (listener == null) {
         this.listener = lis;
      } else {
         if (listeners == null) {
            listeners = new Vector(1);
            listeners.addElement(lis);
         }
      }
   }

   public void removeListener(IBRunnableListener lis) {
      if (listener == lis) {
         listener = null;
      } else if (listeners != null) {
         listeners.removeElement(lis);
      }

   }

   public void setRunFlag(int flag, boolean v) {
      if (v)
         flags = flags | flag;
      else
         flags = flags & ~flag;
   }

   /**
    * Called by UI functions such as the cancel button or the pause button.
    * <br>
    * If the {@link IMRunnable} run method is able to manage the state
    * <li>{@link ITechRunnable#STATE_0_RUNNING} Resume start after a pause
    * <li>{@link ITechRunnable#STATE_1_PAUSED} pause the task
    * <li>{@link ITechRunnable#STATE_3_STOPPED} stop the task at current step and continue
    * <li>{@link ITechRunnable#STATE_2_CANCELED} cancel and roll back 
    * <li>{@link ITechRunnable#STATE_4_CANCELED_ERROR} canceled because of an error. best effort roll back
    * <br>
    * <br>
    * 
    * @param state
    */
   public void requestNewState(int state) {

      //#debug
      toDLog().pFlow("newstate=" + toStringState(state), this, AbstractBRunnable.class, "requestNewState", LVL_05_FINE, true);
      /*
       * Declaring a volatile Java variable means: 
       * The value of this variable will never be cached thread-locally:
       * all reads and writes will go straight to "main memory"; 
       * Access to the variable acts as though it is enclosed in a synchronized block, synchronized on itself.
       * 
       */

      // volatile read by gui thread means that everything that was visible by runnable thread
      // is now visible to visible to gui thread
      int currentState = this.state;
      //volatile write by gui thread means that everything visible by gui will be visible 
      // to runnable thread when it reads stateRequest 
      this.stateRequest = state;

      //since only the pause state requires a lock
      //we don't want a full synchronize for non pause state requests.
      if (currentState == ITechRunnable.STATE_1_PAUSED) {
         //we cannot now. if thread is paused. it is currently waiting on a 
         //the mutex. if current state is paused.
         synchronized (lock) {
            //#debug
            uc.toDLog().pFlow("Inside synchronized lock", this, AbstractBRunnable.class, "setState", ITechLvl.LVL_05_FINE, true);
            this.isWaiting = false; //set the signal at false so wait loop stops
            lock.notify(); //tells pause code to aquire thread lock and resume operations
         }
      }
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "AbstractBRunnable");
      toStringPrivate(dc);
      dc.nl();
      dc.appendVar("isCancelable", hasRunFlag(FLAG_04_CANCELABLE));
      dc.appendVarWithSpace("isPausable", hasRunFlag(FLAG_03_PAUSABLE));
      dc.appendVarWithSpace("isStoppable", hasRunFlag(FLAG_02_STOPPABLE));
      dc.appendVarWithSpace("isUiHidale", hasRunFlag(FLAG_05_UI_HIDABLE));

      dc.nlLvl(listener, "Listener");

   }

   public IDLog toDLog() {
      return uc.toDLog();
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "AbstractMRunnable");
      toStringPrivate(dc);
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("state", toStringState(state));
      dc.appendVarWithSpace("stateRequest", toStringState(stateRequest));
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }
   //#enddebug
}
