package de.zabuza.sparkle.webdriver.event;

import org.openqa.selenium.WebElement;

/**
 * Delayable event that performs a click on a given web element. See also
 * {@link WebElement#click()}.
 * 
 * @author Zabuza
 *
 */
public class ClickEvent implements IDelayableEvent {

	/**
	 * Web element to click, should not be an instance of
	 * {@link de.zabuza.sparkle.webdriver.DelayedWebElement DelayedWebElement}.
	 */
	private final WebElement m_Element;

	/**
	 * Creates a new instance of this object with a given web element.
	 * 
	 * @param element
	 *            Element to click, should not be an instance of
	 *            {@link de.zabuza.sparkle.webdriver.DelayedWebElement
	 *            DelayedWebElement}.
	 */
	public ClickEvent(final WebElement element) {
		m_Element = element;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.webdriver.event.IDelayableEvent#execute()
	 */
	@Override
	public void execute() {
		m_Element.click();
	}

}
