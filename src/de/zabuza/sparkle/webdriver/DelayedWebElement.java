package de.zabuza.sparkle.webdriver;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import de.zabuza.sparkle.webdriver.event.ClickEvent;
import de.zabuza.sparkle.webdriver.event.SubmitEvent;

/**
 * Wrapper for web element objects to delayedly executes events.
 * 
 * @author Zabuza
 *
 */
public final class DelayedWebElement implements WebElement {

	/**
	 * Web element to wrap for delayed event execution.
	 */
	private final WebElement m_Element;
	/**
	 * Event queue to add events to for delayed execution.
	 */
	private final IDelayedEventQueue m_Queue;

	/**
	 * Creates a new instance of this object with a given web element object and
	 * an event queue.
	 * 
	 * @param element
	 *            Web element object to wrap for delayed event execution
	 * @param queue
	 *            Event queue to add events to for delayed execution
	 */
	public DelayedWebElement(final WebElement element, final IDelayedEventQueue queue) {
		m_Element = element;
		m_Queue = queue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#clear()
	 */
	@Override
	public void clear() {
		m_Element.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#click()
	 */
	@Override
	public void click() {
		m_Queue.addEvent(new ClickEvent(m_Element));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#findElement(org.openqa.selenium.By)
	 */
	@Override
	public WebElement findElement(final By by) {
		return new DelayedWebElement(m_Element.findElement(by), m_Queue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#findElements(org.openqa.selenium.By)
	 */
	@Override
	public List<WebElement> findElements(final By by) {
		List<WebElement> elements = m_Element.findElements(by);
		List<WebElement> delayedElements = new LinkedList<WebElement>();
		for (WebElement element : elements) {
			delayedElements.add(new DelayedWebElement(element, m_Queue));
		}

		return delayedElements;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#getAttribute(java.lang.String)
	 */
	@Override
	public String getAttribute(final String name) {
		return m_Element.getAttribute(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#getCssValue(java.lang.String)
	 */
	@Override
	public String getCssValue(final String propertyName) {
		return m_Element.getCssValue(propertyName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#getLocation()
	 */
	@Override
	public Point getLocation() {
		return m_Element.getLocation();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#getRect()
	 */
	@Override
	public Rectangle getRect() {
		return m_Element.getRect();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.openqa.selenium.TakesScreenshot#getScreenshotAs(org.openqa.selenium.
	 * OutputType)
	 */
	@Override
	public <X> X getScreenshotAs(final OutputType<X> target) throws WebDriverException {
		return m_Element.getScreenshotAs(target);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#getSize()
	 */
	@Override
	public Dimension getSize() {
		return m_Element.getSize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#getTagName()
	 */
	@Override
	public String getTagName() {
		return m_Element.getTagName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#getText()
	 */
	@Override
	public String getText() {
		return m_Element.getText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#isDisplayed()
	 */
	@Override
	public boolean isDisplayed() {
		return m_Element.isDisplayed();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return m_Element.isEnabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#isSelected()
	 */
	@Override
	public boolean isSelected() {
		return m_Element.isSelected();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#sendKeys(java.lang.CharSequence[])
	 */
	@Override
	public void sendKeys(final CharSequence... keysToSend) {
		m_Element.sendKeys(keysToSend);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#submit()
	 */
	@Override
	public void submit() {
		m_Queue.addEvent(new SubmitEvent(m_Element));
	}

}
