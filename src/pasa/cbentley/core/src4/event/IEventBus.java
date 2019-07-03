package pasa.cbentley.core.src4.event;

import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.IEventsCore;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * <b>Events</b>
 * <ol>
 * </ol>
 * Channel/Bus interconnecting {@link IEventConsumer}s and {@link IEventProducer}s.
 * <br>
 * <br>
 * <b>Design goals</b><ol>
 * <li> Remove the need of an array structure for holding listener instances. Also removes those interface AListener, BListener etc. All event listeners are {@link IEventConsumer}
 * <li> Use of statically defined ids for low memory devices.
 * <li> Multi-casting: any number of {@link IEventConsumer} may listener to any number of events pid/eid pairs.
 * <li> Consumers don't require a reference to producers.
 * </ol>
 * <b>Limitations</b>
 * <ol>
 * <li> Memory leaks. in src4, we don't have access to weak references. {@link IEventConsumer} is responsible
 *  for unregistering with {@link IEventBus#removeConsumer(IEventConsumer)}
 * <li> Lisibility is less explicit 
 * <li> Threading issue
 * <li> Event Filtering for dynamic producers
 * </ol>
 *  
 * <br>
 * <b>Event Filtering</b>
 * <br>
 * When producers are dynamically created and registered to the same PID, all consumers will receive events from 
 * them.
 * <br>
 * Filtering is done 
 * <li>upstream on the {@link IEventBus} instance 
 * <li>downstream on the reference of the producer
 * <br>
 * <b>Dynamic Producers : Case of the Table Model</b>
 * <br>
 * Any number of table models may have any number of views.
 * On the requirement that a table view holds a reference on the table model,
 * the view registers for the statically and filter on {@link BusEvent#getProducer()}
 * 
 * 
 * <br>
 * <b>Memory leak</b>
 * {@link IEventBus} pins Producer and Consumer in memory.
 * Code must remove {@link IEventConsumer} and Producers. when no longer needed.
 * <br>
 * <br>
 * <b>Multi Threading</b>
 * Some event bus may run exclusively on the so called GUI-thread.
 * <br>
 * 
 * If the generic support offered by the {@link BusEvent} is not enough, one can subclass and provide additional fields and
 * methods.
 * 
 * @author Charles-Philip Bentley
 */
public interface IEventBus extends IStringable {

   /**
    * Registering with this PID will forward any event from any producer.
    * <br>
    * 
    */
   public static final int PID_0_ANY = 0;

   /**
    * Registers 
    * This method allows a Consumer to register to events without having
    * a reference to the producer. Simply use the contextually and statically defined
    * tuple of eventID and producerID
    * @param con
    * @param prodID
    * @param eventID
    */
   public void addConsumer(IEventConsumer con, int prodID, int eventID);

   /**
    * Sends the {@link BusEvent} to registered {@link IEventConsumer}
    * <br>
    * The bus finds event consumers based on eventid and producer id
    * @param be
    */
   public void putOnBus(BusEvent be);

   /**
    * Create an event for this bus. The  {@link ICtx} context owner will be the on
    * <br>
    * After creation, caller may add flags and other parameters.
    * Then it will put the event on the bus with {@link IEventBus#putOnBus(BusEvent)}.
    * 
    * {@link IEventsCore}
    * 
    * <br>
    * 
    * 
    * @param producerID static id defined at the module level
    * @param eventID static id defining the event sub type. 
    * @param producer the object that produced the event
    * @return
    */
   public BusEvent createEvent(int producerID, int eventID, Object producer);

   /**
    * Remove all instances of the event consumer
    * @param consumer
    */
   public void removeConsumer(IEventConsumer consumer);
      
}