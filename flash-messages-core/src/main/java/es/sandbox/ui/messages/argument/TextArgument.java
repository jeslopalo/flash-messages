package es.sandbox.ui.messages.argument;

import es.sandbox.ui.messages.resolver.MessageResolver;
import es.sandbox.ui.messages.resolver.Resolvable;

import java.util.Arrays;


class TextArgument implements Text, Resolvable {

    private final String code;
    private final Object[] arguments;


    /**
     * @param code
     * @param arguments
     */
    TextArgument(String code, Object... arguments) {
        assertThatCodeIsValid(code);

        this.code = code;
        this.arguments = arguments;
    }

    private void assertThatCodeIsValid(String code) {
        if (code == null) {
            throw new NullPointerException("Text code can't be null");
        }

        if (code.trim().isEmpty()) {
            throw new IllegalArgumentException("Text code can't be empty");
        }
    }

    /*
     * (non-Javadoc)
     * @see es.sandbox.ui.messages.resolver.Resolvable#resolve(es.sandbox.ui.messages.resolver.MessageResolver)
     */
    @Override
    public String resolve(MessageResolver messageResolver) {
        MessageResolver.assertThatIsNotNull(messageResolver);

        return messageResolver.resolve(this.code, this.arguments);
    }

    String getCode() {
        return this.code;
    }

    Object[] getArguments() {
        return this.arguments;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if (this.arguments == null || this.arguments.length == 0) {
            return String.format("text{%s}", this.code);
        }
        return String.format("text{%s, %s}", this.code, Arrays.toString(this.arguments));
    }
}
