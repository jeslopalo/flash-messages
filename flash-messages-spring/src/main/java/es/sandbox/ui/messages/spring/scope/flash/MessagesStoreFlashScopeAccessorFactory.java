package es.sandbox.ui.messages.spring.scope.flash;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.sandbox.ui.messages.store.MessagesStoreAccessor;
import es.sandbox.ui.messages.store.MessagesStoreAccessorFactory;


public class MessagesStoreFlashScopeAccessorFactory
		implements MessagesStoreAccessorFactory {

	private static final Logger LOGGER= LoggerFactory.getLogger(MessagesStoreFlashScopeAccessorFactory.class);

	public static final String MESSAGES_PARAMETER= MessagesStoreFlashScopeAccessorFactory.class.getName() + ".MESSAGES";

	private final String flashParameter;


	/**
	 * 
	 */
	public MessagesStoreFlashScopeAccessorFactory() {
		this(MESSAGES_PARAMETER);
	}

	/**
	 * @param flashParameter
	 */
	public MessagesStoreFlashScopeAccessorFactory(String flashParameter) { // NO_UCD (use private)
		if (flashParameter == null) {
			throw new NullPointerException("The flash scope parameter name can't be null");
		}
		if (StringUtils.isBlank(flashParameter)) {
			throw new IllegalArgumentException("The flash scope parameter name can't be empty");
		}

		LOGGER.trace("The messages will be stored in flash scope param [{}]", flashParameter);
		this.flashParameter= flashParameter;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * es.sandbox.ui.messages.store.MessagesStoreAccessorFactory#create(javax.servlet.http.HttpServletRequest,
	 * java.lang.String)
	 */
	@Override
	public MessagesStoreAccessor create(HttpServletRequest request) {
		return new MessagesStoreFlashScopeAccessor(request, this.flashParameter);
	}
}
