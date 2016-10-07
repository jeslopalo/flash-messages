package es.sandbox.ui.messages.spring.config.annotation;

import es.sandbox.ui.messages.Context;
import es.sandbox.ui.messages.CssClassesByLevel;
import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.spring.config.MessageSourceMessageResolverAdapter;
import es.sandbox.ui.messages.spring.config.annotation.ConfigurationByDefaultSpecs.DefaultFlashMessagesConfigurer;
import es.sandbox.ui.messages.spring.scope.flash.FlashScopeStoreAccessorFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DefaultFlashMessagesConfigurer.class)
@WebAppConfiguration
public class ConfigurationByDefaultSpecs
    implements ApplicationContextAware {

    private ApplicationContext context;
    private CssClassesByLevel defaultCssClasses;
    private DelegatingFlashMessagesConfiguration defaultConfiguration;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Before
    public void setup() {
        this.defaultCssClasses = new CssClassesByLevel();
        this.defaultConfiguration = this.context.getBean(DelegatingFlashMessagesConfiguration.class);
    }

    @Test
    public void it_should_load_default_messages_context_bean() {
        final Context defaultContext = this.context.getBean(Context.class);

        assertThat(defaultContext).isNotNull();
        assertThat(defaultContext.levels()).containsExactly(Level.values());

        for (final Level level : Level.values()) {
            assertThat(defaultContext.getLevelCssClass(level)).isEqualTo(this.defaultCssClasses.get(level));
        }
    }

    @Test
    public void it_should_configure_default_messages_store_accessor_factory() {
        assertThat(this.defaultConfiguration.configureFlashStoreAccessorFactory())
            .isInstanceOf(FlashScopeStoreAccessorFactory.class);
    }

    @Test
    public void it_should_configure_default_message_resolver_strategy() {
        assertThat(this.defaultConfiguration.configureMessageResolverStrategy())
            .isInstanceOf(MessageSourceMessageResolverAdapter.class);
    }

    @Test
    public void it_should_configure_default_levels() {
        assertThat(this.defaultConfiguration.configureIncludedLevels())
            .isEqualTo(Level.values());
    }

    @Test
    public void it_should_configure_default_css_classes() {
        final CssClassesByLevel configuredCssClassesByLevel = new CssClassesByLevel(this.defaultCssClasses);
        this.defaultConfiguration.configureCssClassesByLevel(configuredCssClassesByLevel);

        assertThat(configuredCssClassesByLevel)
            .isEqualTo(this.defaultCssClasses);
    }


    @Configuration
    @EnableFlashMessages
    @Import(FixtureFlashMessagesContextConfiguration.class)
    static class DefaultFlashMessagesConfigurer {

    }
}
