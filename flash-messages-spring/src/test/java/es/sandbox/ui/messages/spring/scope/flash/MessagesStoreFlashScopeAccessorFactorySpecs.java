package es.sandbox.ui.messages.spring.scope.flash;

import static es.sandbox.spring.fixture.MockedSpringHttpServletRequest.detachedHttpServletRequest;
import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import es.sandbox.spring.fixture.MockedSpringHttpServletRequest;
import es.sandbox.ui.messages.store.MessagesStoreAccessor;
import es.sandbox.ui.messages.store.MessagesStoreAccessorFactory;


@RunWith(Enclosed.class)
public class MessagesStoreFlashScopeAccessorFactorySpecs {

   public static class DefaultConstructorSpecs {

      @Test
      public void it_should_create_new_factory() {
         assertThat(new MessagesStoreFlashScopeAccessorFactory()).isNotNull();
      }
   }

   public static class CreateAccessorSpecs {

      private MockedSpringHttpServletRequest mockedRequest;
      private MessagesStoreAccessorFactory sut;


      @Before
      public void setup() {
         this.mockedRequest= detachedHttpServletRequest();
         this.sut= new MessagesStoreFlashScopeAccessorFactory();
      }

      @Test
      public void it_should_create_new_accessor_instance() {
         assertThat(this.sut.create(this.mockedRequest)).isInstanceOf(MessagesStoreAccessor.class);
      }

      @Test
      public void it_should_create_flash_scope_based_instance() {
         assertThat(this.sut.create(this.mockedRequest)).isInstanceOf(MessagesStoreFlashScopeAccessor.class);
      }
   }
}
