package tech.ferus.commander.api.context;

import tech.ferus.commander.api.Command;
import tech.ferus.commander.api.CommandGroup;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface ArgumentContext extends Iterable<Argument> {

    @Nonnull String getRawInput();

    @Nonnull String[] getRawParse();

    @Nonnull String[] getRawArgs();

    @Nonnull List<Argument> getArgs();

    @Nullable Argument getFirstForClarification();

    void addArguments(@Nonnull final Collection<Argument> arguments);

    boolean isAllClarified();

    @Nullable CommandGroup getGroup();

    @Nonnull Command getCommand();

    @Nonnull public Map<Property<?>, Object> getProperties();

    boolean hasProperties();

    boolean hasProperty(@Nonnull final Property property);

    <T> void setProperty(@Nonnull final Property<T> property, final T value);

    @Nullable public <T> T getProperty(@Nonnull final Property<T> property);

    @Nonnull
    @Override
    Iterator<Argument> iterator();
}
