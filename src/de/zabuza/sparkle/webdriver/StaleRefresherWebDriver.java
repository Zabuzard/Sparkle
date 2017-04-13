package de.zabuza.sparkle.webdriver;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Wrapper for web driver objects to automatically handle staled elements that
 * would otherwise throw {@link StaleElementReferenceException}s when used.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class StaleRefresherWebDriver implements WebDriver, IWrapsWebDriver {

	/**
	 * Web driver to wrap for staled element handling.
	 */
	private final WebDriver m_WebDriver;

	/**
	 * Creates a new instance of this object with a given web driver.
	 * 
	 * @param driver
	 *            Driver to wrap for staled element handling
	 */
	public StaleRefresherWebDriver(final WebDriver driver) {
		m_WebDriver = driver;
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
		return new StaleRefresherWebElement(m_WebDriver.findElement(by), by, m_WebDriver);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#findElements(org.openqa.selenium.By)
	 */
	@Override
	public List<WebElement> findElements(final By by) {
		final List<WebElement> elements = m_WebDriver.findElements(by);
		final List<WebElement> staleRefresherElements = new LinkedList<WebElement>();
		int i = 0;
		for (final WebElement element : elements) {
			staleRefresherElements.add(new StaleRefresherWebElement(element, by, m_WebDriver, i));
			i++;
		}

		return staleRefresherElements;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#get(java.lang.String)
	 */
	@Override
	public void get(final String url) {
		m_WebDriver.get(url);
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
	 * @see de.zabuza.sparkle.webdriver.IWrapsWebDriver#getRawDriver()
	 */
	@Override
	public WebDriver getRawDriver() {
		return m_WebDriver;
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
		return m_WebDriver.navigate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#quit()
	 */
	@Override
	public void quit() {
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
