package tech.ferus.util.commander.core.context;

import tech.ferus.util.commander.api.context.ContextFailure;
import tech.ferus.util.commander.api.context.ContextState;

import com.google.common.collect.Sets;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GenericContextState implements ContextState {

    @Nullable private final GenericArgumentContext context;
    @Nonnull private final Set<ContextFailure> failures;

    public GenericContextState(@Nonnull final GenericArgumentContext context) {
        this.context = context;
        this.failures = Sets.newHashSet();
    }

    public GenericContextState(@Nonnull final ContextFailure failure) {
        this.context = null;
        this.failures = Sets.newHashSet(failure);
    }

    @Nullable
    @Override
    public GenericArgumentContext getContext() {
        return this.context;
    }

    @Nonnull
    @Override
    public Set<ContextFailure> getFailures() {
        return this.failures;
    }

    @Override
    public void addFailure(@Nonnull final ContextFailure failure) {
        this.failures.add(failure);
    }

    @Override
    public boolean isFailing() {
        return !this.failures.isEmpty();
    }
}
