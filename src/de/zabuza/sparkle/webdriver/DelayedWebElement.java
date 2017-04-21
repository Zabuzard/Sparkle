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
 * Wrapper for web element objects to delayedly execute events.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
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
		this.m_Element = element;
		this.m_Queue = queue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#clear()
	 */
	@Override
	public void clear() {
		this.m_Element.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#click()
	 */
	@Override
	public void click() {
		this.m_Queue.addEvent(new ClickEvent(this.m_Element));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#findElement(org.openqa.selenium.By)
	 */
	@Override
	public WebElement findElement(final By by) {
		return new DelayedWebElement(this.m_Element.findElement(by), this.m_Queue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#findElements(org.openqa.selenium.By)
	 */
	@Override
	public List<WebElement> findElements(final By by) {
		final List<WebElement> elements = this.m_Element.findElements(by);
		final List<WebElement> delayedElements = new LinkedList<>();
		for (final WebElement element : elements) {
			delayedElements.add(new DelayedWebElement(element, this.m_Queue));
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
		return this.m_Element.getAttribute(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#getCssValue(java.lang.String)
	 */
	@Override
	public String getCssValue(final String propertyName) {
		return this.m_Element.getCssValue(propertyName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#getLocation()
	 */
	@Override
	public Point getLocation() {
		return this.m_Element.getLocation();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#getRect()
	 */
	@Override
	public Rectangle getRect() {
		return this.m_Element.getRect();
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
		return this.m_Element.getScreenshotAs(target);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#getSize()
	 */
	@Override
	public Dimension getSize() {
		return this.m_Element.getSize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#getTagName()
	 */
	@Override
	public String getTagName() {
		return this.m_Element.getTagName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#getText()
	 */
	@Override
	public String getText() {
		return this.m_Element.getText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#isDisplayed()
	 */
	@Override
	public boolean isDisplayed() {
		return this.m_Element.isDisplayed();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return this.m_Element.isEnabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#isSelected()
	 */
	@Override
	public boolean isSelected() {
		return this.m_Element.isSelected();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#sendKeys(java.lang.CharSequence[])
	 */
	@Override
	public void sendKeys(final CharSequence... keysToSend) {
		this.m_Element.sendKeys(keysToSend);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebElement#submit()
	 */
	@Override
	public void submit() {
		this.m_Queue.addEvent(new SubmitEvent(this.m_Element));
	}

}
