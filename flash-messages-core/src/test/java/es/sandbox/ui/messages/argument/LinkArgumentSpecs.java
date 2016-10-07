package es.sandbox.ui.messages.argument;

import es.sandbox.ui.messages.resolver.MessageResolver;
import es.sandbox.ui.messages.resolver.StringFormatMessageResolverAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static es.sandbox.test.assertion.ArgumentAssertions.*;
import static org.fest.assertions.api.Assertions.assertThat;


@RunWith(Enclosed.class)
public class LinkArgumentSpecs {

    public static class UrlSpecs {

        @Test
        public void it_should_fail_with_invalid_url() {
            assertThatConstructor(LinkArgument.class, arguments(String.class))
                .throwsNullPointerException()
                .invokedWithNulls();

            assertThatConstructor(LinkArgument.class, arguments(String.class))
                .throwsIllegalArgumentException()
                .invokedWith("")
                .invokedWith(" ");
        }

        @Test
        public void it_should_be_constructed_with_an_url() {
            assertThat(new LinkArgument("/an/url")).isNotNull();
        }

        @Test
        public void it_should_be_possible_to_change_url() {
            final LinkArgument link = new LinkArgument("/an/url");

            link.url("/another/one");

            assertThat(link.toString())
                .doesNotContain("/an/url")
                .contains("/another/one");
        }
    }

    public static class TitleSpecs {

        private LinkArgument sut;


        @Before
        public void setup() {
            this.sut = new LinkArgument("/an/url");
        }

        @Test
        public void it_should_do_nothing_with_null_title() {
            this.sut.title(null);
        }

        @Test
        public void it_should_set_link_title() {
            final LinkArgument link = this.sut;

            link.title(new TextArgument("a"));

            assertThat(link.toString())
                .contains("/an/url")
                .contains("text{a}");
        }

        @Test
        public void it_should_fail_with_invalid_code() {
            assertThatMethod(this.sut, "title", arguments(String.class, Object[].class))
                .throwsNullPointerException()
                .invokedWithNulls();

            assertThatMethod(this.sut, "title", arguments(String.class, Object[].class))
                .throwsIllegalArgumentException()
                .invokedWith("", null)
                .invokedWith(" ", null);
        }

        @Test
        public void it_should_work_with_null_argument() {
            this.sut.title("code", (Object) null);

            assertThat(this.sut.toString()).isEqualTo("link{/an/url, text{code, [null]}, null}");
        }

        @Test
        public void it_should_work_with_null_array_of_arguments() {
            this.sut.title("code", (Object[]) null);

            assertThat(this.sut.toString()).isEqualTo("link{/an/url, text{code}, null}");
        }

        @Test
        public void it_should_work_with_code() {
            this.sut.title("code");

            assertThat(this.sut.toString()).isEqualTo("link{/an/url, text{code}, null}");
        }

        @Test
        public void it_should_work_with_code_and_a_sigle_argument() {
            this.sut.title("code", "arg1");

            assertThat(this.sut.toString()).isEqualTo("link{/an/url, text{code, [arg1]}, null}");
        }

        @Test
        public void it_should_work_with_code_and_multiple_arguments() {
            this.sut.title("code", "arg1", "arg2");

            assertThat(this.sut.toString()).isEqualTo("link{/an/url, text{code, [arg1, arg2]}, null}");
        }
    }

    public static class CssClassSpecs {

        private final LinkArgument sut = new LinkArgument("/an/url");


        @Test
        public void it_should_ignore_invalid_css_class() {
            assertThat(this.sut.cssClass(null).toString()).isEqualTo("link{/an/url, null, null}");
            assertThat(this.sut.cssClass("").toString()).isEqualTo("link{/an/url, null, null}");
            assertThat(this.sut.cssClass(" ").toString()).isEqualTo("link{/an/url, null, null}");
        }

        @Test
        public void it_should_set_css_class() {
            assertThat(this.sut.cssClass("theClass").toString()).isEqualTo("link{/an/url, null, theClass}");
        }
    }

    public static class ResolvingSpecs {

        private MessageResolver messageResolver;
        private LinkArgument sut;


        @Before
        public void setup() {

            this.messageResolver = new MessageResolver(new StringFormatMessageResolverAdapter());
            this.sut = new LinkArgument("/an/url");
        }

        @Test
        public void it_should_fail_with_null_message_resolver() {
            this.sut.title("a link").cssClass("theClass");

            assertThat(this.sut.resolve(this.messageResolver))
                .isEqualTo("<a href=\"/an/url\" title=\"a link\" class=\"theClass\">a link</a>");
        }

        @Test
        public void it_should_use_url_without_title() {
            assertThat(this.sut.resolve(this.messageResolver))
                .isEqualTo("<a href=\"/an/url\" title=\"/an/url\">/an/url</a>");
        }

        @Test
        public void it_should_use_resolve_resolvable_title() {
            this.sut.title(Arguments.text("This is %s %s!", "a", "link"));

            assertThat(this.sut.resolve(this.messageResolver))
                .isEqualTo("<a href=\"/an/url\" title=\"This is a link!\">This is a link!</a>");
        }
    }

    public static class ToStringSpecs {

        @Test
        public void it_should_include_values() {
            assertThat(new LinkArgument("/an/url").toString())
                .isEqualTo("link{/an/url, null, null}");

            assertThat(new LinkArgument("/an/url").title("title").toString())
                .isEqualTo("link{/an/url, text{title}, null}");

            assertThat(new LinkArgument("/an/url").cssClass("theClass").toString())
                .isEqualTo("link{/an/url, null, theClass}");

            assertThat(new LinkArgument("/an/url").title("title").cssClass("theClass").toString())
                .isEqualTo("link{/an/url, text{title}, theClass}");
        }
    }
}
