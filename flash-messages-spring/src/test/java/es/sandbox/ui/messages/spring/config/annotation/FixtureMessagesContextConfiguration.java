package es.sandbox.ui.messages.spring.config.annotation;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.handler.HandlerExceptionResolverComposite;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

@Configuration
class FixtureMessagesContextConfiguration {

   @Bean
   public HandlerExceptionResolver handlerExceptionResolver() {
      final HandlerExceptionResolverComposite handlerExceptionResolver= new HandlerExceptionResolverComposite();
      final ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver= new ExceptionHandlerExceptionResolver();
      exceptionHandlerExceptionResolver.setArgumentResolvers(new ArrayList<HandlerMethodArgumentResolver>());

      handlerExceptionResolver.setExceptionResolvers(Arrays.asList((HandlerExceptionResolver) exceptionHandlerExceptionResolver));

      return handlerExceptionResolver;
   }
}
