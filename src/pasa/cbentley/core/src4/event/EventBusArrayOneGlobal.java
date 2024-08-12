/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.event;

import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.IEventsCore;
import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.ex.UCtxException;
import pasa.cbentley.core.src4.interfaces.IExecutor;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.core.src4.utils.ArrayUtils;

/**
 * Bus with knowledge of the statically defined producers of events.
 * Usage:
 * Create an Event, fill it with your parameters and send it on the bus
 * <br>
 * <br>
 * Same thread events. This class is mainly designed for GUI user applications
 * where a main thread is helped by worker threads.
 * <br>
 * Case 1:
 * Background thread wants to put a event?
 * 
 * @author Charles Bentley
 *
 */
public class EventBusArrayOneGlobal extends ObjectU implements IEventBus, IEventConsumer {

   /**
    * cannot be null
    */
   private ICtx           contextOwner;

   /**
    * Object used for threading calls
    */
   private IExecutor      executor;

   /**
    * Contains
    * <li> {@link IEventConsumer} or 
    * <li> {@link IntToObjects} of {@link IEventConsumer}
    * <li> null if no consumer for the events
    */
   private IntToObjects   listenersToAllEvents;

   /**
    * rows are producers id
    * columns are event consumers
    */
   private IntToObjects[] producerIDToConsumerArray;

   private UCtx           uc;

   /**
    * Static topology is events
    */
   private int[]          topologyStatic;

   /**
    * Registers to dipose memory events 
    * @param uc
    * @param contextOwner
    * @param producersNumEvents containes the event topology. number of producers mapped to number of events
    * 
    * Length of array - 1 is the number of Producer types 
    * 
    */
   public EventBusArrayOneGlobal(UCtx uc, ICtx contextOwner, int[] producersNumEvents) {
      super(uc);

      //#mdebug
      if (contextOwner == null) {
         throw new NullPointerException();
      }
      //#enddebug
      this.contextOwner = contextOwner;
      listenersToAllEvents = new IntToObjects(uc);

      this.topologyStatic = producersNumEvents;

      //construct the structure to host the regular mapping
      IntToObjects[] producerIDToConsumerArray = new IntToObjects[producersNumEvents.length];
      for (int i = 0; i < producerIDToConsumerArray.length; i++) {
         producerIDToConsumerArray[i] = new IntToObjects(uc, producersNumEvents[i], true);
      }
      this.producerIDToConsumerArray = producerIDToConsumerArray;

      //we need to know when a consumer is destroyed so we remove all references
      //register on the Core Bus, which handle memory events
      IEventBus eventBus = this;
      if (contextOwner != uc) {
         eventBus = uc.getEventBusRoot();
      }
      eventBus.addConsumer(this, IEventsCore.PID_03_MEMORY, IEventsCore.PID_03_MEMORY_3_OBJECT_DESTROY);
   }

   public void registerCtxEvents(ICtx ctx, int baseLineSID, int[] topology) {
      for (int i = 0; i < topology.length; i++) {
         int offset = baseLineSID + i;
         producerIDToConsumerArray[offset] = new IntToObjects(uc, topology[i], true);
      }
   }

   public int getNumStaticProducers() {
      return topologyStatic.length;
   }

   public int createNewProducerID(int topoloyNumEvents) {
      int producerID = producerIDToConsumerArray.length;
      int offsetNull = ArrayUtils.getFirstNullIndex(producerIDToConsumerArray);
      if (offsetNull == -1) {
         IntToObjects[] arrayNew = uc.getAU().increaseCapacity(producerIDToConsumerArray, 1);
         producerIDToConsumerArray = arrayNew;
      } else {
         producerID = offsetNull;
      }
      boolean isEmtpyFill = true;
      IntToObjects producer = new IntToObjects(uc, topoloyNumEvents, isEmtpyFill);
      producerIDToConsumerArray[producerID] = producer;
      return producerID;
   }

   public void removeDynamicProducerID(int pid) {
      producerIDToConsumerArray[pid] = null;
   }

   /**
    * The consumer will only consume one event and then will automatically be unregistered
    * TODO
    * @param con
    * @param producerID
    * @param eventID
    * @param threadMode
    */
   public void addConsumerSingleEvent(IEventConsumer con, int producerID, int eventID, int threadMode) {

   }

   public void addConsumer(IEventConsumer con, int producerID, int eventID, int threadMode) {
      //check ids and producerID
      if (producerID == IEventsCore.PID_00_ANY) {
         listenersToAllEvents.add(con);
      } else {
         //should never happen in production code
         //#mdebug
         if (producerID >= producerIDToConsumerArray.length) {
            throw new IllegalArgumentException("ProducerID " + producerID + " is not valid");
         }
         //#enddebug
         IntToObjects consumersForPID = producerIDToConsumerArray[producerID];
         consumersForPID.ensureRoom(eventID, 1);
         Object o = consumersForPID.getObjectAtIndex(eventID);
         if (o != null) {
            //check if we have 
            if (o instanceof IEventConsumer) {
               //add the existing consumer along the new one into a struct
               IntToObjects newConsumers = new IntToObjects(uc, 2);
               int threadModeExistingConsumer = consumersForPID.getInt(eventID);
               newConsumers.add(o, threadModeExistingConsumer);
               newConsumers.add(con, threadMode);
               consumersForPID.setObject(newConsumers, eventID);
            } else {
               //already an IntToObjects
               ((IntToObjects) o).add(con, threadMode);
            }
         } else {
            //put it directly
            consumersForPID.setObjectAndInt(con, threadMode, eventID);
         }
      }
   }

   /**
    * Register this {@link IEventConsumer} for the given eventID and producerID.
    * <br>
    * <br>
    * The caller is responsible to know the producerID.
    * 
    * Memory issue: Add a consumer on the Event bus pins that object to the memory even if the.
    * Other implementations that have access to WeakReference.
    * This implementation is compatible with MIDP 2.0.
    */
   public void addConsumer(IEventConsumer con, int producerID, int eventID) {
      this.addConsumer(con, producerID, eventID, THREAD_MODE_0_POST_NOW);
   }

   public void consumeEvent(BusEvent e) {
      Object prod = e.getProducer();
      if (prod instanceof IEventConsumer) {
         this.removeConsumer((IEventConsumer) prod);
      }
   }

   public BusEvent createEvent(int producerID, int eventID, Object producer) {
      BusEvent e = new BusEvent(uc, this, producerID, eventID);
      e.setProducer(producer);
      return e;
   }

   public BusEvent createEvent(int producerID, int eventID, Object producer, int param1, int param2) {
      BusEvent e = createEvent(producerID, eventID, producer);
      e.setParam1(param1);
      e.setParam2(param2);
      return e;
   }

   private void doConsumer(final IEventConsumer eventConsumer, final BusEvent e, int threadMode) {
      if (executor == null || threadMode == THREAD_MODE_0_POST_NOW) {
         doConsumer2(eventConsumer, e);
      } else {
         //
         Runnable runner = new Runnable() {
            public void run() {
               doConsumer2(eventConsumer, e);
            }
         };
         run(runner, threadMode);
      }
   }

   private void doConsumer(Object consumer, BusEvent e, int threadMode) {
      if (consumer instanceof IEventConsumer) { //we only have one consumer
         doConsumer((IEventConsumer) consumer, e, threadMode);
      } else if (consumer instanceof IntToObjects) { //we have several consumers
         IntToObjects cons = (IntToObjects) consumer;
         for (int i = 0; i < cons.nextempty; i++) {
            IEventConsumer eventConsumer = (IEventConsumer) cons.getObjectAtIndex(i);
            threadMode = cons.getInt(i);
            doConsumer(eventConsumer, e, threadMode);
         }
      }

   }

   /**
    * Forward the event to the consumer and deal with debug messages in debug mode.
    * 
    * Execepion
    * 
    * @param eventConsumer
    * @param e
    */
   private void doConsumer2(IEventConsumer eventConsumer, BusEvent e) {
      try {
         eventConsumer.consumeEvent(e);
      } catch (UCtxException ex) {
         ex.printStackTrace();
         if (ex.getId() == UCtxException.EVENT_MATCH_EX) {
            //#debug
            uc.toDLog().pEventSevere(ex.getMessage(), e, EventBusArrayOneGlobal.class, "doConsumer");
         }
      } catch (Exception exe) {
         //we cannot throw it here. we might be in a thread. TODO log it in a visual interface
         exe.printStackTrace();
      }
      //#mdebug
      if (!e.hasFlag(BusEvent.FLAG_1_ACTED)) {
         //send warning. event was not acted
         uc.toDLog().pEventWarn("BusEvent not consumed", e, EventBusArrayOneGlobal.class, "doConsumer");
      } else {
         uc.toDLog().pEventFiner("BusEvent was consumed", e, EventBusArrayOneGlobal.class, "doConsumer");
      }
      //#enddebug
   }

   public ICtx getCtxOwner() {
      return contextOwner;
   }

   public IExecutor getExecutor() {
      return executor;
   }

   /**
    * Create a new producer line in addition of the module static ones
    * <br>
    * Events produced on a line will only be consumed by {@link IEventConsumer}
    * for registered for that line number. They must know that line.
    * <br>
    * ID used by any Object producer to identify itself.
    * <br>
    * <br>
    * Create the room needed
    * @paran maxEventID the number of different events
    * @return
    */
   public int getNextProducerID(int maxEventID) {
      IntToObjects consumersSlots = new IntToObjects(uc, maxEventID, true);
      int index = producerIDToConsumerArray.length;
      this.producerIDToConsumerArray = uc.getAU().increaseCapacity(producerIDToConsumerArray, 1);
      producerIDToConsumerArray[index] = consumersSlots;
      return index;
   }

   public void putOnBus(final BusEvent be, int threadMode) {
      if (executor == null || threadMode == THREAD_MODE_0_POST_NOW) {
         putOnBus(be);
      } else {
         Runnable runner = new Runnable() {
            public void run() {
               putOnBus(be);
            }
         };
         run(runner, threadMode);
      }
   }

   private void run(Runnable runner, int threadMode) {
      if (threadMode == THREAD_MODE_3_WORKER) {
         executor.executeWorker(runner);
      } else if (threadMode == THREAD_MODE_2_MAIN_LATER) {
         executor.executeMainLater(runner);
      } else if (threadMode == THREAD_MODE_1_MAIN_NOW) {
         executor.executeMainNow(runner);
      }
   }

   /**
    * Find the consumers with {@link BusEvent#producerID} and {@link BusEvent#eventID}.
    * <br>
    * <br>
    * @param be {@link BusEvent}
    * @throws ArrayIndexOutOfBoundsException
    */
   public void putOnBus(BusEvent be) {
      int producerID = be.getProducerID();
      IntToObjects allConsumersForPID = producerIDToConsumerArray[producerID];
      //look up event consumer for the couple 
      int eventID = be.getEventID();
      if (eventID >= allConsumersForPID.nextempty) {
         String msg = "eventID " + eventID + " is not valid for the number of registered event types for producerID " + producerID;
         //#debug
         uc.toDLog().pAlways("Exception with BusEvent", be, EventBusArrayOneGlobal.class, "putOnBus", ITechLvl.LVL_04_FINER, false);
         //#debug
         uc.toDLog().pAlways(eventID + ">=" + allConsumersForPID.nextempty + ". Invalid eventID", this, EventBusArrayOneGlobal.class, "putOnBus", ITechLvl.LVL_04_FINER, false);
         throw new IllegalArgumentException(msg);
      }
      if (eventID == 0) {
         throw new IllegalArgumentException("Cannot Put on Bus a reserved \"Any\" EventID " + eventID);
      }
      //
      Object consumersSpecificEvent = allConsumersForPID.getObjectAtIndex(eventID);
      int threadMode = allConsumersForPID.getInt(eventID);
      doConsumer(consumersSpecificEvent, be, threadMode);
      //send to any inside the PID
      Object consumersAnyEvent = allConsumersForPID.getObjectAtIndex(0);
      if (consumersAnyEvent != null) {
         threadMode = allConsumersForPID.getInt(eventID);
         doConsumer(consumersAnyEvent, be, threadMode);
      }
      //send the event to those listeners that want to recieve all events from all producers
      for (int i = 0; i < listenersToAllEvents.nextempty; i++) {
         Object consumer = listenersToAllEvents.objects[i];
         int tMode = listenersToAllEvents.ints[i];
         doConsumer(consumer, be, tMode);
      }
   }

   public void sendNewEvent(int producerID, int eventID, Object producer) {
      BusEvent be = createEvent(producerID, eventID, producer);
      this.putOnBus(be);
   }

   /**
    * Removes all instances of {@link IEventConsumer} on the bus.
    * @param consumer
    */
   public void removeConsumer(IEventConsumer consumer) {
      for (int i = 0; i < producerIDToConsumerArray.length; i++) {
         if (producerIDToConsumerArray[i] != null) {
            IntToObjects ito = producerIDToConsumerArray[i];
            //iterate over event ids
            for (int j = 0; j < ito.nextempty; j++) {
               Object o = ito.objects[j];
               if (o instanceof IntToObjects) {
                  IntToObjects consumers = (IntToObjects) o;
                  consumers.removeRef(consumer);
               } else {
                  if (o == consumer) {
                     ito.objects[j] = null;
                  }
               }
            }
         }
      }
   }

   public void setExecutor(IExecutor executor) {
      this.executor = executor;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, EventBusArrayOneGlobal.class, 410);
      dc.nlLvl1Line(contextOwner);
      dc.nl();
      dc.append("Listeners To All Events");
      dc.append(" #");
      dc.append(listenersToAllEvents.nextempty);
      dc.append(" ");
      for (int pid = 0; pid < listenersToAllEvents.nextempty; pid++) {
         dc.appendVarWithSpace("Producer", pid);
         dc.appendBracketedWithSpace(uc.getCtxManager().toStringProducerID(pid));
         IntToObjects ito = producerIDToConsumerArray[pid];
         //iterate over events
         for (int eid = 0; eid < ito.nextempty; eid++) {
            dc.appendVarWithSpace("Listeners for Event", eid);
            dc.appendBracketedWithSpace(uc.getCtxManager().toStringEventID(pid, eid));
            Object o = ito.objects[eid];
            if (o instanceof IEventConsumer) {
               //we only have one
               dc.nlLvl1Line((IEventConsumer) o);
            } else {
               dc.nlLvlO(o, "listeners");
            }
         }
      }
      dc.nl();
      dc.append("Specific PID listeners");
      dc.appendVarWithSpace("#", producerIDToConsumerArray.length);
      for (int pid = 0; pid < producerIDToConsumerArray.length; pid++) {
         dc.nl();
         dc.appendVarWithSpace("Producer", pid);
         dc.appendBracketedWithSpace(uc.getCtxManager().toStringProducerID(pid));
         IntToObjects ito = producerIDToConsumerArray[pid];
         dc.append("-> # of events =" + ito.getLength());
         //iterate over events
         dc.tab();
         for (int eid = 0; eid < ito.nextempty; eid++) {
            dc.nl();
            dc.appendVarWithSpace("Listeners for Event", eid);
            dc.appendBracketedWithSpace(uc.getCtxManager().toStringEventID(pid, eid));
            Object o = ito.objects[eid];
            if (o instanceof IEventConsumer) {
               //we only have one
               dc.nlLvl1Line((IEventConsumer) o);
            } else {
               IntToObjects listeners = (IntToObjects) o;
               if (listeners != null) {
                  for (int i = 0; i < listeners.nextempty; i++) {
                     dc.nlLvl1Line((IEventConsumer) listeners.getObjectAtIndex(i));
                  }
               } else {
                  dc.append(' ');
                  dc.append("There are no consumers for eid = " + eid);
               }
            }
         }
         dc.tabRemove();
      }
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, EventBusArrayOneGlobal.class);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug

}
