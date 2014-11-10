package es.sandbox.ui.messages.argument;

import static es.sandbox.test.assertion.ArgumentAssertions.arguments;
import static es.sandbox.test.assertion.ArgumentAssertions.assertThatConstructor;
import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import es.sandbox.ui.messages.resolver.MessageResolver;
import es.sandbox.ui.messages.resolver.StringFormatMessageResolverAdapter;


@RunWith(Enclosed.class)
public class TextArgumentSpecs {

   public static class CodeSpecs {

      @Test
      public void it_should_fail_with_invalid_code() {
         assertThatConstructor(TextArgument.class, arguments(String.class, Object[].class))
               .throwsNullPointerException()
               .invokedWithNulls();

         assertThatConstructor(TextArgument.class, arguments(String.class, Object[].class))
               .throwsIllegalArgumentException()
               .invokedWith("", null)
               .invokedWith(" ", null);
      }

      @Test
      public void it_should_create_new_instance_with_valid_code() {
         assertThat(new TextArgument("code")).isNotNull();
      }

      @Test
      public void it_should_be_accessible() {
         final TextArgument sut= new TextArgument("code");

         assertThat(sut.getCode()).isEqualTo("code");
      }
   }

   public static class ArgumentsSpecs {

      @Test
      public void it_should_be_posible_to_create_an_instance_without_arguments() {
         assertThat(new TextArgument("code")).isNotNull();
      }

      @Test
      public void it_should_create_new_instance_with_an_argument() {
         assertThat(new TextArgument("code", 2L)).isNotNull();
      }

      @Test
      public void it_should_create_new_instance_with_many_arguments() {
         assertThat(new TextArgument("code", 2L, "e")).isNotNull();
      }

      @Test
      public void it_should_be_accessible() {
         final TextArgument sut= new TextArgument("code", 2L, "e");

         assertThat(sut.getArguments()).containsExactly(2L, "e");
      }
   }

   public static class ResolvableSpecs {

      @Test
      public void it_should_resolve_delegating_in_message_resolver() {
         final TextArgument sut= new TextArgument("code %s", 3L);

         assertThat(sut.resolve(new MessageResolver(new StringFormatMessageResolverAdapter()))).isEqualTo("code 3");
      }
   }

   public static class ToStringSpecs {

      @Test
      public void it_should_to_string_only_code() {
         assertThat(new TextArgument("code").toString()).isEqualTo("text{code}");
      }

      @Test
      public void it_should_to_string_code_and_arguments() {
         assertThat(new TextArgument("code", 1L).toString()).isEqualTo("text{code, [1]}");
         assertThat(new TextArgument("code", 1L, "e").toString()).isEqualTo("text{code, [1, e]}");
         assertThat(new TextArgument("code", 1L, "e", 'c').toString()).isEqualTo("text{code, [1, e, c]}");
      }
   }
}
