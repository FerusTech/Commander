package tech.ferus.commander.api.context;

import java.util.List;

import javax.annotation.Nonnull;

public interface ArgumentResolver<T> {

    @Nonnull
    List<Possibility<T>> resolve(@Nonnull final Argument argument);
}
