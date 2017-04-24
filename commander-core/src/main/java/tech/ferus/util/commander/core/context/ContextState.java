package tech.ferus.util.commander.core.context;

import tech.ferus.util.commander.api.context.ContextFailure;

import com.google.common.collect.Sets;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ContextState<T extends GenericArgumentContext> {

    @Nullable private final T context;
    @Nonnull private final Set<ContextFailure> failures;

    public ContextState(@Nonnull final T context) {
        this.context = context;
        this.failures = Sets.newHashSet();
    }

    public ContextState(@Nonnull final ContextFailure failure) {
        this.context = null;
        this.failures = Sets.newHashSet(failure);
    }

    @Nullable public T getContext() {
        return this.context;
    }

    @Nonnull public Set<ContextFailure> getFailures() {
        return this.failures;
    }

    public void addFailure(@Nonnull final ContextFailure failure) {
        this.failures.add(failure);
    }

    public boolean isFailing() {
        return !this.failures.isEmpty();
    }
}
