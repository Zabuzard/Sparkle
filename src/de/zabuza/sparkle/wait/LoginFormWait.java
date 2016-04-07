package de.zabuza.sparkle.wait;

import org.openqa.selenium.WebDriver;

import de.zabuza.sparkle.selectors.CSSSelectors;

public class LoginFormWait extends CSSSelectorPresenceWait {	
	public LoginFormWait(final WebDriver driver) {
		super(driver, CSSSelectors.LOGIN_FORM_SUBMIT);
	}
}
