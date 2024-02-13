/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.ctx;

import pasa.cbentley.core.src4.event.IEventBus;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.event.IEventProducer;
import pasa.cbentley.core.src4.interfaces.IEvents;

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
 * Tagged by {@link IEvents}
 * 
 * @see IEventBus
 * @see IEventConsumer
 * @see IEventProducer
 * 
 * @author Charles Bentley
 */
public interface IEventsCore extends IEvents {

   public static final int A_SID_CORE_EVENT_A                  = 0;

   public static final int A_SID_CORE_EVENT_Z                  = 9;

   /**
    * Number of statically defined events
    */
   public static final int BASE_EVENTS                         = 6;

   public static final int PID_00                              = 0;

   /**
    * 
    * Reserved
    * Events will of instances must register a dynamic PID
    * outside the static range.
    */
   public static final int PID_00_ANY                          = A_SID_CORE_EVENT_A + PID_00;

   public static final int PID_00_ANY_0                        = 0;

   public static final int PID_00_XX                           = 1;

   public static final int PID_01                              = 1;

   /**
    * 
    */
   public static final int PID_01_FRAMEWORK                    = A_SID_CORE_EVENT_A + PID_01;

   /**
    * For registration only. Not for generation of events
    */
   public static final int PID_01_FRAMEWORK_0_ANY              = 0;

   /**
    * Event generated when 
    */
   public static final int PID_01_FRAMEWORK_1_CTX_CREATED      = 1;

   /**
    * When a language has been changed
    */
   public static final int PID_01_FRAMEWORK_2_LANGUAGE_CHANGED = 2;

   public static final int PID_01_XX                           = 3;

   public static final int PID_02                              = 2;

   /**
    * Producer ID. Uses it to send event relating to Host events.
    */
   public static final int PID_02_HOST                         = A_SID_CORE_EVENT_A + PID_02;

   public static final int PID_02_HOST_0_ANY                   = 0;

   public static final int PID_02_HOST_1_UPDATE                = 1;

   public static final int PID_02_XX                           = 2;

   public static final int PID_03                              = 3;

   /**
    * The pid used for memory event on the {@link UCtx#getEventBusRoot()} event bus.
    */
   public static final int PID_03_MEMORY                       = A_SID_CORE_EVENT_A + PID_03;

   /**
    * Any event by producer {@link IEventsCore#PID_03_MEMORY}
    */
   public static final int PID_03_MEMORY_0_ANY                 = 0;

   /**
    * Event ID 1 for Producer Memory
    */
   public static final int PID_03_MEMORY_1_OUT_OF_MEMORY_GC    = 1;

   public static final int PID_03_MEMORY_2_USER_REQUESTED_GC   = 2;

   /**
    * Event sent with Object as producer when object references have to be removed.
    * Typically, cache and event bus will want to catch to events
    * and de reference those objects 
    */
   public static final int PID_03_MEMORY_3_OBJECT_DESTROY      = 3;

   public static final int PID_03_XX                           = 4;

   public static final int PID_04                              = 4;

   public static final int PID_04_LIFE                         = A_SID_CORE_EVENT_A + PID_04;

   public static final int PID_04_LIFE_0_ANY                   = 0;

   public static final int PID_04_LIFE_1_STARTED               = 1;

   public static final int PID_04_LIFE_2_PAUSED                = 2;

   public static final int PID_04_LIFE_3_RESUMED               = 3;

   public static final int PID_04_LIFE_4_STOPPED               = 4;

   public static final int PID_04_LIFE_5_DESTROYED             = 5;

   public static final int PID_04_XX                           = 6;

   public static final int PID_05                              = 5;

   public static final int PID_05_THREAD                       = A_SID_CORE_EVENT_A + PID_05;

   public static final int PID_05_THREAD_0_ANY                 = 0;

   public static final int PID_05_THREAD_1_PULSE_ON            = 1;

   public static final int PID_05_THREAD_2_PULSE_OFF           = 2;

   public static final int PID_05_XX                           = 3;

}
