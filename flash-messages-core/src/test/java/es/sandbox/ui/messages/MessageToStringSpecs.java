package es.sandbox.ui.messages;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static es.sandbox.ui.messages.MessageFixturer.fixturer;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Enclosed.class)
public class MessageToStringSpecs {

    public static class ToStringSpecs {

        private Message sut;


        @Before
        public void setup() {
            this.sut = fixturer().generateDefault();
        }

        @Test
        public void it_should_include_timestamp() {
            assertThat(this.sut.toString()).contains(String.format("%1$tF %1$tT", MessageFixturer.EXAMPLE_TIMESTAMP.toDate()));
        }

        @Test
        public void it_should_include_level() {
            assertThat(this.sut.toString()).contains(this.sut.getLevel().name());
        }

        @Test
        public void it_should_include_text() {
            assertThat(this.sut.toString()).contains(this.sut.getText());
        }

        @Test
        public void it_should_be_deterministic() {
            assertThat(this.sut.toString()).isEqualTo("[(2010-08-19 14:30:00) INFO: This is an example of message]");
        }
    }
}
