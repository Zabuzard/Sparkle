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

public final class DelayedWebElement implements WebElement {

	private final WebElement m_Element;
	private final IDelayedEventQueue m_Queue;

	public DelayedWebElement(final WebElement element, final IDelayedEventQueue queue) {
		m_Element = element;
		m_Queue = queue;
	}

	@Override
	public void clear() {
		m_Element.clear();
	}

	@Override
	public void click() {
		m_Queue.addEvent(new ClickEvent(m_Element));
	}

	@Override
	public WebElement findElement(final By by) {
		return new DelayedWebElement(m_Element.findElement(by), m_Queue);
	}

	@Override
	public List<WebElement> findElements(final By by) {
		List<WebElement> elements = m_Element.findElements(by);
		List<WebElement> delayedElements = new LinkedList<WebElement>();
		for (WebElement element : elements) {
			delayedElements.add(new DelayedWebElement(element, m_Queue));
		}

		return delayedElements;
	}

	@Override
	public String getAttribute(final String name) {
		return m_Element.getAttribute(name);
	}

	@Override
	public String getCssValue(final String propertyName) {
		return m_Element.getCssValue(propertyName);
	}

	@Override
	public Point getLocation() {
		return m_Element.getLocation();
	}

	@Override
	public Rectangle getRect() {
		return m_Element.getRect();
	}

	@Override
	public <X> X getScreenshotAs(final OutputType<X> target) throws WebDriverException {
		return m_Element.getScreenshotAs(target);
	}

	@Override
	public Dimension getSize() {
		return m_Element.getSize();
	}

	@Override
	public String getTagName() {
		return m_Element.getTagName();
	}

	@Override
	public String getText() {
		return m_Element.getText();
	}

	@Override
	public boolean isDisplayed() {
		return m_Element.isDisplayed();
	}

	@Override
	public boolean isEnabled() {
		return m_Element.isEnabled();
	}

	@Override
	public boolean isSelected() {
		return m_Element.isSelected();
	}

	@Override
	public void sendKeys(final CharSequence... keysToSend) {
		m_Element.sendKeys(keysToSend);
	}

	@Override
	public void submit() {
		m_Queue.addEvent(new SubmitEvent(m_Element));
	}

}
