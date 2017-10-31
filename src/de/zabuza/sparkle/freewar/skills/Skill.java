package de.zabuza.sparkle.freewar.skills;

/**
 * Class that represents a specific skill of an user with its current level.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class Skill {
	/**
	 * Constant for a training end time that indicates that the given skill is not
	 * actively trained.
	 */
	private static final long NO_TRAINING_END_TIME = -1L;
	/**
	 * The current level of the skill.
	 */
	private final int mLevel;
	/**
	 * The maximal level of the skill.
	 */
	private final int mMaximalLevel;
	/**
	 * The name of the skill.
	 */
	private final String mName;
	/**
	 * The timestamp of when the training of this skill ends or a negative value if
	 * it is not actively trained.
	 */
	private final long mTrainingEndTime;

	/**
	 * Creates a new skill with given name and current level.
	 * 
	 * @param name
	 *            The name of the skill
	 * @param level
	 *            The current level of the skill
	 * @param maximalLevel
	 *            The maximal level of the skill
	 */
	public Skill(final String name, final int level, final int maximalLevel) {
		this(name, level, maximalLevel, NO_TRAINING_END_TIME);
	}

	/**
	 * Creates a new skill that is actively trained with given name, current level
	 * and training end timestamp.
	 * 
	 * @param name
	 *            The name of the skill
	 * @param level
	 *            The current level of the skill
	 * @param maximalLevel
	 *            The maximal level of the skill
	 * @param trainingEndTime
	 *            The timestamp of when the training of this skill ends or a
	 *            negative value if it is not actively trained
	 */
	public Skill(final String name, final int level, final int maximalLevel, final long trainingEndTime) {
		this.mName = name;
		this.mLevel = level;
		this.mMaximalLevel = maximalLevel;
		this.mTrainingEndTime = trainingEndTime;
	}

	/**
	 * Gets the current level of the skill.
	 * 
	 * @return The current level of the skill
	 */
	public int getLevel() {
		return this.mLevel;
	}

	/**
	 * Gets the maximal level of the skill.
	 * 
	 * @return The maximal level of the skill
	 */
	public int getMaximalLevel() {
		return this.mMaximalLevel;
	}

	/**
	 * Gets the name of the skill.
	 * 
	 * @return The name of the skill
	 */
	public String getName() {
		return this.mName;
	}

	/**
	 * Gets the timestamp of when the training of this skill ends or a negative
	 * value if it is not actively trained.
	 * 
	 * @return The timestamp of when the training of this skill ends or a negative
	 *         value if it is not actively trained
	 */
	public long getTrainingEndTime() {
		return this.mTrainingEndTime;
	}

	/**
	 * Whether the skill is currently trained or not.
	 * 
	 * @return <tt>True</tt> if the skill is currently trained, <tt>false</tt>
	 *         otherwise
	 */
	public boolean isCurrentlyTrained() {
		return this.mTrainingEndTime != NO_TRAINING_END_TIME;
	}

	/**
	 * Whether the level of the skill is maximized or not.
	 * 
	 * @return <tt>True</tt> if the level of the skill is maximized, <tt>false</tt>
	 *         otherwise
	 */
	public boolean isLevelMaximized() {
		return this.mLevel >= this.mMaximalLevel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Skill [name=");
		builder.append(this.mName);
		builder.append(", level=");
		builder.append(this.mLevel);
		builder.append(", maximalLevel=");
		builder.append(this.mMaximalLevel);
		builder.append(", trainingEndTime=");
		builder.append(this.mTrainingEndTime);
		builder.append("]");
		return builder.toString();
	}
}
