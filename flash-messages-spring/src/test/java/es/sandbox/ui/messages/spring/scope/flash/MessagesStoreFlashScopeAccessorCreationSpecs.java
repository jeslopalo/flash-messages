package es.sandbox.ui.messages.spring.scope.flash;

import static es.sandbox.spring.fixture.MockedSpringHttpServletRequest.detachedHttpServletRequest;
import static org.fest.assertions.api.Assertions.assertThat;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;


@RunWith(Enclosed.class)
public class MessagesStoreFlashScopeAccessorCreationSpecs {

   public static class ConstructorSpec {

      @Test(expected= NullPointerException.class)
      public void should_raise_an_exception_without_request() {
         new MessagesStoreFlashScopeAccessor(null, "key");
      }

      @Test(expected= NullPointerException.class)
      public void should_raise_an_exception_with_null_flash_parameter() {
         new MessagesStoreFlashScopeAccessor(detachedHttpServletRequest(), null);
      }

      @Test(expected= IllegalArgumentException.class)
      public void should_raise_an_exception_with_empty_flash_parameter() {
         new MessagesStoreFlashScopeAccessor(detachedHttpServletRequest(), "");
      }

      @Test(expected= IllegalArgumentException.class)
      public void should_raise_an_exception_with_blank_flash_parameter() {
         new MessagesStoreFlashScopeAccessor(detachedHttpServletRequest(), " ");
      }

      @Test
      public void should_create_new_instance() {
         final HttpServletRequest request= detachedHttpServletRequest();

         assertThat(new MessagesStoreFlashScopeAccessor(request, "flash-parameter")).isNotNull();
      }
   }
}
