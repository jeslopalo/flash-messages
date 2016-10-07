package es.sandbox.ui.messages.spring.config;

import es.sandbox.spring.fixture.MockedSpringHttpServletRequest;
import es.sandbox.ui.messages.Context;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import static es.sandbox.spring.fixture.MockedSpringHttpServletRequest.detachedHttpServletRequest;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@RunWith(Enclosed.class)
public class FlashMessagesHandlerInterceptorSpecs {

    public static class ConstructorSpecs {

        private Context mockContext;


        @Before
        public void setup() {
            this.mockContext = mock(Context.class);
        }

        @Test(expected = NullPointerException.class)
        public void it_should_raise_an_exception_with_null_context() {
            new FlashMessagesHandlerInterceptor(null);
        }

        @Test
        public void it_should_be_created() {
            assertThat(new FlashMessagesHandlerInterceptor(this.mockContext)).isNotNull();
        }
    }


    public static class PreHandleSpecs {

        private MockedSpringHttpServletRequest mockRequest;
        private Context mockContext;
        private FlashMessagesHandlerInterceptor sut;


        @Before
        public void setup() {
            this.mockRequest = detachedHttpServletRequest();
            this.mockContext = mock(Context.class);
            this.sut = new FlashMessagesHandlerInterceptor(this.mockContext);
        }

        @Test
        public void it_should_put_context_in_the_request() throws Exception {
            this.sut.preHandle(this.mockRequest, new MockHttpServletResponse(), new Object());

            assertThat(this.mockRequest.getAttribute(Context.FLASH_MESSAGES_CONTEXT_PARAMETER)).isSameAs(this.mockContext);
        }

        @Test
        public void it_should_initialize_context_for_the_request() throws Exception {
            this.sut.preHandle(this.mockRequest, new MockHttpServletResponse(), new Object());

            verify(this.mockContext, only()).initialize(this.mockRequest);
        }

        @Test(expected = NullPointerException.class)
        public void it_should_raise_an_exception_with_null_request() throws Exception {
            this.sut.preHandle(null, new MockHttpServletResponse(), new Object());
        }

        @Test
        public void it_should_ignore_arguments_except_request() throws Exception {
            assertThat(this.sut.preHandle(this.mockRequest, null, new Object())).isTrue();
            assertThat(this.sut.preHandle(this.mockRequest, new MockHttpServletResponse(), null)).isTrue();
            assertThat(this.sut.preHandle(this.mockRequest, new MockHttpServletResponse(), new Object())).isTrue();
            assertThat(this.sut.preHandle(this.mockRequest, new MockHttpServletResponse(), new Object())).isTrue();
        }
    }

    public static class PostHandleSpecs {

        private Context mockContext;
        private FlashMessagesHandlerInterceptor sut;


        @Before
        public void setup() {
            this.mockContext = mock(Context.class);
            this.sut = new FlashMessagesHandlerInterceptor(this.mockContext);
        }

        @Test
        public void it_should_do_nothing() throws Exception {
            this.sut.postHandle(null, null, null, null);
            this.sut.postHandle(null, new MockHttpServletResponse(), new Object(), new ModelAndView());
            this.sut.postHandle(new MockHttpServletRequest(), null, new Object(), new ModelAndView());
            this.sut.postHandle(new MockHttpServletRequest(), new MockHttpServletResponse(), null, new ModelAndView());
            this.sut.postHandle(new MockHttpServletRequest(), new MockHttpServletResponse(), new Object(), null);
            this.sut.postHandle(new MockHttpServletRequest(), new MockHttpServletResponse(), new Object(), new ModelAndView());
        }
    }
}
