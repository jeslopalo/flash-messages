package es.sandbox.ui.messages.spring.config.annotation;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.handler.HandlerExceptionResolverComposite;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import es.sandbox.ui.messages.spring.config.FlashMessagesHandlerInterceptor;
import es.sandbox.ui.messages.spring.config.FlashMessagesMethodArgumentResolver;
import es.sandbox.ui.messages.spring.config.annotation.FlashMessagesConfigurationSupportSpecs.CustomFlashMessagesConfigurationSupport;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= CustomFlashMessagesConfigurationSupport.class)
@WebAppConfiguration
public class FlashMessagesConfigurationSupportSpecs
      implements ApplicationContextAware {

   private ApplicationContext context;
   private CustomFlashMessagesConfigurationSupport messagesConfiguration;


   @Override
   public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
      this.context= applicationContext;
   }

   @Before
   public void setup() {
      this.messagesConfiguration= this.context.getBean(CustomFlashMessagesConfigurationSupport.class);
   }

   @Test
   public void it_should_add_message_handler_interceptor() {
      final SpyInterceptorRegistry registry= new SpyInterceptorRegistry();

      this.messagesConfiguration.addInterceptors(registry);

      assertThat(registry.containsMessagesHandlerInterceptor()).isTrue();
   }

   @Test
   public void it_should_add_messages_method_argument_resolver() {
      final List<HandlerMethodArgumentResolver> resolvers= new ArrayList<HandlerMethodArgumentResolver>();

      this.messagesConfiguration.addArgumentResolvers(resolvers);

      assertThat(resolvers.get(0)).isInstanceOf(FlashMessagesMethodArgumentResolver.class);
   }

   @Test
   public void it_should_add_exception_argument_resolver() {
      final HandlerExceptionResolver handlerExceptionResolver= this.context.getBean(HandlerExceptionResolver.class);

      assertThat(handlerExceptionResolver).isInstanceOf(HandlerExceptionResolverComposite.class);
      for (final HandlerExceptionResolver resolver : ((HandlerExceptionResolverComposite) handlerExceptionResolver).getExceptionResolvers()) {
         if (resolver instanceof ExceptionHandlerExceptionResolver) {
            assertThat(((ExceptionHandlerExceptionResolver) resolver).getArgumentResolvers().getResolvers().get(0))
                  .isInstanceOf(FlashMessagesMethodArgumentResolver.class);
         }
      }
   }


   @Configuration
   @Import(FixtureFlashMessagesContextConfiguration.class)
   static class CustomFlashMessagesConfigurationSupport
         extends FlashMessagesConfigurationSupport {
   }

   static class SpyInterceptorRegistry extends InterceptorRegistry {

      public boolean containsMessagesHandlerInterceptor() {
         for (final Object interceptor : getInterceptors()) {
            if (interceptor instanceof FlashMessagesHandlerInterceptor) {
               return true;
            }
         }
         return false;
      }
   }
}
