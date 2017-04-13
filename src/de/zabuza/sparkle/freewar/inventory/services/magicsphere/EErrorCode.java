package de.zabuza.sparkle.freewar.inventory.services.magicsphere;

/**
 * Enumeration of magic sphere error codes.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public enum EErrorCode {
	/**
	 * Player does have the requested item but it could not be activated.
	 */
	COULD_NOT_ACTIVATE,
	/**
	 * Player does not have the requested item.
	 */
	NO_ITEM,
	/**
	 * The service was not registered for the requested task, try another.
	 */
	NOT_REGISTERED
}
