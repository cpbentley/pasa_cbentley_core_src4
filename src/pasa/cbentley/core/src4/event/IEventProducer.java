/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.event;

/**
 * Interface for all producers of {@link BusEvent}
 * @author Charles Bentley
 *
 */
public interface IEventProducer {

   public int getProducerID();
}
