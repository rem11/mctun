package to.uk.rem11.mctun.config;

/**
 * 22.03.2015
 * 8:19
 */
public class ConfigException extends Exception {
    public ConfigException(String s) {
        super(s);
    }

    public ConfigException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
