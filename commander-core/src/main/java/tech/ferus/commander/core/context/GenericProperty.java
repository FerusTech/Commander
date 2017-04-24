package tech.ferus.commander.core.context;

import tech.ferus.commander.api.context.Property;

import javax.annotation.Nonnull;

public class GenericProperty<T> implements Property<T> {

    @Nonnull private final Class<T> type;

    private GenericProperty(@Nonnull final Class<T> type) {
        this.type = type;
    }

    @Nonnull public Class<T> getType() {
        return this.type;
    }
}