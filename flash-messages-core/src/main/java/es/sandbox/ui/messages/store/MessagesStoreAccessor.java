package es.sandbox.ui.messages.store;


public interface MessagesStoreAccessor {

	/**
	 * @return
	 */
	boolean contains();

	/**
	 * @return
	 * @throws MessagesStoreNotFoundException
	 */
	MessagesStore get()
			throws MessagesStoreNotFoundException;

	/**
	 * @param store
	 */
	void put(MessagesStore store);
}
