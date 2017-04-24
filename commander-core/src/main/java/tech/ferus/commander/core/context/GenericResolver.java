package tech.ferus.commander.core.context;

import tech.ferus.commander.api.context.Argument;
import tech.ferus.commander.api.context.ArgumentResolver;
import tech.ferus.commander.api.context.Possibility;

import com.google.common.collect.Lists;
import java.util.List;

import javax.annotation.Nonnull;

public class GenericResolver<T> implements ArgumentResolver<T> {

    @Nonnull private final Argument argument;

    public GenericResolver(@Nonnull final Argument argument) {
        this.argument = argument;
    }

    @Nonnull
    @Override
    public Argument getArgument() {
        return this.argument;
    }

    @Nonnull
    @Override
    public List<Possibility<T>> resolve() {
        return Lists.newArrayList();
    }
}
