package es.sandbox.ui.messages;

import es.sandbox.ui.messages.resolver.MessageResolverStrategy;


/**
 * Allow controllers to add flash messages in the view.
 * Some examples:
 * <pre>
 * flash.success(&quot;i18n.message.code&quot;);
 * flash.error(&quot;i18n.message.code&quot;,
 *              1L, new Date());
 * flash.warning(&quot;i18n.message.code&quot;,
 *              Arguments.text(&quot;i18n.argument.code&quot;));
 * flash.info(&quot;i18n.message.code&quot;,
 *              Arguments.link(&quot;/path/to/somewhere&quot;).title(&quot;i18n.argument.code&quot;));
 * </pre>
 *
 * @author jeslopalo
 * @since v0.1
 */
public interface Flash {

    /**
     * Flash a new <b>success message</b>. It will be resolved
     * using the configured {@link MessageResolverStrategy}
     *
     * @param code
     * @param arguments
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    void success(String code, Object... arguments)
        throws NullPointerException, IllegalArgumentException;

    /**
     * Flash a new <b>info message</b>. It will be resolved
     * using the configured {@link MessageResolverStrategy}
     *
     * @param code
     * @param arguments
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    void info(String code, Object... arguments)
        throws NullPointerException, IllegalArgumentException;

    /**
     * Flash a new <b>warning message</b>. It will be resolved
     * using the configured {@link MessageResolverStrategy}
     *
     * @param code
     * @param arguments
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    void warning(String code, Object... arguments)
        throws NullPointerException, IllegalArgumentException;

    /**
     * Flash a new <b>error message</b>. It will be resolved
     * using the configured {@link MessageResolverStrategy}
     *
     * @param code
     * @param arguments
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    void error(String code, Object... arguments)
        throws NullPointerException, IllegalArgumentException;

    /**
     * Clear all messages from all {@link Level}s
     */
    void clear();

    /**
     * Check whether there are no messages
     *
     * @return
     */
    boolean isEmpty();
}
