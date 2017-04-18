package de.zabuza.sparkle.freewar;

import de.zabuza.sparkle.freewar.chat.IChat;
import de.zabuza.sparkle.freewar.frames.EFrame;
import de.zabuza.sparkle.freewar.frames.IFrameManager;
import de.zabuza.sparkle.freewar.inventory.IInventory;
import de.zabuza.sparkle.freewar.location.ILocation;
import de.zabuza.sparkle.freewar.movement.IMovement;
import de.zabuza.sparkle.freewar.player.IPlayer;

/**
 * Interface for instances of an logged in account from the MMORPG
 * <tt>Freewar</tt>. A instance can be used to control and play the game.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 * 
 */
public interface IFreewarInstance {
	/**
	 * Tries to click an anchor in the given frame whose content contains the
	 * given needle.
	 * 
	 * @param frame
	 *            The frame which contains the anchor
	 * @param needle
	 *            The needle that is contained by the content of the anchor to
	 *            click
	 * @return <tt>True</tt> if the anchor was found and could be clicked,
	 *         <tt>false</tt> if that was not possible or the anchor was not
	 *         found.
	 */
	public boolean clickAnchorByContent(final EFrame frame, final String needle);

	/**
	 * Gets the chat object of this instance. It can be used to interact with
	 * the chat.
	 * 
	 * @return The chat object of this instance
	 */
	public IChat getChat();

	/**
	 * Gets the frame manager object of this instance. It can be used to switch
	 * frames.
	 * 
	 * @return The frame manager object of this instance
	 */
	public IFrameManager getFrameManager();

	/**
	 * Gets the inventory object of this instance. It can be used to access the
	 * inventory of the player and interact with it.
	 * 
	 * @return The inventory object of this instance
	 */
	public IInventory getInventory();

	/**
	 * Gets the location object of this instance. It can be used to get various
	 * information about the current location. Location also provides access to
	 * NPCs at this location.
	 * 
	 * @return The location object of this instance
	 */
	public ILocation getLocation();

	/**
	 * Gets the movement object of this instance. It can be used to move the
	 * player through the world.
	 * 
	 * @return The movement object of this instance
	 */
	public IMovement getMovement();

	/**
	 * Gets the player object of this instance. It can be used to get various
	 * properties of the player like life-points.
	 * 
	 * @return The player object of this instance
	 */
	public IPlayer getPlayer();

	/**
	 * Returns if the instance cares of not being automatically logged out by
	 * <tt>Freewar</tt> due to absence.
	 * 
	 * @return If the instance cares of not being automatically logged out
	 */
	public boolean isStayLoggedIn();

	/**
	 * Refreshes the current page. By that it is ensured that later called
	 * information is up to date.
	 */
	public void refresh();

	/**
	 * Sets if the instance should care of not being automatically logged out by
	 * <tt>Freewar</tt> due to absence.
	 * 
	 * @param stayLoggedIn
	 *            If true the instance cares of not being automatically logged
	 *            out. If false it can be logged out by the game due to absence.
	 */
	public void setStayLoggedIn(final boolean stayLoggedIn);

	/**
	 * Shutdowns the instance. This does not necessarily logout from the
	 * account. After shutdown an instance is invalid and should not be used
	 * anymore.
	 * 
	 * @param doQuitDriver
	 *            <tt>True</tt> if the browser driver of this instance should be
	 *            quit at shutdown, <tt>false</tt> if not. If not quit the
	 *            browser can still be accessed by the user even after API
	 *            shutdown.
	 */
	public void shutdown(final boolean doQuitDriver);
}
