package de.zabuza.sparkle.wait;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import de.zabuza.sparkle.wait.conditions.LoginPopupCondition;

/**
 * Class for waiting until the login pop-up is present. Start waiting using the
 * {@link #waitUntilCondition()} method.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class LoginPopupWait extends AConditionalWait<Boolean> {
	/**
	 * Condition to wait for.
	 */
	private final ExpectedCondition<Boolean> mCondition;

	/**
	 * Creates a new instance of this object using a given web driver.
	 * 
	 * @param driver
	 *            Driver to use for waiting.
	 */
	public LoginPopupWait(final WebDriver driver) {
		super(driver);
		this.mCondition = new LoginPopupCondition();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.wait.AConditionalWait#getCondition()
	 */
	@Override
	protected ExpectedCondition<Boolean> getCondition() {
		return this.mCondition;
	}
}
