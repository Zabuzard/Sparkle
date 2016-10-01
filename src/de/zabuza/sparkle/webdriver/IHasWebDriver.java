package de.zabuza.sparkle.webdriver;

import org.openqa.selenium.WebDriver;

/**
 * Interface for objects that have a {@link WebDriver}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public interface IHasWebDriver {
	/**
	 * Gets the web driver of this object.
	 * 
	 * @return The web driver to get
	 */
	public WebDriver getWebDriver();
}
