package es.sandbox.ui.messages;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.fest.assertions.api.Assertions.assertThat;


@RunWith(Enclosed.class)
public class StoreSpecs {

    public static class ContainsMessagesByLevelSpecs {

        private Store sut;


        @Before
        public void setup() {
            this.sut = new StoreFixturer()
                .error(1)
                .warning(1)
                .success(1)
                .info(1)
                .generate();
        }

        @Test(expected = NullPointerException.class)
        public void it_should_fail_without_level() {
            new Store().containsMessages(null);
        }

        @Test
        public void it_should_does_not_contain_messages() {
            final Store store = new Store();
            for (final Level level : Level.values()) {
                assertThat(store.containsMessages(level)).isFalse();
            }
        }

        @Test
        public void it_should_contains_messages() {
            for (final Level level : Level.values()) {
                assertThat(this.sut.containsMessages(level)).isTrue();
            }
        }
    }

    public static class ClearingSpecs {

        private Store sut;


        @Before
        public void setup() {
            this.sut = new StoreFixturer().generate();
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

        private MessageFixturer messagesFixturer;
        private Store sut;


        @Before
        public void setup() {
            this.messagesFixturer = MessageFixturer.fixturer();
            this.sut = new Store();
            this.sut.add(this.messagesFixturer.generateDefault());
        }

        @Test
        public void it_should_include_messages_in_to_string() {
            assertThat(this.sut.toString()).isEqualTo("messages [{SUCCESS=[], INFO=[[(2010-08-19 14:30:00) INFO: This is an example of message]], WARNING=[], ERROR=[]}]");
        }

        @Test
        public void it_should_include_empty_with_empty_store() {
            assertThat(new Store().toString()).isEqualTo("messages [empty]");
        }
    }
}
