package de.zabuza.sparkle.selectors;

import org.openqa.selenium.WebElement;

/**
 * Utility class that provides class selectors.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class Classes {

	/**
	 * Class for clan messages.
	 */
	public static final String CHAT_MESSAGE_CLAN = "chattextclan";
	/**
	 * Class for direct messages.
	 */
	public static final String CHAT_MESSAGE_DIRECT = "chattext";
	/**
	 * Class for global messages.
	 */
	public static final String CHAT_MESSAGE_GLOBAL = "chattextglobal";
	/**
	 * Class for group messages.
	 */
	public static final String CHAT_MESSAGE_GROUP = "chattextgroup";
	/**
	 * Class for info messages.
	 */
	public static final String CHAT_MESSAGE_INFO = "chattextinfo";
	/**
	 * Class for scream messages.
	 */
	public static final String CHAT_MESSAGE_SCREAM = "chattextscream";
	/**
	 * Class for whisper messages.
	 */
	public static final String CHAT_MESSAGE_WHISPER = "chattextwhisper";
	/**
	 * Class for world-say messages.
	 */
	public static final String CHAT_MESSAGE_WORLDSAY = "worldsay";
	/**
	 * Class for not accessible fields.
	 */
	public static final String MAP_FIELD_NOCANGO = "nocango";

	/**
	 * Returns if a given element has a given class.
	 * 
	 * @param element
	 *            The element in question
	 * @param className
	 *            The class name in question
	 * @return <tt>True</tt> if the given element has the given class,
	 *         <tt>false</tt> if not.
	 */
	public static boolean hasClass(final WebElement element, final String className) {
		if (element == null || className == null || className.isEmpty()) {
			return false;
		}
		final String attributeContent = element.getAttribute("class");
		if (attributeContent == null || attributeContent.isEmpty()) {
			return false;
		}
		final String[] classes = attributeContent.split(" ");
		for (final String thatClassName : classes) {
			if (className.equals(thatClassName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Utility class. No implementation.
	 */
	private Classes() {

	}
}
