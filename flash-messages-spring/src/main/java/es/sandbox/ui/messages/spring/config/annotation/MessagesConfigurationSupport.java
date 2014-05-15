package es.sandbox.ui.messages.spring.config.annotation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerExceptionResolverComposite;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.context.CssClassesByLevel;
import es.sandbox.ui.messages.context.MessagesContext;
import es.sandbox.ui.messages.context.MessagesContextBuilder;
import es.sandbox.ui.messages.resolver.MessageResolverStrategy;
import es.sandbox.ui.messages.spring.config.MessageSourceMessageResolverAdapterStrategy;
import es.sandbox.ui.messages.spring.config.MessagesHandlerInterceptor;
import es.sandbox.ui.messages.spring.config.MessagesMethodArgumentResolver;
import es.sandbox.ui.messages.spring.scope.flash.MessagesStoreFlashScopeAccessorFactory;
import es.sandbox.ui.messages.store.MessagesStoreAccessorFactory;


public class MessagesConfigurationSupport
      extends WebMvcConfigurerAdapter {

   private HandlerExceptionResolver handlerExceptionResolver;
   private MessageSource messageSource;


   @Autowired
   void setMessageSource(MessageSource messageSource) {
      this.messageSource= messageSource;
   }

   @Autowired
   void setHandlerExceptionResolver(HandlerExceptionResolver handlerExceptionResolver) {
      this.handlerExceptionResolver= handlerExceptionResolver;
   }


   /**
	 * 
	 */
   @PostConstruct
   private void configureMessagesExceptionArgumentResolvers() {
      for (final HandlerExceptionResolver resolver : ((HandlerExceptionResolverComposite) this.handlerExceptionResolver).getExceptionResolvers()) {
         if (resolver instanceof ExceptionHandlerExceptionResolver) {
            configureCustomHandlerMethodArgumentResolver((ExceptionHandlerExceptionResolver) resolver);
         }
      }
   }

   private void configureCustomHandlerMethodArgumentResolver(final ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver) {
      final List<HandlerMethodArgumentResolver> resolvers= new ArrayList<HandlerMethodArgumentResolver>();
      resolvers.addAll(exceptionHandlerExceptionResolver.getArgumentResolvers().getResolvers());
      resolvers.add(messagesMethodArgumentResolver());

      exceptionHandlerExceptionResolver.setArgumentResolvers(resolvers);
   }


   /*
    * (non-Javadoc)
    * @see
    * org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addArgumentResolvers(java.util.List)
    */
   @Override
   public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
      argumentResolvers.add(messagesMethodArgumentResolver());
   }


   private MessagesMethodArgumentResolver messagesMethodArgumentResolver() {
      return new MessagesMethodArgumentResolver(messagesContext());
   }


   /*
    * (non-Javadoc)
    * @see
    * org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addInterceptors(org.springframework
    * .web.servlet.config.annotation.InterceptorRegistry)
    */
   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(new MessagesHandlerInterceptor(messagesContext()));
   }


   /**
    * @return
    */
   @Bean
   protected MessagesContext messagesContext() {
      return new MessagesContextBuilder(configureMessagesStoreAccessorFactory())
            .withMessageResolverStrategy(configureMessageResolverStrategy())
            .withLevels(includedLevels())
            .withCssClassesByLevel(cssClassesByLevel())
            .build();
   }


   /**
    * Override this method to add a custom {@link MessagesStoreAccessorFactory}
    * 
    * @return
    */
   protected MessagesStoreAccessorFactory configureMessagesStoreAccessorFactory() {
      return new MessagesStoreFlashScopeAccessorFactory();
   }

   /**
    * Override this method to add a custom {@link MessageResolverStrategy}
    * 
    * @return
    */
   protected MessageResolverStrategy configureMessageResolverStrategy() {
      return new MessageSourceMessageResolverAdapterStrategy(this.messageSource);
   }


   private Level[] includedLevels() {
      return configureIncludedLevels();
   }

   /**
    * Override this method to configure {@link Level} values
    * that will be used.
    * 
    * @return
    */
   protected Level[] configureIncludedLevels() {
      return Level.values();
   }


   private CssClassesByLevel cssClassesByLevel() {
      final CssClassesByLevel cssClassesByLevel= new CssClassesByLevel();
      configureCssClassesByLevel(cssClassesByLevel);
      return cssClassesByLevel;
   }

   /**
    * Override this method to configure custom css classes in each {@link Level}
    * 
    * @param cssClassesByLevel
    */
   protected void configureCssClassesByLevel(CssClassesByLevel cssClassesByLevel) {

   }
}
