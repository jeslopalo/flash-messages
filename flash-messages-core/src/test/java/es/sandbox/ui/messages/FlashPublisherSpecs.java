package es.sandbox.ui.messages;

import es.sandbox.ui.messages.resolver.MessageResolver;
import es.sandbox.ui.messages.resolver.StringFormatMessageResolverAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static es.sandbox.test.asserts.parameter.ParameterAssertions.assertThat;
import static org.fest.assertions.api.Assertions.assertThat;


@RunWith(Enclosed.class)
public class FlashPublisherSpecs {

    public static class CreationSpecs {

        private MessageResolver messageResolver;
        private Store store;


        @Before
        public void setup() {
            this.messageResolver = new MessageResolver(new StringFormatMessageResolverAdapter());
            this.store = new Store();
        }

        @Test
        public void it_should_fail_with_invalid_arguments() throws NoSuchMethodException {
            final Constructor<FlashPublisher> constructor = FlashPublisher.class.getDeclaredConstructor(MessageResolver.class, Store.class);
            constructor.setAccessible(true);

            assertThat(constructor)
                .willThrowNullPointerException()
                .whenInvokedWithNulls()
                .whenInvokedWith(null, this.store)
                .whenInvokedWith(this.messageResolver, null);
        }

        @Test
        public void it_should_be_created() {
            assertThat(new FlashPublisher(this.messageResolver, this.store)).isNotNull();
        }
    }

    public static class AddingSpecs {

        private MessageResolver messageResolver;
        private Store store;
        private FlashPublisher sut;


        @Before
        public void setup() {
            this.messageResolver = new MessageResolver(new StringFormatMessageResolverAdapter());
            this.store = new Store();
            this.sut = new FlashPublisher(this.messageResolver, this.store);
        }

        @Test
        public void it_should_fail_with_invalid_code() throws NoSuchMethodException {
            for (final Level level : Level.values()) {
                itShouldFailWithInvalidCode(level);
            }
        }

        private void itShouldFailWithInvalidCode(Level level) throws NoSuchMethodException {
            final Method method = FlashPublisher.class.getDeclaredMethod(methodName(level), String.class, Object[].class);
            method.setAccessible(true);

            assertThat(method)
                .in(this.sut)
                .willThrowNullPointerException()
                .whenInvokedWithNulls();

            assertThat(method)
                .in(this.sut)
                .willThrowIllegalArgumentException()
                .whenInvokedWith("", (Object[]) null)
                .whenInvokedWith(" ", (Object[]) null);
        }

        private String methodName(Level level) {
            return level.name().toLowerCase();
        }

        @Test
        public void it_should_add_message_codes_to_the_level() {
            this.sut.info("infoCode");
            assertThat(this.store.getMessages(Level.INFO)).hasSize(1);
            assertThat(this.store.getMessages(Level.INFO).get(0).getText()).isEqualTo("infoCode");

            this.sut.error("errorCode");
            assertThat(this.store.getMessages(Level.ERROR)).hasSize(1);
            assertThat(this.store.getMessages(Level.ERROR).get(0).getText()).isEqualTo("errorCode");

            this.sut.success("successCode");
            assertThat(this.store.getMessages(Level.SUCCESS)).hasSize(1);
            assertThat(this.store.getMessages(Level.SUCCESS).get(0).getText()).isEqualTo("successCode");

            this.sut.warning("warningCode");
            assertThat(this.store.getMessages(Level.WARNING)).hasSize(1);
            assertThat(this.store.getMessages(Level.WARNING).get(0).getText()).isEqualTo("warningCode");
        }

        @Test
        public void it_should_add_messages_to_the_level() {
            this.sut.info("infoCode %s", "!");
            assertThat(this.store.getMessages(Level.INFO)).hasSize(1);
            assertThat(this.store.getMessages(Level.INFO).get(0).getText()).isEqualTo("infoCode !");

            this.sut.error("errorCode %s", "!");
            assertThat(this.store.getMessages(Level.ERROR)).hasSize(1);
            assertThat(this.store.getMessages(Level.ERROR).get(0).getText()).isEqualTo("errorCode !");

            this.sut.success("successCode %s", "!");
            assertThat(this.store.getMessages(Level.SUCCESS)).hasSize(1);
            assertThat(this.store.getMessages(Level.SUCCESS).get(0).getText()).isEqualTo("successCode !");

            this.sut.warning("warningCode %s", "!");
            assertThat(this.store.getMessages(Level.WARNING)).hasSize(1);
            assertThat(this.store.getMessages(Level.WARNING).get(0).getText()).isEqualTo("warningCode !");
        }
    }

    public static class EmptySpecs {

        private MessageResolver messageResolver;
        private Store store;
        private FlashPublisher sut;


        @Before
        public void setup() {
            this.messageResolver = new MessageResolver(new StringFormatMessageResolverAdapter());
            this.store = new Store();
            this.sut = new FlashPublisher(this.messageResolver, this.store);
        }

        @Test
        public void it_should_be_empty_without_messages() {
            assertThat(this.sut.isEmpty()).isTrue();
        }

        @Test
        public void it_should_be_not_empty_with_messages() {
            this.sut.success("code");

            assertThat(this.sut.isEmpty()).isFalse();
        }

        @Test
        public void it_should_delegate_on_store() {
            assertThat(this.sut.isEmpty()).isEqualTo(this.store.isEmpty());

            this.sut.error("code");

            assertThat(this.sut.isEmpty()).isEqualTo(this.store.isEmpty());
        }
    }

    public static class ClearingSpecs {

        private MessageResolver messageResolver;
        private Store store;
        private FlashPublisher sut;


        @Before
        public void setup() {
            this.messageResolver = new MessageResolver(new StringFormatMessageResolverAdapter());
            this.store = new Store();
            this.sut = new FlashPublisher(this.messageResolver, this.store);
        }

        @Test
        public void it_should_do_nothing_without_messages() {
            this.sut.clear();

            assertThat(this.sut.isEmpty()).isTrue();
        }

        @Test
        public void it_should_clear_messages() {
            this.sut.warning("code");

            this.sut.clear();
            assertThat(this.sut.isEmpty()).isTrue();
        }

        @Test
        public void it_should_delegate_on_store() {
            this.store.add(Level.SUCCESS, "message");

            this.sut.clear();
            assertThat(this.store.isEmpty()).isTrue();
        }
    }

    public static class ToStringSpecs {

        private MessageResolver messageResolver;
        private Store store;
        private FlashPublisher sut;


        @Before
        public void setup() {
            this.messageResolver = new MessageResolver(new StringFormatMessageResolverAdapter());
            this.store = new Store();
            this.sut = new FlashPublisher(this.messageResolver, this.store);
        }

        @Test
        public void it_should_delegate_on_store() {
            assertThat(this.sut.toString()).isEqualTo(this.store.toString());

            this.sut.success("message 1");
            assertThat(this.sut.toString()).isEqualTo(this.store.toString());

            this.sut.error("message 2");
            assertThat(this.sut.toString()).isEqualTo(this.store.toString());
        }
    }
}
