package tech.ferus.util.commander.api;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface Command {

    @Nonnull String getTrigger();

    @Nonnull Collection<String> getAliases();

    boolean isTriggered(@Nonnull final String trigger);

    @Nonnull Collection<Command> getChildren();

    boolean hasChildren();

    boolean hasChild(@Nonnull final String trigger);

    @Nullable Command getChild(@Nonnull final String trigger);

    void addChild(@Nonnull final Command child);

    void addChildren(@Nonnull final Collection<Command> children);

    void removeChild(@Nonnull final String trigger);

    void removeChild(@Nullable final Command child);

    @Nonnull String getDescription();

    @Nonnull String getShortDescription();

    @Nonnull String getUsage();

    @Nonnull CommandExecutor getExecutor();
}
