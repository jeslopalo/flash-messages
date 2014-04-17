package es.sandbox.ui.messages.spring.scope.request;

import static org.fest.assertions.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.springframework.web.context.request.RequestContextHolder;

import es.sandbox.spring.fixture.MockedSpringHttpServletRequest;
import es.sandbox.test.utils.ReflectionInvoker;
import es.sandbox.ui.messages.spring.scope.request.HttpServletRequestUtils;
import es.sandbox.ui.messages.spring.scope.request.NotHttpServletRequestBoundToThreadException;


public class HttpServletRequestUtilsSpecs {

	@Test
	public void should_only_has_private_constructors() {
		final Constructor<?>[] constructors= HttpServletRequestUtils.class.getDeclaredConstructors();

		for (final Constructor<?> constructor : constructors)
			assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();
	}

	@Test(expected= UnsupportedOperationException.class)
	public void should_raise_an_exception_when_force_to_call_to_the_private_contructor()
			throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {

		ReflectionInvoker.privateDefaultConstructor(HttpServletRequestUtils.class, UnsupportedOperationException.class);
	}

	@Test(expected= NotHttpServletRequestBoundToThreadException.class)
	public void should_raise_an_exception_without_http_servlet_request_bounded_to_the_current_thread() {
		RequestContextHolder.resetRequestAttributes();

		HttpServletRequestUtils.currentHttpServletRequest();
	}

	@Test
	public void should_return_current_http_servlet_request() {
		final HttpServletRequest mockedRequest= MockedSpringHttpServletRequest.registeredHttpServletRequest();

		assertThat(HttpServletRequestUtils.currentHttpServletRequest()).isEqualTo(mockedRequest);
	}
}
