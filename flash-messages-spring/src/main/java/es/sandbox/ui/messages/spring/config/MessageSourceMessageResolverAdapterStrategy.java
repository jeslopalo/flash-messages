package es.sandbox.ui.messages.spring.config;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;

import es.sandbox.ui.messages.resolver.MessageResolverStrategy;


public class MessageSourceMessageResolverAdapterStrategy
      implements MessageResolverStrategy {

   private final MessageSourceAccessor messageSourceAccessor;


   public MessageSourceMessageResolverAdapterStrategy(MessageSource messageSource) {

      if (messageSource == null) {
         throw new NullPointerException("MessageSource can't be null");
      }
      this.messageSourceAccessor= new MessageSourceAccessor(messageSource);
   }

   /*
    * (non-Javadoc)
    * @see es.sandbox.ui.messages.resolver.MessageResolverStrategy#resolve(java.lang.String, java.lang.Object[])
    */
   @Override
   public String resolve(String code, Object... arguments) {
      return this.messageSourceAccessor.getMessage(code, arguments);
   }
}