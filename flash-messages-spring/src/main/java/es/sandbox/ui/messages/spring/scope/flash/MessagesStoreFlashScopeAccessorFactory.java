package es.sandbox.ui.messages.spring.scope.flash;

import javax.servlet.http.HttpServletRequest;

import es.sandbox.ui.messages.store.MessagesStoreAccessor;
import es.sandbox.ui.messages.store.MessagesStoreAccessorFactory;


public class MessagesStoreFlashScopeAccessorFactory
      implements MessagesStoreAccessorFactory {

   public static final String MESSAGES_PARAMETER= MessagesStoreFlashScopeAccessorFactory.class.getName() + ".MESSAGES";


   /*
    * (non-Javadoc)
    * @see
    * es.sandbox.ui.messages.store.MessagesStoreAccessorFactory#create(javax.servlet.http.HttpServletRequest,
    * java.lang.String)
    */
   @Override
   public MessagesStoreAccessor create(HttpServletRequest request) {
      return new MessagesStoreFlashScopeAccessor(request, MESSAGES_PARAMETER);
   }
}
