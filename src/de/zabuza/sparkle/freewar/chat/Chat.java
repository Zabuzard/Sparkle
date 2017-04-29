package de.zabuza.sparkle.freewar.chat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import de.zabuza.sparkle.freewar.frames.EFrame;
import de.zabuza.sparkle.freewar.frames.IFrameManager;
import de.zabuza.sparkle.locale.ErrorMessages;
import de.zabuza.sparkle.selectors.CSSSelectors;
import de.zabuza.sparkle.selectors.Classes;
import de.zabuza.sparkle.selectors.Patterns;

/**
 * Chat of {@link de.zabuza.sparkle.freewar.IFreewarInstance IFreewarInstance}s.
 * Can be used to interact with the chat, i.e. retrieving and sending messages.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class Chat implements IChat {

	/**
	 * Constant for an empty text.
	 */
	private final static String EMPTY_TEXT = "";
	/**
	 * The web driver used by this chat.
	 */
	private final WebDriver m_Driver;
	/**
	 * Manager to use for switching frames.
	 */
	private final IFrameManager m_FrameManager;

	/**
	 * The name of the user of this instance
	 */
	private final String m_User;

	/**
	 * Creates a new chat object that uses a given web driver.
	 * 
	 * @param driver
	 *            Web driver to use
	 * @param frameManager
	 *            Manager to use for switching frames
	 * @param user
	 *            The name of the user of this instance
	 */
	public Chat(final WebDriver driver, final IFrameManager frameManager, final String user) {
		this.m_Driver = driver;
		this.m_FrameManager = frameManager;
		this.m_User = user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.chat.IChat#focusChatInput()
	 */
	@Override
	public boolean focusChatInput() {
		switchToChatFormFrame();

		// Get focus by sending an empty text
		final List<WebElement> inputElements = this.m_Driver
				.findElements(By.cssSelector(CSSSelectors.CHAT_FORM_MESSAGE_INPUT));
		if (inputElements.isEmpty()) {
			return false;
		}
		final WebElement inputElement = inputElements.iterator().next();
		inputElement.sendKeys(EMPTY_TEXT);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.chat.IChat#getChat()
	 */
	@Override
	public ArrayList<Message> getMessages() {
		return getMessagesBySelector(CSSSelectors.CHAT_TEXT_MESSAGE_ANCHOR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.sparkle.freewar.chat.IChat#getChat(de.zabuza.sparkle.freewar.
	 * chat.EChatType)
	 */
	@Override
	public ArrayList<Message> getMessages(final EChatType chatType) {
		final String className;
		if (chatType == EChatType.DIRECT) {
			className = Classes.CHAT_MESSAGE_DIRECT;
		} else if (chatType == EChatType.CLAN) {
			className = Classes.CHAT_MESSAGE_CLAN;
		} else if (chatType == EChatType.GROUP) {
			className = Classes.CHAT_MESSAGE_GROUP;
		} else if (chatType == EChatType.GLOBAL) {
			className = Classes.CHAT_MESSAGE_GLOBAL;
		} else if (chatType == EChatType.SCREAM) {
			className = Classes.CHAT_MESSAGE_SCREAM;
		} else if (chatType == EChatType.INFO) {
			className = Classes.CHAT_MESSAGE_INFO;
		} else if (chatType == EChatType.WHISPER) {
			className = Classes.CHAT_MESSAGE_WHISPER;
		} else if (chatType == EChatType.WORLDSAY) {
			className = Classes.CHAT_MESSAGE_WORLDSAY;
		} else {
			throw new AssertionError();
		}

		final String selector = CSSSelectors.CHAT_TEXT_MESSAGE_ANCHOR + CSSSelectors.SELECTOR_CLASS + className;

		return getMessagesBySelector(selector);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.chat.IChat#submitMessage(java.lang.String,
	 * de.zabuza.sparkle.freewar.chat.EChatType)
	 */
	@Override
	public boolean submitMessage(final String message, final EChatType chatType) {
		switchToChatFormFrame();

		// Enter the message into the input field
		final List<WebElement> inputElements = this.m_Driver
				.findElements(By.cssSelector(CSSSelectors.CHAT_FORM_MESSAGE_INPUT));
		if (inputElements.isEmpty()) {
			return false;
		}
		final WebElement inputElement = inputElements.iterator().next();
		inputElement.sendKeys(message);

		// Click the corresponding submit button
		final String submitSelector;
		if (chatType == EChatType.CLAN) {
			submitSelector = CSSSelectors.CHAT_FORM_SUBMIT_CLAN;
		} else if (chatType == EChatType.DIRECT) {
			submitSelector = CSSSelectors.CHAT_FORM_SUBMIT_DIRECT;
		} else if (chatType == EChatType.GLOBAL) {
			submitSelector = CSSSelectors.CHAT_FORM_SUBMIT_GLOBAL;
		} else if (chatType == EChatType.GROUP) {
			submitSelector = CSSSelectors.CHAT_FORM_SUBMIT_GROUP;
		} else if (chatType == EChatType.SCREAM) {
			submitSelector = CSSSelectors.CHAT_FORM_SUBMIT_SCREAM;
		} else if (chatType == EChatType.WHISPER) {
			submitSelector = CSSSelectors.CHAT_FORM_SUBMIT_WHISPER;
		} else {
			throw new AssertionError();
		}

		final List<WebElement> submitElements = this.m_Driver.findElements(By.cssSelector(submitSelector));
		if (submitElements.isEmpty()) {
			return false;
		}
		final WebElement submitElement = submitElements.iterator().next();

		// Click the button
		submitElement.click();
		return true;
	}

	/**
	 * Extracts a message object of the given full text of a chat line.
	 * 
	 * @param fullText
	 *            The full text of a chat line
	 * @param chatType
	 *            The chat type of the message
	 * @return The extracted message
	 * @throws IllegalArgumentException
	 *             If the given message is invalid.
	 */
	private Message extractMessage(final String fullText, final EChatType chatType) throws IllegalArgumentException {
		final String sender;
		final String content;
		final int senderGroup;
		final int contentGroup;
		final String messagePattern;

		// First round of matching
		if (chatType == EChatType.CLAN) {
			messagePattern = Patterns.CHAT_MESSAGE_NAME_CONTENT_CLAN;
			senderGroup = 1;
			contentGroup = 2;
		} else if (chatType == EChatType.DIRECT) {
			messagePattern = Patterns.CHAT_MESSAGE_NAME_CONTENT_DIRECT_FROM_USER;
			senderGroup = 1;
			contentGroup = 2;
		} else if (chatType == EChatType.GLOBAL) {
			messagePattern = Patterns.CHAT_MESSAGE_NAME_CONTENT_GLOBAL;
			senderGroup = 1;
			contentGroup = 2;
		} else if (chatType == EChatType.GROUP) {
			messagePattern = Patterns.CHAT_MESSAGE_NAME_CONTENT_GROUP;
			senderGroup = 1;
			contentGroup = 2;
		} else if (chatType == EChatType.INFO) {
			messagePattern = Patterns.CHAT_MESSAGE_NAME_CONTENT_INFO;
			senderGroup = -1;
			contentGroup = 1;
		} else if (chatType == EChatType.SCREAM) {
			messagePattern = Patterns.CHAT_MESSAGE_NAME_CONTENT_SCREAM;
			senderGroup = 1;
			contentGroup = 2;
		} else if (chatType == EChatType.WHISPER) {
			messagePattern = Patterns.CHAT_MESSAGE_NAME_CONTENT_WHISPER_RECEIVING;
			senderGroup = 1;
			contentGroup = 2;
		} else if (chatType == EChatType.WORLDSAY) {
			messagePattern = Patterns.CHAT_MESSAGE_NAME_CONTENT_WORLDSAY;
			senderGroup = -1;
			contentGroup = 1;
		} else {
			throw new AssertionError();
		}

		// Execute the first round
		final Pattern pattern = Pattern.compile(messagePattern);
		final Matcher matcher = pattern.matcher(fullText);
		if (matcher.matches()) {
			content = matcher.group(contentGroup);
			if (senderGroup != -1) {
				sender = matcher.group(senderGroup);
				return new Message(sender, content, chatType);
			}
			return new Message(content, chatType);
		}

		// Try the second round if not successful
		final String secondMessagePattern;
		final int receiverGroup;
		final int secondContentGroup;
		if (chatType == EChatType.WHISPER) {
			secondMessagePattern = Patterns.CHAT_MESSAGE_NAME_CONTENT_WHISPER_SENDING;
			receiverGroup = 1;
			secondContentGroup = 2;
		} else if (chatType == EChatType.DIRECT) {
			secondMessagePattern = Patterns.CHAT_MESSAGE_NAME_CONTENT_DIRECT_NEUTRAL;
			receiverGroup = -1;
			secondContentGroup = 1;
		} else {
			throw new IllegalArgumentException(ErrorMessages.CHAT_MESSAGE_INVALID + " " + chatType + ": " + fullText);
		}

		// Execute the second round
		final Pattern secondPattern = Pattern.compile(secondMessagePattern);
		final Matcher secondMatcher = secondPattern.matcher(fullText);
		if (secondMatcher.matches()) {
			content = secondMatcher.group(secondContentGroup);
			if (receiverGroup != -1) {
				final String receiver = secondMatcher.group(receiverGroup);
				return new Message(this.m_User, receiver, content, chatType);
			}
			return new Message(content, chatType);
		}

		// Try the third round if not successful
		final String thirdMessagePattern;
		final int thirdContentGroup;
		if (chatType == EChatType.WHISPER) {
			thirdMessagePattern = Patterns.CHAT_MESSAGE_NAME_CONTENT_WHISPER_NEUTRAL;
			thirdContentGroup = 1;
		} else {
			throw new IllegalArgumentException(ErrorMessages.CHAT_MESSAGE_INVALID + " " + chatType + ": " + fullText);
		}

		// Execute the second round
		final Pattern thirdPattern = Pattern.compile(thirdMessagePattern);
		final Matcher thirdMatcher = thirdPattern.matcher(fullText);
		if (thirdMatcher.matches()) {
			content = thirdMatcher.group(thirdContentGroup);
			return new Message(content, chatType);
		}

		throw new IllegalArgumentException(ErrorMessages.CHAT_MESSAGE_INVALID + " " + chatType + ": " + fullText);
	}

	/**
	 * Gets all messages of the chat that matches the given selector in time
	 * ascending order. That is the latest message first. The collection type is
	 * an {@link ArrayList} which allows a fast get access if needed.
	 * 
	 * @param cssSelector
	 *            The CSS selector to match the messages to get
	 * @return All messages of the chat that matches the given selector in time
	 *         ascending order
	 */
	private ArrayList<Message> getMessagesBySelector(final String cssSelector) {
		switchToChatTextFrame();

		// Fetch the elements and immediately extract the data before creating
		// big objects to prevent stale elements
		final LinkedList<String> elementsClasses = new LinkedList<>();
		final LinkedList<String> elementsFullTexts = new LinkedList<>();
		final List<WebElement> elements = this.m_Driver.findElements(By.cssSelector(cssSelector));
		for (final WebElement element : elements) {
			elementsClasses.add(Classes.getClassAttribute(element));
			elementsFullTexts.add(element.getText());
		}

		// Now shift the data to message objects
		final ArrayList<Message> messages = new ArrayList<>(elementsClasses.size());
		final Iterator<String> classes = elementsClasses.iterator();
		final Iterator<String> fullTexts = elementsFullTexts.iterator();

		while (classes.hasNext() && fullTexts.hasNext()) {
			final String classAttribute = classes.next();
			final String fullText = fullTexts.next();

			final EChatType chatType;
			if (Classes.hasClass(classAttribute, Classes.CHAT_MESSAGE_CLAN)) {
				chatType = EChatType.CLAN;
			} else if (Classes.hasClass(classAttribute, Classes.CHAT_MESSAGE_DIRECT)) {
				chatType = EChatType.DIRECT;
			} else if (Classes.hasClass(classAttribute, Classes.CHAT_MESSAGE_GLOBAL)) {
				chatType = EChatType.GLOBAL;
			} else if (Classes.hasClass(classAttribute, Classes.CHAT_MESSAGE_GROUP)) {
				chatType = EChatType.GROUP;
			} else if (Classes.hasClass(classAttribute, Classes.CHAT_MESSAGE_INFO)) {
				chatType = EChatType.INFO;
			} else if (Classes.hasClass(classAttribute, Classes.CHAT_MESSAGE_SCREAM)) {
				chatType = EChatType.SCREAM;
			} else if (Classes.hasClass(classAttribute, Classes.CHAT_MESSAGE_WHISPER)) {
				chatType = EChatType.WHISPER;
			} else if (Classes.hasClass(classAttribute, Classes.CHAT_MESSAGE_WORLDSAY)) {
				chatType = EChatType.WORLDSAY;
			} else {
				throw new AssertionError();
			}

			messages.add(extractMessage(fullText, chatType));
		}

		return messages;
	}

	/**
	 * Switches to the chat form frame of <tt>Freewar</tt> and waits until it is
	 * loaded. It ensures that previous queued events are processed before
	 * switching frames.
	 */
	private void switchToChatFormFrame() {
		this.m_FrameManager.switchToFrame(EFrame.CHAT_FORM);
	}

	/**
	 * Switches to the chat text frame of <tt>Freewar</tt> and waits until it is
	 * loaded. It ensures that previous queued events are processed before
	 * switching frames.
	 */
	private void switchToChatTextFrame() {
		this.m_FrameManager.switchToFrame(EFrame.CHAT_TEXT);
	}
}
