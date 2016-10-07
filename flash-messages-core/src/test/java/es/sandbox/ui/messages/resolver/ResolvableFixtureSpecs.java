package es.sandbox.ui.messages.resolver;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.fest.assertions.api.Assertions.assertThat;


@RunWith(Enclosed.class)
public class ResolvableFixtureSpecs {

    private static final String EXAMPLE_CODE = "code %s";


    public static class CreationSpecs {

        @Test(expected = NullPointerException.class)
        public void it_should_raise_an_exception_with_null_code() {
            ResolvableFixture.resolvable(null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void it_should_rasie_an_exception_with_empty_code() {
            ResolvableFixture.resolvable("");
        }

        @Test(expected = IllegalArgumentException.class)
        public void it_should_raise_an_exception_with_blank_code() {
            ResolvableFixture.resolvable(" ");
        }

        @Test
        public void it_should_be_created_with_a_code() {
            assertThat(ResolvableFixture.resolvable(EXAMPLE_CODE)).isNotNull();
        }

        @Test
        public void it_should_be_created_with_null_arguments() {
            assertThat(ResolvableFixture.resolvable(EXAMPLE_CODE, (Object) null)).isNotNull();
            assertThat(ResolvableFixture.resolvable(EXAMPLE_CODE, (Object) null, (Object) null)).isNotNull();
        }

        @Test
        public void it_should_be_created_with_null_vararg_arguments() {
            assertThat(ResolvableFixture.resolvable(EXAMPLE_CODE, (Object[]) null)).isNotNull();
        }

        @Test
        public void it_should_be_created_with_one_argument() {
            assertThat(ResolvableFixture.resolvable(EXAMPLE_CODE, 1L)).isNotNull();
        }

        @Test
        public void it_should_be_created_with_varargs_arguments() {
            assertThat(ResolvableFixture.resolvable(EXAMPLE_CODE, 1L, "two")).isNotNull();
        }
    }

    public static class ResolvableSpecs {

        private final MessageResolver messageResolver = new MessageResolver(new StringFormatMessageResolverAdapter());
        private ResolvableFixture sut;


        @Before
        public void setup() {
            this.sut = ResolvableFixture.resolvable(EXAMPLE_CODE, 1L);
        }

        @Test
        public void it_should_be_resolvable() {
            assertThat(this.sut).isInstanceOf(Resolvable.class);
        }

        @Test
        public void it_should_be_resolved_with_a_resolver() {
            assertThat(this.sut.resolve(this.messageResolver)).isEqualTo("code 1");
        }
    }
}
