package es.sandbox.ui.messages.argument;

import static es.sandbox.test.assertion.ArgumentAssertions.assertThatIn;
import static es.sandbox.test.assertion.ArgumentAssertions.blank;
import static es.sandbox.test.assertion.ArgumentAssertions.empty;
import static es.sandbox.test.assertion.ArgumentAssertions.nullArgument;
import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;


@RunWith(Enclosed.class)
public class LinkArgumentSpecs {

	public static class UrlSpecs {

		@Test
		public void it_should_fail_with_invalid_url() {
			assertThatIn(LinkArgument.class)
					.constructorWithArguments(String.class)
					.willThrow(NullPointerException.class)
					.invokedWith(nullArgument());

			assertThatIn(LinkArgument.class)
					.constructorWithArguments(String.class)
					.willThrow(IllegalArgumentException.class)
					.invokedWith(empty())
					.invokedWith(blank());
		}

		@Test
		public void it_should_be_constructed_with_an_url() {
			assertThat(new LinkArgument("/an/url")).isNotNull();
		}
	}
}
