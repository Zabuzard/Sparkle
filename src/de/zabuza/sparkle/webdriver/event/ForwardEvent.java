package de.zabuza.sparkle.webdriver.event;

import org.openqa.selenium.WebDriver.Navigation;

public class ForwardEvent implements IDelayableEvent {
	
	private final Navigation m_Navigation;
	
	public ForwardEvent(final Navigation navigation) {
		m_Navigation = navigation;
	}

	@Override
	public void execute() {
		m_Navigation.forward();
	}

}
