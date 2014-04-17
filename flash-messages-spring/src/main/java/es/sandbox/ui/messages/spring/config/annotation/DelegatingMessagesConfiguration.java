package es.sandbox.ui.messages.spring.config.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.context.CssClassesByLevel;
import es.sandbox.ui.messages.resolver.MessageResolverStrategy;
import es.sandbox.ui.messages.store.MessagesStoreAccessorFactory;

@Configuration
class DelegatingMessagesConfiguration
		extends MessagesConfigurationSupport {

	private MessagesConfigurer configurer;


	@Autowired(required= false)
	void setMessagesConfigurer(MessagesConfigurer messagesConfigurer) { // NO_UCD (unused code)
		this.configurer= messagesConfigurer;
	}

	/*
	 * (non-Javadoc)
	 * @see es.sandbox.ui.messages.spring.MessagesConfigurationSupport#configureMessagesStoreAccessorFactory()
	 */
	@Override
	protected MessagesStoreAccessorFactory configureMessagesStoreAccessorFactory() {
		final MessagesStoreAccessorFactory candidate= configuredMessagesStoreAccessorFactory();
		return candidate == null? super.configureMessagesStoreAccessorFactory() : candidate;
	}

	private MessagesStoreAccessorFactory configuredMessagesStoreAccessorFactory() {
		if (this.configurer != null) {
			return this.configurer.configureMessagesStoreAccessorFactory();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see es.sandbox.ui.messages.spring.MessagesConfigurationSupport#configureMessageResolverStrategy()
	 */
	@Override
	protected MessageResolverStrategy configureMessageResolverStrategy() {
		final MessageResolverStrategy candidate= configuredMessageResolverStrategy();
		return candidate == null? super.configureMessageResolverStrategy() : candidate;
	}

	private MessageResolverStrategy configuredMessageResolverStrategy() {
		if (this.configurer != null) {
			return this.configurer.configureMessageResolverStrategy();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see es.sandbox.ui.messages.spring.MessagesConfigurationSupport#configureIncludedLevels()
	 */
	@Override
	protected Level[] configureIncludedLevels() {
		final Level[] levels= configuredIncludedLevels();
		return levels == null? super.configureIncludedLevels() : levels;
	}

	private Level[] configuredIncludedLevels() {
		if (this.configurer != null) {
			return this.configurer.configureIncludedLevels();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * es.sandbox.ui.messages.spring.MessagesConfigurationSupport#configureCssClassesByLevel(es.sandbox.ui.messages.
	 * context.CssClassesByLevel)
	 */
	@Override
	protected void configureCssClassesByLevel(CssClassesByLevel cssClassesByLevel) {
		this.configurer.configureCssClassesByLevel(cssClassesByLevel);
	}
}
