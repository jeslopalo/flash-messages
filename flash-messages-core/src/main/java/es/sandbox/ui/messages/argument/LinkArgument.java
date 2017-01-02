package es.sandbox.ui.messages.argument;

import es.sandbox.ui.messages.resolver.MessageResolver;
import es.sandbox.ui.messages.resolver.Resolvable;

import java.util.ArrayList;
import java.util.List;


class LinkArgument implements Link, Resolvable {

    private static final String LINK_FORMAT = "<a href=\"%s\" title=\"%s\" class=\"%s\">%s</a>";
    private static final String LINK_FORMAT_WITHOUT_CSS_CLASS = "<a href=\"%s\" title=\"%s\">%s</a>";

    private String url;
    private Text text;
    private String cssClass;


    public LinkArgument(String url) {
        url(url);
    }

    private void assertThatUrlIsValid(String url) {
        if (url == null) {
            throw new NullPointerException("Link url can't be null");
        }

        if (url.trim().isEmpty()) {
            throw new IllegalArgumentException("Link url can't be empty");
        }
    }

    @Override
    public LinkArgument url(String url) {
        assertThatUrlIsValid(url);
        this.url = url;
        return this;
    }

    @Override
    public LinkArgument title(Text text) {
        this.text = text;
        return this;
    }

    @Override
    public LinkArgument title(String code, Object... arguments) {
        this.text = new TextArgument(code, arguments);
        return this;
    }

    @Override
    public LinkArgument cssClass(String cssClass) {
        this.cssClass = trimToNull(cssClass);
        return this;
    }

    private static final String trimToNull(final String theString) {
        if (theString == null) {
            return null;
        }
        final String trimmed = theString.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    @Override
    public String resolve(MessageResolver messageResolver) {
        MessageResolver.assertThatIsNotNull(messageResolver);

        return String.format(linkFormat(), arguments(messageResolver));
    }

    private String linkFormat() {
        return this.cssClass == null ? LINK_FORMAT_WITHOUT_CSS_CLASS : LINK_FORMAT;
    }

    private Object[] arguments(MessageResolver messageResolver) {
        final List<Object> arguments = new ArrayList<Object>();

        final String title = resolveTitle(messageResolver);

        arguments.add(this.url);
        arguments.add(title == null ? this.url : title);
        if (this.cssClass != null) {
            arguments.add(this.cssClass);
        }
        arguments.add(title == null ? this.url : title);

        return arguments.toArray(new Object[0]);
    }

    private String resolveTitle(MessageResolver messageResolver) {
        return trimToNull(this.text == null ? null : ((Resolvable) this.text).resolve(messageResolver));
    }

    @Override
    public String toString() {
        return String.format("link{%s, %s, %s}", this.url, this.text, this.cssClass);
    }
}
