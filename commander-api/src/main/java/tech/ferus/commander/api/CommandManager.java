package tech.ferus.commander.api;

import tech.ferus.commander.api.context.ContextState;

import java.util.Collection;

import javax.annotation.Nonnull;

public interface CommandManager {

    @Nonnull Collection<Command> getCommands();

    @Nonnull Collection<CommandGroup> getGroups();

    @Nonnull String getPrefix();

    void setPrefix(@Nonnull final String prefix);

    @Nonnull ContextState parseInput(@Nonnull final String input);
}
