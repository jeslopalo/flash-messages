package es.sandbox.ui.messages.argument;

import static es.sandbox.test.assertion.ArgumentAssertions.assertThatConstructor;
import static es.sandbox.test.assertion.ArgumentAssertions.assertThatMethod;
import static org.fest.assertions.api.Assertions.assertThat;

import java.io.Serializable;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;


@RunWith(Enclosed.class)
public class LinkArgumentSpecs {

   public static class UrlSpecs {

      @Test
      public void it_should_fail_with_invalid_url() {
         assertThatConstructor(LinkArgument.class, String.class)
               .throwsNullPointerException()
               .invokedWithNulls();

         assertThatConstructor(LinkArgument.class, String.class)
               .throwsIllegalArgumentException()
               .invokedWith("")
               .invokedWith(" ");
      }

      @Test
      public void it_should_be_constructed_with_an_url() {
         assertThat(new LinkArgument("/an/url")).isNotNull();
      }

      @Test
      public void it_should_be_possible_to_change_url() {
         final LinkArgument link= new LinkArgument("/an/url");

         link.url("/another/one");

         assertThat(link.toString())
               .doesNotContain("/an/url")
               .contains("/another/one");
      }
   }

   public static class TitleSpecs {

      private LinkArgument sut;


      @Before
      public void setup() {
         this.sut= new LinkArgument("/an/url");
      }

      @Test
      public void it_should_do_nothing_with_null_title() {
         this.sut.title(null);
      }

      @Test
      public void it_should_set_link_title() {
         final LinkArgument link= this.sut;

         link.title(new TextArgument("a"));

         assertThat(link.toString())
               .contains("/an/url")
               .contains("text{a}");
      }

      @Test
      public void it_should_fail_with_invalid_code() {
         assertThatMethod(this.sut, "title", String.class, Serializable[].class)
               .throwsNullPointerException()
               .invokedWithNulls();

         assertThatMethod(this.sut, "title", String.class, Serializable[].class)
               .throwsIllegalArgumentException()
               .invokedWith("", null)
               .invokedWith(" ", null);
      }

      @Test
      public void it_should_work_with_null_argument() {
         this.sut.title("code", (Serializable) null);

         assertThat(this.sut.toString()).isEqualTo("link{/an/url, text{code, [null]}, null}");
      }

      @Test
      public void it_should_work_with_null_array_of_arguments() {
         this.sut.title("code", (Serializable[]) null);

         assertThat(this.sut.toString()).isEqualTo("link{/an/url, text{code}, null}");
      }

      @Test
      public void it_should_work_with_code() {
         this.sut.title("code");

         assertThat(this.sut.toString()).isEqualTo("link{/an/url, text{code}, null}");
      }

      @Test
      public void it_should_work_with_code_and_a_sigle_argument() {
         this.sut.title("code", "arg1");

         assertThat(this.sut.toString()).isEqualTo("link{/an/url, text{code, [arg1]}, null}");
      }

      @Test
      public void it_should_work_with_code_and_multiple_arguments() {
         this.sut.title("code", "arg1", "arg2");

         assertThat(this.sut.toString()).isEqualTo("link{/an/url, text{code, [arg1, arg2]}, null}");
      }
   }
}
