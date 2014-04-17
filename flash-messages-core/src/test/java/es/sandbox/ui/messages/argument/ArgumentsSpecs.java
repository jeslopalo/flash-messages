package es.sandbox.ui.messages.argument;

import static org.fest.assertions.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import es.sandbox.test.utils.ReflectionInvoker;
import es.sandbox.ui.messages.argument.Arguments;


@RunWith(Enclosed.class)
public class ArgumentsSpecs {

	public static class TextCreationSpecs {

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

		@Test(expected= NullPointerException.class)
		public void it_should_raise_an_exception_with_null_code() {
			Arguments.text(null);
		}

		@Test(expected= IllegalArgumentException.class)
		public void it_should_raise_an_exception_with_empty_code() {
			Arguments.text("");
		}

		@Test(expected= IllegalArgumentException.class)
		public void it_should_raise_an_exception_with_blank_code() {
			Arguments.text("  ");
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
}
