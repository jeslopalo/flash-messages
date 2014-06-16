package es.sandbox.ui.messages;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;


public class StoreNotFoundExceptionSpecs {

   public static class DefaultConstructorSpecs {

      @Test
      public void it_should_be_constructed() {
         assertThat(new StoreNotFoundException()).isNotNull();
      }

      @Test
      public void it_should_be_an_illegalstateexception_instance() {
         assertThat(new StoreNotFoundException()).isInstanceOf(IllegalStateException.class);
      }
   }

   public static class MessageSpecs {

      @Test
      public void it_should_has_message() {
         assertThat(new StoreNotFoundException().getMessage()).isEqualTo("There is not Store. Probably Context is not initialized!");
      }
   }
}
