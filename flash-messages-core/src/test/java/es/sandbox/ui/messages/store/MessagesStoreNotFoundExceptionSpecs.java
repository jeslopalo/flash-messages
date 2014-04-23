package es.sandbox.ui.messages.store;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;


public class MessagesStoreNotFoundExceptionSpecs {

   public static class DefaultConstructorSpecs {

      @Test
      public void it_should_be_constructed() {
         assertThat(new MessagesStoreNotFoundException()).isNotNull();
      }

      @Test
      public void it_should_be_an_illegalstateexception_instance() {
         assertThat(new MessagesStoreNotFoundException()).isInstanceOf(IllegalStateException.class);
      }
   }

   public static class MessageSpecs {

      @Test
      public void it_should_has_message() {
         assertThat(new MessagesStoreNotFoundException().getMessage()).isEqualTo("There is not MessagesStore. Probably MessagesContext is not initialized!");
      }
   }
}
