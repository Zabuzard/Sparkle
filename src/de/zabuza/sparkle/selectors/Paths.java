package de.zabuza.sparkle.selectors;

import de.zabuza.sparkle.freewar.EWorld;

/**
 * Utility class that provides paths.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class Paths {
	/**
	 * Sub-domain of the action world.
	 */
	public final static String ACTION_WORLD = "afsrv";
	/**
	 * Top-domain of the german worlds.
	 */
	public final static String DE_DOMAIN = "freewar.de";
	/**
	 * Constant used for separating domains.
	 */
	public final static String DOMAIN_SEPARATOR = ".";
	/**
	 * Top-domain of the english worlds.
	 */
	public final static String EN_DOMAIN = "freewar.com";
	/**
	 * Sub-domain of the english worlds.
	 */
	public final static String EN_WORLD = "world";
	/**
	 * Path to the actual game page that is used when playing the game.
	 */
	public final static String IN_GAME = "freewar/internal/friset.php";
	/**
	 * Path to the login page.
	 */
	public final static String LOGIN = "freewar";
	/**
	 * Web protocol used by the game.
	 */
	public final static String PROTOCOL = "http://";
	/**
	 * Sub-domain of the regular worlds.
	 */
	public final static String REGULAR_WORLD = "welt";
	/**
	 * Sub-domain of the role-play world.
	 */
	public final static String ROLEPLAY_WORLD = "rpsrv";
	/**
	 * Constant used for separating folders in URLs.
	 */
	public final static String URL_SEPARATOR = "/";

	/**
	 * Gets the full domain url corresponding to the given world. For example
	 * '<tt>http://welt1.freewar.de/</tt>'.
	 * 
	 * @param world
	 *            World to get full domain url for
	 * @return The full domain url corresponding to the given world
	 */
	public static String getFullWorldDomain(final EWorld world) {
		final StringBuilder sb = new StringBuilder();

		sb.append(PROTOCOL);
		sb.append(getHostDomain(world));
		sb.append(URL_SEPARATOR);

		return sb.toString();
	}

	/**
	 * Gets the host domain corresponding to the given world. For example
	 * '<tt>welt1.freewar.de</tt>'.
	 * 
	 * @param world
	 *            World to get host domain for
	 * @return The host domain corresponding to the given world
	 */
	public static String getHostDomain(final EWorld world) {
		final StringBuilder sb = new StringBuilder();

		if (world == EWorld.ACTION) {
			sb.append(ACTION_WORLD);
		} else if (world == EWorld.ROLEPLAY) {
			sb.append(ROLEPLAY_WORLD);
		} else if (world == EWorld.ONE_EN) {
			sb.append(EN_WORLD).append(1);
		} else {
			sb.append(REGULAR_WORLD);
			int worldNumber;
			if (world == EWorld.ONE) {
				worldNumber = 1;
			} else if (world == EWorld.TWO) {
				worldNumber = 2;
			} else if (world == EWorld.THREE) {
				worldNumber = 3;
			} else if (world == EWorld.FOUR) {
				worldNumber = 4;
			} else if (world == EWorld.FIVE) {
				worldNumber = 5;
			} else if (world == EWorld.SIX) {
				worldNumber = 6;
			} else if (world == EWorld.SEVEN) {
				worldNumber = 7;
			} else if (world == EWorld.EIGHT) {
				worldNumber = 8;
			} else if (world == EWorld.NINE) {
				worldNumber = 9;
			} else if (world == EWorld.TEN) {
				worldNumber = 10;
			} else if (world == EWorld.ELEVEN) {
				worldNumber = 11;
			} else if (world == EWorld.TWELVE) {
				worldNumber = 12;
			} else if (world == EWorld.THIRTEEN) {
				worldNumber = 13;
			} else {
				worldNumber = 14;
			}
			sb.append(worldNumber);
		}
		sb.append(DOMAIN_SEPARATOR);

		if (world != EWorld.ONE_EN) {
			sb.append(DE_DOMAIN);
		} else {
			sb.append(EN_DOMAIN);
		}

		return sb.toString();
	}

	/**
	 * Utility class. No implementation.
	 */
	private Paths() {

	}
}
