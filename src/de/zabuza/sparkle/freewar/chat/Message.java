package de.zabuza.sparkle.freewar.chat;

import java.util.Optional;

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
	private final EChatType mChatType;
	/**
	 * The content of the message.
	 */
	private final String mContent;
	/**
	 * The receiver of the message.
	 */
	private final String mReceiver;
	/**
	 * The sender of the message.
	 */
	private final String mSender;

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
		this.mSender = sender;
		this.mReceiver = receiver;
		this.mContent = content;
		this.mChatType = chatType;
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
		if (this.mChatType != other.mChatType) {
			return false;
		}
		if (this.mContent == null) {
			if (other.mContent != null) {
				return false;
			}
		} else if (!this.mContent.equals(other.mContent)) {
			return false;
		}
		if (this.mReceiver == null) {
			if (other.mReceiver != null) {
				return false;
			}
		} else if (!this.mReceiver.equals(other.mReceiver)) {
			return false;
		}
		if (this.mSender == null) {
			if (other.mSender != null) {
				return false;
			}
		} else if (!this.mSender.equals(other.mSender)) {
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
		return this.mChatType;
	}

	/**
	 * Gets the content of the message.
	 * 
	 * @return The content of the message to get
	 */
	public String getContent() {
		return this.mContent;
	}

	/**
	 * Gets the receiver of the message if present.
	 * 
	 * @return If present the receiver of the message
	 */
	public Optional<String> getReceiver() {
		return Optional.of(this.mReceiver);
	}

	/**
	 * Gets the sender of the message if present.
	 * 
	 * @return The sender of the message to get
	 */
	public Optional<String> getSender() {
		return Optional.of(this.mSender);
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
		result = prime * result + ((this.mChatType == null) ? 0 : this.mChatType.hashCode());
		result = prime * result + ((this.mContent == null) ? 0 : this.mContent.hashCode());
		result = prime * result + ((this.mReceiver == null) ? 0 : this.mReceiver.hashCode());
		result = prime * result + ((this.mSender == null) ? 0 : this.mSender.hashCode());
		return result;
	}

	/**
	 * Whether the message has a receiver or not.
	 * 
	 * @return <tt>True</tt> if the message has a receiver, <tt>false</tt> if
	 *         not
	 */
	public boolean hasReceiver() {
		return this.mReceiver != null;
	}

	/**
	 * Whether the message has a sender or not.
	 * 
	 * @return <tt>True</tt> if the message has a sender, <tt>false</tt> if not
	 */
	public boolean hasSender() {
		return this.mSender != null;
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
		if (this.mChatType != null) {
			builder.append("chatType=");
			builder.append(this.mChatType);
			builder.append(", ");
		}
		if (this.mSender != null) {
			builder.append("sender=");
			builder.append(this.mSender);
			builder.append(", ");
		}
		if (this.mReceiver != null) {
			builder.append("receiver=");
			builder.append(this.mReceiver);
			builder.append(", ");
		}
		if (this.mContent != null) {
			builder.append("content=");
			builder.append(this.mContent);
		}
		builder.append("]");
		return builder.toString();
	}
}
