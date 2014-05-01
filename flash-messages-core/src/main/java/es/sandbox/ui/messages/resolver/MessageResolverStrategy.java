package es.sandbox.ui.messages.resolver;

import java.io.Serializable;


public interface MessageResolverStrategy {

	/**
	 * @param code
	 * @param arguments
	 * @return
	 */
	String resolve(String code, Serializable... arguments);
}
