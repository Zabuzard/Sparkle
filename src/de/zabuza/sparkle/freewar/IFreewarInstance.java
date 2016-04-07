package de.zabuza.sparkle.freewar;

import org.openqa.selenium.WebDriver;

import de.zabuza.sparkle.freewar.inventory.IInventory;
import de.zabuza.sparkle.freewar.location.ILocation;
import de.zabuza.sparkle.freewar.movement.IMovement;
import de.zabuza.sparkle.freewar.player.IPlayer;

/**
 * Interface for instances of an logged in account from the MMORPG
 * <tt>Freewar</tt>. A instance can be used to control and play the game.
 * 
 * @author Zabuza
 * 
 */
public interface IFreewarInstance {
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
	 * Gets the web driver used by this instance.
	 * 
	 * @return The web driver used by this instance
	 */
	public WebDriver getWebDriver();

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
	 */
	public void shutdown();
}
