package es.sandbox.ui.messages.spring.scope.request;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.fest.assertions.api.Assertions.assertThat;


@RunWith(Enclosed.class)
public class NotHttpServletRequestBoundToThreadExceptionSpecs {

    public static class CreationSpec {

        @Test
        public void should_not_be_null() {
            assertThat(new NotHttpServletRequestBoundToThreadException()).isNotNull();
        }

        @Test
        public void should_be_an_instance_of_runtimeexception() {
            assertThat(new NotHttpServletRequestBoundToThreadException()).isInstanceOf(RuntimeException.class);
        }

        @Test
        public void should_has_message() {
            assertThat(new NotHttpServletRequestBoundToThreadException()).hasMessage("There is not an HttpServletRequest bound to the current Thread");
        }
    }
}
