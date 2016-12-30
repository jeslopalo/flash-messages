package es.sandbox.ui.messages;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Enclosed.class)
public class MessageCreationSpecs {

    private static final Level EXAMPLE_LEVEL = MessageFixturer.EXAMPLE_LEVEL;
    private static final String EXAMPLE_TEXT = MessageFixturer.EXAMPLE_TEXT;


    public static class MessageTimestampSpecs {

        @Test
        public void it_should_contains_creation_timestamp() {
            final Message sut = Message.create(EXAMPLE_LEVEL, EXAMPLE_TEXT);

            assertThat(sut.getTimestamp().toDate()).isToday();
        }

        @Test
        public void it_should_be_incremental() throws InterruptedException {

            final Message sutOne = Message.create(EXAMPLE_LEVEL, EXAMPLE_TEXT);
            Thread.sleep(100);
            final Message sutTwo = Message.create(EXAMPLE_LEVEL, EXAMPLE_TEXT);
            Thread.sleep(100);
            final Message sutThree = Message.create(EXAMPLE_LEVEL, EXAMPLE_TEXT);
            Thread.sleep(100);

            assertThat(sutOne.getTimestamp().toDate()).isBefore(sutTwo.getTimestamp().toDate());
            assertThat(sutTwo.getTimestamp().toDate()).isBefore(sutThree.getTimestamp().toDate());
            assertThat(sutThree.getTimestamp().toDate()).isBefore(new Date());
        }
    }

    public static class MessageLevelSpecs {

        @Test(expected = NullPointerException.class)
        public void it_should_raise_an_exception_with_null_level() {
            Message.create(null, EXAMPLE_TEXT);
        }

        @Test
        public void it_should_be_created_with_level() {
            assertThat(Message.create(Level.WARNING, EXAMPLE_TEXT).getLevel()).isEqualTo(Level.WARNING);
            assertThat(Message.create(Level.SUCCESS, EXAMPLE_TEXT).getLevel()).isEqualTo(Level.SUCCESS);
            assertThat(Message.create(Level.ERROR, EXAMPLE_TEXT).getLevel()).isEqualTo(Level.ERROR);
            assertThat(Message.create(Level.INFO, EXAMPLE_TEXT).getLevel()).isEqualTo(Level.INFO);
        }
    }

    public static class MessageTextSpecs {

        @Test(expected = NullPointerException.class)
        public void it_should_raise_an_exception_with_null_text() {
            Message.create(EXAMPLE_LEVEL, null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void it_should_raise_an_exception_with_empty_text() {
            Message.create(EXAMPLE_LEVEL, "");
        }

        @Test(expected = IllegalArgumentException.class)
        public void it_should_raise_an_exception_with_blank_text() {
            Message.create(EXAMPLE_LEVEL, " ");
        }

        @Test
        public void it_should_be_created_with_text() {
            final Message sut = Message.create(EXAMPLE_LEVEL, EXAMPLE_TEXT);

            assertThat(sut).isNotNull();
            assertThat(sut.getText()).isEqualTo(EXAMPLE_TEXT);
        }
    }
}
