package es.sandbox.ui.messages.spring.config;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@RunWith(Enclosed.class)
public class MessageSourceMessageResolverAdapterStrategySpecs {

   @RunWith(MockitoJUnitRunner.class)
   public static class ConstructorSpecs {

      @Mock
      private MessageSource messageSource;


      @Test(expected= NullPointerException.class)
      public void it_should_raise_an_exception_with_null_message_source() {
         new MessageSourceMessageResolverAdapterStrategy(null);
      }

      @Test
      public void it_should_be_constructed() {
         assertThat(new MessageSourceMessageResolverAdapterStrategy(this.messageSource)).isNotNull();
      }
   }

   @RunWith(MockitoJUnitRunner.class)
   public static class MessageSourceDelegationSpecs {

      @Mock
      private MessageSource messageSource;

      private MessageSourceMessageResolverAdapterStrategy sut;


      @Before
      public void setup() {
         this.sut= new MessageSourceMessageResolverAdapterStrategy(this.messageSource);
      }

      @Test
      public void it_should_delegate_getmessage_to_message_source() {

         this.sut.resolve("code", 1L);

         verify(this.messageSource).getMessage("code", new Object[] { 1L }, LocaleContextHolder.getLocale());
      }
   }
}
