package pasa.cbentley.core.src4.ctx;

import pasa.cbentley.core.src4.event.IEventBus;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.event.IEventProducer;

/**
 * Centralizes all the events for the {@link UCtx#getEventBusRoot()}
 * <br>
 * <br>
 * Post about Event Driven Sofware Engineering.
 * http://softwareengineering.stackexchange.com/questions/156970/how-to-ease-the-maintenance-of-event-driven-code
 * <br>
 * <br>
 * <li> Don't raise event from within events
 * <li> encapsulate event code. see data. do work. job done.
 * <li> Centralized event processor {@link EveChannel}
 * <br>
 * <br>
 * Producers of Event
 * <li> Each producer uses an ID to identify itself.
 * <br>
 * Why centralizing event definitions ?
 * <li> Because its convenient to know what kind of events a module context generates
 * <br>
 * Developers are not forced to use the {@link IEventBus} framework and can use their own or simple
 * event listener interfaces.
 * 
 * <li> Event identity is a integer ID. Originally designed for low memory environment.
 * <li> IDs are unique integers enforced by the {@link CtxManager} at runtime.
 * 
 * @see IEventBus
 * @see IEventConsumer
 * @see IEventProducer
 * 
 * @author Charles Bentley
 */
public interface IEventsCore {

   /**
    * Number of statically defined events
    */
   public static final int BASE_EVENTS                    = 4;

   /**
    * Any Producer will send the event. This production line 
    * should be used by rare events.
    * <br>
    * Events will of instances must register a dynamic PID
    * outside the static range.
    */
   public static final int PID_0_ANY                      = 0;

   /**
    * 
    */
   public static final int PID_1_FRAMEWORK                = 1;

   /**
    * Producer ID. Uses it to send event relating to Host events.
    */
   public static final int PID_2_HOST                     = 2;

   public static final int SID_EVENTS_2                   = 2;

   /**
    * When a language has been changed
    */
   public static final int EID_FRAMEWORK_2_LANGUAGE_CHANGED    = 2;

   /**
    * Event generated when 
    */
   public static final int EID_FRAMEWORK_1_CTX_CREATED         = 1;

   /**
    * The pid used for memory event on the {@link UCtx#getEventBusRoot()} event bus.
    */
   public static final int PID_3_MEMORY                   = 3;

   /**
    * Any event by producer {@link IEventsCore#PID_3_MEMORY}
    */
   public static final int EID_MEMORY_0_ANY               = 0;

   public static final int EID_MEMORY_1_OUT_OF_MEMORY_GC  = 1;

   public static final int EID_MEMORY_2_USER_REQUESTED_GC = 2;

   /**
    * Event sent with Object as producer when object references have to be removed.
    * Typically, cache and event bus will want to catch to events
    * and de reference those objects 
    */
   public static final int EID_MEMORY_3_OBJECT_DESTROY    = 3;

   public static final int EID_MEMORY_X_NUM               = 3;

}
