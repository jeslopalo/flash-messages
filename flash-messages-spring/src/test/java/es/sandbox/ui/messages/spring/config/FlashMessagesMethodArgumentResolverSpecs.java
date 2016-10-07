package es.sandbox.ui.messages.spring.config;

import es.sandbox.spring.fixture.MockedSpringHttpServletRequest;
import es.sandbox.ui.messages.*;
import es.sandbox.ui.messages.spring.scope.flash.FlashScopeStoreAccessorFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.springframework.context.MessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static es.sandbox.spring.fixture.MockedSpringHttpServletRequest.detachedHttpServletRequest;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@RunWith(Enclosed.class)
public class FlashMessagesMethodArgumentResolverSpecs {

    public static class CreationSpecs {

        @Test(expected = NullPointerException.class)
        public void it_should_raise_an_exception_with_null_context() {
            new FlashMessagesMethodArgumentResolver(null);
        }

        @Test
        public void it_should_be_created_with_context() {
            final Context mockContext = mock(Context.class);

            assertThat(new FlashMessagesMethodArgumentResolver(mockContext)).isNotNull();
        }
    }

    public static class SupportsParameterSpec {

        private MethodParameter mockedMethodParameter;
        private Context mockedContext;

        private HandlerMethodArgumentResolver sut;


        @Before
        public void setup() {
            this.mockedMethodParameter = mock(MethodParameter.class);
            this.mockedContext = mock(Context.class);
            this.sut = new FlashMessagesMethodArgumentResolver(this.mockedContext);
        }

        @Test
        @SuppressWarnings("unchecked")
        public void should_supports_a_messages_parameter() {

            given((Class<Flash>) this.mockedMethodParameter.getParameterType()).willReturn(Flash.class);

            final boolean isSupported = this.sut.supportsParameter(this.mockedMethodParameter);

            assertThat(isSupported).isTrue();

            verify(this.mockedMethodParameter).getParameterType();
        }

        @Test(expected = NullPointerException.class)
        public void should_raise_an_exception_with_null_parameter() {
            this.sut.supportsParameter(null);
        }

        @Test
        @SuppressWarnings("unchecked")
        public void should_not_support_another_type_of_parameters() {
            given((Class<String>) this.mockedMethodParameter.getParameterType()).willReturn(String.class);

            final boolean isSupported = this.sut.supportsParameter(this.mockedMethodParameter);

            assertThat(isSupported).isFalse();

            verify(this.mockedMethodParameter).getParameterType();
        }
    }

    public static class ResolveArgumentSpec {

        private MethodParameter mockedMethodParameter;
        private ModelAndViewContainer mockedMAVContainer;
        private NativeWebRequest mockedNativeWebRequest;
        private MockedSpringHttpServletRequest mockedRequest;
        private WebDataBinderFactory mockedWebDataBinderFactory;
        private MessageSource mockedMessageSource;

        private Context context;

        private FlashMessagesMethodArgumentResolver sut;


        @Before
        public void setup() {
            this.mockedMethodParameter = mock(MethodParameter.class);
            this.mockedMAVContainer = mock(ModelAndViewContainer.class);
            this.mockedNativeWebRequest = mock(NativeWebRequest.class);
            this.mockedRequest = detachedHttpServletRequest();
            this.mockedWebDataBinderFactory = mock(WebDataBinderFactory.class);
            this.mockedMessageSource = mock(MessageSource.class);

            given(this.mockedNativeWebRequest.getNativeRequest()).willReturn(this.mockedRequest);

            this.context = new ContextBuilder(new FlashScopeStoreAccessorFactory())
                .withMessageResolverStrategy(new MessageSourceMessageResolverAdapter(this.mockedMessageSource))
                .build();

            this.sut = new FlashMessagesMethodArgumentResolver(this.context);
        }

        private void initializeOutputFlash(Store store) {
            this.mockedRequest.addOutputFlashAttribute(FlashScopeStoreAccessorFactory.FLASH_MESSAGES_PARAMETER, store);
        }


        @Test
        public void it_should_never_return_null() {
            initializeOutputFlash(new Store());

            final Object argument = this.sut.resolveArgument(this.mockedMethodParameter, this.mockedMAVContainer, this.mockedNativeWebRequest, this.mockedWebDataBinderFactory);
            assertThat(argument).isNotNull();
        }

        @Test
        public void it_should_return_an_argument_instance() {
            initializeOutputFlash(new Store());

            final Object argument = this.sut.resolveArgument(this.mockedMethodParameter, this.mockedMAVContainer, this.mockedNativeWebRequest, this.mockedWebDataBinderFactory);
            assertThat(argument).isInstanceOf(Flash.class);
        }

        @Test(expected = StoreNotFoundException.class)
        public void it_should_raise_an_exception_with_uninitialized_context() {
            this.sut.resolveArgument(this.mockedMethodParameter, this.mockedMAVContainer, this.mockedNativeWebRequest, this.mockedWebDataBinderFactory);
        }
    }
}
