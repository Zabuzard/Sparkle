package de.zabuza.sparkle.wait.conditions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * Condition that outputs whether a given time period has passed. The timer
 * automatically starts by the first time {@link #apply(WebDriver)} is called.
 * 
 * @author Zabuza
 *
 */
public class TimedCondition implements ExpectedCondition<Boolean> {
	/**
	 * If the timer has started.
	 */
	private boolean m_ConditionActivated;
	/**
	 * A system time stamp from that point where the timer has started.
	 */
	private long m_TimeStampStarted;
	/**
	 * Time period to wait for to pass, in milliseconds.
	 */
	private long m_TimeToWait;

	/**
	 * Creates a new instance of this object with a given time to wait. The
	 * timer automatically starts by the first time {@link #apply(WebDriver)} is
	 * called.
	 * 
	 * @param timeToWait
	 *            Time period to wait for to pass, in milliseconds
	 */
	public TimedCondition(final long timeToWait) {
		m_TimeToWait = timeToWait;
		m_ConditionActivated = false;
		m_TimeStampStarted = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	@Override
	public Boolean apply(final WebDriver driver) {
		if (!m_ConditionActivated) {
			m_TimeStampStarted = System.currentTimeMillis();
			m_ConditionActivated = true;
		}
		return System.currentTimeMillis() - m_TimeStampStarted > m_TimeToWait;
	}

}
