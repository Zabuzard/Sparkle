package de.zabuza.sparkle.freewar.chat;

import com.google.common.base.Optional;

/**
 * Wrapper class that represents chat messages.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class Message {
	/**
	 * The chat type of the message.
	 */
	private final EChatType m_ChatType;
	/**
	 * The content of the message.
	 */
	private final String m_Content;
	/**
	 * The receiver of the message.
	 */
	private final String m_Receiver;
	/**
	 * The sender of the message.
	 */
	private final String m_Sender;

	/**
	 * Creates a new message with a given content.
	 * 
	 * @param content
	 *            The content of the message
	 * @param chatType
	 *            The chat type of the message
	 */
	public Message(final String content, final EChatType chatType) {
		this(null, null, content, chatType);
	}

	/**
	 * Creates a new message with a given sender and content.
	 * 
	 * @param sender
	 *            The sender of the message
	 * @param content
	 *            The content of the message
	 * @param chatType
	 *            The chat type of the message
	 */
	public Message(final String sender, final String content, final EChatType chatType) {
		this(sender, null, content, chatType);
	}

	/**
	 * Creates a new message with a given sender, receiver and content.
	 * 
	 * @param sender
	 *            The sender of the message
	 * @param receiver
	 *            The receiver of the message
	 * @param content
	 *            The content of the message
	 * @param chatType
	 *            The chat type of the message
	 */
	public Message(final String sender, final String receiver, final String content, final EChatType chatType) {
		this.m_Sender = sender;
		this.m_Receiver = receiver;
		this.m_Content = content;
		this.m_ChatType = chatType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Message)) {
			return false;
		}
		final Message other = (Message) obj;
		if (this.m_ChatType != other.m_ChatType) {
			return false;
		}
		if (this.m_Content == null) {
			if (other.m_Content != null) {
				return false;
			}
		} else if (!this.m_Content.equals(other.m_Content)) {
			return false;
		}
		if (this.m_Receiver == null) {
			if (other.m_Receiver != null) {
				return false;
			}
		} else if (!this.m_Receiver.equals(other.m_Receiver)) {
			return false;
		}
		if (this.m_Sender == null) {
			if (other.m_Sender != null) {
				return false;
			}
		} else if (!this.m_Sender.equals(other.m_Sender)) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the chat type of the message.
	 * 
	 * @return The chat type of the message to get
	 */
	public EChatType getChatType() {
		return this.m_ChatType;
	}

	/**
	 * Gets the content of the message.
	 * 
	 * @return The content of the message to get
	 */
	public String getContent() {
		return this.m_Content;
	}

	/**
	 * Gets the receiver of the message if present.
	 * 
	 * @return If present the receiver of the message
	 */
	public Optional<String> getReceiver() {
		return Optional.of(this.m_Receiver);
	}

	/**
	 * Gets the sender of the message if present.
	 * 
	 * @return The sender of the message to get
	 */
	public Optional<String> getSender() {
		return Optional.of(this.m_Sender);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.m_ChatType == null) ? 0 : this.m_ChatType.hashCode());
		result = prime * result + ((this.m_Content == null) ? 0 : this.m_Content.hashCode());
		result = prime * result + ((this.m_Receiver == null) ? 0 : this.m_Receiver.hashCode());
		result = prime * result + ((this.m_Sender == null) ? 0 : this.m_Sender.hashCode());
		return result;
	}

	/**
	 * Whether the message has a receiver or not.
	 * 
	 * @return <tt>True</tt> if the message has a receiver, <tt>false</tt> if
	 *         not
	 */
	public boolean hasReceiver() {
		return this.m_Receiver != null;
	}

	/**
	 * Whether the message has a sender or not.
	 * 
	 * @return <tt>True</tt> if the message has a sender, <tt>false</tt> if not
	 */
	public boolean hasSender() {
		return this.m_Sender != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Message [");
		if (this.m_ChatType != null) {
			builder.append("chatType=");
			builder.append(this.m_ChatType);
			builder.append(", ");
		}
		if (this.m_Sender != null) {
			builder.append("sender=");
			builder.append(this.m_Sender);
			builder.append(", ");
		}
		if (this.m_Receiver != null) {
			builder.append("receiver=");
			builder.append(this.m_Receiver);
			builder.append(", ");
		}
		if (this.m_Content != null) {
			builder.append("content=");
			builder.append(this.m_Content);
		}
		builder.append("]");
		return builder.toString();
	}
}
