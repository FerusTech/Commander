package tech.ferus.commander.api;

import tech.ferus.commander.api.context.ContextState;

import java.util.Collection;

import javax.annotation.Nonnull;

public interface CommandManager {

    @Nonnull Collection<Command> getCommands();

    void addCommand(@Nonnull final Command command);

    @Nonnull Collection<CommandGroup> getGroups();

    void addGroup(@Nonnull final CommandGroup group);

    @Nonnull String getPrefix();

    void setPrefix(@Nonnull final String prefix);

    @Nonnull ContextState parseInput(@Nonnull final String input);
}
