package tech.ferus.util.commander.api.context;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface ContextState<T extends ArgumentContext> {

    @Nullable T getContext();

    @Nonnull Collection<ContextFailure> getFailures();

    void addFailure(@Nonnull final ContextFailure failure);

    boolean isFailing();
}
