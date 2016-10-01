package de.zabuza.sparkle.wait;

import org.openqa.selenium.WebDriver;

import de.zabuza.sparkle.selectors.CSSSelectors;

/**
 * Class for waiting until the login form is present. Start waiting using the
 * {@link #waitUntilCondition()} method.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public class LoginFormWait extends CSSSelectorPresenceWait {
	/**
	 * Creates a new instance of this object using a given driver.
	 * 
	 * @param driver
	 *            Driver to use for waiting
	 */
	public LoginFormWait(final WebDriver driver) {
		super(driver, CSSSelectors.LOGIN_FORM_SUBMIT);
	}
}
