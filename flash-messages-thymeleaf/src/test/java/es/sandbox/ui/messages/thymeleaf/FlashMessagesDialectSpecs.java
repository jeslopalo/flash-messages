package es.sandbox.ui.messages.thymeleaf;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.processor.StandardXmlNsTagProcessor;

import java.lang.reflect.Method;
import java.util.Set;

import static es.sandbox.test.asserts.parameter.ParameterAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jeslopalo on 29/12/16.
 */
@RunWith(Enclosed.class)
public class FlashMessagesDialectSpecs {

    public static final class CreationSpecs {

        @Test
        public void it_should_be_created() {
            assertThat(new FlashMessagesDialect()).isNotNull();
        }
    }

    public static final class GettingPrcessorsSpecs {

        private FlashMessagesDialect sut;

        @Before
        public void setup() {
            this.sut = new FlashMessagesDialect();
        }

        @Test
        public void it_should_fail_without_dialect_processor() throws NoSuchMethodException {
            final Method method = FlashMessagesDialect.class.getMethod("getProcessors", String.class);

            assertThat(method)
                .in(this.sut)
                .willThrowNullPointerException()
                .whenInvokedWithNulls();

            assertThat(method)
                .in(this.sut)
                .willThrowIllegalArgumentException()
                .whenInvokedWith("")
                .whenInvokedWith(" ");
        }

        @Test
        public void it_should_get_two_processors() {
            final Set<IProcessor> processors = this.sut.getProcessors("messages");

            assertThat(processors).hasSize(2);
        }

        @Test
        public void it_should_get_a_standarxmlnstagprocessor() {
            final Set<IProcessor> processors = this.sut.getProcessors("messages");

            final long count = processors
                .stream()
                .filter(processor -> !(processor instanceof StandardXmlNsTagProcessor))
                .count();

            assertThat(count).isEqualTo(1);
        }

        @Test
        public void it_should_get_a_flashmessageselementtagprocessor() {
            final Set<IProcessor> processors = this.sut.getProcessors("messages");

            final long count = processors
                .stream()
                .filter(processor -> !(processor instanceof FlashMessagesElementTagProcessor))
                .count();

            assertThat(count).isEqualTo(1);
        }
    }
}
