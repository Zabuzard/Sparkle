package de.zabuza.sparkle.selectors;

/**
 * Utility class that provides regex patterns.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class Patterns {
	/**
	 * Regex pattern for an integer.
	 */
	public final static String INTEGER = "-?\\d+";
	/**
	 * Regex pattern for extracting the maximal amount of life points.
	 */
	public final static String PLAYER_MAX_LIFE_POINTS = "\\(\\d+/(\\d+)\\)";
	/**
	 * Coordinate pattern for the wiki content. The first group holds the x
	 * coordinate, the second group holds the y coordinate.
	 */
	public final static String WIKI_CONTENT_COORDINATES = "(-?\\d+),(-?\\d+);?";

	/**
	 * Utility class. No implementation.
	 */
	private Patterns() {

	}
}
