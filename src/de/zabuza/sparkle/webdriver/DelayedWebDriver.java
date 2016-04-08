package de.zabuza.sparkle.webdriver;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import de.zabuza.sparkle.webdriver.event.GetEvent;

public final class DelayedWebDriver implements WebDriver {
	private final DelayedEventExecutor m_DelayedEventExecutor;
	private final WebDriver m_WebDriver;

	public DelayedWebDriver(final WebDriver driver) {
		m_WebDriver = driver;
		m_DelayedEventExecutor = new DelayedEventExecutor();
		m_DelayedEventExecutor.start();
	}

	@Override
	public void close() {
		m_WebDriver.close();
	}

	@Override
	public WebElement findElement(final By by) {
		return new DelayedWebElement(m_WebDriver.findElement(by), m_DelayedEventExecutor);
	}

	@Override
	public List<WebElement> findElements(final By by) {
		List<WebElement> elements = m_WebDriver.findElements(by);
		List<WebElement> delayedElements = new LinkedList<WebElement>();
		for (WebElement element : elements) {
			delayedElements.add(new DelayedWebElement(element, m_DelayedEventExecutor));
		}

		return delayedElements;
	}

	@Override
	public void get(final String url) {
		m_DelayedEventExecutor.addEvent(new GetEvent(m_WebDriver, url));
	}

	@Override
	public String getCurrentUrl() {
		return m_WebDriver.getCurrentUrl();
	}

	@Override
	public String getPageSource() {
		return m_WebDriver.getPageSource();
	}

	@Override
	public String getTitle() {
		return m_WebDriver.getTitle();
	}

	@Override
	public String getWindowHandle() {
		return m_WebDriver.getWindowHandle();
	}

	@Override
	public Set<String> getWindowHandles() {
		return m_WebDriver.getWindowHandles();
	}

	public boolean isEventQueueEmpty() {
		return m_DelayedEventExecutor.isEmpty();
	}

	@Override
	public Options manage() {
		return m_WebDriver.manage();
	}

	@Override
	public Navigation navigate() {
		return new DelayedNavigation(m_WebDriver.navigate(), m_DelayedEventExecutor);
	}

	@Override
	public void quit() {
		m_DelayedEventExecutor.stopExecution();
		m_WebDriver.quit();
	}

	@Override
	public TargetLocator switchTo() {
		return m_WebDriver.switchTo();
	}

}
