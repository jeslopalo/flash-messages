package es.sandbox.ui.messages.thymeleaf;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.context.WebEngineContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.IElementTagStructureHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static es.sandbox.test.asserts.parameter.ParameterAssertions.assertThat;
import static org.mockito.Mockito.mock;

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

    public static final class Processing {

        private FlashMessagesElementTagProcessor sut;

        private ITemplateContext mockTemplateContext;
        private WebEngineContext mockWebEngineContext;
        private IProcessableElementTag mockElementTag;
        private IElementTagStructureHandler mockStructureHandler;

        @Before
        public void setup() {
            this.sut = new FlashMessagesElementTagProcessor("flash");
            this.mockTemplateContext = mock(ITemplateContext.class);
            this.mockWebEngineContext = mock(WebEngineContext.class);
            this.mockElementTag = mock(IProcessableElementTag.class);
            this.mockStructureHandler = mock(IElementTagStructureHandler.class);
        }

        @Test
        public void it_should_fail_with_invalid_arguments() throws NoSuchMethodException {
            final Method method = FlashMessagesElementTagProcessor.class.getDeclaredMethod("doProcess", ITemplateContext.class, IProcessableElementTag.class, IElementTagStructureHandler.class);
            method.setAccessible(true);

            assertThat(method)
                .in(this.sut)
                .willThrowNullPointerException()
                .whenInvokedWithNulls()
                .whenInvokedWith(null, this.mockElementTag, this.mockStructureHandler)
                .whenInvokedWith(this.mockWebEngineContext, null, this.mockStructureHandler)
                .whenInvokedWith(this.mockWebEngineContext, this.mockElementTag, null);

            assertThat(method)
                .in(this.sut)
                .willThrowIllegalArgumentException()
                .whenInvokedWith(this.mockTemplateContext, this.mockElementTag, this.mockStructureHandler);
        }
    }
}
