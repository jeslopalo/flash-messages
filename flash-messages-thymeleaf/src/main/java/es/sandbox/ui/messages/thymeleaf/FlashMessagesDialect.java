package es.sandbox.ui.messages.thymeleaf;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.standard.processor.StandardXmlNsTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jeslopalo on 19/12/16.
 */
public class FlashMessagesDialect extends AbstractProcessorDialect {

    private static final String DIALECT_NAME = "Flash Messages Dialect";


    public FlashMessagesDialect() {
        super(DIALECT_NAME, "flash", StandardDialect.PROCESSOR_PRECEDENCE);
    }

    public Set<IProcessor> getProcessors(final String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(new FlashMessagesElementTagProcessor(dialectPrefix));
        // This will remove the xmlns:score attributes we might add for IDE validation
        processors.add(new StandardXmlNsTagProcessor(TemplateMode.HTML, dialectPrefix));
        return processors;
    }
}
