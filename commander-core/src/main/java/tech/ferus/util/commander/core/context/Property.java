package tech.ferus.util.commander.core.context;

public class Property<T> {

    private final Class<T> type;

    private Property(final Class<T> type) {
        this.type = type;
    }

    public Class<T> getType() {
        return this.type;
    }

    public static <T> Property<T> of(final Class<T> type) {
        return new Property<>(type);
    }
}
