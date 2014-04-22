package es.sandbox.ui.messages;

import static es.sandbox.ui.messages.MessageFixtureGenerator.fixturer;
import static org.fest.assertions.api.Assertions.assertThat;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class MessageEqualitySpecs {

   private static final DateTime EXAMPLE_TIMESTAMP= MessageFixtureGenerator.EXAMPLE_TIMESTAMP;
   private static final Level EXAMPLE_LEVEL= MessageFixtureGenerator.EXAMPLE_LEVEL;
   private static final String EXAMPLE_TEXT= MessageFixtureGenerator.EXAMPLE_TEXT;


   public static class EqualityContractSpec {

      @Test
      public void should_respect_equals_and_hashcode_contract() {
         EqualsVerifier.
               forClass(Message.class)
               .allFieldsShouldBeUsed()
               .verify();
      }
   }

   public static class GenericHashCodeSpec {

      private final Message sut= Message.rehydrate(EXAMPLE_TIMESTAMP, EXAMPLE_LEVEL, EXAMPLE_TEXT);


      @Test
      public void invoked_on_the_same_object_more_than_once_should_return_the_same_integer() {
         final int expectedHashCode= this.sut.hashCode();

         for (int times= 0; times < 100; times++) {
            assertThat(this.sut.hashCode()).isEqualTo(expectedHashCode);
         }
      }

      @Test
      public void identical_objects_should_return_the_same_integer() {
         final Message oneSut= Message.rehydrate(EXAMPLE_TIMESTAMP, EXAMPLE_LEVEL, EXAMPLE_TEXT);
         final Message anotherSut= Message.rehydrate(EXAMPLE_TIMESTAMP, EXAMPLE_LEVEL, EXAMPLE_TEXT);

         assertThat(oneSut).isEqualTo(anotherSut);
         assertThat(oneSut.hashCode()).isEqualTo(anotherSut.hashCode());
      }

      @Test
      public void different_objects_should_return_different_hash_codes() {
         final Message oneSut= Message.rehydrate(EXAMPLE_TIMESTAMP, EXAMPLE_LEVEL, EXAMPLE_TEXT);
         final Message anotherSut= Message.rehydrate(new DateTime(), Level.WARNING, "Another text");

         assertThat(oneSut).isNotEqualTo(anotherSut);
         assertThat(oneSut.hashCode()).isNotEqualTo(anotherSut.hashCode());
      }
   }

   public static class GenericEqualsSpec {

      private Message sut;


      @Before
      public void message() {
         this.sut= fixturer().generateOne();
      }

      @Test
      public void should_be_equal_to_itself() {
         final Object object= this.sut;
         assertThat(this.sut.equals(object)).isTrue();
      }

      @Test
      public void should_not_be_equal_to_null() {
         assertThat(this.sut.equals(null)).isFalse();
      }

      @Test
      public void should_not_be_equal_to_different_class_objects() {
         final Object object= new Integer(2);
         assertThat(this.sut.equals(object)).isFalse();
      }
   }
}
