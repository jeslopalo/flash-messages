package es.sandbox.ui.messages.spring.config.annotation;

import es.sandbox.ui.messages.CssClassesByLevel;
import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.StoreAccessorFactory;
import es.sandbox.ui.messages.resolver.MessageResolverStrategy;


public interface FlashMessagesConfigurer {

    /**
     * @return
     */
    StoreAccessorFactory configureMessagesStoreAccessorFactory();

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
