package es.sandbox.ui.messages.resolver;


import org.apache.commons.lang3.StringUtils;


public class ResolvableFixture implements Resolvable {

    private final String code;
    private final Object[] arguments;


    /**
     * @param code
     * @param arguments
     * @return
     */
    public static ResolvableFixture resolvable(String code, Object... arguments) {
        return new ResolvableFixture(code, arguments);
    }

    private ResolvableFixture(String code, Object... arguments) {
        assertThatCodeIsValid(code);

        this.code = code;
        this.arguments = arguments;
    }

    private void assertThatCodeIsValid(String code) {
        if (code == null) {
            throw new NullPointerException("Code can't be null");
        }

        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("Code can't be blank");
        }
    }

    /*
     * (non-Javadoc)
     * @see es.sandbox.ui.messages.resolver.Resolvable#resolve(es.sandbox.ui.messages.resolver.MessageResolver)
     */
    @Override
    public String resolve(MessageResolver messageResolver) {
        if (messageResolver == null) {
            throw new NullPointerException("MessageResolver can't be null");
        }

        return messageResolver.resolve(this.code, this.arguments);
    }
}
