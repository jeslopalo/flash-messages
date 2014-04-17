package es.sandbox.ui.messages.spring.config.annotation;

import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.context.CssClassesByLevel;
import es.sandbox.ui.messages.resolver.MessageResolverStrategy;
import es.sandbox.ui.messages.store.MessagesStoreAccessorFactory;


public interface MessagesConfigurer {

	/**
	 * @return
	 */
	MessagesStoreAccessorFactory configureMessagesStoreAccessorFactory();

	/**
	 * @param messageSource
	 * @return
	 */
	MessageResolverStrategy configureMessageResolverStrategy();

	/**
	 * @return
	 */
	Level[] configureIncludedLevels();

	/**
	 * @param cssClasses
	 */
	void configureCssClassesByLevel(CssClassesByLevel cssClasses);
}
