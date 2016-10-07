package es.sandbox.ui.messages.spring.scope.flash;

import es.sandbox.spring.fixture.MockedSpringHttpServletRequest;
import es.sandbox.ui.messages.StoreAccessor;
import es.sandbox.ui.messages.StoreAccessorFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static es.sandbox.spring.fixture.MockedSpringHttpServletRequest.detachedHttpServletRequest;
import static org.fest.assertions.api.Assertions.assertThat;


@RunWith(Enclosed.class)
public class FlashScopeStoreAccessorFactorySpecs {

    public static class DefaultConstructorSpecs {

        @Test
        public void it_should_create_new_factory() {
            assertThat(new FlashScopeStoreAccessorFactory()).isNotNull();
        }
    }

    public static class CreateAccessorSpecs {

        private MockedSpringHttpServletRequest mockedRequest;
        private StoreAccessorFactory sut;


        @Before
        public void setup() {
            this.mockedRequest = detachedHttpServletRequest();
            this.sut = new FlashScopeStoreAccessorFactory();
        }

        @Test
        public void it_should_create_new_accessor_instance() {
            assertThat(this.sut.create(this.mockedRequest)).isInstanceOf(StoreAccessor.class);
        }

        @Test
        public void it_should_create_flash_scope_based_instance() {
            assertThat(this.sut.create(this.mockedRequest)).isInstanceOf(FlashScopeStoreAccessor.class);
        }
    }
}
