package tech.ferus.commander.core.context;

import tech.ferus.commander.api.context.Possibility;

import javax.annotation.Nonnull;

public class GenericPossibility<V> implements Possibility<V> {

    private final String key;
    private final V value;

    public GenericPossibility(final String key, final V value) {
        this.key = key;
        this.value = value;
    }

    @Nonnull
    @Override
    public String getKey() {
        return this.key;
    }

    @Nonnull
    @Override
    public V getValue() {
        return this.value;
    }
}
