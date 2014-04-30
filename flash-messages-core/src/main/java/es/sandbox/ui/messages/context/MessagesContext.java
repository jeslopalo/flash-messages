package es.sandbox.ui.messages.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.Message;
import es.sandbox.ui.messages.Messages;
import es.sandbox.ui.messages.MessagesPublisher;
import es.sandbox.ui.messages.resolver.MessageResolver;
import es.sandbox.ui.messages.resolver.MessageResolverStrategy;
import es.sandbox.ui.messages.store.MessagesStore;
import es.sandbox.ui.messages.store.MessagesStoreAccessor;
import es.sandbox.ui.messages.store.MessagesStoreAccessorFactory;


public class MessagesContext {

   private static final Logger LOGGER= LoggerFactory.getLogger(MessagesContext.class);

   public static final String MESSAGES_CONTEXT_PARAMETER= MessagesContext.class.getName() + ".MESSAGES_CONTEXT";

   private final MessagesStoreAccessorFactory factory;
   private final MessageResolver messageResolver;

   private Level[] levels;
   private CssClassesByLevel cssClassesByLevel;


   /**
    * @param factory
    * @param strategy
    * @throws NullPointerException
    */
   MessagesContext(MessagesStoreAccessorFactory factory, MessageResolverStrategy strategy) throws NullPointerException {

      if (factory == null) {
         throw new NullPointerException("MessagesStoreAccessorFactory can't not be null");
      }

      if (strategy == null) {
         throw new NullPointerException("MessageResolverStrategy can't not be null");
      }

      this.factory= factory;
      this.messageResolver= new MessageResolver(strategy);
      this.levels= Level.values();
      this.cssClassesByLevel= new CssClassesByLevel();
   }

   /**
    * @return
    */
   public Level[] levels() {
      return this.levels;
   }

   void setLevels(Level... levels) {
      this.levels= sanitize(levels);
   }

   private Level[] sanitize(Level... levels) {
      final List<Level> levelsWithoutNulls=
            new ArrayList<Level>(Arrays.asList(ObjectUtils.defaultIfNull(levels, new Level[0])));
      levelsWithoutNulls.remove(null);
      return levelsWithoutNulls.toArray(new Level[0]);
   }

   /**
    * @param level
    * @return
    */
   public String getLevelCssClass(Level level) {
      return this.cssClassesByLevel.get(level);
   }

   void setCssClassesByLevel(CssClassesByLevel cssClassesByLevel) {
      this.cssClassesByLevel= new CssClassesByLevel(cssClassesByLevel);
   }

   /**
    * @param request
    * @throws NullPointerException
    */
   public void initialize(HttpServletRequest request)
         throws NullPointerException {

      LOGGER.debug("Initializing the message store");
      final MessagesStoreAccessor accessor= createAccessor(request);
      if (!accessor.contains()) {
         accessor.put(new MessagesStore());
      }
   }


   /**
    * @param request
    * @return
    * @throws NullPointerException
    */
   public Messages publisher(HttpServletRequest request)
         throws NullPointerException {

      LOGGER.debug("Getting the messages publisher");
      final MessagesStoreAccessor accessor= createAccessor(request);
      return new MessagesPublisher(this.messageResolver, accessor.get());
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
      final MessagesStoreAccessor accessor= createAccessor(request);
      return accessor.get().getMessages(level);
   }


   private MessagesStoreAccessor createAccessor(HttpServletRequest request)
         throws NullPointerException {

      if (request == null) {
         throw new NullPointerException("HttpServletRequest can't be null");
      }
      return this.factory.create(request);
   }

   @Override
   public String toString() {
      return String.format("MessagesContext [factory=%s, messageResolver=%s, levels=%s, cssClassesByLevel=%s]",
            this.factory.getClass().getSimpleName(),
            this.messageResolver,
            Arrays.toString(this.levels),
            this.cssClassesByLevel);
   }
}
