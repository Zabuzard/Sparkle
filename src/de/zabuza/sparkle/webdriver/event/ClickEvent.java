package de.zabuza.sparkle.webdriver.event;

import org.openqa.selenium.WebElement;

public class ClickEvent implements IDelayableEvent {
	
	private final WebElement m_Element;
	
	public ClickEvent(final WebElement element) {
		m_Element = element;
	}

	@Override
	public void execute() {
		m_Element.click();
	}

}
