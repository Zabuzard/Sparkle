package de.zabuza.sparkle.freewar.skills;

import java.util.Map;

import de.zabuza.sparkle.freewar.player.Player;

/**
 * Interface for objects that manage the skills of a {@link Player}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 * 
 */
public interface ISkillManager {
	/**
	 * Aborts the training of the skill that is currently active.
	 * 
	 * @return <tt>True</tt> if the training was aborted or <tt>false</tt> if not,
	 *         for example if there was no training active
	 */
	public boolean abortTrainingOfSkill();

	/**
	 * Tries to activate the special skill of the player. The method only supports
	 * special skills of races that do not need any further arguments.
	 * 
	 * @return <tt>True</tt> if the special skill was activated, <tt>false</tt> if
	 *         it could not be activated.
	 */
	public boolean activateSpecialSkill();

	/**
	 * Closes the skill menu if not already closed. It ensures that previous queued
	 * events are processed before closing the menu.
	 */
	public void closeSkillMenu();

	/**
	 * Gets the skill that is currently trained by the player.
	 * 
	 * @return The skill that is currently trained by the player or <tt>null</tt> if
	 *         there is no.
	 */
	public Skill getCurrentlyTrainedSkill();

	/**
	 * Gets all skills the player has accessible by their names.
	 * 
	 * @return All skills the player has accessible by their names
	 */
	public Map<String, Skill> getSkills();

	/**
	 * Whether the skill menu is opened or not.
	 * 
	 * @return <tt>True</tt> if the skill menu is opened, <tt>false</tt> otherwise
	 */
	public boolean isSkillMenuOpened();

	/**
	 * Opens the skill menu and waits until it is loaded if not already opened. It
	 * ensures that previous queued events are processed before opening the menu.
	 */
	public void openSkillMenu();

	/**
	 * Starts the training of the given skill.
	 * 
	 * @param skillName
	 *            The name of the skill to train
	 * @return <tt>True</tt> if the training was started or <tt>false</tt> if not,
	 *         for example if the player does not know a skill with the given name
	 *         or if there is already a training active
	 */
	public boolean startTrainingOfSkill(final String skillName);
}
