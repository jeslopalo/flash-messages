package es.sandbox.ui.messages.context;

import static es.sandbox.test.assertion.ArgumentAssertions.assertThatConstructor;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.resolver.MessageResolverStrategy;
import es.sandbox.ui.messages.resolver.StringFormatMessageResolverStrategy;
import es.sandbox.ui.messages.store.MessagesStoreAccessorFactory;

@RunWith(Enclosed.class)
public class MessagesContextSpecs {

   public static class ConstructorSpecs {

      private MessagesStoreAccessorFactory mockMessagesStoreAccessorFactory;


      @Before
      public void setup() {
         this.mockMessagesStoreAccessorFactory= mock(MessagesStoreAccessorFactory.class);
      }

      @Test
      public void it_should_fail_with_invalid_arguments() {
         assertThatConstructor(MessagesContext.class, MessagesStoreAccessorFactory.class, MessageResolverStrategy.class)
               .throwsNullPointerException()
               .invokedWith(null, new StringFormatMessageResolverStrategy())
               .invokedWith(this.mockMessagesStoreAccessorFactory, null);
      }

      @Test
      public void it_should_create_new_instance() {
         assertThat(new MessagesContext(this.mockMessagesStoreAccessorFactory, new StringFormatMessageResolverStrategy())).isNotNull();
      }

      @Test
      public void it_should_has_default_levels() {
         final MessagesContext sut= new MessagesContext(this.mockMessagesStoreAccessorFactory, new StringFormatMessageResolverStrategy());

         assertThat(sut.levels()).isEqualTo(Level.values());
      }

      @Test
      public void it_should_has_default_css_classes() {
         final MessagesContext sut= new MessagesContext(this.mockMessagesStoreAccessorFactory, new StringFormatMessageResolverStrategy());

         assertThat(sut.getLevelCssClass(Level.ERROR)).isEqualTo("alert alert-error");
         assertThat(sut.getLevelCssClass(Level.INFO)).isEqualTo("alert alert-info");
         assertThat(sut.getLevelCssClass(Level.SUCCESS)).isEqualTo("alert alert-success");
         assertThat(sut.getLevelCssClass(Level.WARNING)).isEqualTo("alert alert-warning");
      }
   }
}
