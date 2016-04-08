package de.zabuza.sparkle.webdriver.event;

import org.openqa.selenium.WebDriver.Navigation;

public class RefreshEvent implements IDelayableEvent {

	private final Navigation m_Navigation;

	public RefreshEvent(final Navigation navigation) {
		m_Navigation = navigation;
	}

	@Override
	public void execute() {
		m_Navigation.refresh();
	}

}
