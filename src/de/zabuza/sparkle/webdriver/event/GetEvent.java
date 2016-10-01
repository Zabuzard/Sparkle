package de.zabuza.sparkle.webdriver.event;

import org.openqa.selenium.WebDriver;

/**
 * Delayable event that performs a get request to a given URL on a given web
 * driver. See also {@link WebDriver#get(String)}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class GetEvent implements IDelayableEvent {

	/**
	 * URL to get.
	 */
	private final String m_Url;
	/**
	 * Web driver to use for the get request, should not be an instance of
	 * {@link de.zabuza.sparkle.webdriver.DelayedWebDriver DelayedWebDriver}.
	 */
	private final WebDriver m_WebDriver;

	/**
	 * Creates a new instance of this object using a given web driver and URL.
	 * 
	 * @param driver
	 *            Driver to use for the get request, should not be an instance
	 *            of {@link de.zabuza.sparkle.webdriver.DelayedWebDriver
	 *            DelayedWebDriver}.
	 * @param url
	 *            URL to get
	 */
	public GetEvent(final WebDriver driver, final String url) {
		m_Url = url;
		m_WebDriver = driver;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.webdriver.event.IDelayableEvent#execute()
	 */
	@Override
	public void execute() {
		m_WebDriver.get(m_Url);
	}

	/**
	 * Gets the url.
	 * 
	 * @return the url
	 */
	public String getUrl() {
		return m_Url;
	}
}
