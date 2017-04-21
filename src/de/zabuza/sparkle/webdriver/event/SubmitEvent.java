package de.zabuza.sparkle.webdriver.event;

import org.openqa.selenium.WebElement;

/**
 * Delayable event that performs a submit on a given web element. See also
 * {@link WebElement#submit()}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class SubmitEvent implements IDelayableEvent {

	/**
	 * Web element to submit, should not be an instance of
	 * {@link de.zabuza.sparkle.webdriver.DelayedWebElement DelayedWebElement}.
	 */
	private final WebElement m_Element;

	/**
	 * Creates a new instance of this object with a given web element.
	 * 
	 * @param element
	 *            Element to submit, should not be an instance of
	 *            {@link de.zabuza.sparkle.webdriver.DelayedWebElement
	 *            DelayedWebElement}.
	 */
	public SubmitEvent(final WebElement element) {
		this.m_Element = element;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.webdriver.event.IDelayableEvent#execute()
	 */
	@Override
	public void execute() {
		this.m_Element.submit();
	}

}
