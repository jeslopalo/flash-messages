package es.sandbox.ui.messages.resolver;


public interface MessageResolverStrategy {

    /**
     * @param code
     * @param arguments
     * @return
     */
    String resolve(String code, Object... arguments);
}
