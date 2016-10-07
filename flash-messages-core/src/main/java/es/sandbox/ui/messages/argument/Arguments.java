package es.sandbox.ui.messages.argument;

/**
 * Build flash message arguments.
 *
 * @author jeslopalo
 * @since v0.1
 */
public class Arguments {

    /**
     * Private constructor to prevent instances
     *
     * @throws UnsupportedOperationException
     */
    private Arguments() {
        throw new UnsupportedOperationException();
    }

    /**
     * Build a new {@link Text} argument. {@link Text} consists of
     * a multilingual code and a number of optional arguments.
     *
     * @param code
     * @param arguments
     * @return
     */
    public static Text text(String code, Object... arguments) {
        return new TextArgument(code, arguments);
    }

    /**
     * Build a new {@link Link} argument. {@link Link} consists of
     * an url.
     *
     * @param url
     * @return
     */
    public static Link link(String url) {
        return new LinkArgument(url);
    }
}
