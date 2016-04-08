package de.zabuza.sparkle.webdriver;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import de.zabuza.sparkle.webdriver.event.GetEvent;

/**
 * Wrapper for web driver objects to delayedly executes events.
 * 
 * @author Zabuza
 *
 */
public final class DelayedWebDriver implements WebDriver {
	/**
	 * Object that delayedly executes added events.
	 */
	private final DelayedEventExecutor m_DelayedEventExecutor;
	/**
	 * Web driver to wrap for delayed event execution.
	 */
	private final WebDriver m_WebDriver;

	/**
	 * Creates a new instance of this object with a given web driver.
	 * 
	 * @param driver
	 *            Driver to wrap for delayed event execution
	 */
	public DelayedWebDriver(final WebDriver driver) {
		m_WebDriver = driver;
		m_DelayedEventExecutor = new DelayedEventExecutor();
		m_DelayedEventExecutor.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#close()
	 */
	@Override
	public void close() {
		m_WebDriver.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#findElement(org.openqa.selenium.By)
	 */
	@Override
	public WebElement findElement(final By by) {
		return new DelayedWebElement(m_WebDriver.findElement(by), m_DelayedEventExecutor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#findElements(org.openqa.selenium.By)
	 */
	@Override
	public List<WebElement> findElements(final By by) {
		List<WebElement> elements = m_WebDriver.findElements(by);
		List<WebElement> delayedElements = new LinkedList<WebElement>();
		for (WebElement element : elements) {
			delayedElements.add(new DelayedWebElement(element, m_DelayedEventExecutor));
		}

		return delayedElements;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#get(java.lang.String)
	 */
	@Override
	public void get(final String url) {
		m_DelayedEventExecutor.addEvent(new GetEvent(m_WebDriver, url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#getCurrentUrl()
	 */
	@Override
	public String getCurrentUrl() {
		return m_WebDriver.getCurrentUrl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#getPageSource()
	 */
	@Override
	public String getPageSource() {
		return m_WebDriver.getPageSource();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#getTitle()
	 */
	@Override
	public String getTitle() {
		return m_WebDriver.getTitle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#getWindowHandle()
	 */
	@Override
	public String getWindowHandle() {
		return m_WebDriver.getWindowHandle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#getWindowHandles()
	 */
	@Override
	public Set<String> getWindowHandles() {
		return m_WebDriver.getWindowHandles();
	}

	/**
	 * Returns whether the event queue of this driver is empty.
	 * 
	 * @return <tt>True</tt> if the event queue is empty, <tt>false</tt> if not.
	 */
	public boolean isEventQueueEmpty() {
		return m_DelayedEventExecutor.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#manage()
	 */
	@Override
	public Options manage() {
		return m_WebDriver.manage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#navigate()
	 */
	@Override
	public Navigation navigate() {
		return new DelayedNavigation(m_WebDriver.navigate(), m_DelayedEventExecutor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#quit()
	 */
	@Override
	public void quit() {
		m_DelayedEventExecutor.stopExecution();
		m_WebDriver.quit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#switchTo()
	 */
	@Override
	public TargetLocator switchTo() {
		return m_WebDriver.switchTo();
	}

}
