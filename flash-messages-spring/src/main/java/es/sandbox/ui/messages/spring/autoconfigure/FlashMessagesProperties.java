package es.sandbox.ui.messages.spring.autoconfigure;

import es.sandbox.ui.messages.CssClassesByLevel;
import es.sandbox.ui.messages.Level;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>
 *  flash-messages.levels=ERROR,INFO
 *  flash-messages.error.cssClass="alert alert-error"
 *  flash-messages.warning.cssClass="alert alert-warning"
 *  flash-messages.info.cssClass="alert alert-info"
 *  flash-messages.success.cssClass="alert alert-success"
 * </code>
 * Created by jeslopalo on 3/11/16.
 */
@ConfigurationProperties("flash-messages")
public class FlashMessagesProperties {

    private List<String> levels = new ArrayList<String>();

    public List<String> getLevels() {
        return this.levels;
    }

    public void setLevels(List<String> levels) {
        this.levels = levels;
    }

    public static class LevelConfig {

        private String cssClass;

        public String getCssClass() {
            return this.cssClass;
        }

        public void setCssClass(String cssClass) {
            this.cssClass = cssClass;
        }

        public boolean isConfigured() {
            return this.cssClass != null && !this.cssClass.isEmpty();
        }
    }


    private final LevelConfig success = new LevelConfig();
    private final LevelConfig info = new LevelConfig();
    private final LevelConfig warning = new LevelConfig();
    private final LevelConfig error = new LevelConfig();

    public LevelConfig getSuccess() {
        return this.success;
    }

    public LevelConfig getInfo() {
        return this.info;
    }

    public LevelConfig getWarning() {
        return this.warning;
    }

    public LevelConfig getError() {
        return this.error;
    }


    public CssClassesByLevel getCssClassesByLevel() {

        final CssClassesByLevel cssClassesByLevel = new CssClassesByLevel();

        if (this.success.isConfigured()) {
            cssClassesByLevel.put(Level.SUCCESS, this.success.cssClass);
        }
        if (this.info.isConfigured()) {
            cssClassesByLevel.put(Level.INFO, this.info.cssClass);
        }
        if (this.warning.isConfigured()) {
            cssClassesByLevel.put(Level.WARNING, this.warning.cssClass);
        }
        if (this.error.isConfigured()) {
            cssClassesByLevel.put(Level.ERROR, this.error.cssClass);
        }

        return cssClassesByLevel;
    }
}
