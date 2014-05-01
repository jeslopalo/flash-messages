package es.sandbox.ui.messages.store;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.MessageFixtureGenerator;


@RunWith(Enclosed.class)
public class MessagesStoreSpecs {

	public static class ClearingSpecs {

		private MessagesStore sut;


		@Before
		public void setup() {
			this.sut= new MessagesStoreFixtureGenerator().generate();
		}

		@Test
		public void it_should_clear_messages() {
			this.sut.clear();

			for (final Level level : Level.values()) {
				assertThat(this.sut.containsMessages(level)).isFalse();
			}
		}
	}

	public static class ToStringSpecs {

		private MessageFixtureGenerator messagesFixturer;
		private MessagesStore sut;


		@Before
		public void setup() {
			this.messagesFixturer= MessageFixtureGenerator.fixturer();
			this.sut= new MessagesStore();
			this.sut.add(this.messagesFixturer.generateDefault());
		}

		@Test
		public void it_should_include_messages_in_to_string() {
			assertThat(this.sut.toString()).isEqualTo("messages [{SUCCESS=[], INFO=[[(2010-08-19 14:30:00) INFO: This is an example of message]], WARNING=[], ERROR=[]}]");
		}

		@Test
		public void it_should_include_empty_with_empty_store() {
			assertThat(new MessagesStore().toString()).isEqualTo("messages [empty]");
		}
	}
}
