package es.sandbox.ui.messages.spring.config.annotation;

import es.sandbox.ui.messages.Context;
import es.sandbox.ui.messages.ContextBuilder;
import es.sandbox.ui.messages.CssClassesByLevel;
import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.StoreAccessorFactory;
import es.sandbox.ui.messages.resolver.MessageResolverStrategy;
import es.sandbox.ui.messages.spring.config.FlashMessagesHandlerInterceptor;
import es.sandbox.ui.messages.spring.config.FlashMessagesMethodArgumentResolver;
import es.sandbox.ui.messages.spring.config.MessageSourceMessageResolverAdapter;
import es.sandbox.ui.messages.spring.scope.flash.FlashScopeStoreAccessorFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerExceptionResolverComposite;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


public class FlashMessagesConfigurationSupport
    extends WebMvcConfigurerAdapter
    implements ApplicationContextAware {

    private HandlerExceptionResolver handlerExceptionResolver;
    private MessageSource messageSource;
    private ApplicationContext applicationContext;


    @Autowired
    void setHandlerExceptionResolver(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Autowired
    void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    /**
     *
     */
    @PostConstruct
    private void configureMessagesExceptionArgumentResolvers() {
        for (final HandlerExceptionResolver resolver : ((HandlerExceptionResolverComposite) this.handlerExceptionResolver).getExceptionResolvers()) {
            if (resolver instanceof ExceptionHandlerExceptionResolver) {
                configureCustomHandlerMethodArgumentResolver((ExceptionHandlerExceptionResolver) resolver);
            }
        }
    }

    private void configureCustomHandlerMethodArgumentResolver(final ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver) {
        final List<HandlerMethodArgumentResolver> resolvers = new ArrayList<HandlerMethodArgumentResolver>();
        resolvers.addAll(exceptionHandlerExceptionResolver.getArgumentResolvers().getResolvers());
        resolvers.add(flashMessagesMethodArgumentResolver());

        exceptionHandlerExceptionResolver.setArgumentResolvers(resolvers);
        exceptionHandlerExceptionResolver.setApplicationContext(this.applicationContext);
        exceptionHandlerExceptionResolver.afterPropertiesSet();
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addArgumentResolvers(java.util.List)
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(flashMessagesMethodArgumentResolver());
    }


    private FlashMessagesMethodArgumentResolver flashMessagesMethodArgumentResolver() {
        return new FlashMessagesMethodArgumentResolver(flashMessagesContext());
    }


    /*
     * (non-Javadoc)
     * @see
     * org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addInterceptors(org.springframework.
     * web.servlet.config.annotation.InterceptorRegistry)
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FlashMessagesHandlerInterceptor(flashMessagesContext()));
    }


    /**
     * @return
     */
    @Bean
    Context flashMessagesContext() {
        return new ContextBuilder(configureFlashStoreAccessorFactory())
            .withMessageResolverStrategy(configureMessageResolverStrategy())
            .withLevels(includedLevels())
            .withCssClassesByLevel(cssClassesByLevel())
            .build();
    }


    /**
     * Override this method to add a custom {@link StoreAccessorFactory}
     *
     * @return
     */
    protected StoreAccessorFactory configureFlashStoreAccessorFactory() {
        return new FlashScopeStoreAccessorFactory();
    }

    /**
     * Override this method to add a custom {@link MessageResolverStrategy}
     *
     * @return
     */
    protected MessageResolverStrategy configureMessageResolverStrategy() {
        return new MessageSourceMessageResolverAdapter(this.messageSource);
    }


    private Level[] includedLevels() {
        return configureIncludedLevels();
    }

    /**
     * Override this method to configure {@link Level} values
     * that will be used.
     *
     * @return
     */
    protected Level[] configureIncludedLevels() {
        return Level.values();
    }


    private CssClassesByLevel cssClassesByLevel() {
        final CssClassesByLevel cssClassesByLevel = new CssClassesByLevel();
        configureCssClassesByLevel(cssClassesByLevel);
        return cssClassesByLevel;
    }

    /**
     * Override this method to configure custom css classes in each {@link Level}
     *
     * @param cssClassesByLevel
     */
    protected void configureCssClassesByLevel(CssClassesByLevel cssClassesByLevel) {

    }
}
