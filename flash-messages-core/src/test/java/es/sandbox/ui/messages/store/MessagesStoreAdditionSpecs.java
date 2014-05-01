package es.sandbox.ui.messages.store;

import static es.sandbox.ui.messages.MessageFixtureGenerator.fixturer;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.entry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fest.assertions.api.ListAssert;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import es.sandbox.test.jodatime.JodaTimeFreezer;
import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.Message;
import es.sandbox.ui.messages.MessageFixtureGenerator;

@RunWith(Enclosed.class)
public class MessagesStoreAdditionSpecs {

   private static final Message MESSAGE_DEFAULT_EXAMPLE= fixturer().generateDefault();


   public static class AddingALevelMessageSpecs {

      private MessagesStore sut;


      @BeforeClass
      public static void freezeTime() {
         JodaTimeFreezer.freeze(MessageFixtureGenerator.EXAMPLE_TIMESTAMP);
      }

      @Before
      public void setup() {
         this.sut= new MessagesStore();
      }

      @AfterClass
      public static void unfreezeTime() {
         JodaTimeFreezer.unfreeze();
      }


      @Test(expected= NullPointerException.class)
      public void it_should_raise_an_exception_without_level() {
         this.sut.add(null, MessageFixtureGenerator.EXAMPLE_TEXT);
      }

      @Test(expected= NullPointerException.class)
      public void it_should_raise_an_exception_without_message() {
         this.sut.add(Level.SUCCESS, null);
      }

      @Test
      public void it_should_not_be_empty() {
         this.sut.add(Level.SUCCESS, MessageFixtureGenerator.EXAMPLE_TEXT);

         assertThat(this.sut.isEmpty()).isFalse();
      }

      @Test
      public void it_should_only_contains_messages_in_that_level() {
         this.sut.add(Level.SUCCESS, MessageFixtureGenerator.EXAMPLE_TEXT);

         for (final Level level : Level.values()) {
            assertThat(this.sut.containsMessages(level)).isEqualTo(level == Level.SUCCESS);
         }
      }

      @Test
      public void it_should_be_possible_to_get_it_only_in_that_level() {
         this.sut.add(MessageFixtureGenerator.EXAMPLE_LEVEL, MessageFixtureGenerator.EXAMPLE_TEXT);

         for (final Level level : Level.values()) {
            final ListAssert<Message> assertion= assertThat(this.sut.getMessages(level));

            if (level == MessageFixtureGenerator.EXAMPLE_LEVEL) {
               assertion.containsExactly(MESSAGE_DEFAULT_EXAMPLE);
            }
            else {
               assertion.doesNotContain(MESSAGE_DEFAULT_EXAMPLE);
            }
         }
      }

      @Test
      public void it_should_be_contained_in_to_string() {
         this.sut.add(Level.SUCCESS, MessageFixtureGenerator.EXAMPLE_TEXT);

         assertThat(this.sut.toString())
               .isEqualTo("messages [{SUCCESS=[[(2010-08-19 14:30:00) SUCCESS: This is an example of message]], INFO=[], WARNING=[], ERROR=[]}]");
      }

      @Test
      public void it_should_be_contained_in_the_messages() {
         this.sut.add(MessageFixtureGenerator.EXAMPLE_LEVEL, MessageFixtureGenerator.EXAMPLE_TEXT);

         assertThat(this.sut.getMessages())
               .containsKey(MessageFixtureGenerator.EXAMPLE_LEVEL)
               .containsValue(Arrays.asList(MESSAGE_DEFAULT_EXAMPLE));
         assertThat(this.sut.getMessages()).doesNotContainKey(Level.ERROR);
         assertThat(this.sut.getMessages()).doesNotContainKey(Level.SUCCESS);
         assertThat(this.sut.getMessages()).doesNotContainKey(Level.WARNING);
      }
   }

   public static class AddingMessageSpecs {

      private Message messageExample;

      private MessagesStore sut;


      @BeforeClass
      public static void setupTime() {
         JodaTimeFreezer.freeze(MessageFixtureGenerator.EXAMPLE_TIMESTAMP);
      }

      @AfterClass
      public static void cleanupTime() {
         JodaTimeFreezer.unfreeze();
      }

      @Before
      public void setup_fixtures() {
         this.messageExample= fixturer().generateDefault();
         this.sut= new MessagesStore();
      }

      @Test
      public void it_should_add_a_message() {
         this.sut.add(this.messageExample);

         assertThat(this.sut.getMessages(this.messageExample.getLevel())).contains(this.messageExample);
      }

      @Test(expected= NullPointerException.class)
      public void it_should_raise_an_exception_with_null_message() {
         this.sut.add(null);
      }
   }


   public static class AddingACollectionOfMessagesSpec {

      private MessagesStore sut;


      @Before
      public void setup() {
         this.sut= new MessagesStore();
      }

      @Test(expected= NullPointerException.class)
      public void it_should_raise_an_exception_without_messages() {
         this.sut.addAll(null);
      }

      @Test(expected= NullPointerException.class)
      public void it_should_raise_an_exception_with_null_messages() {
         final List<Message> messages= fixturer().withLevels(Level.SUCCESS).generateAtLeastOne();
         messages.add(null);

         this.sut.addAll(messages);
      }

      @Test
      public void it_should_do_nothing_with_an_empty_collection() {
         this.sut.addAll(new ArrayList<Message>());

         assertThat(this.sut.isEmpty()).isTrue();
      }

      @Test
      public void it_should_add_messages_by_level() {
         final MessageFixtureGenerator fixturer= fixturer();

         final List<Message> error= fixturer.withLevels(Level.ERROR).generate();
         final List<Message> info= fixturer.withLevels(Level.INFO).generate();
         final List<Message> success= fixturer.withLevels(Level.SUCCESS).generate();
         final List<Message> warning= fixturer.withLevels(Level.WARNING).generate();

         this.sut.addAll(error);
         this.sut.addAll(info);
         this.sut.addAll(success);
         this.sut.addAll(warning);

         assertThatSUTContainsMessages(Level.ERROR, error);
         assertThatSUTContainsMessages(Level.INFO, info);
         assertThatSUTContainsMessages(Level.SUCCESS, success);
         assertThatSUTContainsMessages(Level.WARNING, warning);
      }

      private void assertThatSUTContainsMessages(final Level level, final List<Message> messages) {
         if (messages.isEmpty()) {
            assertThat(this.sut.getMessages()).doesNotContainKey(level);
         }
         else {
            assertThat(this.sut.getMessages()).contains(entry(level, messages));
         }
      }
   }
}
