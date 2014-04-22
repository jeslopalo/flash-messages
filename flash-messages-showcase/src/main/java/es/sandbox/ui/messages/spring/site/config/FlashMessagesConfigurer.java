package es.sandbox.ui.messages.spring.site.config;

import org.springframework.context.annotation.Configuration;

import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.context.CssClassesByLevel;
import es.sandbox.ui.messages.spring.config.annotation.EnableFlashMessages;
import es.sandbox.ui.messages.spring.config.annotation.MessagesConfigurerAdapter;

@Configuration
@EnableFlashMessages
public class FlashMessagesConfigurer
      extends MessagesConfigurerAdapter {


   @Override
   public void configureCssClassesByLevel(CssClassesByLevel cssClasses) {
      cssClasses.put(Level.ERROR, "alert alert-danger");
   }
}
