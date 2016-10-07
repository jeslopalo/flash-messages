package es.sandbox.ui.messages.tags;

import es.sandbox.test.utils.ReflectionInvoker;
import es.sandbox.ui.messages.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(Enclosed.class)
public class FlashMessagesTaglibSupportSpecs {

    public static final class ConstructorSpecs {

        @Test(expected = UnsupportedOperationException.class)
        public void it_should_raise_an_exception_when_force_to_call_to_the_private_contructor()
            throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {

            ReflectionInvoker.privateDefaultConstructor(FlashMessagesTaglibSupport.class, UnsupportedOperationException.class);
        }
    }

    public static final class LevelsSpecs {

        private HttpServletRequest mockRequest;
        private StoreAccessorFactory mockFactory;
        private ContextBuilder contextBuilder;


        @Before
        public void setup() {
            this.mockRequest = mock(HttpServletRequest.class);
            this.mockFactory = mock(StoreAccessorFactory.class);

            this.contextBuilder = new ContextBuilder(this.mockFactory);
        }

        @Test
        public void it_should_return_zero_levels_with_null_request() {
            assertThat(FlashMessagesTaglibSupport.levels(null)).isEmpty();
        }

        @Test
        public void it_should_return_zero_levels_with_no_context() {
            assertThat(FlashMessagesTaglibSupport.levels(this.mockRequest)).isEmpty();

            verify(this.mockRequest, only()).getAttribute(Context.FLASH_MESSAGES_CONTEXT_PARAMETER);
        }

        @Test
        public void it_should_return_the_configured_levels_from_context() {
            final Context context = this.contextBuilder.withLevels(Level.ERROR, Level.SUCCESS).build();

            given(this.mockRequest.getAttribute(Context.FLASH_MESSAGES_CONTEXT_PARAMETER)).willReturn(context);

            assertThat(FlashMessagesTaglibSupport.levels(this.mockRequest)).containsExactly(Level.ERROR, Level.SUCCESS);
        }
    }

    public static final class LevelCssClassSpecs {

        private HttpServletRequest mockRequest;
        private StoreAccessorFactory mockFactory;
        private ContextBuilder contextBuilder;


        @Before
        public void setup() {
            this.mockRequest = mock(HttpServletRequest.class);
            this.mockFactory = mock(StoreAccessorFactory.class);

            this.contextBuilder = new ContextBuilder(this.mockFactory);
        }

        @Test
        public void it_should_be_empty_without_context_in_request() {
            assertThat(FlashMessagesTaglibSupport.levelCssClass(Level.SUCCESS, this.mockRequest)).isEmpty();
        }

        @Test
        public void it_should_be_empty_without_level() {
            final Context context = this.contextBuilder.build();

            given(this.mockRequest.getAttribute(Context.FLASH_MESSAGES_CONTEXT_PARAMETER)).willReturn(context);

            assertThat(FlashMessagesTaglibSupport.levelCssClass(null, this.mockRequest)).isEmpty();
        }

        @Test
        public void it_should_be_empty_without_request() {
            assertThat(FlashMessagesTaglibSupport.levelCssClass(Level.SUCCESS, null)).isEmpty();
        }

        @Test
        public void it_should_return_level_class() {
            final Context context = this.contextBuilder.build();

            given(this.mockRequest.getAttribute(Context.FLASH_MESSAGES_CONTEXT_PARAMETER)).willReturn(context);

            final CssClassesByLevel defaultsByLevel = new CssClassesByLevel();

            for (final Level level : Level.values()) {
                assertThat(FlashMessagesTaglibSupport.levelCssClass(level, this.mockRequest)).isEqualTo(defaultsByLevel.get(level));
            }
        }
    }

    public static final class LevelMessagesSpecs {

        private HttpServletRequest mockRequest;
        private StoreAccessorFactory mockFactory;
        private StoreAccessor mockAccessor;
        private ContextBuilder contextBuilder;


        @Before
        public void setup() {
            this.mockRequest = mock(HttpServletRequest.class);
            this.mockFactory = mock(StoreAccessorFactory.class);
            this.mockAccessor = mock(StoreAccessor.class);


            this.contextBuilder = new ContextBuilder(this.mockFactory);
        }

        @Test
        public void it_should_be_empty_without_level() {
            final Context context = this.contextBuilder.build();

            given(this.mockRequest.getAttribute(Context.FLASH_MESSAGES_CONTEXT_PARAMETER)).willReturn(context);

            assertThat(FlashMessagesTaglibSupport.levelMessages(null, this.mockRequest)).isEmpty();
        }

        @Test
        public void it_should_be_empty_without_request() {
            final Context context = this.contextBuilder.build();

            given(this.mockRequest.getAttribute(Context.FLASH_MESSAGES_CONTEXT_PARAMETER)).willReturn(context);

            assertThat(FlashMessagesTaglibSupport.levelMessages(Level.SUCCESS, null)).isEmpty();
        }

        @Test
        public void it_should_be_empty_without_messages() {
            final Context context = this.contextBuilder.build();

            given(this.mockRequest.getAttribute(Context.FLASH_MESSAGES_CONTEXT_PARAMETER)).willReturn(context);
            given(this.mockFactory.create(this.mockRequest)).willReturn(this.mockAccessor);
            given(this.mockAccessor.get()).willReturn(new Store());

            assertThat(FlashMessagesTaglibSupport.levelMessages(Level.SUCCESS, this.mockRequest)).isEmpty();
        }
    }
}
