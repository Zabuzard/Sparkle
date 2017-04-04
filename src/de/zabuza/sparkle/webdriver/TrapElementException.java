package de.zabuza.sparkle.webdriver;

import org.openqa.selenium.WebElement;

/**
 * Exception which is thrown whenever a {@link WebElement} that seems to be a
 * bot trap is detected.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class TrapElementException extends RuntimeException {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new trap element exception.
	 */
	public TrapElementException() {
		super();
	}
}
