package es.sandbox.ui.messages;

import es.sandbox.test.utils.SerializationUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Enclosed.class)
public class StoreCreationSpecs {

    public static class DefaultConstructorSpecs {

        @Test
        public void it_should_create_new_instance() {
            assertThat(new Store()).isNotNull();
        }
    }

    public static class SerializationSpecs {

        @Test
        public void it_should_be_serializable() throws IOException, ClassNotFoundException {
            final Store sut = new StoreFixturer().generate();

            assertThat(sut).isInstanceOf(Serializable.class);
            assertThat(SerializationUtils.isSerializable(sut)).isTrue();
        }
    }

    public static class InitialEmptyStateSpecs {

        private Store sut;


        @Before
        public void setup() {
            this.sut = new Store();
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
