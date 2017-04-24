package tech.ferus.util.commander.api.context;

import tech.ferus.util.commander.core.Command;
import tech.ferus.util.commander.core.CommandGroup;
import tech.ferus.util.commander.core.context.Property;

import java.util.Collection;
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

    @Nonnull public Map<Property<?>, >
}
