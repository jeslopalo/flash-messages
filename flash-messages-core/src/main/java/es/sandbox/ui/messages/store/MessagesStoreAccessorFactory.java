package es.sandbox.ui.messages.store;

import javax.servlet.http.HttpServletRequest;


public interface MessagesStoreAccessorFactory {

	/**
	 * @param request
	 * @return
	 */
	MessagesStoreAccessor create(HttpServletRequest request);
}
