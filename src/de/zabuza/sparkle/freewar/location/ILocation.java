package de.zabuza.sparkle.freewar.location;

import java.awt.Point;

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
	 * @return <tt>True</tt> if the given NPC was chased, <tt>false</tt> if it
	 *         could not be chased. Later can occur for example when the NPC is
	 *         not present on this location or when the player has to few
	 *         experience points for this option.
	 */
	public boolean chaseNPC(final String npcName);

	/**
	 * Tries to attack a given NPC using the fast attack option.
	 * 
	 * @param npcName
	 *            The Name of the NPC
	 * @return <tt>True</tt> if the given NPC was attacked, <tt>false</tt> if it
	 *         could not be attacked. Later can occur for example when the NPC
	 *         is not present on this location or when the player has to few
	 *         experience points for this option.
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
	 * If there is a given NPC on the location.
	 * 
	 * @param npcName
	 *            The name of the NPC in question
	 * @return <tt>True</tt> if the given NPC is on that location,
	 *         <tt>false</tt> if not.
	 */
	public boolean hasNPC(final String npcName);

	/**
	 * Tries to attack a given NPC using the regular attack option.
	 * 
	 * @param npcName
	 *            The Name of the NPC
	 * @return <tt>True</tt> if the given NPC was attacked, <tt>false</tt> if it
	 *         could not be attacked. Later can occur for example when the NPC
	 *         is not present on this location.
	 */
	public boolean regularAttackNPC(final String npcName);

	/**
	 * Tries to attack a given NPC using the single attack option.
	 * 
	 * @param npcName
	 *            The Name of the NPC
	 * @return <tt>True</tt> if the given NPC was attacked, <tt>false</tt> if it
	 *         could not be attacked. Later can occur for example when the NPC
	 *         is not present on this location.
	 */
	public boolean singleAttackNPC(final String npcName);
}
