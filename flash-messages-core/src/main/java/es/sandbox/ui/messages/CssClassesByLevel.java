package es.sandbox.ui.messages;

import java.util.EnumMap;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;


public final class CssClassesByLevel {

    private static final String DEFAULT_MAIN_CSS_CLASS = "alert";
    private static final String DEFAULT_PREFIX_CSS_CLASSES = "alert";

    private final EnumMap<Level, String> cssClasses;


    /**
     *
     */
    public CssClassesByLevel() {
        this.cssClasses = defaultLevelCssClasses();
    }

    private static EnumMap<Level, String> defaultLevelCssClasses() {
        final EnumMap<Level, String> cssClasses = emptyMap();
        for (final Level level : Level.values()) {
            cssClasses.put(level, cssClass(level));
        }
        return cssClasses;
    }

    private static String cssClass(Level level) {
        return String.format("%s %s-%s",
            DEFAULT_MAIN_CSS_CLASS,
            DEFAULT_PREFIX_CSS_CLASSES,
            level.name().toLowerCase());
    }

    /**
     * Copy constructor
     *
     * @param other
     */
    public CssClassesByLevel(CssClassesByLevel other) {
        this.cssClasses = copyLevelCssClasses(other);
    }

    private static EnumMap<Level, String> copyLevelCssClasses(CssClassesByLevel other) {
        return other == null ? emptyMap() : new EnumMap<Level, String>(other.cssClasses);
    }

    private static EnumMap<Level, String> emptyMap() {
        return new EnumMap<Level, String>(Level.class);
    }

    /**
     * @param level
     * @param cssClass
     */
    public void put(Level level, String cssClass) {
        this.cssClasses.put(level, trimToEmpty(cssClass));
    }

    /**
     * @param level
     * @return
     */
    public String get(Level level) {
        if (level == null) {
            return null;
        }
        return this.cssClasses.get(level);
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.cssClasses == null) ? 0 : this.cssClasses.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CssClassesByLevel)) {
            return false;
        }
        final CssClassesByLevel other = (CssClassesByLevel) obj;
        if (this.cssClasses == null) {
            if (other.cssClasses != null) {
                return false;
            }
        } else if (!this.cssClasses.equals(other.cssClasses)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[css classes=" + this.cssClasses + "]";
    }
}
