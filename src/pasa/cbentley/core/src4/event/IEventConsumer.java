package pasa.cbentley.core.src4.event;

import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Generic interface with a One fits all Method. If more methods are needed, extend this interface
 * <br>
 * Somewhat Dynamic Typing.
 * A method on {@link IEventBus} allow to poll an IEventConsumer for which types it is interested.
 * One can get ProducerID and EventID for an IEventConsumer.
 * <br>This work dynamically.
 * <br>
 * <br>
 * @author  Charles Bentley
 */
public interface IEventConsumer extends IStringable {

   /**
     * @param e {@link BusEvent}.
     * @return
     */
   public void consumeEvent(BusEvent e);
}
