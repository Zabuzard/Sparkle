package de.zabuza.sparkle.freewar.chat;

import java.util.ArrayList;

/**
 * Interface for the chat of {@link de.zabuza.sparkle.freewar.IFreewarInstance
 * IFreewarInstance}s. Can be used to interact with the chat, i.e. retrieving
 * and sending messages.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public interface IChat {
	/**
	 * Focuses the chat input element.
	 * 
	 * @return <tt>True</tt> if the action was successful, <tt>false</tt> if not.
	 */
	public boolean focusChatInput();

	/**
	 * Gets all messages of the chat in time ascending order. That is the latest
	 * message first. The collection type is an {@link ArrayList} which allows a
	 * fast get access if needed.
	 * 
	 * @return All messages of the chat in time ascending order
	 */
	public ArrayList<Message> getMessages();

	/**
	 * Gets all messages of the chat given by its type in time ascending order. That
	 * is the latest message first. The collection type is an {@link ArrayList}
	 * which allows a fast get access if needed.
	 * 
	 * @param chatType
	 *            The type of the chat to get messages of
	 * @return All messages of the chat given by its type in time ascending order
	 */
	public ArrayList<Message> getMessages(final EChatType chatType);

	/**
	 * Submits the given message to the given chat.
	 * 
	 * @param message
	 *            The message to submit
	 * @param chatType
	 *            The type of the chat to submit to
	 * @return <tt>True</tt> if the message was submitted, <tt>false</tt> if it
	 *         could not be submitted.
	 */
	public boolean submitMessage(final String message, final EChatType chatType);
}
