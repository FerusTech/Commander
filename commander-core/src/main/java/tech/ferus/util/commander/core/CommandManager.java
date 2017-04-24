package tech.ferus.util.commander.core;

import tech.ferus.util.commander.core.context.ContextState;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class CommandManager {

    private static final Pattern ARGUMENT_PATTERN = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");

    @Nonnull private final Set<Command> commands;
    @Nonnull private final Set<CommandGroup> groups;
    @Nonnull private String prefix;

    public CommandManager(@Nonnull final String prefix) {
        this.commands = Sets.newHashSet();
        this.groups = Sets.newHashSet();
        this.prefix = prefix;
    }

    public CommandManager() {
        this("");
    }

    @Nonnull public Set<Command> getCommands() {
        return this.commands;
    }

    @Nonnull public Set<CommandGroup> getGroups() {
        return this.groups;
    }

    @Nonnull public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(@Nonnull final String prefix) {
        this.prefix = prefix;
    }

    public String[] parseArguments(final String remaining) {
        final List<String> matches = Lists.newArrayList();

        final Matcher m = ARGUMENT_PATTERN.matcher(remaining);
        while (m.find()) {
            if (m.group(1) != null) {
                matches.add(m.group(1));
            } else if (m.group(2) != null) {
                matches.add(m.group(2));
            } else {
                matches.add(m.group());
            }
        }

        return matches.toArray(new String[matches.size()]);
    }

    @Nonnull public CommandLookup lookup(@Nonnull final String[] args) {
        if (args.length == 0) {
            return CommandLookup.fail();
        }

        for (final Command command : this.commands) {
            if (command.isTriggered(args[0])) {
                return this.lookup(null, command, Arrays.copyOfRange(args, 1, args.length));
            }
        }

        for (final CommandGroup group : this.groups) {
            for (final Command command : group.getCommands()) {
                if (command.isTriggered(args[0])) {
                    return this.lookup(group, command, Arrays.copyOfRange(args, 1, args.length));
                }
            }
        }

        return CommandLookup.fail();
    }

    @Nonnull public CommandLookup lookup(@Nullable final CommandGroup group,
                                           @Nonnull final Command command,
                                           @Nonnull final String[] args) {
        if (args.length == 0) {
            return CommandLookup.success(group, command, args);
        }

        for (final Command child : command.getChildren()) {
            if (child.isTriggered(args[0])) {
                return this.lookup(group, child, Arrays.copyOfRange(args, 1, args.length));
            }
        }

        return CommandLookup.success(group, command, args);
    }

    public abstract ContextState parseInput(@Nonnull final String input);
}
