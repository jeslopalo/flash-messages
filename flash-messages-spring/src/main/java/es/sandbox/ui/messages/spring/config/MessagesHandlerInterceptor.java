package es.sandbox.ui.messages.spring.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import es.sandbox.ui.messages.context.MessagesContext;


public class MessagesHandlerInterceptor
		extends HandlerInterceptorAdapter {

	private static final Logger LOGGER= LoggerFactory.getLogger(MessagesHandlerInterceptor.class);

	private final MessagesContext context;


	public MessagesHandlerInterceptor(MessagesContext context) {
		this.context= context;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		LOGGER.trace("MessagesHandlerInterceptor#preHandle --------------------------------------");
		request.setAttribute(MessagesContext.MESSAGES_CONTEXT_PARAMETER, this.context);

		this.context.initialize(request);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		LOGGER.trace("MessagesHandlerInterceptor#postHandle --------------------------------------");
	}
}
