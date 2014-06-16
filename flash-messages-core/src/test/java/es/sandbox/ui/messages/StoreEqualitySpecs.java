package es.sandbox.ui.messages;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import es.sandbox.ui.messages.Store;

@RunWith(Enclosed.class)
public class StoreEqualitySpecs {

	public static class EqualityContractSpec {

		@Test
		public void should_respect_equals_contract() {
			EqualsVerifier.
					forClass(Store.class)
					.allFieldsShouldBeUsed()
					.verify();
		}
	}
}
