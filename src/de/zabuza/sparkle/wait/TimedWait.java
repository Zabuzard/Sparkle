package de.zabuza.sparkle.wait;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import de.zabuza.sparkle.wait.conditions.TimedCondition;

public class TimedWait extends AConditionalWait<Boolean> {
	private static final int TIMEOUT_OFFSET = 2;
	private static final int SECONDS_TO_MILLIS = 1000;
	
	private final ExpectedCondition<Boolean> m_Condition;
	
	public TimedWait(final WebDriver driver, final long timeToWait) {
		super(driver, (timeToWait / SECONDS_TO_MILLIS) + TIMEOUT_OFFSET);
		m_Condition = new TimedCondition(timeToWait);
	}

	@Override
	protected ExpectedCondition<Boolean> getCondition() {
		return m_Condition;
	}
}
