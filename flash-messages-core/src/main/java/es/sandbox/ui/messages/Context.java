package es.sandbox.ui.messages;

import es.sandbox.ui.messages.resolver.MessageResolver;
import es.sandbox.ui.messages.resolver.MessageResolverStrategy;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class Context {

    private static final Logger LOGGER = LoggerFactory.getLogger(Context.class);

    public static final String FLASH_MESSAGES_CONTEXT_PARAMETER = Context.class.getName() + ".MESSAGES_CONTEXT";

    private final StoreAccessorFactory factory;
    private final MessageResolver messageResolver;

    private List<Level> levels;
    private CssClassesByLevel cssClassesByLevel;


    /**
     * @param factory
     * @param strategy
     * @throws NullPointerException
     */
    Context(StoreAccessorFactory factory, MessageResolverStrategy strategy)
        throws NullPointerException {

        if (factory == null) {
            throw new NullPointerException("StoreAccessorFactory can't not be null");
        }

        if (strategy == null) {
            throw new NullPointerException("MessageResolverStrategy can't not be null");
        }

        this.factory = factory;
        this.messageResolver = new MessageResolver(strategy);
        this.levels = Arrays.asList(Level.values());
        this.cssClassesByLevel = new CssClassesByLevel();
    }

    /**
     * @return
     */
    public List<Level> levels() {
        return this.levels;
    }

    void setLevels(Level... levels) {
        this.levels = sanitize(levels);
    }

    private List<Level> sanitize(Level... levels) {
        final List<Level> levelsWithoutNulls =
            new ArrayList<Level>(Arrays.asList(ObjectUtils.defaultIfNull(levels, new Level[0])));

        levelsWithoutNulls.remove(null);
        return levelsWithoutNulls;
    }

    /**
     * @param level
     * @return
     */
    public String getLevelCssClass(Level level) {
        return this.cssClassesByLevel.get(level);
    }

    void setCssClassesByLevel(CssClassesByLevel cssClassesByLevel) {
        this.cssClassesByLevel = new CssClassesByLevel(cssClassesByLevel);
    }

    /**
     * @param request
     * @throws NullPointerException
     */
    public void initialize(HttpServletRequest request)
        throws NullPointerException {

        LOGGER.debug("Initializing the message store");
        final StoreAccessor accessor = createAccessor(request);
        if (!accessor.contains()) {
            accessor.put(new Store());
        }
    }


    /**
     * @param request
     * @return
     * @throws NullPointerException
     */
    public Flash publisher(HttpServletRequest request)
        throws NullPointerException {

        LOGGER.debug("Getting the messages publisher");
        final StoreAccessor accessor = createAccessor(request);
        return new FlashPublisher(this.messageResolver, accessor.get());
    }

    /**
     * @param level
     * @param request
     * @return
     * @throws NullPointerException
     */
    public Collection<Message> levelMessages(Level level, HttpServletRequest request)
        throws NullPointerException {

        LOGGER.debug("Getting the '{}' level messages from store", level);
        final StoreAccessor accessor = createAccessor(request);
        return accessor.get().getMessages(level);
    }


    private StoreAccessor createAccessor(HttpServletRequest request)
        throws NullPointerException {

        if (request == null) {
            throw new NullPointerException("HttpServletRequest can't be null");
        }
        return this.factory.create(request);
    }

    @Override
    public String toString() {
        return
            String.format("Context [factory=%s, messageResolver=%s, levels=%s, cssClassesByLevel=%s]",
                this.factory.getClass().getSimpleName(),
                this.messageResolver,
                this.levels,
                this.cssClassesByLevel);
    }
}
