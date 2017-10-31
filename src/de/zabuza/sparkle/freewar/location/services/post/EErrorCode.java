package de.zabuza.sparkle.freewar.location.services.post;

/**
 * Enumeration of post office error codes.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public enum EErrorCode {
	/**
	 * Player has less gold than the letter service costs.
	 */
	NO_GOLD,
	/**
	 * The receiver of the letter is inexistent.
	 */
	RECEIVER_INEXISTENT,
	/**
	 * The letter service is unavailable. For example if the player is at the wrong
	 * position or the location was disabled by spells.
	 */
	SERVICE_UNAVAILABLE
}
