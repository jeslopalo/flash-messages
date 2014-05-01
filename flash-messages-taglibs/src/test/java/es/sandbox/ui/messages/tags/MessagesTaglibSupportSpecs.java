package es.sandbox.ui.messages.tags;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import es.sandbox.test.utils.ReflectionInvoker;
import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.context.MessagesContext;
import es.sandbox.ui.messages.context.MessagesContextBuilder;
import es.sandbox.ui.messages.store.MessagesStoreAccessorFactory;

@RunWith(Enclosed.class)
public class MessagesTaglibSupportSpecs {

   public static final class ConstructorSpecs {

      @Test(expected= UnsupportedOperationException.class)
      public void it_should_raise_an_exception_when_force_to_call_to_the_private_contructor()
            throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {

         ReflectionInvoker.privateDefaultConstructor(MessagesTaglibSupport.class, UnsupportedOperationException.class);
      }
   }

   public static final class LevelsSpecs {

      private HttpServletRequest mockRequest;
      private MessagesStoreAccessorFactory mockFactory;
      private MessagesContextBuilder contextBuilder;


      @Before
      public void setup() {
         this.mockRequest= mock(HttpServletRequest.class);
         this.mockFactory= mock(MessagesStoreAccessorFactory.class);

         this.contextBuilder= new MessagesContextBuilder(this.mockFactory);
      }

      @Test
      public void it_should_return_zero_levels_with_null_request() {
         assertThat(MessagesTaglibSupport.levels(null)).isEmpty();
      }

      @Test
      public void it_should_return_zero_levels_with_no_context() {
         assertThat(MessagesTaglibSupport.levels(this.mockRequest)).isEmpty();

         verify(this.mockRequest, only()).getAttribute(MessagesContext.MESSAGES_CONTEXT_PARAMETER);
      }

      @Test
      public void it_should_return_the_configured_levels_from_context() {
         final MessagesContext context= this.contextBuilder.withLevels(Level.ERROR, Level.SUCCESS).build();

         given(this.mockRequest.getAttribute(MessagesContext.MESSAGES_CONTEXT_PARAMETER)).willReturn(context);

         assertThat(MessagesTaglibSupport.levels(this.mockRequest)).containsExactly(Level.ERROR, Level.SUCCESS);
      }
   }
}
