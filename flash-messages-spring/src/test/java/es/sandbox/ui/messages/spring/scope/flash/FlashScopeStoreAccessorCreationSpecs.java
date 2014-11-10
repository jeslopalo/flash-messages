package es.sandbox.ui.messages.spring.scope.flash;

import static es.sandbox.spring.fixture.MockedSpringHttpServletRequest.detachedHttpServletRequest;
import static es.sandbox.test.assertion.ArgumentAssertions.arguments;
import static es.sandbox.test.assertion.ArgumentAssertions.assertThatConstructor;
import static org.fest.assertions.api.Assertions.assertThat;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import es.sandbox.spring.fixture.MockedSpringHttpServletRequest;
import es.sandbox.ui.messages.Store;


@RunWith(Enclosed.class)
public class FlashScopeStoreAccessorCreationSpecs {

   public static class ConstructorSpec {

      private final String FLASH_PARAMETER_NAME= "flash-parameter";

      private Store store;


      @Before
      public void setup() {
         this.store= new Store();
      }

      @Test
      public void it_should_fail_with_invalid_args() {
         assertThatConstructor(FlashScopeStoreAccessor.class, arguments(HttpServletRequest.class, String.class))
               .throwsNullPointerException()
               .invokedWithNulls()
               .invokedWith(null, "key")
               .invokedWith(detachedHttpServletRequest(), null);

         assertThatConstructor(FlashScopeStoreAccessor.class, arguments(HttpServletRequest.class, String.class))
               .throwsIllegalArgumentException()
               .invokedWith(detachedHttpServletRequest(), "")
               .invokedWith(detachedHttpServletRequest(), " ");
      }

      @Test
      public void it_should_create_new_instance() {
         final HttpServletRequest request= detachedHttpServletRequest();

         assertThat(new FlashScopeStoreAccessor(request, this.FLASH_PARAMETER_NAME)).isNotNull();
      }

      @Test
      public void it_should_do_nothing_without_previous_store_in_flash_scope() {
         final MockedSpringHttpServletRequest mockedRequest= detachedHttpServletRequest();

         final FlashScopeStoreAccessor sut= new FlashScopeStoreAccessor(mockedRequest, this.FLASH_PARAMETER_NAME);

         assertThat(sut).isNotNull();
         mockedRequest.assertThatOutputFlashScopeDoesNotContain(this.FLASH_PARAMETER_NAME);
      }

      @Test
      public void it_should_initialize_with_previous_store_in_flash_scope() {
         final MockedSpringHttpServletRequest mockedRequest= detachedHttpServletRequest();
         mockedRequest.addInputFlashAttribute(this.FLASH_PARAMETER_NAME, this.store);

         new FlashScopeStoreAccessor(mockedRequest, this.FLASH_PARAMETER_NAME);

         mockedRequest.assertThatOutputFlashScopeContains(this.FLASH_PARAMETER_NAME, this.store);
      }
   }
}
