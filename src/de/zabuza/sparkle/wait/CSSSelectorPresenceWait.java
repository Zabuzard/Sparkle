package de.zabuza.sparkle.wait;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CSSSelectorPresenceWait extends AConditionalWait<WebElement> {
	private final ExpectedCondition<WebElement> m_Condition;
	
	public CSSSelectorPresenceWait(final WebDriver driver, final String cssSelector) {
		super(driver);
		m_Condition = ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector));
	}

	@Override
	protected ExpectedCondition<WebElement> getCondition() {
		return m_Condition;
	}
}
