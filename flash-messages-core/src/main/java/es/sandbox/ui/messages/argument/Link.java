package es.sandbox.ui.messages.argument;

import java.io.Serializable;


public interface Link extends Serializable {

	/**
	 * @param url
	 * @return
	 */
	Link url(String url);

	/**
	 * @param code
	 * @param arguments
	 * @return
	 */
	Link title(String code, Serializable... arguments);

	/**
	 * @param text
	 * @return
	 */
	Link title(Text text);

	/**
	 * @param cssClass
	 * @return
	 */
	Link cssClass(String cssClass);
}
