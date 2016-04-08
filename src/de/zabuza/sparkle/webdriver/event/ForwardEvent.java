package de.zabuza.sparkle.webdriver.event;

import org.openqa.selenium.WebDriver.Navigation;

/**
 * Delayable event that navigates forward in browser history. See also
 * {@link Navigation#forward()}.
 * 
 * @author Zabuza
 *
 */
public class ForwardEvent implements IDelayableEvent {

	/**
	 * Object to use for navigation, should not be an instance of
	 * {@link de.zabuza.sparkle.webdriver.DelayedNavigation DelayedNavigation}.
	 */
	private final Navigation m_Navigation;

	/**
	 * Creates a new instance of this object using a given navigation object.
	 * 
	 * @param navigation
	 *            Object to use for navigation, should not be an instance of
	 *            {@link de.zabuza.sparkle.webdriver.DelayedNavigation
	 *            DelayedNavigation}.
	 */
	public ForwardEvent(final Navigation navigation) {
		m_Navigation = navigation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.webdriver.event.IDelayableEvent#execute()
	 */
	@Override
	public void execute() {
		m_Navigation.forward();
	}

}
