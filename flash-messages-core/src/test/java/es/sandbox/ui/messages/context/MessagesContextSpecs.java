package es.sandbox.ui.messages.context;

import static es.sandbox.test.assertion.ArgumentAssertions.assertThatConstructor;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.Messages;
import es.sandbox.ui.messages.MessagesPublisher;
import es.sandbox.ui.messages.resolver.MessageResolverStrategy;
import es.sandbox.ui.messages.resolver.StringFormatMessageResolverStrategy;
import es.sandbox.ui.messages.store.MessagesStore;
import es.sandbox.ui.messages.store.MessagesStoreAccessor;
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

   public static class InitializingSpecs {

      private MessagesStoreAccessorFactory mockMessagesStoreAccessorFactory;
      private HttpServletRequest mockHttpServletRequest;
      private MessagesStoreAccessor mockMessagesStoreAccessor;
      private MessagesContext sut;


      @Before
      public void setup() {
         this.mockMessagesStoreAccessorFactory= mock(MessagesStoreAccessorFactory.class);
         this.mockHttpServletRequest= mock(HttpServletRequest.class);
         this.mockMessagesStoreAccessor= mock(MessagesStoreAccessor.class);

         this.sut= new MessagesContext(this.mockMessagesStoreAccessorFactory, new StringFormatMessageResolverStrategy());
      }

      @Test(expected= NullPointerException.class)
      public void it_should_raise_an_exception_with_null_request() {
         this.sut.initialize(null);
      }

      @Test
      public void it_should_put_a_new_store_when_there_is_not_a_store() {
         given(this.mockMessagesStoreAccessorFactory.create(this.mockHttpServletRequest)).willReturn(this.mockMessagesStoreAccessor);
         given(this.mockMessagesStoreAccessor.contains()).willReturn(false);

         this.sut.initialize(this.mockHttpServletRequest);

         verify(this.mockMessagesStoreAccessor).put(any(MessagesStore.class));
      }

      @Test
      public void it_should_do_nothing_when_already_there_is_a_store() {
         given(this.mockMessagesStoreAccessorFactory.create(this.mockHttpServletRequest)).willReturn(this.mockMessagesStoreAccessor);
         given(this.mockMessagesStoreAccessor.contains()).willReturn(true);

         this.sut.initialize(this.mockHttpServletRequest);

         verify(this.mockMessagesStoreAccessor, never()).put(any(MessagesStore.class));
      }
   }

   public static class PublisherSpecs {

      private MessagesStoreAccessorFactory mockMessagesStoreAccessorFactory;
      private HttpServletRequest mockHttpServletRequest;
      private MessagesStoreAccessor mockMessagesStoreAccessor;
      private MessagesContext sut;


      @Before
      public void setup() {
         this.mockMessagesStoreAccessorFactory= mock(MessagesStoreAccessorFactory.class);
         this.mockHttpServletRequest= mock(HttpServletRequest.class);
         this.mockMessagesStoreAccessor= mock(MessagesStoreAccessor.class);

         this.sut= new MessagesContext(this.mockMessagesStoreAccessorFactory, new StringFormatMessageResolverStrategy());
      }

      @Test(expected= NullPointerException.class)
      public void it_should_raise_an_exception_with_null_request() {
         this.sut.publisher(null);
      }

      @Test
      public void it_should_return_a_publisher() {
         given(this.mockMessagesStoreAccessorFactory.create(this.mockHttpServletRequest)).willReturn(this.mockMessagesStoreAccessor);
         given(this.mockMessagesStoreAccessor.get()).willReturn(new MessagesStore());

         final Messages messages= this.sut.publisher(this.mockHttpServletRequest);

         assertThat(messages).isInstanceOf(MessagesPublisher.class);
      }
   }

   public static class LevelMessagesSpecs {

      private MessagesStoreAccessorFactory mockMessagesStoreAccessorFactory;
      private HttpServletRequest mockHttpServletRequest;
      private MessagesStoreAccessor mockMessagesStoreAccessor;
      private MessagesContext sut;


      @Before
      public void setup() {
         this.mockMessagesStoreAccessorFactory= mock(MessagesStoreAccessorFactory.class);
         this.mockHttpServletRequest= mock(HttpServletRequest.class);
         this.mockMessagesStoreAccessor= mock(MessagesStoreAccessor.class);

         this.sut= new MessagesContext(this.mockMessagesStoreAccessorFactory, new StringFormatMessageResolverStrategy());
      }

      @Test(expected= NullPointerException.class)
      public void it_should_raise_an_exception_with_null_level() {
         given(this.mockMessagesStoreAccessorFactory.create(this.mockHttpServletRequest)).willReturn(this.mockMessagesStoreAccessor);
         given(this.mockMessagesStoreAccessor.get()).willReturn(new MessagesStore());

         this.sut.levelMessages(null, this.mockHttpServletRequest);
      }

      @Test(expected= NullPointerException.class)
      public void it_should_raise_an_exception_with_null_request() {
         this.sut.levelMessages(Level.SUCCESS, null);
      }

      @Test
      public void it_should_return__messages_by_level() {
         given(this.mockMessagesStoreAccessorFactory.create(this.mockHttpServletRequest)).willReturn(this.mockMessagesStoreAccessor);
         given(this.mockMessagesStoreAccessor.get()).willReturn(new MessagesStore());

         for (final Level level : Level.values()) {
            assertThat(this.sut.levelMessages(level, this.mockHttpServletRequest)).isEmpty();
         }
      }
   }

}
