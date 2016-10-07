package es.sandbox.ui.messages.spring.scope.request;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


public final class HttpServletRequestUtils { // NO_UCD (test only)

    /**
     * Private constructor to prevent instances
     *
     * @throws UnsupportedOperationException
     */
    private HttpServletRequestUtils() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * @return
     * @throws NotHttpServletRequestBoundToThreadException
     */
    public static final HttpServletRequest currentHttpServletRequest() // NO_UCD (use default)
        throws NotHttpServletRequestBoundToThreadException {

        final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new NotHttpServletRequestBoundToThreadException();
        }

        return attributes.getRequest();
    }
}
