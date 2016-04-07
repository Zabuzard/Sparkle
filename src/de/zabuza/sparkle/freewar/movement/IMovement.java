package de.zabuza.sparkle.freewar.movement;

import java.awt.Point;

/**
 * Interface for movements of {@link de.zabuza.sparkle.freewar.IFreewarInstance
 * IFreewarInstance}s. Can be used to move the player through the world.
 * 
 * @author Zabuza
 * 
 */
public interface IMovement {
	/**
	 * Gets the position of the current location in coordinates.
	 * 
	 * @return The position of the current location in coordinates
	 */
	public Point getPosition();

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
