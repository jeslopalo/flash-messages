package es.sandbox.ui.messages.spring.scope.memory;

import javax.servlet.http.HttpServletRequest;

import es.sandbox.ui.messages.store.MessagesStore;
import es.sandbox.ui.messages.store.MessagesStoreAccessor;
import es.sandbox.ui.messages.store.MessagesStoreAccessorFactory;
import es.sandbox.ui.messages.store.MessagesStoreNotFoundException;


public class InMemoryMessagesStoreAccessorFactory
      implements MessagesStoreAccessorFactory {

   @Override
   public MessagesStoreAccessor create(HttpServletRequest request) {
      return new MessagesStoreAccessor() {

         private MessagesStore store= new MessagesStore();


         @Override
         public void put(MessagesStore store) {
            this.store= store;
         }

         @Override
         public MessagesStore get() throws MessagesStoreNotFoundException {
            if (this.store == null) {
               throw new MessagesStoreNotFoundException();
            }
            return this.store;
         }

         @Override
         public boolean contains() {
            return this.store != null;
         }
      };
   }
}
