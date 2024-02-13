/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.thread;

import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.IEventsCore;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.IEventBus;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.interfaces.ITechThread;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Blink Thread. Provides a mechanism to blink a state ON/OFF.
 * 
 * Calls Back when there is a switch. Uses 
 * <br>
 * <br>
 * Object may be shared between instances.
 * @author Charles-Philip Bentley
 *
 */
public class PulseThread extends Thread implements IStringable, ITechThread {

   public long       pulseSleepOff  = 600;

   public long       pulseSleepOn   = 800;

   private IEventBus ec;

   private boolean   isPulseRunning = false;

   /**
    * Number of pulses after which it will shut down.
    * 
    * Ignores if 0
    */
   private int       numPulses;

   private int       numPulsesCount;

   private int       producerID;

   private long      pulseSleep     = 1000;

   private int       state;

   /**
    * 
    * @param ec
    * @param initState usually the {@link ITechThread#STATE_1_OFF} or {@link ITechThread#STATE_0_ON}
    * @param pid registered id from {@link IEventBus#createNewProducerID(int)}
    */
   public PulseThread(IEventBus ec, int initState, int pid) {
      this.ec = ec;
      state = initState;
      producerID = pid;
   }

   public void addEventConsumer(IEventConsumer consumer, int eid) {
      ec.addConsumer(consumer, producerID, eid);
   }

   public ICtx getCtx() {
      return ec.getCtxOwner();
   }

   /**
    * Number of pulses after which it will shut down.
    * 
    * Ignored if 0
    */
   public int getNumPulses() {
      return numPulses;
   }

   public int getProducerID() {
      return producerID;
   }

   public int getPulseState() {
      return this.state;
   }

   public boolean isPulseRunning() {
      return isPulseRunning;
   }

   public synchronized void resetToOff() {
      state = STATE_1_OFF;
      notify();
   }

   /**
    * TODO when a nav key is pressed or, reset caret back to ON. i.e. it stops waiting 
    * When a thread wants to pause Pulse thread in a given mode
    * <br>
    * Synchronize caret blinking with GUI event thread.
    * <br>
    * Caret thread is paused and caretOn is true when an GUI event modifies the String.
    * 
    */
   public synchronized void resetToOn() {
      state = STATE_0_ON;
      notify();
   }

   /**
    * 
    */
   public synchronized void run() {
      //#debug
      toDLog().pFlow("run", this, PulseThread.class, "run", LVL_05_FINE, true);
      while (isPulseRunning) {
         try {
            switch (state) {
               case STATE_0_ON:
                  //#debug
                  toDLog().pFlow("STATE_0_ON", this, PulseThread.class, "run", LVL_05_FINE, true);
                  ec.sendNewEvent(producerID, IEventsCore.PID_05_THREAD_1_PULSE_ON, this);
                  state = STATE_1_OFF;
                  pulseSleep = pulseSleepOn;
                  numPulsesCount++;
                  wait(pulseSleep);
                  break;
               case STATE_1_OFF:
                  //#debug
                  toDLog().pFlow("STATE_1_OFF", this, PulseThread.class, "run", LVL_05_FINE, true);
                  ec.sendNewEvent(producerID, IEventsCore.PID_05_THREAD_2_PULSE_OFF, this);
                  if (numPulses != 0 && numPulsesCount >= numPulses) {
                     state = STATE_2_SHUT_DOWN;
                  } else {
                     state = STATE_0_ON;
                  }
                  pulseSleep = pulseSleepOff;
                  wait(pulseSleep);
                  break;
               case STATE_2_SHUT_DOWN:
                  isPulseRunning = false;
                  break;
               case STATE_3_PAUSED:
                  wait();
                  break;
               default:
                  break;
            }
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
   }

   /**
    * Number of pulses after which it will shut down.
    * 
    * Ignores if 0
    */
   public void setNumPulses(int numPulses) {
      this.numPulses = numPulses;
   }

   public void setOnOffWaitTimes(int on, int off) {
      if (on != 0)
         pulseSleepOn = on;
      if (off != 0) {
         pulseSleepOff = off;
      }
   }

   /**
    * Used to stop the blink thread
    * @param state
    */
   public void setPulseState(int state) {
      this.state = state;
   }

   public void start() {
      isPulseRunning = true;
      super.start();
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "PulseThread");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PulseThread");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return ec.toStringGetUCtx();
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("producerID", producerID);
      dc.appendVarWithSpace("isPulseRunning", isPulseRunning);
      dc.appendVarWithSpace("pulseSleep", pulseSleep);
      dc.appendVarWithSpace("pulseSleepOn", pulseSleepOn);
      dc.appendVarWithSpace("pulseSleepOff", pulseSleepOff);
      dc.appendVarWithSpace("state", state);
   }

   public int getNumPulsesCount() {
      return numPulsesCount;
   }

   public void setNumPulsesCount(int numPulsesCount) {
      this.numPulsesCount = numPulsesCount;
   }

   //#enddebug

}
