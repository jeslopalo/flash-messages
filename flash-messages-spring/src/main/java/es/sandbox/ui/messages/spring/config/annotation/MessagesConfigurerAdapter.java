package es.sandbox.ui.messages.spring.config.annotation;

import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.context.CssClassesByLevel;
import es.sandbox.ui.messages.resolver.MessageResolverStrategy;
import es.sandbox.ui.messages.store.MessagesStoreAccessorFactory;


public abstract class MessagesConfigurerAdapter
		implements MessagesConfigurer {

	@Override
	public MessagesStoreAccessorFactory configureMessagesStoreAccessorFactory() {
		return null;
	}

	@Override
	public MessageResolverStrategy configureMessageResolverStrategy() {
		return null;
	}

	@Override
	public Level[] configureIncludedLevels() {
		return null;
	}

	@Override
	public void configureCssClassesByLevel(CssClassesByLevel cssClasses) {

	}
}
