package de.zabuza.sparkle.freewar.player;

/**
 * Interface for the player of {@link de.zabuza.sparkle.freewar.IFreewarInstance
 * IFreewarInstance}s. Can be used to access various properties of the player.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 * 
 */
public interface IPlayer {
	/**
	 * Constant for a non valid value.
	 */
	public final static int NO_VALUE = -1;
	/**
	 * Constant for a non valid weapon.
	 */
	public final static String NO_WEAPON = "keine";

	/**
	 * Tries to activate the special skill of the player. The method only
	 * supports special skills of races that do not need any further arguments.
	 * 
	 * @return <tt>True</tt> if the special skill was activated, <tt>false</tt>
	 *         if it could not be activated.
	 */
	public boolean activateSpecialSkill();

	/**
	 * Gets the amount of attack points the player has.
	 * 
	 * @return The amount of attack points the player has or {@link #NO_VALUE}
	 *         if unknown.
	 */
	public int getAttackPoints();

	/**
	 * Gets the name of the attack weapon the player currently holds.
	 * 
	 * @return The name of the attack weapon the player currently holds or
	 *         {@link #NO_WEAPON} if unknown or he holds no attack weapon.
	 */
	public String getAttackWeapon();

	/**
	 * Gets the amount of defense points the player has.
	 * 
	 * @return The amount of defense points the player has or {@link #NO_VALUE}
	 *         if unknown.
	 */
	public int getDefensePoints();

	/**
	 * Gets the name of the defense weapon the player currently holds.
	 * 
	 * @return The name of the defense weapon the player currently holds or
	 *         {@link #NO_WEAPON} if unknown or he holds no defense weapon.
	 */
	public String getDefenseWeapon();

	/**
	 * Gets the amount of experience points the player has.
	 * 
	 * @return The amount of experience points the player has or
	 *         {@link #NO_VALUE} if unknown.
	 */
	public int getExperiencePoints();

	/**
	 * Gets the amount of gold the player has.
	 * 
	 * @return The amount of gold the player has or {@link #NO_VALUE} if
	 *         unknown.
	 */
	public int getGold();

	/**
	 * Gets the amount of gold the player has.
	 * 
	 * @return The amount of gold the player has or {@link #NO_VALUE} if
	 *         unknown.
	 */
	public int getIntelligence();

	/**
	 * Gets the amount of life points the player has.
	 * 
	 * @return The amount of life points the player has or {@link #NO_VALUE} if
	 *         unknown.
	 */
	public int getLifePoints();

	/**
	 * Gets the maximal amount of life points the player has, that are the life
	 * points if the player is fully healed.
	 * 
	 * @return The maximal amount of life points the player has or
	 *         {@link #NO_VALUE} if unknown.
	 */
	public int getMaxLifePoints();

	/**
	 * Gets the name of the player.
	 * 
	 * @return The name of the player or <tt>null</tt> if that is not possible.
	 */
	public String getName();

	/**
	 * Gets the amount of speed the player has.
	 * 
	 * @return The amount of speed the player has or {@link #NO_VALUE} if
	 *         unknown.
	 */
	public int getSpeed();

	/**
	 * Gets all statuses the player currently has.
	 * 
	 * @return All statuses the player currently has or an empty string if he
	 *         has no status.
	 */
	public String getStatus();
}
