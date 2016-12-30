package es.sandbox.ui.messages.spring.config.annotation;

import es.sandbox.ui.messages.Context;
import es.sandbox.ui.messages.CssClassesByLevel;
import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.StoreAccessorFactory;
import es.sandbox.ui.messages.resolver.MessageResolverStrategy;
import es.sandbox.ui.messages.resolver.StringFormatMessageResolverAdapter;
import es.sandbox.ui.messages.spring.config.annotation.AdvancedConfigurationExtendingAndCustomizingFlashMessagesConfigurationSupportSpecs.ExtendingAndCustomizingFlashMessagesConfigurationSupport;
import es.sandbox.ui.messages.spring.scope.memory.InMemoryStoreAccessorFactory;
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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ExtendingAndCustomizingFlashMessagesConfigurationSupport.class)
@WebAppConfiguration
public class AdvancedConfigurationExtendingAndCustomizingFlashMessagesConfigurationSupportSpecs
    implements ApplicationContextAware {

    private ApplicationContext context;
    private ExtendingAndCustomizingFlashMessagesConfigurationSupport messagesConfiguration;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Before
    public void setup() {
        this.messagesConfiguration = this.context.getBean(ExtendingAndCustomizingFlashMessagesConfigurationSupport.class);
    }

    @Test
    public void it_should_load_customized_messages_context_bean() {
        final Context customizedContext = this.context.getBean(Context.class);

        assertThat(customizedContext).isNotNull();
        assertThat(customizedContext.levels()).containsExactly(Level.SUCCESS, Level.ERROR);

        for (final Level level : Level.values()) {
            assertThat(customizedContext.getLevelCssClass(level)).isEqualTo(level.name().toLowerCase());
        }
    }

    @Test
    public void it_should_configure_dummy_messages_store_accessor_factory() {
        assertThat(this.messagesConfiguration.configureFlashStoreAccessorFactory())
            .isInstanceOf(InMemoryStoreAccessorFactory.class);
    }

    @Test
    public void it_should_configure_string_format_message_resolver_strategy() {
        assertThat(this.messagesConfiguration.configureMessageResolverStrategy())
            .isInstanceOf(StringFormatMessageResolverAdapter.class);
    }

    @Test
    public void it_should_configure_only_success_and_error_levels() {
        assertThat(this.messagesConfiguration.configureIncludedLevels())
            .isEqualTo(new Level[]{Level.SUCCESS, Level.ERROR});
    }

    @Test
    public void it_should_configure_css_classes() {
        final CssClassesByLevel customizedCssClassesByLevel = new CssClassesByLevel();
        this.messagesConfiguration.configureCssClassesByLevel(customizedCssClassesByLevel);

        for (final Level level : Level.values()) {
            assertThat(customizedCssClassesByLevel.get(level)).isEqualTo(level.name().toLowerCase());
        }
    }


    @Configuration
    @Import(FixtureFlashMessagesContextConfiguration.class)
    static class ExtendingAndCustomizingFlashMessagesConfigurationSupport
        extends FlashMessagesConfigurationSupport {


        @Override
        protected StoreAccessorFactory configureFlashStoreAccessorFactory() {
            return new InMemoryStoreAccessorFactory();
        }

        @Override
        protected MessageResolverStrategy configureMessageResolverStrategy() {
            return new StringFormatMessageResolverAdapter();
        }

        @Override
        protected Level[] configureIncludedLevels() {
            return new Level[]{Level.SUCCESS, Level.ERROR};
        }

        @Override
        protected void configureCssClassesByLevel(CssClassesByLevel cssClassesByLevel) {
            for (final Level level : Level.values()) {
                cssClassesByLevel.put(level, level.name().toLowerCase());
            }
        }
    }
}
