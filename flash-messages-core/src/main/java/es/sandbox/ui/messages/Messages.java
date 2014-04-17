package es.sandbox.ui.messages;

import java.io.Serializable;


public interface Messages {

	/**
	 * @param code
	 * @param arguments
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	void success(String code, Serializable... arguments);

	/**
	 * @param code
	 * @param arguments
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	void info(String code, Serializable... arguments);

	/**
	 * @param code
	 * @param arguments
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	void warning(String code, Serializable... arguments);

	/**
	 * @param code
	 * @param arguments
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	void error(String code, Serializable... arguments);

	/**
	 * Clear all {@link Level} messages
	 */
	void clear();

	/**
	 * Return true if there are not messages in any level
	 * 
	 * @return
	 */
	boolean isEmpty();
}
