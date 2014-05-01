package es.sandbox.ui.messages.store;

import static es.sandbox.ui.messages.MessageFixtureGenerator.fixturer;

import java.util.EnumMap;

import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.MessageFixtureGenerator;

public class MessagesStoreFixtureGenerator {

	public static final int RANDOM_AMOUNT= -1;
	public static final int NONE= 0;

	private final EnumMap<Level, Integer> amounts;
	private final MessageFixtureGenerator messageGenerator;


	public MessagesStoreFixtureGenerator() {
		this.amounts= initializeAmounts(RANDOM_AMOUNT);
		this.messageGenerator= fixturer();
	}

	private final EnumMap<Level, Integer> initializeAmounts(int initial) {
		final EnumMap<Level, Integer> amounts= new EnumMap<Level, Integer>(Level.class);

		for (final Level level : Level.values()) {
			amounts.put(level, initial);
		}

		return amounts;
	}

	public MessagesStoreFixtureGenerator amount(Level level, int amount) {
		this.amounts.put(level, Math.max(amount, RANDOM_AMOUNT));
		return this;
	}

	private int amount(Level level) {
		return this.amounts.get(level);
	}

	public MessagesStoreFixtureGenerator success(int amount) {
		return amount(Level.SUCCESS, amount);
	}

	public MessagesStoreFixtureGenerator info(int amount) {
		return amount(Level.INFO, amount);
	}

	public MessagesStoreFixtureGenerator warning(int amount) {
		return amount(Level.WARNING, amount);
	}

	public MessagesStoreFixtureGenerator error(int amount) {
		return amount(Level.ERROR, amount);
	}

	public MessagesStore generate() {
		final MessagesStore messagesStore= new MessagesStore();

		for (final Level level : Level.values()) {
			messagesStore.addAll(this.messageGenerator.withLevels(level).generate(amount(level)));
		}

		return messagesStore;
	}
}
