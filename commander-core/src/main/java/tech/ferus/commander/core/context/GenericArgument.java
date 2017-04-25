package tech.ferus.commander.core.context;

import tech.ferus.commander.api.context.Argument;
import tech.ferus.commander.api.context.ArgumentContext;
import tech.ferus.commander.api.context.ArgumentResolver;
import tech.ferus.commander.api.context.Possibility;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GenericArgument implements Argument {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericArgument.class);

    @Nonnull private final ArgumentContext context;
    @Nonnull private final String rawInput;
    @Nonnull private final List<Possibility<?>> possibilities;
    private int selected;

    public GenericArgument(@Nonnull final ArgumentContext context, @Nonnull final String rawInput) {
        this.context = context;
        this.rawInput = rawInput;
        this.possibilities = Lists.newArrayList();
        this.selected = -1;
    }

    @Nonnull
    @Override
    public ArgumentContext getContext() {
        return this.context;
    }

    @Nonnull
    @Override
    public String getRawInput() {
        return this.rawInput;
    }

    @Nonnull
    @Override
    public List<Possibility<?>> getPossibilities() {
        return this.possibilities;
    }

    @Nonnull
    @Override
    public List<?> getPossibleValues() {
        return this.possibilities.stream().map(possibility -> possibility.getValue()).collect(Collectors.toList());
    }

    @Nonnull
    @Override
    public <T> List<T> getPossibleValues(@Nonnull final Class<T> type) {
        return this.possibilities.stream()
                .filter(possibility -> type.isInstance(possibility.getValue()))
                .map(possibility -> type.cast(possibility.getValue()))
                .collect(Collectors.toList());
    }

    @Override public <V> void addPossibility(@Nonnull final String key, @Nonnull final V value) {
        this.possibilities.add(new GenericPossibility<>(key, value));
    }

    @Nullable
    @Override
    public Possibility<?> getSelected() {
        if (this.possibilities.size() == 1) {
            return this.possibilities.get(0);
        }

        return this.isClarified() ? this.possibilities.get(this.selected) : null;
    }

    @Override public boolean isValidSelection(final int index) {
        return index >= 0 && this.possibilities.size() >= index + 1;
    }

    @Override public boolean select(final int index) {
        if (!this.isValidSelection(index)) {
            LOGGER.warn("Selection \"{}\" was ignored for being invalid.", index);
            return false;
        }

        this.selected = index;
        return true;
    }

    @Override public boolean isClarified() {
        return this.possibilities.size() == 1 || this.selected >= 0;
    }

    @Override public <T> void resolveAs(@Nonnull final ArgumentResolver<T> resolver) {
        this.possibilities.addAll(resolver.resolve(this));
    }
}
