package tech.ferus.util.commander.api.context;

import javax.annotation.Nonnull;

public interface Property<T> {

    @Nonnull Class<T> getType();
}
