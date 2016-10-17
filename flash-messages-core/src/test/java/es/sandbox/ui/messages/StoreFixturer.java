package es.sandbox.ui.messages;

import java.util.EnumMap;

import static es.sandbox.ui.messages.MessageFixturer.fixturer;

public class StoreFixturer {

    public static final int RANDOM_AMOUNT = -1;
    public static final int NONE = 0;

    private final EnumMap<Level, Integer> amounts;
    private final MessageFixturer messageGenerator;


    public StoreFixturer() {
        this.amounts = initializeAmounts(RANDOM_AMOUNT);
        this.messageGenerator = fixturer();
    }

    private final EnumMap<Level, Integer> initializeAmounts(int initial) {
        final EnumMap<Level, Integer> amounts = new EnumMap<Level, Integer>(Level.class);

        for (final Level level : Level.values()) {
            amounts.put(level, initial);
        }

        return amounts;
    }

    public StoreFixturer amount(Level level, int amount) {
        this.amounts.put(level, Math.max(amount, RANDOM_AMOUNT));
        return this;
    }

    private int amount(Level level) {
        return this.amounts.get(level);
    }

    public StoreFixturer success(int amount) {
        return amount(Level.SUCCESS, amount);
    }

    public StoreFixturer info(int amount) {
        return amount(Level.INFO, amount);
    }

    public StoreFixturer warning(int amount) {
        return amount(Level.WARNING, amount);
    }

    public StoreFixturer error(int amount) {
        return amount(Level.ERROR, amount);
    }

    public Store generate() {
        final Store store = new Store();

        for (final Level level : Level.values()) {
            store.addAll(this.messageGenerator.withLevels(level).generate(amount(level)));
        }

        return store;
    }
}
