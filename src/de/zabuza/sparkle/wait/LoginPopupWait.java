package de.zabuza.sparkle.wait;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import de.zabuza.sparkle.wait.conditions.LoginPopupCondition;

public final class LoginPopupWait extends AConditionalWait<Boolean> {
	private final ExpectedCondition<Boolean> m_Condition;

	public LoginPopupWait(final WebDriver driver) {
		super(driver);
		m_Condition = new LoginPopupCondition();
	}

	@Override
	protected ExpectedCondition<Boolean> getCondition() {
		return m_Condition;
	}
}
