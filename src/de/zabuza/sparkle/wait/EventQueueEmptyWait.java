package de.zabuza.sparkle.wait;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import de.zabuza.sparkle.wait.conditions.EventQueueEmptyCondition;

public final class EventQueueEmptyWait extends AConditionalWait<Boolean> {
	private final ExpectedCondition<Boolean> m_Condition;
	
	public EventQueueEmptyWait(final WebDriver driver) {
		super(driver);
		m_Condition = new EventQueueEmptyCondition();
	}

	@Override
	protected ExpectedCondition<Boolean> getCondition() {
		return m_Condition;
	}
}
