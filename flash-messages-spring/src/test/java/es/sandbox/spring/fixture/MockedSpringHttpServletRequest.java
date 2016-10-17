package es.sandbox.spring.fixture;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.entry;


public final class MockedSpringHttpServletRequest
    extends MockHttpServletRequest {


    private MockedSpringHttpServletRequest(final boolean registered) {
        setAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE, new HashMap<String, Object>());
        setAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE, new FlashMap());

        registered(registered);
    }

    public static MockedSpringHttpServletRequest registeredHttpServletRequest() {
        return new MockedSpringHttpServletRequest(true);
    }

    public static MockedSpringHttpServletRequest detachedHttpServletRequest() {
        return new MockedSpringHttpServletRequest(false);
    }

    private void registered(final boolean registered) {
        if (registered) {
            register();
        } else {
            unregister();
        }
    }


    public final void register() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(this));
    }

    public final void unregister() {
        RequestContextHolder.resetRequestAttributes();
    }


    public <T> void addInputFlashAttribute(final String key, final T value) {
        @SuppressWarnings("unchecked")
        final Map<String, Object> inputFlashMap = (Map<String, Object>) getAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE);

        inputFlashMap.put(key, value);
    }

    public <T> void addOutputFlashAttribute(final String key, final T value) {
        final FlashMap outputFlashMap = (FlashMap) getAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE);

        outputFlashMap.put(key, value);
    }


    public final <T> void assertThatOutputFlashScopeContains(final String key, final T value) {
        final FlashMap flash = RequestContextUtils.getOutputFlashMap(this);
        assertThat(flash).contains(entry(key, value));
    }

    public final <T> void assertThatOutputFlashScopeDoesNotContain(final String key) {
        final FlashMap flash = RequestContextUtils.getOutputFlashMap(this);
        assertThat(flash).doesNotContainKey(key);
    }
}
