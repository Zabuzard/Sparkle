package de.zabuza.sparkle.wait.conditions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import de.zabuza.sparkle.webdriver.DelayedWebDriver;

/**
 * Condition that outputs whether the event queue of a given web driver is empty
 * or not.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class EventQueueEmptyCondition implements ExpectedCondition<Boolean> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	@Override
	public Boolean apply(final WebDriver driver) {
		if (driver instanceof DelayedWebDriver) {
			DelayedWebDriver driverAsDelayed = (DelayedWebDriver) driver;
			return driverAsDelayed.isEventQueueEmpty();
		} else {
			return true;
		}
	}
}
