package de.zabuza.sparkle.wait.conditions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import de.zabuza.sparkle.webdriver.DelayedWebDriver;

public final class EventQueueEmptyCondition implements ExpectedCondition<Boolean> {

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
