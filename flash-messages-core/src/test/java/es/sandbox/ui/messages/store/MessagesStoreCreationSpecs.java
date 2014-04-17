package es.sandbox.ui.messages.store;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.Serializable;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import es.sandbox.test.utils.SerializationUtils;
import es.sandbox.ui.messages.Level;

@RunWith(Enclosed.class)
public class MessagesStoreCreationSpecs {

	public static class DefaultConstructorSpecs {

		@Test
		public void it_should_create_new_instance() {
			assertThat(new MessagesStore()).isNotNull();
		}
	}

	public static class SerializationSpecs {

		@Test
		public void it_should_be_serializable() throws IOException, ClassNotFoundException {
			final MessagesStore sut= new MessagesStoreFixtureGenerator().generate();

			assertThat(sut).isInstanceOf(Serializable.class);
			assertThat(SerializationUtils.isSerializable(sut)).isTrue();
		}
	}

	public static class InitialEmptyStateSpecs {

		private MessagesStore sut;


		@Before
		public void setup() {
			this.sut= new MessagesStore();
		}

		@Test
		public void it_should_be_empty() {
			assertThat(this.sut.isEmpty()).isTrue();
		}

		@Test
		public void it_should_return_an_empty_map() {
			assertThat(this.sut.getMessages()).isEmpty();
		}

		@Test
		public void it_should_not_contains_messages_for_any_level() {

			for (final Level level : Level.values()) {
				assertThat(this.sut.containsMessages(level)).isFalse();
				assertThat(this.sut.getMessages(level)).isEmpty();
			}
		}

		@Test
		public void it_should_to_string() {
			assertThat(this.sut.toString()).isEqualTo("messages [empty]");
		}
	}
}
