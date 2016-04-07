package de.zabuza.sparkle.webdriver.event;

import java.net.URL;

import org.openqa.selenium.WebDriver.Navigation;

public class ToUrlEvent implements IDelayableEvent {
	
	private final Navigation m_Navigation;
	private final URL m_Url;
	
	public ToUrlEvent(final Navigation navigation, final URL url) {
		m_Navigation = navigation;
		m_Url = url;
	}

	@Override
	public void execute() {
		m_Navigation.to(m_Url);
	}

}
