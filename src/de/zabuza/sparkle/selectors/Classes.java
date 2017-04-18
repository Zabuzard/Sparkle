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
	 * The attribute key for classes.
	 */
	private static final String ATTRIBUTE_CLASS_KEY = "class";
	/**
	 * The value which separates classes from the class attribute.
	 */
	private static final String ATTRIBUTE_CLASS_SEPARATOR = " ";

	/**
	 * Gets the class attribute of the given element.
	 * 
	 * @param element
	 *            The element to get the class attribute of
	 * @return The class attribute of the given element or <tt>null</tt> if the
	 *         element is <tt>null</tt>.
	 */
	public static String getClassAttribute(final WebElement element) {
		if (element == null) {
			return null;
		}
		return element.getAttribute(ATTRIBUTE_CLASS_KEY);
	}

	/**
	 * Returns if a given class attribute has a given class.
	 * 
	 * @param classAttribue
	 *            The class attribute in question
	 * @param className
	 *            The class name in question
	 * @return <tt>True</tt> if the given class attribute has the given class,
	 *         <tt>false</tt> if not.
	 */
	public static boolean hasClass(final String classAttribue, final String className) {
		if (classAttribue == null || classAttribue.isEmpty() || className == null || className.isEmpty()) {
			return false;
		}
		final String[] classes = classAttribue.split(ATTRIBUTE_CLASS_SEPARATOR);
		for (final String thatClassName : classes) {
			if (className.equals(thatClassName)) {
				return true;
			}
		}
		return false;
	}

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
		return hasClass(getClassAttribute(element), className);
	}

	/**
	 * Utility class. No implementation.
	 */
	private Classes() {

	}
}
