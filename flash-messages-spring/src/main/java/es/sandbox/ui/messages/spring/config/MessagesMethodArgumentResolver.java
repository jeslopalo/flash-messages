package es.sandbox.ui.messages.spring.config;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import es.sandbox.ui.messages.Messages;
import es.sandbox.ui.messages.context.MessagesContext;


public class MessagesMethodArgumentResolver
      implements HandlerMethodArgumentResolver {

   private static final Logger LOGGER= LoggerFactory.getLogger(MessagesMethodArgumentResolver.class);

   private final MessagesContext context;


   /**
    * @param context
    */
   public MessagesMethodArgumentResolver(MessagesContext context) {
      if (context == null) {
         throw new NullPointerException("MessagesContext can't be null");
      }
      this.context= context;
   }

   /*
    * (non-Javadoc)
    * @see
    * org.springframework.web.method.support.HandlerMethodArgumentResolver#supportsParameter(org.springframework.core
    * .MethodParameter)
    */
   @Override
   public boolean supportsParameter(MethodParameter parameter) { // NO_UCD (test only)
      return Messages.class.isAssignableFrom(parameter.getParameterType());
   }

   /*
    * (non-Javadoc)
    * @see
    * org.springframework.web.method.support.HandlerMethodArgumentResolver#resolveArgument(org.springframework.core
    * .MethodParameter, org.springframework.web.method.support.ModelAndViewContainer,
    * org.springframework.web.context.request.NativeWebRequest,
    * org.springframework.web.bind.support.WebDataBinderFactory)
    */
   @Override
   public Object resolveArgument( // NO_UCD (test only)
         MethodParameter parameter,
         ModelAndViewContainer mavContainer,
         NativeWebRequest webRequest,
         WebDataBinderFactory binderFactory) {

      LOGGER.trace("Accesing to the messages publisher from the request");
      return this.context.publisher(nativeRequest(webRequest));
   }

   private HttpServletRequest nativeRequest(NativeWebRequest webRequest) {
      return (HttpServletRequest) webRequest.getNativeRequest();
   }
}