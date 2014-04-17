/**
 * 
 */
package es.sandbox.ui.messages.store;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.Message;

/**
 * <b>MessagesStore</b>
 * 
 * @author 21/06/2013 - jesuslopez
 */
public final class MessagesStore
		implements Serializable {

	private static final long serialVersionUID= -1559332402223360761L;

	private final EnumMap<Level, List<Message>> messages= new EnumMap<Level, List<Message>>(Level.class);


	/**
	 * Creates an empty {@link MessagesStore}
	 */
	public MessagesStore() {
		for (final Level level : Level.values()) {
			this.messages.put(level, new ArrayList<Message>());
		}
	}


	/**
	 * Add a collection of {@link Message} to the Storage
	 * 
	 * @param messages
	 * @throws NullPointerException
	 */
	protected final void addAll(List<Message> messages)
			throws NullPointerException {

		for (final Message message : messages) {
			add(message);
		}
	}

	/**
	 * Add {@link Message} to the Storage
	 * 
	 * @param message
	 * @throws NullPointerException
	 */
	final void add(Message message)
			throws NullPointerException {

		if (message == null) {
			throw new NullPointerException("Message can't be null");
		}

		this.messages.get(message.getLevel()).add(message);
	}

	/**
	 * Add the {@link Level} <code>message</code> to the Storage
	 * 
	 * @param level
	 * @param message
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	final void add(Level level, String message)
			throws NullPointerException, IllegalArgumentException {

		add(Message.create(level, message));
	}

	/**
	 * @param message
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public void success(String message)
			throws NullPointerException, IllegalArgumentException {

		add(Level.SUCCESS, message);
	}

	/**
	 * @param message
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public void info(String message)
			throws NullPointerException, IllegalArgumentException {

		add(Level.INFO, message);
	}

	/**
	 * @param message
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public void warning(String message)
			throws NullPointerException, IllegalArgumentException {

		add(Level.WARNING, message);
	}

	/**
	 * @param message
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public void error(String message)
			throws NullPointerException, IllegalArgumentException {
		add(Level.ERROR, message);
	}


	/**
	 * Cleans the stored messages
	 */
	public final void clear() {
		for (final Level level : Level.values()) {
			this.messages.get(level).clear();
		}
	}

	/**
	 * @return
	 */
	public boolean isEmpty() {
		for (final Level level : Level.values()) {
			if (containsMessages(level)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns a copy of the internal {@link EnumMap} with {@link Message}s distributed by {@link Level}
	 * 
	 * @return
	 */
	public EnumMap<Level, List<Message>> getMessages() {
		final EnumMap<Level, List<Message>> copyOfMessages= new EnumMap<Level, List<Message>>(Level.class);
		for (final Level level : Level.values()) {
			if (containsMessages(level)) {
				copyOfMessages.put(level, getMessages(level));
			}
		}
		return copyOfMessages;
	}

	/**
	 * Returns an inmutable copy of the {@link Message}s contained in a {@link Level}
	 * 
	 * @param level
	 * @return
	 */
	public List<Message> getMessages(final Level level) {
		return Collections.unmodifiableList(this.messages.get(level));
	}

	/**
	 * Returns true if a {@link Level} is not empty
	 * 
	 * @param level
	 * @return
	 */
	boolean containsMessages(final Level level) {
		return !this.messages.get(level).isEmpty();
	}


	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return 31 * 1 + ((this.messages == null)? 0 : this.messages.hashCode());
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if (!(other instanceof MessagesStore)) {
			return false;
		}
		final MessagesStore that= (MessagesStore) other;
		if (this.messages == null) {
			if (that.messages != null) {
				return false;
			}
		}
		else if (!this.messages.equals(that.messages)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (isEmpty()) {
			return "messages [empty]";
		}
		return String.format("messages [%s]", this.messages);
	}
}
