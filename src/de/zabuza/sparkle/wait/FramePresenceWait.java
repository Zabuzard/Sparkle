package de.zabuza.sparkle.wait;

import org.openqa.selenium.WebDriver;

public final class FramePresenceWait extends NamePresenceWait {
	public FramePresenceWait(final WebDriver driver, final String frameName) {
		super(driver, frameName);
	}
}
