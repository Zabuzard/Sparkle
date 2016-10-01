package de.zabuza.sparkle.wait;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import de.zabuza.sparkle.wait.conditions.EventQueueEmptyCondition;

/**
 * Class for waiting until the event queue of a given web driver is empty. Start
 * waiting using the {@link #waitUntilCondition()} method.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class EventQueueEmptyWait extends AConditionalWait<Boolean> {
	/**
	 * Condition to wait for.
	 */
	private final ExpectedCondition<Boolean> m_Condition;

	/**
	 * Creates a new instance of this object using a given web driver.
	 * 
	 * @param driver
	 *            Driver to wait for its event queue to be empty
	 */
	public EventQueueEmptyWait(final WebDriver driver) {
		super(driver);
		m_Condition = new EventQueueEmptyCondition();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.wait.AConditionalWait#getCondition()
	 */
	@Override
	protected ExpectedCondition<Boolean> getCondition() {
		return m_Condition;
	}
}
