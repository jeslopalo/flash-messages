package es.sandbox.ui.messages;

import static org.fest.assertions.api.Assertions.assertThat;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class MessageRehydrationSpecs {

	private static final DateTime EXAMPLE_TIMESTAMP= MessageFixtureGenerator.EXAMPLE_TIMESTAMP;
	private static final Level EXAMPLE_LEVEL= MessageFixtureGenerator.EXAMPLE_LEVEL;
	private static final String EXAMPLE_TEXT= MessageFixtureGenerator.EXAMPLE_TEXT;


	public static class MessageTimestampSpecs {

		@Test(expected= NullPointerException.class)
		public void it_should_raise_an_exception_with_null_timestamp() {
			Message.rehydrate(null, EXAMPLE_LEVEL, EXAMPLE_TEXT);
		}

		@Test
		public void it_should_be_rehydrated() {
			final Message sut= Message.rehydrate(EXAMPLE_TIMESTAMP, EXAMPLE_LEVEL, EXAMPLE_TEXT);
			assertThat(sut.getTimestamp()).isEqualTo(EXAMPLE_TIMESTAMP);
		}
	}

	public static class MessageLevelSpecs {

		@Test(expected= NullPointerException.class)
		public void it_should_raise_an_exception_with_null_level() {
			Message.rehydrate(EXAMPLE_TIMESTAMP, null, EXAMPLE_TEXT);
		}

		@Test
		public void it_should_be_rehydrated_with_level() {
			assertThat(Message.rehydrate(EXAMPLE_TIMESTAMP, Level.WARNING, EXAMPLE_TEXT).getLevel()).isEqualTo(Level.WARNING);
			assertThat(Message.rehydrate(EXAMPLE_TIMESTAMP, Level.SUCCESS, EXAMPLE_TEXT).getLevel()).isEqualTo(Level.SUCCESS);
			assertThat(Message.rehydrate(EXAMPLE_TIMESTAMP, Level.ERROR, EXAMPLE_TEXT).getLevel()).isEqualTo(Level.ERROR);
			assertThat(Message.rehydrate(EXAMPLE_TIMESTAMP, Level.INFO, EXAMPLE_TEXT).getLevel()).isEqualTo(Level.INFO);
		}
	}

	public static class MessageTextSpecs {

		@Test(expected= NullPointerException.class)
		public void it_should_raise_an_exception_with_null_text() {
			Message.rehydrate(EXAMPLE_TIMESTAMP, EXAMPLE_LEVEL, null);
		}

		@Test(expected= IllegalArgumentException.class)
		public void it_should_raise_an_exception_with_empty_text() {
			Message.rehydrate(EXAMPLE_TIMESTAMP, EXAMPLE_LEVEL, "");
		}

		@Test(expected= IllegalArgumentException.class)
		public void it_should_raise_an_exception_with_blank_text() {
			Message.rehydrate(EXAMPLE_TIMESTAMP, EXAMPLE_LEVEL, " ");
		}

		@Test
		public void it_should_be_rehydrated_with_text() {
			final Message sut= Message.rehydrate(EXAMPLE_TIMESTAMP, EXAMPLE_LEVEL, EXAMPLE_TEXT);

			assertThat(sut).isNotNull();
			assertThat(sut.getText()).isEqualTo(EXAMPLE_TEXT);
		}
	}
}
