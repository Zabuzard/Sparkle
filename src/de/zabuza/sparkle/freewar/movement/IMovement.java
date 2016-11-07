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

	/**
	 * Tries to move the player to the given destination. This method is
	 * blocking, it returns either when the destination was reached or it
	 * aborted the movement.
	 * 
	 * @param xCoordinate
	 *            The x-coordinate of the destination
	 * @param yCoordinate
	 *            The y-coordinate of the destination
	 * @return <tt>True</tt> if the player has reached the given destination,
	 *         <tt>false</tt> if the destination was not reached and the method
	 *         aborted the movement
	 */
	public boolean moveTo(final int xCoordinate, final int yCoordinate);
}
