package es.sandbox.ui.messages.spring.scope.flash;

import static es.sandbox.spring.fixture.MockedSpringHttpServletRequest.detachedHttpServletRequest;
import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import es.sandbox.spring.fixture.MockedSpringHttpServletRequest;
import es.sandbox.ui.messages.store.MessagesStore;
import es.sandbox.ui.messages.store.MessagesStoreAccessor;
import es.sandbox.ui.messages.store.MessagesStoreNotFoundException;

@RunWith(Enclosed.class)
public class MessagesStoreFlashScopeAccessorGeneralSpecs {

   private static final String EXAMPLE_KEY= "key";
   private static final MessagesStore EXAMPLE_STORE= new MessagesStore();


   public static class ContainsStoreInFlashScopeSpecs {

      private MockedSpringHttpServletRequest mockedRequest;
      private MessagesStoreAccessor sut;


      @Before
      public void setup() {
         this.mockedRequest= detachedHttpServletRequest();
         this.sut= new MessagesStoreFlashScopeAccessor(this.mockedRequest, EXAMPLE_KEY);
      }

      @Test
      public void it_should_not_contains_store_in_flash() {

         assertThat(this.sut.contains()).isFalse();
      }

      @Test
      public void it_should_contains_store_from_previous_request() {
         this.mockedRequest.addInputFlashAttribute(EXAMPLE_KEY, EXAMPLE_STORE);

         assertThat(this.sut.contains()).isTrue();
      }

      @Test
      public void it_should_contains_store() {
         this.mockedRequest.addOutputFlashAttribute(EXAMPLE_KEY, EXAMPLE_STORE);

         assertThat(this.sut.contains()).isTrue();
      }
   }

   public static class PutStoreInFlashScopeSpecs {

      private MockedSpringHttpServletRequest mockedRequest;
      private MessagesStoreAccessor sut;


      @Before
      public void setup() {
         this.mockedRequest= detachedHttpServletRequest();
         this.sut= new MessagesStoreFlashScopeAccessor(this.mockedRequest, EXAMPLE_KEY);
      }

      @Test
      public void it_should_put_in_flash_scope() {
         this.sut.put(EXAMPLE_STORE);

         this.mockedRequest.assertThatOutputFlashScopeContains(EXAMPLE_KEY, EXAMPLE_STORE);
      }

      @Test
      public void it_should_put_null_in_flash_scope() {
         this.sut.put(null);

         this.mockedRequest.assertThatOutputFlashScopeContains(EXAMPLE_KEY, null);
      }

   }

   public static class GetStoreFromFlashScopeSpec {

      private MockedSpringHttpServletRequest mockedRequest;
      private MessagesStoreAccessor sut;


      @Before
      public void setup() {
         this.mockedRequest= detachedHttpServletRequest();
         this.sut= new MessagesStoreFlashScopeAccessor(this.mockedRequest, EXAMPLE_KEY);
      }

      @Test
      public void it_should_get_the_previous_request_instance_from_flash_scope() {
         this.mockedRequest.addInputFlashAttribute(EXAMPLE_KEY, EXAMPLE_STORE);
         final MessagesStoreAccessor sut= new MessagesStoreFlashScopeAccessor(this.mockedRequest, EXAMPLE_KEY);

         assertThat(sut.get()).isSameAs(EXAMPLE_STORE);
      }

      @Test(expected= MessagesStoreNotFoundException.class)
      public void it_should_raise_an_exception_with_uninitialized_store() {
         this.mockedRequest.addInputFlashAttribute(EXAMPLE_KEY, null);

         this.sut.get();
      }
   }
}
