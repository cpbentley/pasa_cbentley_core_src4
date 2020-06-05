/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.thread;

import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.IEventBus;
import pasa.cbentley.core.src4.event.IEventConsumer;
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
public class PulseThread extends Thread implements IStringable {

   public static String debuState(int state) {
      switch (state) {
         case STATE_KEEP_ON:
            return "KeepOn";
         case STATE_0_ON:
            return "ON";
         case STATE_1_OFF:
            return "OFF";
         default:
            return "Unknown " + state;
      }
   }

   public static final int  STATE_0_ON        = 0;

   public static final int  STATE_1_OFF       = 1;

   public static final int  STATE_2_SHUT_DOWN = 2;

   /**
    * TODO when a nav key is pressed or, reset caret back to ON. i.e. it stops waiting 
    * When a thread wants to pause Pulse thread in a given mode
    * <br>
    * Synchronize caret blinking with GUI event thread.
    * <br>
    * Caret thread is paused and caretOn is true when an GUI event modifies the String.
    * 
    */
   public static final int  STATE_PAUSED      = 3;

   /**
    * 
    */
   public static final int  STATE_KEEP_ON     = 4;

   private static final int EVENT_ID_MAX      = 1;

   public static final int  EVENT_ID_0_PULSE  = 0;

   private long             pulseSleep        = 1000;

   public static long       pulseSleepOn      = 800;

   public static long       pulseSleepOff     = 600;

   private boolean          isPulseRunning    = false;

   private int              state;

   private int              producerID;

   private IEventBus        ec;

   public PulseThread(IEventBus ec, int initState, int pid) {
      this.ec = ec;
      state = initState;
      producerID = pid;

   }

   public boolean isPulseRunning() {
      return isPulseRunning;
   }
   
   public void addEventConsumer(IEventConsumer consumer, int eid) {
      ec.addConsumer(consumer, producerID, eid);
   }

   public ICtx getCtx() {
      return ec.getCtxOwner();
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

   public synchronized void resetToOff() {
      state = STATE_1_OFF;
      notify();
   }

   public int getProducerID() {
      return producerID;
   }

   public void start() {
      isPulseRunning = true;
      super.start();
   }

   /**
    * 
    */
   public synchronized void run() {
      while (isPulseRunning) {
         try {
            switch (state) {
               case STATE_0_ON:
                  ec.sendNewEvent(producerID, STATE_0_ON, this);
                  state = STATE_1_OFF;
                  pulseSleep = pulseSleepOn;
                  wait(pulseSleep);
                  break;
               case STATE_1_OFF:
                  ec.sendNewEvent(producerID, STATE_1_OFF, this);
                  state = STATE_0_ON;
                  pulseSleep = pulseSleepOff;
                  wait(pulseSleep);
                  break;
               case STATE_2_SHUT_DOWN:
                  isPulseRunning = false;
                  break;
               case STATE_PAUSED:
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

   public int getPulseState() {
      return this.state;
   }

   /**
    * Used to stop the blink thread
    * @param state
    */
   public void setPulseState(int state) {
      this.state = state;
   }

   public void setOnOffWaitTimes(int on, int off) {
      if (on != 0)
         PulseThread.pulseSleepOn = on;
      if (off != 0) {
         PulseThread.pulseSleepOff = off;
      }
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

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PulseThread");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return ec.toStringGetUCtx();
   }

   //#enddebug
   

}
