package es.sandbox.ui.messages.resolver;

import java.util.ArrayList;
import java.util.List;


public class MessageResolver {

    private final MessageResolverStrategy strategy;


    /**
     * @param strategy
     */
    public MessageResolver(MessageResolverStrategy strategy) {
        if (strategy == null) {
            throw new NullPointerException("MessageResolverStrategy can't be null");
        }

        this.strategy = strategy;
    }

    /**
     * @param code
     * @param arguments
     * @return
     */
    public String resolve(String code, Object... arguments) {
        assertThatCodeIsValid(code);

        return this.strategy.resolve(code, arguments(arguments));
    }

    private void assertThatCodeIsValid(String code) {
        if (code == null) {
            throw new NullPointerException("Code can't be null");
        }

        if (code.trim().isEmpty()) {
            throw new IllegalArgumentException("Code can't be blank");
        }
    }

    private Object[] arguments(Object... arguments) {
        final List<Object> resolved = new ArrayList<Object>();
        if (arguments != null) {
            for (final Object argument : arguments) {
                resolved.add(resolveSingle(argument));
            }
        }
        return resolved.toArray(new Object[0]);
    }

    private Object resolveSingle(Object argument) {
        if (argument instanceof Resolvable) {
            return ((Resolvable) argument).resolve(this);
        }
        return argument;
    }

    /**
     * @param messageResolver
     * @throws NullPointerException
     */
    public static void assertThatIsNotNull(MessageResolver messageResolver) throws NullPointerException {
        if (messageResolver == null) {
            throw new NullPointerException("MessageResolver can't be null");
        }
    }

    @Override
    public String toString() {
        return String.format("MessageResolver(%s)", this.strategy.getClass().getSimpleName());
    }
}
