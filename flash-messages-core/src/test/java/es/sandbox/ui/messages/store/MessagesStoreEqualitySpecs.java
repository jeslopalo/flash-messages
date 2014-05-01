package es.sandbox.ui.messages.store;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class MessagesStoreEqualitySpecs {

	public static class EqualityContractSpec {

		@Test
		public void should_respect_equals_contract() {
			EqualsVerifier.
					forClass(MessagesStore.class)
					.allFieldsShouldBeUsed()
					.verify();
		}
	}
}
