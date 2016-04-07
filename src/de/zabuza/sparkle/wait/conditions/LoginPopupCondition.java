package de.zabuza.sparkle.wait.conditions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public final class LoginPopupCondition implements ExpectedCondition<Boolean> {

	@Override
	public Boolean apply(final WebDriver driver) {
		return driver.getWindowHandles().size() > 1;
	}
	
}
