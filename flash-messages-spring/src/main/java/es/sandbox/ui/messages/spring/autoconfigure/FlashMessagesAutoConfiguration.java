package es.sandbox.ui.messages.spring.autoconfigure;

import es.sandbox.ui.messages.CssClassesByLevel;
import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.spring.config.annotation.FlashMessagesConfigurationSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.MessageSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeslopalo on 3/11/16.
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnMissingBean(value = FlashMessagesConfigurationSupport.class, search = SearchStrategy.ALL)
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
@AutoConfigureAfter(MessageSourceAutoConfiguration.class)
@EnableConfigurationProperties(FlashMessagesProperties.class)
public class FlashMessagesAutoConfiguration extends FlashMessagesConfigurationSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlashMessagesAutoConfiguration.class);

    public FlashMessagesAutoConfiguration() {
        LOGGER.info("Load flash-messages autoconfiguration...");
    }

    @Autowired
    private FlashMessagesProperties properties;

    @Override
    public void configureCssClassesByLevel(CssClassesByLevel cssClasses) {
        cssClasses.copyAll(this.properties.getCssClassesByLevel());
    }

    @Override
    public Level[] configureIncludedLevels() {
        return levels(this.properties.getLevels());
    }

    private Level[] levels(List<String> levels) {
        if (levels.isEmpty()) {
            return Level.values();
        }
        final List<Level> values = new ArrayList<Level>();
        for (String level : levels) {
            values.add(Level.valueOf(level));
        }
        return values.toArray(new Level[levels.size()]);
    }
}
