package de.zabuza.sparkle.freewar.chat;

/**
 * Types of chats of the MMORPG <tt>Freewar</tt> that are supported by
 * {@link de.zabuza.sparkle.IFreewarAPI IFreewarAPI}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 * 
 */
public enum EChatType {
	/**
	 * The clan chat channel which is used for messages only visible to players
	 * of the same clan.
	 */
	CLAN,
	/**
	 * The direct chat channel which is used for messages only visible at the
	 * current location.
	 */
	DIRECT,
	/**
	 * The global chat channel which is used for messages visible to players
	 * across all worlds.
	 */
	GLOBAL,
	/**
	 * The group chat channel which is used for messages only visible to players
	 * of the same group.
	 */
	GROUP,
	/**
	 * The info-say chat channel which is used for messages visible to players
	 * on the whole world, usually used for commercial messages.
	 */
	INFO,
	/**
	 * The scream chat channel which is used for messages visible to players on
	 * the whole world.
	 */
	SCREAM,
	/**
	 * The whisper chat channel which is used for messages visible to the
	 * receiving player.
	 */
	WHISPER,
	/**
	 * The world-say chat channel which is used for messages visible to players
	 * on the whole world, usually used for moderator messages.
	 */
	WORLDSAY
}
