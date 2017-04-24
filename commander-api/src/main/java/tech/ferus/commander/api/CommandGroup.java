package tech.ferus.commander.api;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface CommandGroup {

    @Nonnull String getName();

    @Nonnull Collection<CommandGroup> getChildren();

    boolean hasChildren();

    boolean hasChild(@Nonnull final String name);

    @Nullable CommandGroup getChild(final String name);

    void addChild(@Nonnull final CommandGroup child);

    void addChildren(@Nonnull final Collection<CommandGroup> children);

    void removeChild(@Nonnull final String name);

    void removeChild(@Nonnull final CommandGroup child);

    @Nonnull Collection<Command> getCommands();

    boolean hasCommands();

    boolean hasCommand(@Nonnull final String trigger);

    @Nullable Command getCommand(@Nonnull final String trigger);

    void addCommand(@Nonnull final Command command);

    void addCommands(@Nonnull final Collection<Command> commands);

    @Nonnull String getDescription();

    @Nonnull String getShortDescription();
}
