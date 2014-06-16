package es.sandbox.ui.messages;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import es.sandbox.ui.messages.Context;
import es.sandbox.ui.messages.ContextBuilder;
import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.StoreAccessorFactory;
import es.sandbox.ui.messages.resolver.MessageResolverStrategy;

@RunWith(Enclosed.class)
public class ContextBuilderSpecs {

   public static class ConstructorSpecs {

      private StoreAccessorFactory mockFactory;


      @Before
      public void setup() {
         this.mockFactory= mock(StoreAccessorFactory.class);
      }

      @Test(expected= NullPointerException.class)
      public void it_should_fail_without_factory() {
         new ContextBuilder(null);
      }

      @Test
      public void it_should_be_created_with_factory() {
         new ContextBuilder(this.mockFactory);
      }
   }

   public static class DefaultContextSpecs {

      private StoreAccessorFactory mockFactory;


      @Before
      public void setup() {
         this.mockFactory= mock(StoreAccessorFactory.class);
      }

      @Test
      public void it_should_build_a_context() {
         final Context context= new ContextBuilder(this.mockFactory).build();

         assertThat(context).isNotNull();
      }

      @Test
      public void it_should_be_configured_with_default_values() {
         final Context context= new ContextBuilder(this.mockFactory).build();

         assertThat(context.toString())
               .contains("factory=StoreAccessorFactory$$EnhancerByMockitoWithCGLIB")
               .contains("messageResolver=MessageResolver(StringFormatMessageResolverAdapter)")
               .contains("levels=[SUCCESS, INFO, WARNING, ERROR]")
               .contains("cssClassesByLevel=[css classes={SUCCESS=alert alert-success, INFO=alert alert-info, WARNING=alert alert-warning, ERROR=alert alert-error}]");
      }
   }

   public static class WithMessageResolverStrategySpecs {

      private StoreAccessorFactory mockFactory;
      private MessageResolverStrategy mockStrategy;
      private ContextBuilder sut;


      @Before
      public void setup() {
         this.mockFactory= mock(StoreAccessorFactory.class);
         this.mockStrategy= mock(MessageResolverStrategy.class);
         this.sut= new ContextBuilder(this.mockFactory);
      }

      @Test(expected= NullPointerException.class)
      public void it_should_fail_without_message_resolver() {
         this.sut.withMessageResolverStrategy(null).build();
      }

      @Test
      public void it_should_build_a_context() {
         final Context context= this.sut.withMessageResolverStrategy(this.mockStrategy).build();

         assertThat(context).isNotNull();
      }

      @Test
      public void it_should_be_configured_with_default_values() {
         final Context context= this.sut.withMessageResolverStrategy(this.mockStrategy).build();

         assertThat(context.toString())
               .contains("factory=StoreAccessorFactory$$EnhancerByMockitoWithCGLIB")
               .contains("messageResolver=MessageResolver(MessageResolverStrategy$$EnhancerByMockitoWithCGLIB")
               .contains("levels=[SUCCESS, INFO, WARNING, ERROR]")
               .contains("cssClassesByLevel=[css classes={SUCCESS=alert alert-success, INFO=alert alert-info, WARNING=alert alert-warning, ERROR=alert alert-error}]");
      }
   }

   public static class WithLevelsSpecs {

      private StoreAccessorFactory mockFactory;
      private ContextBuilder sut;


      @Before
      public void setup() {
         this.mockFactory= mock(StoreAccessorFactory.class);
         this.sut= new ContextBuilder(this.mockFactory);
      }

      @Test
      public void it_should_be_empty_with_null_array_of_values() {
         final Context context= this.sut.withLevels((Level[]) null).build();

         assertThat(context.levels()).isEmpty();
      }

      @Test
      public void it_should_be_empty_with_null_level() {
         final Context context= this.sut.withLevels((Level) null).build();

         assertThat(context.levels()).isEmpty();
      }

      @Test
      public void it_should_be_empty_without_levels() {
         final Context context= this.sut.withLevels().build();

         assertThat(context.levels()).isEmpty();
      }

      @Test
      public void it_should_only_contains_not_null_levels() {
         final Context context= this.sut.withLevels(Level.SUCCESS, null, Level.ERROR).build();

         assertThat(context.levels()).containsOnly(Level.SUCCESS, Level.ERROR);
      }

      @Test
      public void it_should_build_a_context() {
         final Context context= this.sut.withLevels(Level.SUCCESS).build();

         assertThat(context).isNotNull();
      }

      @Test
      public void it_should_configure_the_order_of_levels() {
         final Context contextWithDisorderedLevels= this.sut.withLevels(Level.ERROR, Level.INFO, Level.WARNING, Level.SUCCESS).build();
         assertThat(contextWithDisorderedLevels.levels()).containsExactly(Level.ERROR, Level.INFO, Level.WARNING, Level.SUCCESS);

         final Context contextWithOrderedLevels= this.sut.withLevels(Level.values()).build();
         assertThat(contextWithOrderedLevels.levels()).containsExactly(Level.SUCCESS, Level.INFO, Level.WARNING, Level.ERROR);
      }
   }

   public static class WithCssClassesByLevelSpecs {

      private StoreAccessorFactory mockFactory;
      private ContextBuilder sut;


      @Before
      public void setup() {
         this.mockFactory= mock(StoreAccessorFactory.class);
         this.sut= new ContextBuilder(this.mockFactory);
      }

      @Test
      public void it_should_set_null_class_in_all_levels_with_null() {

         this.sut.withCssClassesByLevel(null);

         final Context context= this.sut.build();
         for (final Level level : Level.values()) {
            assertThat(context.getLevelCssClass(level)).isNull();
         }
      }
   }
}
