package es.sandbox.ui.messages.context;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.resolver.MessageResolverStrategy;
import es.sandbox.ui.messages.resolver.StringFormatMessageResolverStrategy;
import es.sandbox.ui.messages.store.MessagesStoreAccessorFactory;


public class MessagesContextBuilder {

	private static final Logger LOGGER= LoggerFactory.getLogger(MessagesContextBuilder.class);

	private MessagesStoreAccessorFactory factory;
	private MessageResolverStrategy strategy;
	private Level[] levels;
	private CssClassesByLevel cssClassesByLevel;


	/**
	 * @param factory
	 */
	public MessagesContextBuilder(MessagesStoreAccessorFactory factory) {
		this.factory= factory;
		this.strategy= new StringFormatMessageResolverStrategy();
		this.levels= Level.values();
		this.cssClassesByLevel= new CssClassesByLevel();
	}

	/**
	 * @param factory
	 * @return
	 */
	public MessagesContextBuilder withMessagesStoreAccessorFactory(MessagesStoreAccessorFactory factory) {
		this.factory= factory;
		return this;
	}

	/**
	 * @param strategy
	 * @return
	 */
	public MessagesContextBuilder withMessageResolverStrategy(MessageResolverStrategy strategy) {
		this.strategy= strategy;
		return this;
	}

	/**
	 * @param levels
	 * @return
	 */
	public MessagesContextBuilder withLevels(Level... levels) {
		this.levels= ObjectUtils.defaultIfNull(levels, Level.values());
		return this;
	}

	/**
	 * @param level
	 * @param cssClass
	 * @return
	 */
	public MessagesContextBuilder withCssClass(Level level, String cssClass) {
		if (StringUtils.isNotBlank(cssClass)) {
			this.cssClassesByLevel.put(level, cssClass);
		}
		return this;
	}

	/**
	 * @param cssClasses
	 * @return
	 */
	public MessagesContextBuilder withCssClassesByLevel(CssClassesByLevel cssClasses) {
		if (cssClasses != null) {
			this.cssClassesByLevel= new CssClassesByLevel(cssClasses);
		}
		return this;
	}

	/**
	 * @param level
	 * @return
	 */
	public MessagesContextBuilder withoutCssClass(Level level) {
		this.cssClassesByLevel.put(level, null);
		return this;
	}

	/**
	 * @return
	 */
	public MessagesContext build() {
		final MessagesContext context= new MessagesContext(this.factory, this.strategy);

		context.setLevels(this.levels);
		context.setCssClassesByLevel(this.cssClassesByLevel);

		LOGGER.info("The messages context has been built successfuly!");
		LOGGER.debug("{}", context);
		return context;
	}
}
