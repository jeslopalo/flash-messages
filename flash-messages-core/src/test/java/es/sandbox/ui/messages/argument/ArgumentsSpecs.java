package es.sandbox.ui.messages.argument;

import es.sandbox.test.utils.ReflectionInvoker;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static es.sandbox.test.asserts.parameter.ParameterAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Enclosed.class)
public class ArgumentsSpecs {

    public static class ClassSpecs {

        @Test
        public void it_should_only_has_private_constructors() {
            final Constructor<?>[] constructors = Arguments.class.getDeclaredConstructors();

            for (final Constructor<?> constructor : constructors) {
                assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();
            }
        }

        @Test(expected = UnsupportedOperationException.class)
        public void should_raise_an_exception_when_force_to_call_to_the_private_contructor()
            throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {

            ReflectionInvoker.privateDefaultConstructor(Arguments.class, UnsupportedOperationException.class);
        }
    }

    public static class TextCreationSpecs {

        @Test
        public void it_should_fail_with_not_valid_code() throws NoSuchMethodException {
            final Method text = Arguments.class.getDeclaredMethod("text", String.class, Object[].class);
            text.setAccessible(true);

            assertThat(text)
                .beingStatic()
                .willThrowNullPointerException()
                .whenInvokedWithNulls();

            assertThat(text)
                .beingStatic()
                .willThrowIllegalArgumentException()
                .whenInvokedWith("", (Object[]) null)
                .whenInvokedWith(" ", (Object[]) null);
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
        public void it_should_fail_with_not_valid_argument() throws NoSuchMethodException {

            final Method link = Arguments.class.getDeclaredMethod("link", String.class);
            link.setAccessible(true);

            assertThat(link)
                .beingStatic()
                .willThrowNullPointerException()
                .whenInvokedWithNulls();

            assertThat(link)
                .beingStatic()
                .willThrowIllegalArgumentException()
                .whenInvokedWith("")
                .whenInvokedWith(" ");
        }

        @Test
        public void it_should_create_new_instance_with_an_url() {
            assertThat(Arguments.link("http://url")).isNotNull();
        }
    }
}
