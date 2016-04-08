package de.zabuza.sparkle.wait;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AConditionalWait<V> {
	protected static final int STANDARD_TIMEOUT = 6;

	private final WebDriverWait m_Wait;

	public AConditionalWait(final WebDriver driver) {
		this(driver, STANDARD_TIMEOUT);
	}

	public AConditionalWait(final WebDriver driver, final long timeOutInSeconds) {
		m_Wait = new WebDriverWait(driver, timeOutInSeconds);
	}

	public V waitUntilCondition() {
		return m_Wait.until(getCondition());
	}

	protected abstract ExpectedCondition<V> getCondition();
}
