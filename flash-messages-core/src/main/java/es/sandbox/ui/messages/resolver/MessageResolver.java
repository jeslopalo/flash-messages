package es.sandbox.ui.messages.resolver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


public class MessageResolver {

	private final MessageResolverStrategy strategy;


	/**
	 * @param strategy
	 */
	public MessageResolver(MessageResolverStrategy strategy) {
		if (strategy == null) {
			throw new NullPointerException("MessageResolverStrategy can't be null");
		}

		this.strategy= strategy;
	}

	/**
	 * @param code
	 * @param arguments
	 * @return
	 */
	public String resolve(String code, Serializable... arguments) {
		assertThatCodeIsValid(code);

		return this.strategy.resolve(code, arguments(arguments));
	}

	private void assertThatCodeIsValid(String code) {
		if (code == null) {
			throw new NullPointerException("Code can't be null");
		}

		if (StringUtils.isBlank(code)) {
			throw new IllegalArgumentException("Code can't be blank");
		}
	}

	private Serializable[] arguments(Serializable... arguments) {
		final List<Serializable> resolved= new ArrayList<Serializable>();
		if (arguments != null) {
			for (final Serializable argument : arguments) {
				resolved.add(resolveSingle(argument));
			}
		}
		return resolved.toArray(new Serializable[0]);
	}

	private Serializable resolveSingle(Serializable argument) {
		if (argument instanceof Resolvable) {
			return ((Resolvable) argument).resolve(this);
		}
		return argument;
	}

	/**
	 * @param messageResolver
	 * @throws NullPointerException
	 */
	public static void assertThatIsNotNull(MessageResolver messageResolver) throws NullPointerException {
		if (messageResolver == null) {
			throw new NullPointerException("MessageResolver can't be null");
		}
	}

	@Override
	public String toString() {
		return String.format("MessageResolver(%s)", this.strategy.getClass().getSimpleName());
	}
}
