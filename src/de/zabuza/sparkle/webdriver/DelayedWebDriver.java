package de.zabuza.sparkle.webdriver;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import de.zabuza.sparkle.webdriver.event.GetEvent;

/**
 * Wrapper for web driver objects to delayedly execute events.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class DelayedWebDriver implements WebDriver, IWrapsWebDriver {
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
		this.m_WebDriver = driver;
		this.m_DelayedEventExecutor = new DelayedEventExecutor();
		this.m_DelayedEventExecutor.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#close()
	 */
	@Override
	public void close() {
		this.m_WebDriver.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#findElement(org.openqa.selenium.By)
	 */
	@Override
	public WebElement findElement(final By by) {
		return new DelayedWebElement(this.m_WebDriver.findElement(by), this.m_DelayedEventExecutor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#findElements(org.openqa.selenium.By)
	 */
	@Override
	public List<WebElement> findElements(final By by) {
		final List<WebElement> elements = this.m_WebDriver.findElements(by);
		final List<WebElement> delayedElements = new LinkedList<>();
		for (final WebElement element : elements) {
			delayedElements.add(new DelayedWebElement(element, this.m_DelayedEventExecutor));
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
		this.m_DelayedEventExecutor.addEvent(new GetEvent(this.m_WebDriver, url));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#getCurrentUrl()
	 */
	@Override
	public String getCurrentUrl() {
		return this.m_WebDriver.getCurrentUrl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#getPageSource()
	 */
	@Override
	public String getPageSource() {
		return this.m_WebDriver.getPageSource();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.webdriver.IWrapsWebDriver#getRawDriver()
	 */
	@Override
	public WebDriver getRawDriver() {
		return this.m_WebDriver;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#getTitle()
	 */
	@Override
	public String getTitle() {
		return this.m_WebDriver.getTitle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#getWindowHandle()
	 */
	@Override
	public String getWindowHandle() {
		return this.m_WebDriver.getWindowHandle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#getWindowHandles()
	 */
	@Override
	public Set<String> getWindowHandles() {
		return this.m_WebDriver.getWindowHandles();
	}

	/**
	 * Returns whether the event queue of this driver is empty.
	 * 
	 * @return <tt>True</tt> if the event queue is empty, <tt>false</tt> if not.
	 */
	public boolean isEventQueueEmpty() {
		return this.m_DelayedEventExecutor.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#manage()
	 */
	@Override
	public Options manage() {
		return this.m_WebDriver.manage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#navigate()
	 */
	@Override
	public Navigation navigate() {
		return new DelayedNavigation(this.m_WebDriver.navigate(), this.m_DelayedEventExecutor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#quit()
	 */
	@Override
	public void quit() {
		this.m_DelayedEventExecutor.stopExecution();
		this.m_WebDriver.quit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#switchTo()
	 */
	@Override
	public TargetLocator switchTo() {
		return this.m_WebDriver.switchTo();
	}

}
