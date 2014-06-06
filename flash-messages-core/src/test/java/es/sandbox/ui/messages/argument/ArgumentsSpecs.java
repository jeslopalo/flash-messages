package es.sandbox.ui.messages.argument;

import static es.sandbox.test.assertion.ArgumentAssertions.arguments;
import static es.sandbox.test.assertion.ArgumentAssertions.assertThatStaticMethod;
import static org.fest.assertions.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import es.sandbox.test.utils.ReflectionInvoker;


@RunWith(Enclosed.class)
public class ArgumentsSpecs {

   public static class ClassSpecs {

      @Test
      public void it_should_only_has_private_constructors() {
         final Constructor<?>[] constructors= Arguments.class.getDeclaredConstructors();

         for (final Constructor<?> constructor : constructors) {
            assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();
         }
      }

      @Test(expected= UnsupportedOperationException.class)
      public void should_raise_an_exception_when_force_to_call_to_the_private_contructor()
            throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {

         ReflectionInvoker.privateDefaultConstructor(Arguments.class, UnsupportedOperationException.class);
      }
   }

   public static class TextCreationSpecs {

      @Test
      public void it_should_fail_with_not_valid_code() {
         assertThatStaticMethod(Arguments.class, "text", arguments(String.class, Object[].class))
               .throwsNullPointerException()
               .invokedWithNulls();

         assertThatStaticMethod(Arguments.class, "text", arguments(String.class, Object[].class))
               .throwsIllegalArgumentException()
               .invokedWith("")
               .invokedWith(" ");
      }

      @Test
      public void it_should_create_new_instance_with_valid_code() {
         assertThat(Arguments.text("code")).isNotNull();
      }

      @Test
      public void it_should_create_new_instance_with_an_argument() {
         assertThat(Arguments.text("code", 2L)).isNotNull();
      }

      @Test
      public void it_should_create_new_instance_with_many_arguments() {
         assertThat(Arguments.text("code", 2L, "e")).isNotNull();
      }
   }

   public static class LinkCreationSpecs {

      @Test
      public void it_should_fail_with_not_valid_argument() {
         assertThatStaticMethod(Arguments.class, "link", arguments(String.class))
               .throwsIllegalArgumentException()
               .invokedWith("")
               .invokedWith(" ");

         assertThatStaticMethod(Arguments.class, "link", arguments(String.class))
               .throwsNullPointerException()
               .invokedWithNulls();
      }

      @Test
      public void it_should_create_new_instance_with_an_url() {
         assertThat(Arguments.link("http://url")).isNotNull();
      }
   }
}
