package de.zabuza.sparkle.wait.conditions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class TimedCondition implements ExpectedCondition<Boolean> {
	private long m_TimeToWait;
	private boolean m_ConditionActivated;
	private long m_TimeStampStarted;
	
	public TimedCondition(final long timeToWait) {
		m_TimeToWait = timeToWait;
		m_ConditionActivated = false;
		m_TimeStampStarted = 0;
	}

	@Override
	public Boolean apply(final WebDriver driver) {
		if (!m_ConditionActivated) {
			m_TimeStampStarted = System.currentTimeMillis();
			m_ConditionActivated = true;
		}
		return System.currentTimeMillis() - m_TimeStampStarted > m_TimeToWait;
	}

}
