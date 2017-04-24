package tech.ferus.commander.api.context;

import javax.annotation.Nonnull;

public interface Possibility<V> {

    @Nonnull String getKey();

    @Nonnull V getValue();
}
