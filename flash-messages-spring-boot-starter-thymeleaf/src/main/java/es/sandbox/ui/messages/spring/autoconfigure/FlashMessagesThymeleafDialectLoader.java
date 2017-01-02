package es.sandbox.ui.messages.spring.autoconfigure;

import es.sandbox.ui.messages.thymeleaf.FlashMessagesDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring4.SpringTemplateEngine;

/**
 * Created by jeslopalo on 20/12/16.
 */
@Configuration
@ConditionalOnClass(SpringTemplateEngine.class)
@AutoConfigureAfter({WebMvcAutoConfiguration.class, FlashMessagesAutoConfiguration.class})
@AutoConfigureBefore(ThymeleafAutoConfiguration.class)
public class FlashMessagesThymeleafDialectLoader {

    private static final Logger LOG = LoggerFactory.getLogger(FlashMessagesThymeleafDialectLoader.class);

    public FlashMessagesThymeleafDialectLoader() {
        LOG.info("Loading FlashMessagesThymeleafDialectLoader...");
    }

    @Bean
    public FlashMessagesDialect flashMessagesDialect() {
        LOG.info("Loading thymeleaf's flash-messages dialect...");
        return new FlashMessagesDialect();
    }
}
