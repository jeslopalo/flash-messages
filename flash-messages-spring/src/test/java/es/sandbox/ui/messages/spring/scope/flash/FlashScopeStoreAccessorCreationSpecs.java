package es.sandbox.ui.messages.spring.scope.flash;

import es.sandbox.spring.fixture.MockedSpringHttpServletRequest;
import es.sandbox.ui.messages.Store;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;

import static es.sandbox.spring.fixture.MockedSpringHttpServletRequest.detachedHttpServletRequest;
import static es.sandbox.test.asserts.parameter.ParameterAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(Enclosed.class)
public class FlashScopeStoreAccessorCreationSpecs {

    public static class ConstructorSpec {

        private final String FLASH_PARAMETER_NAME = "flash-parameter";

        private Store store;


        @Before
        public void setup() {
            this.store = new Store();
        }

        @Test
        public void it_should_fail_with_invalid_args() throws NoSuchMethodException {

            final Constructor<FlashScopeStoreAccessor> constructor = FlashScopeStoreAccessor.class.getDeclaredConstructor(HttpServletRequest.class, String.class);
            constructor.setAccessible(true);

            assertThat(constructor)
                .willThrowNullPointerException()
                .whenInvokedWithNulls()
                .whenInvokedWith(null, "key")
                .whenInvokedWith(detachedHttpServletRequest(), null);

            assertThat(constructor)
                .willThrowIllegalArgumentException()
                .whenInvokedWith(detachedHttpServletRequest(), "")
                .whenInvokedWith(detachedHttpServletRequest(), " ");
        }

        @Test
        public void it_should_create_new_instance() {
            final HttpServletRequest request = detachedHttpServletRequest();

            assertThat(new FlashScopeStoreAccessor(request, this.FLASH_PARAMETER_NAME)).isNotNull();
        }

        @Test
        public void it_should_do_nothing_without_previous_store_in_flash_scope() {
            final MockedSpringHttpServletRequest mockedRequest = detachedHttpServletRequest();

            final FlashScopeStoreAccessor sut = new FlashScopeStoreAccessor(mockedRequest, this.FLASH_PARAMETER_NAME);

            assertThat(sut).isNotNull();
            mockedRequest.assertThatOutputFlashScopeDoesNotContain(this.FLASH_PARAMETER_NAME);
        }

        @Test
        public void it_should_initialize_with_previous_store_in_flash_scope() {
            final MockedSpringHttpServletRequest mockedRequest = detachedHttpServletRequest();
            mockedRequest.addInputFlashAttribute(this.FLASH_PARAMETER_NAME, this.store);

            new FlashScopeStoreAccessor(mockedRequest, this.FLASH_PARAMETER_NAME);

            mockedRequest.assertThatOutputFlashScopeContains(this.FLASH_PARAMETER_NAME, this.store);
        }
    }
}
