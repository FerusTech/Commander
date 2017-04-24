package tech.ferus.commander.api.context;

import java.util.List;

import javax.annotation.Nonnull;

public interface ArgumentResolver<T> {

    @Nonnull Argument getArgument();

    @Nonnull List<Possibility<T>> resolve();
}
