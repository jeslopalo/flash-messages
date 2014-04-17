package es.sandbox.ui.messages.spring.scope.request;

import javax.servlet.http.HttpServletRequest;


class NotHttpServletRequestBoundToThreadException
		extends RuntimeException {

	private static final long serialVersionUID= -7745349766135946473L;

	private static final String DEFAULT_EXCEPTION_MESSAGE= "There is not an HttpServletRequest bound to the current Thread";


	/**
	 * There is not an {@link HttpServletRequest} bound to the current {@link Thread}
	 */
	public NotHttpServletRequestBoundToThreadException() {
		super(DEFAULT_EXCEPTION_MESSAGE);
	}
}
