package de.zabuza.sparkle.webdriver.event;

import org.openqa.selenium.WebDriver.Navigation;

public class BackEvent implements IDelayableEvent {
	
	private final Navigation m_Navigation;
	
	public BackEvent(final Navigation navigation) {
		m_Navigation = navigation;
	}

	@Override
	public void execute() {
		m_Navigation.back();
	}

}
