package tech.ferus.util.commander.core;

import tech.ferus.util.commander.api.Command;
import tech.ferus.util.commander.api.CommandGroup;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CommandLookup {

    @Nullable private final CommandGroup group;
    @Nullable private final Command command;
    @Nonnull private final String[] remaining;

    public CommandLookup(@Nullable final CommandGroup group,
                         @Nullable final Command command,
                         @Nonnull String[] remaining) {
        this.group = group;
        this.command = command;
        this.remaining = remaining;
    }

    @Nullable public CommandGroup getGroup() {
        return this.group;
    }

    @Nullable public Command getCommand() {
        return this.command;
    }

    @Nonnull public String[] getRemaining() {
        return this.remaining;
    }

    public static CommandLookup fail() {
        return new CommandLookup(null, null, new String[0]);
    }

    public static CommandLookup success(@Nullable final CommandGroup group,
                                        @Nonnull final Command command,
                                        @Nonnull final String[] remaining) {
        return new CommandLookup(group, command, remaining);
    }
}
