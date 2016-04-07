package de.zabuza.sparkle.webdriver.event;

import org.openqa.selenium.WebDriver;

public final class GetEvent implements IDelayableEvent {
	
	private final String m_Url;
	private final WebDriver m_WebDriver;
	
	public GetEvent(final WebDriver driver, final String url) {
		m_Url = url;
		m_WebDriver = driver;
	}

	/**
	 * Gets the url.
	 * @return the url
	 */
	public String getUrl() {
		return m_Url;
	}

	@Override
	public void execute() {
		m_WebDriver.get(m_Url);
	}
}
