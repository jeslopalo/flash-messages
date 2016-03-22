package es.sandbox.ui.messages;

import javax.servlet.http.HttpServletRequest;


public interface StoreAccessorFactory {

	/**
	 * @param request
	 * @return
	 */
	StoreAccessor create(HttpServletRequest request);
}
