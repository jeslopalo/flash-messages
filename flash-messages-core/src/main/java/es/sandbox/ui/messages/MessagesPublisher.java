package es.sandbox.ui.messages;

import java.io.Serializable;

import es.sandbox.ui.messages.resolver.MessageResolver;
import es.sandbox.ui.messages.store.MessagesStore;


public class MessagesPublisher implements Messages {

   private final MessageResolver messageResolver;
   private final MessagesStore store;


   public MessagesPublisher(MessageResolver messageResolver, MessagesStore store) {
      assertThatMessageResolverIsNotNull(messageResolver);
      assertThatMessagesStoreIsNotNull(store);

      this.messageResolver= messageResolver;
      this.store= store;
   }

   private void assertThatMessageResolverIsNotNull(MessageResolver messageResolver) {
      if (messageResolver == null) {
         throw new NullPointerException("MessageResolver can't be null");
      }
   }

   private void assertThatMessagesStoreIsNotNull(MessagesStore store) {
      if (store == null) {
         throw new NullPointerException("MessagesStore can't be null");
      }
   }

   private String resolveMessage(String code, Serializable... arguments) {
      return this.messageResolver.resolve(code, arguments);
   }

   /*
    * (non-Javadoc)
    * @see es.sandbox.ui.messages.Messages#success(java.lang.String, java.io.Serializable[])
    */
   @Override
   public void addSuccess(String code, Serializable... arguments) {
      this.store.add(Level.SUCCESS, resolveMessage(code, arguments));
   }

   /*
    * (non-Javadoc)
    * @see es.sandbox.ui.messages.Messages#info(java.lang.String, java.io.Serializable[])
    */
   @Override
   public void addInfo(String code, Serializable... arguments) {
      this.store.add(Level.INFO, resolveMessage(code, arguments));
   }

   /*
    * (non-Javadoc)
    * @see es.sandbox.ui.messages.Messages#warning(java.lang.String, java.io.Serializable[])
    */
   @Override
   public void addWarning(String code, Serializable... arguments) {
      this.store.add(Level.WARNING, resolveMessage(code, arguments));
   }

   /*
    * (non-Javadoc)
    * @see es.sandbox.ui.messages.Messages#error(java.lang.String, java.io.Serializable[])
    */
   @Override
   public void addError(String code, Serializable... arguments) {
      this.store.add(Level.ERROR, resolveMessage(code, arguments));
   }

   /*
    * (non-Javadoc)
    * @see es.sandbox.ui.messages.Messages#clear()
    */
   @Override
   public void clear() {
      this.store.clear();
   }

   /*
    * (non-Javadoc)
    * @see es.sandbox.ui.messages.Messages#isEmpty()
    */
   @Override
   public boolean isEmpty() {
      return this.store.isEmpty();
   }


   /*
    * (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString() {
      return this.store.toString();
   }
}
