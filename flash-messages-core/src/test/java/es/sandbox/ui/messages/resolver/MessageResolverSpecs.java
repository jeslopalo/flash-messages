package es.sandbox.ui.messages.resolver;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static es.sandbox.ui.messages.resolver.ResolvableFixture.resolvable;
import static org.fest.assertions.api.Assertions.assertThat;


@RunWith(Enclosed.class)
public class MessageResolverSpecs {

    public static class StrategyConstructorSpecs {

        @Test(expected = NullPointerException.class)
        public void it_should_raise_an_exception_with_null_strategy() {
            new MessageResolver(null);
        }

        @Test
        public void it_should_be_constructed() {
            assertThat(new MessageResolver(new StringFormatMessageResolverAdapter())).isNotNull();
        }
    }

    public static class ResolvingSpecs {

        private MessageResolver sut;


        @Before
        public void setup() {
            this.sut = new MessageResolver(new StringFormatMessageResolverAdapter());
        }

        @Test(expected = NullPointerException.class)
        public void it_should_raise_an_exception_with_null_code() {
            this.sut.resolve((String) null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void it_should_raise_an_exception_with_empty_code() {
            this.sut.resolve("");
        }

        @Test(expected = IllegalArgumentException.class)
        public void it_should_raise_an_exception_with_blank_code() {
            this.sut.resolve(" ");
        }

        @Test
        public void it_should_resolve_messages() {
            assertThat(this.sut.resolve("message one")).isEqualTo("message one");
            assertThat(this.sut.resolve("message two")).isEqualTo("message two");
        }

        @Test
        public void it_should_resolve_messages_with_null_argument() {
            assertThat(this.sut.resolve("message", (Object) null)).isEqualTo("message");
        }

        @Test
        public void it_should_resolve_messages_with_null_array_of_arguments() {
            assertThat(this.sut.resolve("message", (Object[]) null)).isEqualTo("message");
        }


        @Test
        public void it_should_resolve_messages_with_arguments() {
            assertThat(this.sut.resolve("message %s", "one")).isEqualTo("message one");
            assertThat(this.sut.resolve("message %s", "two")).isEqualTo("message two");
            assertThat(this.sut.resolve("message %s %s", "one", "two")).isEqualTo("message one two");
        }

        @Test
        public void it_should_resolve_messages_with_resolvable_arguments() {
            assertThat(this.sut.resolve("message %s", resolvable("%s!", "one"))).isEqualTo("message one!");
        }
    }

    public static class ToStringSpecs {

        private MessageResolver sut;


        @Before
        public void setup() {
            this.sut = new MessageResolver(new StringFormatMessageResolverAdapter());
        }

        @Test
        public void it_should_include_strategy_name_in_to_string() {
            assertThat(this.sut.toString()).isEqualTo("MessageResolver(StringFormatMessageResolverAdapter)");
        }
    }

    public static class AssertionSpecs {

        @Test(expected = NullPointerException.class)
        public void it_should_raise_an_exception_with_null_resolver() {
            MessageResolver.assertThatIsNotNull(null);
        }

        @Test
        public void it_should_do_nothing_with_not_null_resolver() {
            MessageResolver.assertThatIsNotNull(new MessageResolver(new StringFormatMessageResolverAdapter()));
        }
    }
}
