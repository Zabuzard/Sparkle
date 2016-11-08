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
	 * Stops and rejects the current movement task if there is one. Otherwise
	 * the method has no effect.
	 */
	public void cancelMovementTask();

	/**
	 * Returns whether the player currently can move, i.e. if its speed
	 * currently allows him to move.
	 * 
	 * @return <tt>True</tt> if player currently can move, <tt>false</tt>
	 *         otherwhise
	 */
	public boolean canMove();

	/**
	 * Returns whether there is a movement task currently which gets executed.
	 * 
	 * @return <tt>True</tt> if there is such a movement task, <tt>false</tt> if
	 *         not
	 */
	public boolean hasMovementTask();

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
	 * Tries to move the player to the given destination. This method is not
	 * blocking, it starts the movement in another task and then returns.
	 * 
	 * @param xCoordinate
	 *            The x-coordinate of the destination
	 * @param yCoordinate
	 *            The y-coordinate of the destination
	 */
	public void moveTo(final int xCoordinate, final int yCoordinate);

	/**
	 * Tries to move the player into a given direction but waits for
	 * {@link #canMove()} to return <tt>true</tt>. This method is blocking, i.e.
	 * it does not return before waiting and finishing.
	 * 
	 * @param direction
	 *            Direction to move into
	 * @return <tt>True</tt> if the player was moved into the given direction,
	 *         <tt>false</tt> if that was not possible.
	 */
	public boolean moveWaiting(final EDirection direction);

	/**
	 * Returns whether the last executed movement task was successful or not.
	 * 
	 * @return <tt>True</tt> if the last executed movement task was successful,
	 *         i.e. the player reached the given destination. <tt>False</tt> if
	 *         the task was aborted by {@link #cancelMovementTask()} or the
	 *         method task aborted itself.
	 */
	public boolean wasTaskSuccessful();
}
