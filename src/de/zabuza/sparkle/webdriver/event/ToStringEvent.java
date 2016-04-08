package de.zabuza.sparkle.webdriver.event;

import org.openqa.selenium.WebDriver.Navigation;

public class ToStringEvent implements IDelayableEvent {

	private final Navigation m_Navigation;
	private final String m_Url;

	public ToStringEvent(final Navigation navigation, final String url) {
		m_Navigation = navigation;
		m_Url = url;
	}

	@Override
	public void execute() {
		m_Navigation.to(m_Url);
	}

}
