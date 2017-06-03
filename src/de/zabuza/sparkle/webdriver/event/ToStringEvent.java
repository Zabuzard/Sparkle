package de.zabuza.sparkle.webdriver.event;

import org.openqa.selenium.WebDriver.Navigation;

/**
 * Delayable event that navigates to an URL, given as string. See also
 * {@link Navigation#to(String)}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class ToStringEvent implements IDelayableEvent {

	/**
	 * Object to use for navigation, should not be an instance of
	 * {@link de.zabuza.sparkle.webdriver.DelayedNavigation DelayedNavigation}.
	 */
	private final Navigation mNavigation;
	/**
	 * URL to navigate to.
	 */
	private final String mUrl;

	/**
	 * Creates a new instance of this object using a given navigation object.
	 * 
	 * @param navigation
	 *            Object to use for navigation, should not be an instance of
	 *            {@link de.zabuza.sparkle.webdriver.DelayedNavigation
	 *            DelayedNavigation}.
	 * @param url
	 *            URl to navigate to
	 */
	public ToStringEvent(final Navigation navigation, final String url) {
		this.mNavigation = navigation;
		this.mUrl = url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.webdriver.event.IDelayableEvent#execute()
	 */
	@Override
	public void execute() {
		this.mNavigation.to(this.mUrl);
	}

}
