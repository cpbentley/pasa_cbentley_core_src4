package pasa.cbentley.core.src4.event;

import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.IEventsCore;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.ex.UCtxException;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.core.src4.structs.IntToObjects;

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
public class EventBusArray implements IEventBus, IEventConsumer {
   /**
    * rows are producers id
    * columns are event consumers
    */
   private IntToObjects[] producerIDToConsumerArray;

   /**
    * Contains
    * <li> {@link IEventConsumer} or 
    * <li> {@link IntToObjects} of {@link IEventConsumer}
    * <li> null if no consumer for the events
    */
   private IntToObjects   listenersToAllEvents;

   private UCtx           uc;

   private ICtx           contextOwner;

   /**
    * Registers to dipose memory events 
    * @param uc
    * @param contextOwner
    * @param producersNumEvents containes the event topology. number of producers mapped to number of events
    */
   public EventBusArray(UCtx uc, ICtx contextOwner, int[] producersNumEvents) {
      this.uc = uc;
      this.contextOwner = contextOwner;
      listenersToAllEvents = new IntToObjects(uc);

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
      eventBus.addConsumer(this, IEventsCore.PID_3_MEMORY, IEventsCore.EID_MEMORY_3_OBJECT_DESTROY);
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

   /**
    * Registers for all events of all producers.
    * <br>
    * @param con
    */
   public void addConsumer(IEventConsumer con) {
      listenersToAllEvents.add(con, 0);
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
      //check ids and producerID
      if (producerID == PID_0_ANY) {
         listenersToAllEvents.add(con);
      } else {
         //should never happen in production code
         //#mdebug
         if (producerID >= producerIDToConsumerArray.length) {
            throw new IllegalArgumentException("ProducerID " + producerID + " is not valid");
         }
         //#enddebug
         IntToObjects ito1 = producerIDToConsumerArray[producerID];
         ito1.ensureRoom(eventID, 1);
         Object o = ito1.objects[eventID];
         if (o != null) {
            //check if we have 
            if (o instanceof IEventConsumer) {
               //add the existing consumer along the new one into a struct
               IntToObjects ito = new IntToObjects(uc, new Object[] { o, con });
               ito1.objects[eventID] = ito;
            } else {
               //already an item
               ((IntToObjects) o).add(con, 0);
            }
         } else {
            //put it directly
            ito1.objects[eventID] = con;
         }
      }
   }

   public BusEvent createEvent(int producerID, int eventID, Object producer) {
      BusEvent e = new BusEvent(uc, contextOwner, producerID, eventID);
      e.setProducer(producer);
      return e;
   }

   public BusEvent createEvent(int producerID, int eventID, Object producer, int param1, int param2) {
      BusEvent e = createEvent(producerID, eventID, producer);
      e.setParam1(param1);
      e.setParam2(param2);
      return e;
   }

   private void doConsumer(Object consumer, BusEvent e) {
      try {
         if (consumer instanceof IEventConsumer) { //we only have one consumer
            ((IEventConsumer) consumer).consumeEvent(e);
         } else if (consumer instanceof IntToObjects) { //we have several consumers
            IntToObjects cons = (IntToObjects) consumer;
            for (int i = 0; i < cons.nextempty; i++) {
               ((IEventConsumer) cons.objects[i]).consumeEvent(e);
            }
         }
      } catch (UCtxException ex) {
         if (ex.getId() == UCtxException.EVENT_MATCH_EX) {
            //#debug
            uc.toDLog().pEventSevere(ex.getMessage(), e, EventBusArray.class, "doConsumer");
         }
      }
      //#mdebug
      if (!e.hasFlag(BusEvent.FLAG_1_ACTED)) {
         //send warning. event was not acted
         uc.toDLog().pEventWarn("BusEvent not consumed", e, EventBusArray.class, "doConsumer");
      } else {
         uc.toDLog().pEventFiner("BusEvent was consumed", e, EventBusArray.class, "doConsumer");
      }
      //#enddebug
   }

   public void consumeEvent(BusEvent e) {
      Object prod = e.getProducer();
      if (prod instanceof IEventConsumer) {
         this.removeConsumer((IEventConsumer) prod);
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
         //#debug
         uc.toDLog().pFlow(eventID + ">=" + allConsumersForPID.nextempty + ". Invalid eventID" + be.toString1Line(), this, EventBusArray.class, "putOnBus", ITechLvl.LVL_04_FINER, false);
         throw new IllegalArgumentException();
      }
      if (eventID == 0) {
         throw new IllegalArgumentException("Cannot Put on Bus Any EventID " + eventID);
      }
      //
      Object consumersSpecificEvent = allConsumersForPID.getObjectAtIndex(eventID);
      doConsumer(consumersSpecificEvent, be);
      //send to any inside the PID
      Object consumersAnyEvent = allConsumersForPID.getObjectAtIndex(0);
      doConsumer(consumersAnyEvent, be);
      //send the event to those listeners that want to recieve all events from all producers
      for (int i = 0; i < listenersToAllEvents.nextempty; i++) {
         Object consumer = listenersToAllEvents.objects[i];
         doConsumer(consumer, be);
      }
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

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "EventBusArray");
      dc.nlLvlOneLine(contextOwner);
      dc.nl();
      dc.append("Listeners To All Events");
      for (int pid = 0; pid < listenersToAllEvents.nextempty; pid++) {
         dc.append("Producer " + pid + " = " + contextOwner.toStringProducerID(pid));
         IntToObjects ito = producerIDToConsumerArray[pid];
         //iterate over events
         for (int eid = 0; eid < ito.nextempty; eid++) {
            dc.append("Listeners for Event " + eid + " = " + contextOwner.toStringEventID(pid, eid));
            Object o = ito.objects[eid];
            if (o instanceof IEventConsumer) {
               //we only have one
               dc.nlLvlOneLine((IEventConsumer) o);
            } else {
               IntToObjects listeners = (IntToObjects) o;
               for (int i = 0; i < listeners.nextempty; i++) {
                  dc.nlLvlOneLine((IEventConsumer) listeners.getObjectAtIndex(i));
               }
            }
         }
      }
      dc.nl();
      dc.append("Specific PID listeners");
      for (int pid = 0; pid < producerIDToConsumerArray.length; pid++) {
         dc.nl();
         dc.append("Producer " + pid + " = " + contextOwner.toStringProducerID(pid));
         IntToObjects ito = producerIDToConsumerArray[pid];
         dc.append("# of events =" + ito.getLength());
         //iterate over events
         dc.tab();
         for (int eid = 0; eid < ito.nextempty; eid++) {
            dc.nl();
            dc.append("Listeners for Event " + eid + " = " + contextOwner.toStringEventID(pid, eid));
            Object o = ito.objects[eid];
            if (o instanceof IEventConsumer) {
               //we only have one
               dc.nlLvlOneLine((IEventConsumer) o);
            } else {
               IntToObjects listeners = (IntToObjects) o;
               if (listeners != null) {
                  for (int i = 0; i < listeners.nextempty; i++) {
                     dc.nlLvlOneLine((IEventConsumer) listeners.getObjectAtIndex(i));
                  }
               } else {
                  dc.append("null... no consumers for eid = " + eid);
               }
            }
         }
         dc.tabRemove();
      }
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "EvChannel");
   }

   //#enddebug

}
