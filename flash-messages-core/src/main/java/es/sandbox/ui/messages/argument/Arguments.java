package es.sandbox.ui.messages.argument;

import java.io.Serializable;


public class Arguments {

	/**
	 * Private constructor to prevent instances
	 * 
	 * @throws UnsupportedOperationException
	 */
	private Arguments() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @param code
	 * @param arguments
	 * @return
	 */
	public static Text text(String code, Serializable... arguments) {
		return new TextArgument(code, arguments);
	}

	/**
	 * @param url
	 * @return
	 */
	public static Link link(String url) {
		return new LinkArgument(url);
	}
}
