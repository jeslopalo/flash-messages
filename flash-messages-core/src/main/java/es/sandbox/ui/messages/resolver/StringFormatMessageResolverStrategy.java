package es.sandbox.ui.messages.resolver;

import java.io.Serializable;


public class StringFormatMessageResolverStrategy
		implements MessageResolverStrategy {

	/*
	 * (non-Javadoc)
	 * @see es.sandbox.ui.messages.resolver.MessageResolverStrategy#resolveMessage(java.lang.String,
	 * java.io.Serializable[])
	 */
	@Override
	public String resolve(String code, Serializable... arguments) {
		return String.format(code, (Object[]) arguments);
	}
}
