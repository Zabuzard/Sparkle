package de.zabuza.sparkle.webdriver;

import de.zabuza.sparkle.webdriver.event.IDelayableEvent;

/**
 * Interface for delayed event queues.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public interface IDelayedEventQueue {
	/**
	 * Adds a given delayable event to the event queue.
	 * 
	 * @param event
	 *            Event to add
	 */
	public void addEvent(final IDelayableEvent event);

	/**
	 * Returns whether the event queue is empty.
	 * 
	 * @return <tt>True</tt> if the event queue is empty, <tt>false</tt> if not
	 */
	public boolean isEmpty();
}
