package es.sandbox.ui.messages;

import es.sandbox.ui.messages.resolver.MessageResolverStrategy;
import es.sandbox.ui.messages.resolver.StringFormatMessageResolverAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;

import static es.sandbox.test.asserts.parameter.ParameterAssertions.assertThat;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(Enclosed.class)
public class ContextSpecs {

    public static class ConstructorSpecs {

        private StoreAccessorFactory mockMessagesStoreAccessorFactory;


        @Before
        public void setup() {
            this.mockMessagesStoreAccessorFactory = mock(StoreAccessorFactory.class);
        }

        @Test
        public void it_should_fail_with_invalid_arguments() throws NoSuchMethodException {

            final Constructor<Context> constructor = Context.class.getDeclaredConstructor(StoreAccessorFactory.class, MessageResolverStrategy.class);
            constructor.setAccessible(true);

            assertThat(constructor)
                .willThrowNullPointerException()
                .whenInvokedWith(null, new StringFormatMessageResolverAdapter())
                .whenInvokedWith(this.mockMessagesStoreAccessorFactory, null);
        }

        @Test
        public void it_should_create_new_instance() {
            assertThat(new Context(this.mockMessagesStoreAccessorFactory, new StringFormatMessageResolverAdapter())).isNotNull();
        }

        @Test
        public void it_should_has_default_levels() {
            final Context sut = new Context(this.mockMessagesStoreAccessorFactory, new StringFormatMessageResolverAdapter());

            assertThat(sut.levels()).containsExactly(Level.values());
        }

        @Test
        public void it_should_has_default_css_classes() {
            final Context sut = new Context(this.mockMessagesStoreAccessorFactory, new StringFormatMessageResolverAdapter());

            assertThat(sut.getLevelCssClass(Level.ERROR)).isEqualTo("alert alert-error");
            assertThat(sut.getLevelCssClass(Level.INFO)).isEqualTo("alert alert-info");
            assertThat(sut.getLevelCssClass(Level.SUCCESS)).isEqualTo("alert alert-success");
            assertThat(sut.getLevelCssClass(Level.WARNING)).isEqualTo("alert alert-warning");
        }
    }

    public static class InitializingSpecs {

        private StoreAccessorFactory mockMessagesStoreAccessorFactory;
        private HttpServletRequest mockHttpServletRequest;
        private StoreAccessor mockMessagesStoreAccessor;
        private Context sut;


        @Before
        public void setup() {
            this.mockMessagesStoreAccessorFactory = mock(StoreAccessorFactory.class);
            this.mockHttpServletRequest = mock(HttpServletRequest.class);
            this.mockMessagesStoreAccessor = mock(StoreAccessor.class);

            this.sut = new Context(this.mockMessagesStoreAccessorFactory, new StringFormatMessageResolverAdapter());
        }

        @Test(expected = NullPointerException.class)
        public void it_should_raise_an_exception_with_null_request() {
            this.sut.initialize(null);
        }

        @Test
        public void it_should_put_a_new_store_when_there_is_not_a_store() {
            given(this.mockMessagesStoreAccessorFactory.create(this.mockHttpServletRequest)).willReturn(this.mockMessagesStoreAccessor);
            given(this.mockMessagesStoreAccessor.contains()).willReturn(false);

            this.sut.initialize(this.mockHttpServletRequest);

            verify(this.mockMessagesStoreAccessor).put(any(Store.class));
        }

        @Test
        public void it_should_do_nothing_when_already_there_is_a_store() {
            given(this.mockMessagesStoreAccessorFactory.create(this.mockHttpServletRequest)).willReturn(this.mockMessagesStoreAccessor);
            given(this.mockMessagesStoreAccessor.contains()).willReturn(true);

            this.sut.initialize(this.mockHttpServletRequest);

            verify(this.mockMessagesStoreAccessor, never()).put(any(Store.class));
        }
    }

    public static class PublisherSpecs {

        private StoreAccessorFactory mockMessagesStoreAccessorFactory;
        private HttpServletRequest mockHttpServletRequest;
        private StoreAccessor mockMessagesStoreAccessor;
        private Context sut;


        @Before
        public void setup() {
            this.mockMessagesStoreAccessorFactory = mock(StoreAccessorFactory.class);
            this.mockHttpServletRequest = mock(HttpServletRequest.class);
            this.mockMessagesStoreAccessor = mock(StoreAccessor.class);

            this.sut = new Context(this.mockMessagesStoreAccessorFactory, new StringFormatMessageResolverAdapter());
        }

        @Test(expected = NullPointerException.class)
        public void it_should_raise_an_exception_with_null_request() {
            this.sut.publisher(null);
        }

        @Test
        public void it_should_return_a_publisher() {
            given(this.mockMessagesStoreAccessorFactory.create(this.mockHttpServletRequest)).willReturn(this.mockMessagesStoreAccessor);
            given(this.mockMessagesStoreAccessor.get()).willReturn(new Store());

            final Flash messages = this.sut.publisher(this.mockHttpServletRequest);

            assertThat(messages).isInstanceOf(FlashPublisher.class);
        }
    }

    public static class LevelMessagesSpecs {

        private StoreAccessorFactory mockMessagesStoreAccessorFactory;
        private HttpServletRequest mockHttpServletRequest;
        private StoreAccessor mockMessagesStoreAccessor;
        private Context sut;


        @Before
        public void setup() {
            this.mockMessagesStoreAccessorFactory = mock(StoreAccessorFactory.class);
            this.mockHttpServletRequest = mock(HttpServletRequest.class);
            this.mockMessagesStoreAccessor = mock(StoreAccessor.class);

            this.sut = new Context(this.mockMessagesStoreAccessorFactory, new StringFormatMessageResolverAdapter());
        }

        @Test(expected = NullPointerException.class)
        public void it_should_raise_an_exception_with_null_level() {
            given(this.mockMessagesStoreAccessorFactory.create(this.mockHttpServletRequest)).willReturn(this.mockMessagesStoreAccessor);
            given(this.mockMessagesStoreAccessor.get()).willReturn(new Store());

            this.sut.levelMessages(null, this.mockHttpServletRequest);
        }

        @Test(expected = NullPointerException.class)
        public void it_should_raise_an_exception_with_null_request() {
            this.sut.levelMessages(Level.SUCCESS, null);
        }

        @Test
        public void it_should_return__messages_by_level() {
            given(this.mockMessagesStoreAccessorFactory.create(this.mockHttpServletRequest)).willReturn(this.mockMessagesStoreAccessor);
            given(this.mockMessagesStoreAccessor.get()).willReturn(new Store());

            for (final Level level : Level.values()) {
                assertThat(this.sut.levelMessages(level, this.mockHttpServletRequest)).isEmpty();
            }
        }
    }

}
