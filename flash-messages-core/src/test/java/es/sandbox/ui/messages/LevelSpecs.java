package es.sandbox.ui.messages;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(Enclosed.class)
public class LevelSpecs {

    public static class ValueOfSpec {

        @Test
        public void should_return_the_enum_constant_with_the_specified_name() {
            assertThat(Level.valueOf("ERROR")).isEqualTo(Level.ERROR);
            assertThat(Level.valueOf("INFO")).isEqualTo(Level.INFO);
            assertThat(Level.valueOf("SUCCESS")).isEqualTo(Level.SUCCESS);
            assertThat(Level.valueOf("WARNING")).isEqualTo(Level.WARNING);
        }

        @Test(expected = IllegalArgumentException.class)
        public void should_raise_an_exception_with_unknown_name() {
            Level.valueOf("BLABLA");
        }
    }
}
