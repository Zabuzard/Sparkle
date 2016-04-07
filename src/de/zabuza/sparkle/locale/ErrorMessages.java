package de.zabuza.sparkle.locale;

/**
 * Utility class that provides error messages for the API.
 * 
 * @author Zabuza
 * 
 */
public final class ErrorMessages {
	/**
	 * Thrown when a {@link de.zabuza.sparkle.webdriver.DelayedEventExecutor
	 * DelayedEventExecutor} gets interrupted while sleeping.
	 */
	public static final String DELAYED_EVENT_EXECUTOR_INTERRUPTED = "The delayed event executor was interrupted while sleeping.";
	/**
	 * Thrown when a {@link de.zabuza.sparkle.freewar.StayLoggedInService
	 * StayLoggedInService} gets interrupted while sleeping.
	 */
	public static final String STAY_LOGGED_IN_SERVICE_INTERRUPTED = "The stay logged in service was interrupted while sleeping.";

	/**
	 * Utility class. No implementation.
	 */
	private ErrorMessages() {

	}
}
