package es.sandbox.ui.messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;

public class MessageFixturer {

   public static final DateTime EXAMPLE_TIMESTAMP= new DateTime(2010, 8, 19, 14, 30, 0, 0);
   public static final Level EXAMPLE_LEVEL= Level.INFO;
   public static final String EXAMPLE_TEXT= "This is an example of message";

   private static final int MAX_AMOUNT_OF_RANDOMLY_GENERATED_FIXTURES= 10;
   private static final int MAX_LENGTH_OF_RANDOM_MESSAGE_TEXT= 10;

   private static final Random RANDOM_GENERATOR= new Random(System.currentTimeMillis());

   private List<DateTime> timestamps;
   private Iterator<DateTime> timestampsIterator;

   private List<String> messages;
   private Iterator<String> messagesIterator;

   private List<Level> levels;
   private Iterator<Level> levelsIterator;


   public static final MessageFixturer fixturer() {
      return new MessageFixturer();
   }

   private MessageFixturer() {
   }


   public MessageFixturer withTimestamps(final DateTime... timestamps) {
      this.timestamps= Arrays.asList(timestamps);
      this.timestampsIterator= this.timestamps.iterator();
      return this;
   }

   public MessageFixturer withLevels(Level... levels) {
      this.levels= Arrays.asList(levels);
      this.levelsIterator= this.levels.iterator();
      return this;
   }

   public MessageFixturer withMessages(final String... messages) {
      this.messages= Arrays.asList(messages);
      this.messagesIterator= this.messages.iterator();
      return this;
   }


   /**
    * @return
    */
   public Message generateDefault() {
      return Message.rehydrate(EXAMPLE_TIMESTAMP, EXAMPLE_LEVEL, EXAMPLE_TEXT);
   }

   /**
    * Amount of {@link Message} to be generated. If <code>-1</code> then
    * a random amount will be generated.
    * 
    * @param amount
    * @return
    */
   public List<Message> generate(int amount) {
      final List<Message> fixtures= new ArrayList<Message>();

      for (int messages= 0; messages < randomIfLessThanZero(amount); messages++) {
         fixtures.add(Message.rehydrate(nextTimestamp(), nextLevel(), nextMessage()));
      }

      return fixtures;
   }

   private int randomIfLessThanZero(int amount) {
      return amount < 0? RANDOM_GENERATOR.nextInt(MAX_AMOUNT_OF_RANDOMLY_GENERATED_FIXTURES) : amount;
   }

   /**
    * @return
    */
   public List<Message> generate() {
      return generate(-1);
   }

   /**
    * @return
    */
   public List<Message> generateAtLeastOne() {
      return generate(randomGreaterThanZero());
   }

   private int randomGreaterThanZero() {
      return Math.max(1, randomIfLessThanZero(-1));
   }

   /**
    * @return
    */
   public Message generateOne() {
      return generate(1).get(0);
   }


   private DateTime nextTimestamp() {
      if (this.timestamps == null) {
         return randomTimestamp(new DateTime().minusYears(1).getYear());
      }

      if (!this.timestampsIterator.hasNext()) {
         this.timestampsIterator= this.timestamps.iterator();
      }
      return this.timestampsIterator.next();
   }

   private DateTime randomTimestamp(int year) {
      final DateTime firstMinuteOfYear= new DateTime(year, 1, 1, 0, 0, 0, 0);
      final DateTime lastMinuteOfYear= firstMinuteOfYear.plusYears(1);

      final long offset= firstMinuteOfYear.getMillis();
      final long end= lastMinuteOfYear.getMillis();
      final long diff= end - offset + 1;

      return new DateTime(offset + (long) (Math.random() * diff));
   }


   private String nextMessage() {
      if (this.messages == null) {
         return RandomStringUtils.randomAlphanumeric(MAX_LENGTH_OF_RANDOM_MESSAGE_TEXT);
      }

      if (!this.messagesIterator.hasNext()) {
         this.messagesIterator= this.messages.iterator();
      }
      return this.messagesIterator.next();
   }


   private Level nextLevel() {
      if (this.levels == null) {
         return randomLevel();
      }

      if (!this.levelsIterator.hasNext()) {
         this.levelsIterator= this.levels.iterator();
      }
      return this.levelsIterator.next();
   }

   private Level randomLevel() {
      final Level[] values= Level.values();
      return values[RANDOM_GENERATOR.nextInt(values.length)];
   }
}
