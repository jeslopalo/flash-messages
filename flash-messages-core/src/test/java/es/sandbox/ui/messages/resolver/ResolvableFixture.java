package es.sandbox.ui.messages.resolver;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;


public class ResolvableFixture implements Resolvable, Serializable {

	private static final long serialVersionUID= 180487505412175130L;

	private final String code;
	private final Serializable[] arguments;


	/**
	 * @param code
	 * @param arguments
	 * @return
	 */
	public static ResolvableFixture resolvable(String code, Serializable... arguments) {
		return new ResolvableFixture(code, arguments);
	}

	private ResolvableFixture(String code, Serializable... arguments) {
		assertThatCodeIsValid(code);

		this.code= code;
		this.arguments= arguments;
	}

	private void assertThatCodeIsValid(String code) {
		if (code == null) {
			throw new NullPointerException("Code can't be null");
		}

		if (StringUtils.isBlank(code)) {
			throw new IllegalArgumentException("Code can't be blank");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see es.sandbox.ui.messages.resolver.Resolvable#resolve(es.sandbox.ui.messages.resolver.MessageResolver)
	 */
	@Override
	public String resolve(MessageResolver messageResolver) {
		if (messageResolver == null) {
			throw new NullPointerException("MessageResolver can't be null");
		}

		return messageResolver.resolve(this.code, this.arguments);
	}
}
