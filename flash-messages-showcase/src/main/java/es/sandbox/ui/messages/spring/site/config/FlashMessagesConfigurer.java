package es.sandbox.ui.messages.spring.site.config;

import org.springframework.context.annotation.Configuration;

import es.sandbox.ui.messages.CssClassesByLevel;
import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.spring.config.annotation.EnableFlashMessages;
import es.sandbox.ui.messages.spring.config.annotation.FlashMessagesConfigurerAdapter;

@Configuration
@EnableFlashMessages
public class FlashMessagesConfigurer
      extends FlashMessagesConfigurerAdapter {

   @Override
   public void configureCssClassesByLevel(CssClassesByLevel cssClasses) {
      cssClasses.put(Level.ERROR, "alert alert-danger");
   }
}
