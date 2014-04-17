package es.sandbox.ui.messages.store;


public class MessagesStoreNotFoundException extends IllegalStateException {

	private static final long serialVersionUID= -1747140702489823659L;


	public MessagesStoreNotFoundException() {
		super("There is not MessagesStore. Probably MessagesContext is not initialized!");
	}
}
