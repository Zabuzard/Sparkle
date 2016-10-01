package de.zabuza.sparkle.freewar.movement;

/**
 * Interface for movements of {@link de.zabuza.sparkle.freewar.IFreewarInstance
 * IFreewarInstance}s. Can be used to move the player through the world.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 * 
 */
public interface IMovement {
	/**
	 * Tries to move the player into a given direction.
	 * 
	 * @param direction
	 *            Direction to move into
	 * @return <tt>True</tt> if the player was moved into the given direction,
	 *         <tt>false</tt> if that was not possible.
	 */
	public boolean move(final EDirection direction);
}
