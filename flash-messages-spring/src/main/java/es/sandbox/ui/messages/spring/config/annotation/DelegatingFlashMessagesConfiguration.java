package es.sandbox.ui.messages.spring.config.annotation;

import es.sandbox.ui.messages.CssClassesByLevel;
import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.StoreAccessorFactory;
import es.sandbox.ui.messages.resolver.MessageResolverStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
class DelegatingFlashMessagesConfiguration
    extends FlashMessagesConfigurationSupport {

    private FlashMessagesConfigurer configurer;


    @Autowired(required = false)
    void setMessagesConfigurer(FlashMessagesConfigurer flashMessagesConfigurer) { // NO_UCD (unused code)
        this.configurer = flashMessagesConfigurer;
    }

    /*
     * (non-Javadoc)
     * @see es.sandbox.ui.messages.spring.MessagesConfigurationSupport#configureMessagesStoreAccessorFactory()
     */
    @Override
    protected StoreAccessorFactory configureFlashStoreAccessorFactory() {
        final StoreAccessorFactory candidate = configuredMessagesStoreAccessorFactory();
        return candidate == null ? super.configureFlashStoreAccessorFactory() : candidate;
    }

    private StoreAccessorFactory configuredMessagesStoreAccessorFactory() {
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
        final MessageResolverStrategy candidate = configuredMessageResolverStrategy();
        return candidate == null ? super.configureMessageResolverStrategy() : candidate;
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
        final Level[] levels = configuredIncludedLevels();
        return levels == null ? super.configureIncludedLevels() : levels;
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
        super.configureCssClassesByLevel(cssClassesByLevel);

        if (this.configurer != null) {
            this.configurer.configureCssClassesByLevel(cssClassesByLevel);
        }
    }
}
