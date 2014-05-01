package es.sandbox.ui.messages;

import static es.sandbox.test.assertion.ArgumentAssertions.assertThatConstructor;
import static es.sandbox.test.assertion.ArgumentAssertions.assertThatMethod;
import static org.fest.assertions.api.Assertions.assertThat;

import java.io.Serializable;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import es.sandbox.ui.messages.resolver.MessageResolver;
import es.sandbox.ui.messages.resolver.StringFormatMessageResolverStrategy;
import es.sandbox.ui.messages.store.MessagesStore;


@RunWith(Enclosed.class)
public class MessagesPublisherSpecs {

   public static class CreationSpecs {

      private MessageResolver messageResolver;
      private MessagesStore messagesStore;


      @Before
      public void setup() {
         this.messageResolver= new MessageResolver(new StringFormatMessageResolverStrategy());
         this.messagesStore= new MessagesStore();
      }

      @Test
      public void it_should_fail_with_invalid_arguments() {
         assertThatConstructor(MessagesPublisher.class, MessageResolver.class, MessagesStore.class)
               .throwsNullPointerException()
               .invokedWithNulls()
               .invokedWith(null, this.messagesStore)
               .invokedWith(this.messageResolver, null);
      }

      @Test
      public void it_should_be_created() {
         assertThat(new MessagesPublisher(this.messageResolver, this.messagesStore)).isNotNull();
      }
   }

   public static class AddingSpecs {

      private MessageResolver messageResolver;
      private MessagesStore messagesStore;
      private MessagesPublisher sut;


      @Before
      public void setup() {
         this.messageResolver= new MessageResolver(new StringFormatMessageResolverStrategy());
         this.messagesStore= new MessagesStore();
         this.sut= new MessagesPublisher(this.messageResolver, this.messagesStore);
      }

      @Test
      public void it_should_fail_with_invalid_code() {
         for (final Level level : Level.values()) {
            itShouldFailWithInvalidCode(level);
         }
      }

      private void itShouldFailWithInvalidCode(Level level) {
         assertThatMethod(this.sut, level.name().toLowerCase(), String.class, Serializable[].class)
               .throwsNullPointerException()
               .invokedWithNulls();

         assertThatMethod(this.sut, level.name().toLowerCase(), String.class, Serializable[].class)
               .throwsIllegalArgumentException()
               .invokedWith("")
               .invokedWith(" ");
      }

      @Test
      public void it_should_add_message_codes_to_the_level() {
         this.sut.info("infoCode");
         assertThat(this.messagesStore.getMessages(Level.INFO)).hasSize(1);
         assertThat(this.messagesStore.getMessages(Level.INFO).get(0).getText()).isEqualTo("infoCode");

         this.sut.error("errorCode");
         assertThat(this.messagesStore.getMessages(Level.ERROR)).hasSize(1);
         assertThat(this.messagesStore.getMessages(Level.ERROR).get(0).getText()).isEqualTo("errorCode");

         this.sut.success("successCode");
         assertThat(this.messagesStore.getMessages(Level.SUCCESS)).hasSize(1);
         assertThat(this.messagesStore.getMessages(Level.SUCCESS).get(0).getText()).isEqualTo("successCode");

         this.sut.warning("warningCode");
         assertThat(this.messagesStore.getMessages(Level.WARNING)).hasSize(1);
         assertThat(this.messagesStore.getMessages(Level.WARNING).get(0).getText()).isEqualTo("warningCode");
      }

      @Test
      public void it_should_add_messages_to_the_level() {
         this.sut.info("infoCode %s", "!");
         assertThat(this.messagesStore.getMessages(Level.INFO)).hasSize(1);
         assertThat(this.messagesStore.getMessages(Level.INFO).get(0).getText()).isEqualTo("infoCode !");

         this.sut.error("errorCode %s", "!");
         assertThat(this.messagesStore.getMessages(Level.ERROR)).hasSize(1);
         assertThat(this.messagesStore.getMessages(Level.ERROR).get(0).getText()).isEqualTo("errorCode !");

         this.sut.success("successCode %s", "!");
         assertThat(this.messagesStore.getMessages(Level.SUCCESS)).hasSize(1);
         assertThat(this.messagesStore.getMessages(Level.SUCCESS).get(0).getText()).isEqualTo("successCode !");

         this.sut.warning("warningCode %s", "!");
         assertThat(this.messagesStore.getMessages(Level.WARNING)).hasSize(1);
         assertThat(this.messagesStore.getMessages(Level.WARNING).get(0).getText()).isEqualTo("warningCode !");
      }
   }

   public static class EmptySpecs {

      private MessageResolver messageResolver;
      private MessagesStore messagesStore;
      private MessagesPublisher sut;


      @Before
      public void setup() {
         this.messageResolver= new MessageResolver(new StringFormatMessageResolverStrategy());
         this.messagesStore= new MessagesStore();
         this.sut= new MessagesPublisher(this.messageResolver, this.messagesStore);
      }

      @Test
      public void it_should_be_empty_without_messages() {
         assertThat(this.sut.isEmpty()).isTrue();
      }

      @Test
      public void it_should_be_not_empty_with_messages() {
         this.sut.success("code");

         assertThat(this.sut.isEmpty()).isFalse();
      }

      @Test
      public void it_should_delegate_on_store() {
         assertThat(this.sut.isEmpty()).isEqualTo(this.messagesStore.isEmpty());

         this.sut.error("code");

         assertThat(this.sut.isEmpty()).isEqualTo(this.messagesStore.isEmpty());
      }
   }

   public static class ClearingSpecs {

      private MessageResolver messageResolver;
      private MessagesStore messagesStore;
      private MessagesPublisher sut;


      @Before
      public void setup() {
         this.messageResolver= new MessageResolver(new StringFormatMessageResolverStrategy());
         this.messagesStore= new MessagesStore();
         this.sut= new MessagesPublisher(this.messageResolver, this.messagesStore);
      }

      @Test
      public void it_should_do_nothing_without_messages() {
         this.sut.clear();

         assertThat(this.sut.isEmpty()).isTrue();
      }

      @Test
      public void it_should_clear_messages() {
         this.sut.warning("code");

         this.sut.clear();
         assertThat(this.sut.isEmpty()).isTrue();
      }

      @Test
      public void it_should_delegate_on_store() {
         this.messagesStore.add(Level.SUCCESS, "message");

         this.sut.clear();
         assertThat(this.messagesStore.isEmpty()).isTrue();
      }
   }

   public static class ToStringSpecs {

      private MessageResolver messageResolver;
      private MessagesStore messagesStore;
      private MessagesPublisher sut;


      @Before
      public void setup() {
         this.messageResolver= new MessageResolver(new StringFormatMessageResolverStrategy());
         this.messagesStore= new MessagesStore();
         this.sut= new MessagesPublisher(this.messageResolver, this.messagesStore);
      }

      @Test
      public void it_should_delegate_on_store() {
         assertThat(this.sut.toString()).isEqualTo(this.messagesStore.toString());

         this.sut.success("message 1");
         assertThat(this.sut.toString()).isEqualTo(this.messagesStore.toString());

         this.sut.error("message 2");
         assertThat(this.sut.toString()).isEqualTo(this.messagesStore.toString());
      }
   }
}
