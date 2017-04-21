package de.zabuza.sparkle.webdriver.event;

import java.net.URL;

import org.openqa.selenium.WebDriver.Navigation;

/**
 * Delayable event that navigates to an URL, given as URL object. See also
 * {@link Navigation#to(URL)}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class ToUrlEvent implements IDelayableEvent {

	/**
	 * Object to use for navigation, should not be an instance of
	 * {@link de.zabuza.sparkle.webdriver.DelayedNavigation DelayedNavigation}.
	 */
	private final Navigation m_Navigation;
	/**
	 * URL to navigate to.
	 */
	private final URL m_Url;

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
	public ToUrlEvent(final Navigation navigation, final URL url) {
		this.m_Navigation = navigation;
		this.m_Url = url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.webdriver.event.IDelayableEvent#execute()
	 */
	@Override
	public void execute() {
		this.m_Navigation.to(this.m_Url);
	}

}
