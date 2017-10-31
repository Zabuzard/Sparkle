package de.zabuza.sparkle.freewar.location;

import java.awt.Point;
import java.util.Optional;

import org.openqa.selenium.WebDriver;

import de.zabuza.sparkle.freewar.IFreewarInstance;
import de.zabuza.sparkle.freewar.frames.IFrameManager;
import de.zabuza.sparkle.freewar.location.services.ILocationService;

/**
 * Interface for locations of {@link de.zabuza.sparkle.freewar.IFreewarInstance
 * IFreewarInstance}s. Can be used to access the current location of a player.
 * Provides access to NPCs on the location.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 * 
 */
public interface ILocation {
	/**
	 * Constant for a non valid value.
	 */
	public final static int NO_VALUE = -1;

	/**
	 * Tries to chase a given NPC using the chase option.
	 * 
	 * @param npcName
	 *            The Name of the NPC
	 * @return <tt>True</tt> if the given NPC was chased, <tt>false</tt> if it could
	 *         not be chased. Later can occur for example when the NPC is not
	 *         present on this location or when the player has to few experience
	 *         points for this option.
	 */
	public boolean chaseNPC(final String npcName);

	/**
	 * Tries to attack a given NPC using the fast attack option.
	 * 
	 * @param npcName
	 *            The Name of the NPC
	 * @return <tt>True</tt> if the given NPC was attacked, <tt>false</tt> if it
	 *         could not be attacked. Later can occur for example when the NPC is
	 *         not present on this location or when the player has to few experience
	 *         points for this option.
	 */
	public boolean fastAttackNPC(final String npcName);

	/**
	 * Gets the NPCs on the location.
	 *
	 * @return The NPCs on the location.
	 */
	public String[] getNPCs();

	/**
	 * Gets the position of the current location in coordinates.
	 * 
	 * @return The position of the current location in coordinates
	 */
	public Point getPosition();

	/**
	 * Gets the {@link ILocationService} that is registered for the current
	 * location. Services can be registered using
	 * {@link #registerService(Point, Class)}.
	 * 
	 * @return If present, the {@link ILocationService} that is registered for the
	 *         current location. If not, there is no service registered.
	 * @throws IllegalStateException
	 *             If the registered service is non valid and does not declare an
	 *             appropriate constructor as defined in
	 *             {@link #registerService(Point, Class)}.
	 */
	public Optional<ILocationService> getService() throws IllegalStateException;

	/**
	 * If there is a given NPC on the location.
	 * 
	 * @param npcName
	 *            The name of the NPC in question
	 * @return <tt>True</tt> if the given NPC is on that location, <tt>false</tt> if
	 *         not.
	 */
	public boolean hasNPC(final String npcName);

	/**
	 * If there is a {@link ILocationService} registered at the current location.
	 * Services can be registered using {@link #registerService(Point, Class)}.
	 * 
	 * @return <tt>True</tt> if the current location has a registered
	 *         {@link ILocationService}, <tt>false</tt> if not.
	 */
	public boolean hasService();

	/**
	 * Registers the given location service for the given location. It can be
	 * accessed by {@link #getService()} if {@link #getPosition()} returns the given
	 * location. The framework will automatically build instances of services on
	 * demand. For this each implementing class must have a public constructor with
	 * arguments {@link Point}, {@link IFreewarInstance}, {@link WebDriver} and
	 * {@link IFrameManager}. The framework will use this constructor and provide
	 * all those parameters to the class. If there was already a service registered
	 * to the given location it will be overridden.
	 * 
	 * @param location
	 *            The position of the location to register the service for
	 * @param service
	 *            The class of the service to register for the given location
	 * @throws IllegalArgumentException
	 *             If the service to register is non valid and does not declare an
	 *             appropriate constructor
	 */
	public void registerService(final Point location, final Class<? extends ILocationService> service)
			throws IllegalArgumentException;

	/**
	 * Tries to attack a given NPC using the regular attack option.
	 * 
	 * @param npcName
	 *            The Name of the NPC
	 * @return <tt>True</tt> if the given NPC was attacked, <tt>false</tt> if it
	 *         could not be attacked. Later can occur for example when the NPC is
	 *         not present on this location.
	 */
	public boolean regularAttackNPC(final String npcName);

	/**
	 * Tries to attack a given NPC using the single attack option.
	 * 
	 * @param npcName
	 *            The Name of the NPC
	 * @return <tt>True</tt> if the given NPC was attacked, <tt>false</tt> if it
	 *         could not be attacked. Later can occur for example when the NPC is
	 *         not present on this location.
	 */
	public boolean singleAttackNPC(final String npcName);
}
