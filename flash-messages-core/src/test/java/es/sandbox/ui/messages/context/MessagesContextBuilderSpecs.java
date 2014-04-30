package es.sandbox.ui.messages.context;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.resolver.MessageResolverStrategy;
import es.sandbox.ui.messages.store.MessagesStoreAccessorFactory;

@RunWith(Enclosed.class)
public class MessagesContextBuilderSpecs {

   public static class ConstructorSpecs {

      private MessagesStoreAccessorFactory mockFactory;


      @Before
      public void setup() {
         this.mockFactory= mock(MessagesStoreAccessorFactory.class);
      }

      @Test(expected= NullPointerException.class)
      public void it_should_fail_without_factory() {
         new MessagesContextBuilder(null);
      }

      @Test
      public void it_should_be_created_with_factory() {
         new MessagesContextBuilder(this.mockFactory);
      }
   }

   public static class DefaultContextSpecs {

      private MessagesStoreAccessorFactory mockFactory;


      @Before
      public void setup() {
         this.mockFactory= mock(MessagesStoreAccessorFactory.class);
      }

      @Test
      public void it_should_build_a_context() {
         final MessagesContext context= new MessagesContextBuilder(this.mockFactory).build();

         assertThat(context).isNotNull();
      }

      @Test
      public void it_should_be_configured_with_default_values() {
         final MessagesContext context= new MessagesContextBuilder(this.mockFactory).build();

         assertThat(context.toString())
               .contains("factory=MessagesStoreAccessorFactory$$EnhancerByMockitoWithCGLIB")
               .contains("messageResolver=MessageResolver(StringFormatMessageResolverStrategy)")
               .contains("levels=[SUCCESS, INFO, WARNING, ERROR]")
               .contains("cssClassesByLevel=[css classes={SUCCESS=alert alert-success, INFO=alert alert-info, WARNING=alert alert-warning, ERROR=alert alert-error}]");
      }
   }

   public static class WithMessageResolverStrategySpecs {

      private MessagesStoreAccessorFactory mockFactory;
      private MessageResolverStrategy mockStrategy;
      private MessagesContextBuilder sut;


      @Before
      public void setup() {
         this.mockFactory= mock(MessagesStoreAccessorFactory.class);
         this.mockStrategy= mock(MessageResolverStrategy.class);
         this.sut= new MessagesContextBuilder(this.mockFactory);
      }

      @Test(expected= NullPointerException.class)
      public void it_should_fail_without_message_resolver() {
         this.sut.withMessageResolverStrategy(null).build();
      }

      @Test
      public void it_should_build_a_context() {
         final MessagesContext context= this.sut.withMessageResolverStrategy(this.mockStrategy).build();

         assertThat(context).isNotNull();
      }

      @Test
      public void it_should_be_configured_with_default_values() {
         final MessagesContext context= this.sut.withMessageResolverStrategy(this.mockStrategy).build();

         assertThat(context.toString())
               .contains("factory=MessagesStoreAccessorFactory$$EnhancerByMockitoWithCGLIB")
               .contains("messageResolver=MessageResolver(MessageResolverStrategy$$EnhancerByMockitoWithCGLIB")
               .contains("levels=[SUCCESS, INFO, WARNING, ERROR]")
               .contains("cssClassesByLevel=[css classes={SUCCESS=alert alert-success, INFO=alert alert-info, WARNING=alert alert-warning, ERROR=alert alert-error}]");
      }
   }

   public static class WithLevelsSpecs {

      private MessagesStoreAccessorFactory mockFactory;
      private MessagesContextBuilder sut;


      @Before
      public void setup() {
         this.mockFactory= mock(MessagesStoreAccessorFactory.class);
         this.sut= new MessagesContextBuilder(this.mockFactory);
      }

      @Test
      public void it_should_be_empty_with_null_array_of_values() {
         final MessagesContext context= this.sut.withLevels((Level[]) null).build();

         assertThat(context.levels()).isEmpty();
      }

      @Test
      public void it_should_be_empty_with_null_level() {
         final MessagesContext context= this.sut.withLevels((Level) null).build();

         assertThat(context.levels()).isEmpty();
      }

      @Test
      public void it_should_be_empty_without_levels() {
         final MessagesContext context= this.sut.withLevels().build();

         assertThat(context.levels()).isEmpty();
      }

      @Test
      public void it_should_only_contains_not_null_levels() {
         final MessagesContext context= this.sut.withLevels(Level.SUCCESS, null, Level.ERROR).build();

         assertThat(context.levels()).containsOnly(Level.SUCCESS, Level.ERROR);
      }

      @Test
      public void it_should_build_a_context() {
         final MessagesContext context= this.sut.withLevels(Level.SUCCESS).build();

         assertThat(context).isNotNull();
      }

      @Test
      public void it_should_configure_the_order_of_levels() {
         final MessagesContext contextWithDisorderedLevels= this.sut.withLevels(Level.ERROR, Level.INFO, Level.WARNING, Level.SUCCESS).build();
         assertThat(contextWithDisorderedLevels.levels()).containsExactly(Level.ERROR, Level.INFO, Level.WARNING, Level.SUCCESS);

         final MessagesContext contextWithOrderedLevels= this.sut.withLevels(Level.values()).build();
         assertThat(contextWithOrderedLevels.levels()).containsExactly(Level.SUCCESS, Level.INFO, Level.WARNING, Level.ERROR);
      }
   }
}
