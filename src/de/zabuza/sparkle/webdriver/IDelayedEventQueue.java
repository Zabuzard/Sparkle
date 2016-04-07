package de.zabuza.sparkle.webdriver;

import de.zabuza.sparkle.webdriver.event.IDelayableEvent;

public interface IDelayedEventQueue {
	public void addEvent(final IDelayableEvent event);
	public boolean isEmpty();
}
