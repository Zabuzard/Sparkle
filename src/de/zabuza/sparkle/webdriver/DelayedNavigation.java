package de.zabuza.sparkle.webdriver;

import java.net.URL;

import org.openqa.selenium.WebDriver.Navigation;

import de.zabuza.sparkle.webdriver.event.BackEvent;
import de.zabuza.sparkle.webdriver.event.ForwardEvent;
import de.zabuza.sparkle.webdriver.event.RefreshEvent;
import de.zabuza.sparkle.webdriver.event.ToStringEvent;
import de.zabuza.sparkle.webdriver.event.ToUrlEvent;

/**
 * Wrapper for navigation objects to delayedly execute events.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public class DelayedNavigation implements Navigation {

	/**
	 * Navigation object to wrap for delayed event execution.
	 */
	private final Navigation mNavigation;
	/**
	 * Event queue to add events to for delayed execution.
	 */
	private final IDelayedEventQueue mQueue;

	/**
	 * Creates a new instance of this object with a given navigation object and
	 * an event queue.
	 * 
	 * @param navigation
	 *            Navigation object to wrap for delayed event execution
	 * @param queue
	 *            Event queue to add events to for delayed execution
	 */
	public DelayedNavigation(final Navigation navigation, final IDelayedEventQueue queue) {
		this.mNavigation = navigation;
		this.mQueue = queue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver.Navigation#back()
	 */
	@Override
	public void back() {
		this.mQueue.addEvent(new BackEvent(this.mNavigation));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver.Navigation#forward()
	 */
	@Override
	public void forward() {
		this.mQueue.addEvent(new ForwardEvent(this.mNavigation));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver.Navigation#refresh()
	 */
	@Override
	public void refresh() {
		this.mQueue.addEvent(new RefreshEvent(this.mNavigation));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver.Navigation#to(java.lang.String)
	 */
	@Override
	public void to(final String url) {
		this.mQueue.addEvent(new ToStringEvent(this.mNavigation, url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver.Navigation#to(java.net.URL)
	 */
	@Override
	public void to(final URL url) {
		this.mQueue.addEvent(new ToUrlEvent(this.mNavigation, url));
	}

}
