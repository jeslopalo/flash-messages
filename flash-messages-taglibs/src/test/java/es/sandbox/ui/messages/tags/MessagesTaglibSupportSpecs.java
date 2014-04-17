package es.sandbox.ui.messages.tags;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import es.sandbox.test.utils.ReflectionInvoker;

@RunWith(Enclosed.class)
public class MessagesTaglibSupportSpecs {

	public static final class RequestParameterNameSpecs {


		@Test(expected= UnsupportedOperationException.class)
		public void it_should_raise_an_exception_when_force_to_call_to_the_private_contructor()
				throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {

			ReflectionInvoker.privateDefaultConstructor(MessagesTaglibSupport.class, UnsupportedOperationException.class);
		}
	}
}
