package es.sandbox.ui.messages.spring.config.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DelegatingFlashMessagesConfiguration.class)
public @interface EnableFlashMessages {

}
