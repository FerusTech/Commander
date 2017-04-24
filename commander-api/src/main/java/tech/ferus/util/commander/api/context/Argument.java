package tech.ferus.util.commander.api.context;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface Argument {

    @Nonnull ArgumentContext getContext();

    @Nonnull String getRawInput();

    @Nonnull List<Possibility<?>> getPossibilities();

    <V> void addPossibility(@Nonnull final String key, @Nonnull final V value);

    @Nullable Possibility<?> getSelected();

    boolean isValidSelection(final int index);

    boolean select(final int index);

    boolean isClarified();

    void resolveAs(@Nonnull final ArgumentResolver resolver);
}
