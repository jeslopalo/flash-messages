package es.sandbox.ui.messages;

import es.sandbox.ui.messages.resolver.MessageResolverStrategy;
import es.sandbox.ui.messages.resolver.StringFormatMessageResolverAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ContextBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContextBuilder.class);

    private final StoreAccessorFactory factory;
    private MessageResolverStrategy strategy;
    private Level[] levels;
    private CssClassesByLevel cssClassesByLevel;


    /**
     * @param factory
     */
    public ContextBuilder(StoreAccessorFactory factory) {
        checkFactory(factory);

        this.factory = factory;
        this.strategy = new StringFormatMessageResolverAdapter();
        this.levels = Level.values();
        this.cssClassesByLevel = new CssClassesByLevel();
    }

    private void checkFactory(StoreAccessorFactory factory) {
        if (factory == null) {
            throw new NullPointerException("StoreAccessorFactory can't be null");
        }
    }


    /**
     * @param strategy
     * @return
     */
    public ContextBuilder withMessageResolverStrategy(MessageResolverStrategy strategy) {
        this.strategy = strategy;
        return this;
    }


    /**
     * @param levels
     * @return
     */
    public ContextBuilder withLevels(Level... levels) {
        this.levels = levels;
        return this;
    }


    /**
     * @param cssClasses
     * @return
     */
    public ContextBuilder withCssClassesByLevel(CssClassesByLevel cssClasses) {
        this.cssClassesByLevel = new CssClassesByLevel(cssClasses);
        return this;
    }


    /**
     * @return
     */
    public Context build() {
        final Context context = new Context(this.factory, this.strategy);

        context.setLevels(this.levels);
        context.setCssClassesByLevel(this.cssClassesByLevel);

        LOGGER.info("The messages context has been built successfuly!");
        LOGGER.debug("{}", context);
        return context;
    }
}
