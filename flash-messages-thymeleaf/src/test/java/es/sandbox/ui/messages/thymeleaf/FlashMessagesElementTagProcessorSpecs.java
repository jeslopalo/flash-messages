package es.sandbox.ui.messages.thymeleaf;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.lang.reflect.Constructor;

import static es.sandbox.test.asserts.parameter.ParameterAssertions.assertThat;

/**
 * Created by jeslopalo on 29/12/16.
 */
@RunWith(Enclosed.class)
public class FlashMessagesElementTagProcessorSpecs {


    public static final class Creation {

        @Test
        public void it_should_fail_without_dialect_prefix() throws NoSuchMethodException {
            final Constructor constructor = FlashMessagesElementTagProcessor.class.getConstructor(String.class);

            assertThat(constructor)
                .willThrowNullPointerException()
                .whenInvokedWithNulls();

            assertThat(constructor)
                .willThrowIllegalArgumentException()
                .whenInvokedWith("")
                .whenInvokedWith(" ");
        }
    }
}
