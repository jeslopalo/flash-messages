package es.sandbox;

import es.sandbox.test.utils.ReflectionInvoker;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by jeslopalo on 27/12/16.
 */
public class NoSourceCodeTest {

    @Test(expected = UnsupportedOperationException.class)
    public void it_should_not_be_instantiable() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ReflectionInvoker.privateDefaultConstructor(NoSourceCode.class, UnsupportedOperationException.class);
    }
}
