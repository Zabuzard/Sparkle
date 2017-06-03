package de.zabuza.sparkle.webdriver.event;

import org.openqa.selenium.WebDriver.Navigation;

/**
 * Delayable event that navigates forward in browser history. See also
 * {@link Navigation#forward()}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class ForwardEvent implements IDelayableEvent {

	/**
	 * Object to use for navigation, should not be an instance of
	 * {@link de.zabuza.sparkle.webdriver.DelayedNavigation DelayedNavigation}.
	 */
	private final Navigation mNavigation;

	/**
	 * Creates a new instance of this object using a given navigation object.
	 * 
	 * @param navigation
	 *            Object to use for navigation, should not be an instance of
	 *            {@link de.zabuza.sparkle.webdriver.DelayedNavigation
	 *            DelayedNavigation}.
	 */
	public ForwardEvent(final Navigation navigation) {
		this.mNavigation = navigation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.webdriver.event.IDelayableEvent#execute()
	 */
	@Override
	public void execute() {
		this.mNavigation.forward();
	}

}
