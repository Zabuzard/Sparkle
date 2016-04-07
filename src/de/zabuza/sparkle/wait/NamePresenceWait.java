package de.zabuza.sparkle.wait;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NamePresenceWait extends AConditionalWait<WebElement> {
	private final ExpectedCondition<WebElement> m_Condition;
	
	public NamePresenceWait(final WebDriver driver, final String name) {
		super(driver);
		m_Condition = ExpectedConditions.presenceOfElementLocated(By.name(name));
	}

	@Override
	protected ExpectedCondition<WebElement> getCondition() {
		return m_Condition;
	}
}
