package es.sandbox.ui.messages.spring.config.annotation;

import static org.fest.assertions.api.Assertions.assertThat;

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

import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.context.CssClassesByLevel;
import es.sandbox.ui.messages.context.MessagesContext;
import es.sandbox.ui.messages.spring.config.annotation.EnableFlashMessagesDefaultConfigurationSpecs.DefaultMessagesConfigurer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= DefaultMessagesConfigurer.class)
@WebAppConfiguration
public class EnableFlashMessagesDefaultConfigurationSpecs
      implements ApplicationContextAware {

   private ApplicationContext context;
   private CssClassesByLevel defaultCssClasses;


   @Before
   public void setup() {
      this.defaultCssClasses= new CssClassesByLevel();
   }

   @Override
   public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
      this.context= applicationContext;
   }

   @Test
   public void it_should_load_default_messages_context_bean() {
      final MessagesContext defaultContext= this.context.getBean(MessagesContext.class);

      assertThat(defaultContext).isNotNull();
      assertThat(defaultContext.levels()).isEqualTo(Level.values());

      for (final Level level : Level.values()) {
         assertThat(defaultContext.getLevelCssClass(level)).isEqualTo(this.defaultCssClasses.get(level));
      }
   }


   @Configuration
   @EnableFlashMessages
   @Import(FixtureMessagesContextConfiguration.class)
   static class DefaultMessagesConfigurer {

   }
}
