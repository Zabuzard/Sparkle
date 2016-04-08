package de.zabuza.sparkle.webdriver.event;

/**
 * Interface for events that can be executed delayedly.
 * 
 * @author Zabuza
 *
 */
public interface IDelayableEvent {

	/**
	 * Executes this event.
	 */
	public abstract void execute();
}
