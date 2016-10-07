package es.sandbox.ui.messages.spring.config.annotation;

import es.sandbox.ui.messages.CssClassesByLevel;
import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.StoreAccessorFactory;
import es.sandbox.ui.messages.resolver.MessageResolverStrategy;


public abstract class FlashMessagesConfigurerAdapter
    implements FlashMessagesConfigurer {

    @Override
    public StoreAccessorFactory configureMessagesStoreAccessorFactory() {
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
