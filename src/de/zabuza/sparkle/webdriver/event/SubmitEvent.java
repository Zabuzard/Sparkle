package de.zabuza.sparkle.webdriver.event;

import org.openqa.selenium.WebElement;

public class SubmitEvent implements IDelayableEvent {
	
	private final WebElement m_Element;
	
	public SubmitEvent(final WebElement element) {
		m_Element = element;
	}

	@Override
	public void execute() {
		m_Element.submit();
	}

}
