package es.sandbox;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static es.sandbox.test.utils.ReflectionInvoker.privateDefaultConstructor;

/**
 * Created by jeslopalo on 27/12/16.
 */
public class NoSourceCodeTest {

    @Test(expected = UnsupportedOperationException.class)
    public void it_should_not_be_instantiable() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        privateDefaultConstructor(NoSourceCode.class, UnsupportedOperationException.class);
    }
}
