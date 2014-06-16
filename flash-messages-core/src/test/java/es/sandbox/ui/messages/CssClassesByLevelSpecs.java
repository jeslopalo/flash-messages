package es.sandbox.ui.messages;

import static org.fest.assertions.api.Assertions.assertThat;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import es.sandbox.ui.messages.CssClassesByLevel;
import es.sandbox.ui.messages.Level;


@RunWith(Enclosed.class)
public class CssClassesByLevelSpecs {

   public static class DefaultConstructorSpecs {

      @Test
      public void it_should_be_created() {
         assertThat(new CssClassesByLevel()).isNotNull();
      }

      @Test
      public void it_should_contains_default_css_classes_for_every_level() {
         final CssClassesByLevel sut= new CssClassesByLevel();

         assertThat(Level.values().length).isEqualTo(4);

         assertThat(sut.get(Level.ERROR)).isEqualTo("alert alert-error");
         assertThat(sut.get(Level.INFO)).isEqualTo("alert alert-info");
         assertThat(sut.get(Level.SUCCESS)).isEqualTo("alert alert-success");
         assertThat(sut.get(Level.WARNING)).isEqualTo("alert alert-warning");
      }
   }

   public static class CustomizingCssClassesByLevelSpecs {

      private CssClassesByLevel sut;


      @Before
      public void setup() {
         this.sut= new CssClassesByLevel();
      }

      @Test
      public void it_should_put_custom_css_classes_in_a_level() {

         this.sut.put(Level.ERROR, "custom-css-class");

         assertThat(Level.values().length).isEqualTo(4);

         assertThat(this.sut.get(Level.ERROR)).isEqualTo("custom-css-class");
         assertThat(this.sut.get(Level.INFO)).isEqualTo("alert alert-info");
         assertThat(this.sut.get(Level.SUCCESS)).isEqualTo("alert alert-success");
         assertThat(this.sut.get(Level.WARNING)).isEqualTo("alert alert-warning");
      }

      @Test(expected= NullPointerException.class)
      public void it_should_raise_an_exception_with_null_level() {
         this.sut.put(null, "class");
      }

      @Test
      public void it_should_get_empty_string_without_css_class() {

         this.sut.put(Level.ERROR, null);
         this.sut.put(Level.INFO, "");
         this.sut.put(Level.SUCCESS, " ");

         assertThat(this.sut.get(Level.ERROR)).isEqualTo("");
         assertThat(this.sut.get(Level.INFO)).isEqualTo("");
         assertThat(this.sut.get(Level.SUCCESS)).isEqualTo("");
      }
   }

   public static class CopyConstructorSpecs {

      @Test
      public void it_should_be_copied() {
         final CssClassesByLevel source= new CssClassesByLevel();
         source.put(Level.SUCCESS, null);
         source.put(Level.ERROR, "alert alert-danger");

         final CssClassesByLevel copy= new CssClassesByLevel(source);

         assertThat(source).isEqualTo(copy);
      }

      @Test
      public void it_should_set_null_class_in_all_levels_with_null() {
         final CssClassesByLevel sut= new CssClassesByLevel(null);

         for (final Level level : Level.values()) {
            assertThat(sut.get(level)).isNull();
         }
      }
   }

   public static class GettingLevelSpecs {

      private CssClassesByLevel sut;


      @Before
      public void setup() {
         this.sut= new CssClassesByLevel();
      }

      @Test
      public void it_should_get_null_with_null_level() {
         assertThat(this.sut.get(null)).isNull();
      }

      @Test
      public void it_should_get_classes() {
         assertThat(this.sut.get(Level.SUCCESS)).isEqualTo("alert alert-success");
      }
   }

   public static class EqualitySpecs {

      @Test
      public void it_should_respect_the_equals_and_hashcode_contracts() {
         EqualsVerifier.forClass(CssClassesByLevel.class).allFieldsShouldBeUsed().verify();
      }
   }

   public static class ToStringSpecs {

      @Test
      public void it_should_print_default_classes_in_tostring() {
         final CssClassesByLevel sut= new CssClassesByLevel();

         assertThat(sut.toString()).isEqualTo("[css classes={SUCCESS=alert alert-success, INFO=alert alert-info, WARNING=alert alert-warning, ERROR=alert alert-error}]");
      }

      @Test
      public void it_should_print_customized_classes_in_tostring() {
         final CssClassesByLevel sut= new CssClassesByLevel();
         sut.put(Level.SUCCESS, null);
         sut.put(Level.INFO, "");
         sut.put(Level.ERROR, "customized-class");

         assertThat(sut.toString()).isEqualTo("[css classes={SUCCESS=, INFO=, WARNING=alert alert-warning, ERROR=customized-class}]");
      }

   }
}
