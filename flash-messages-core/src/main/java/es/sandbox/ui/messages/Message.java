package es.sandbox.ui.messages;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

/**
 * <b>Message</b>
 * 
 * @author 03/08/2012 - jesuslopez
 */
public final class Message
      implements Serializable {

   private static final long serialVersionUID= 331280984300887014L;

   private final DateTime timestamp;
   private final Level level;
   private final String text;


   /**
    * @param level
    * @param text
    * @return
    */
   static Message create(Level level, String text) {
      return new Message(level, text);
   }

   private Message(Level level, String text) {
      this(new DateTime(), level, text);
   }

   /**
    * @param timestamp
    * @param level
    * @param text
    * @return
    */
   static Message rehydrate(DateTime timestamp, Level level, String text) {
      return new Message(timestamp, level, text);
   }

   private Message(DateTime timestamp, Level level, String text) {
      assertThatTimestampIsNotNull(timestamp);
      assertThatTextIsValid(text);
      assertThatLevelIsNotNull(level);

      this.timestamp= timestamp;
      this.level= level;
      this.text= text;
   }

   private void assertThatTextIsValid(final String text)
         throws NullPointerException, IllegalArgumentException {

      if (text == null) {
         throw new NullPointerException("Text can't be null");
      }

      if (StringUtils.isBlank(text)) {
         throw new IllegalArgumentException("Text can't be blank");
      }
   }

   private void assertThatLevelIsNotNull(Level level) {
      if (level == null) {
         throw new NullPointerException("Level can't be null");
      }
   }

   private void assertThatTimestampIsNotNull(DateTime timestamp) {
      if (timestamp == null) {
         throw new NullPointerException("Timestamp can't be null");
      }
   }


   /**
    * @return
    */
   public DateTime getTimestamp() {
      return this.timestamp;
   }

   /**
    * @return
    */
   public Level getLevel() {
      return this.level;
   }

   /**
    * @return
    */
   public String getText() {
      return this.text;
   }


   @Override
   public int hashCode() {
      final int prime= 31;
      int result= 1;
      result= prime * result + ((this.level == null)? 0 : (this.level.ordinal() + 1));
      result= prime * result + ((this.text == null)? 0 : this.text.hashCode());
      result= prime * result + ((this.timestamp == null)? 0 : this.timestamp.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }
      if (obj == null) {
         return false;
      }
      if (!(obj instanceof Message)) {
         return false;
      }
      final Message other= (Message) obj;
      if (this.level != other.level) {
         return false;
      }
      if (this.text == null) {
         if (other.text != null) {
            return false;
         }
      }
      else if (!this.text.equals(other.text)) {
         return false;
      }
      if (this.timestamp == null) {
         if (other.timestamp != null) {
            return false;
         }
      }
      else if (!this.timestamp.equals(other.timestamp)) {
         return false;
      }
      return true;
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString() {
      return String.format("[(%1$tF %1$tT) %2$s: %3$s]", this.timestamp.toDate(), this.level, this.text);
   }
}
