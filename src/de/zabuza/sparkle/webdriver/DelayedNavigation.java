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
	private final Navigation m_Navigation;
	/**
	 * Event queue to add events to for delayed execution.
	 */
	private final IDelayedEventQueue m_Queue;

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
		this.m_Navigation = navigation;
		this.m_Queue = queue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver.Navigation#back()
	 */
	@Override
	public void back() {
		this.m_Queue.addEvent(new BackEvent(this.m_Navigation));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver.Navigation#forward()
	 */
	@Override
	public void forward() {
		this.m_Queue.addEvent(new ForwardEvent(this.m_Navigation));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver.Navigation#refresh()
	 */
	@Override
	public void refresh() {
		this.m_Queue.addEvent(new RefreshEvent(this.m_Navigation));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver.Navigation#to(java.lang.String)
	 */
	@Override
	public void to(final String url) {
		this.m_Queue.addEvent(new ToStringEvent(this.m_Navigation, url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver.Navigation#to(java.net.URL)
	 */
	@Override
	public void to(final URL url) {
		this.m_Queue.addEvent(new ToUrlEvent(this.m_Navigation, url));
	}

}
