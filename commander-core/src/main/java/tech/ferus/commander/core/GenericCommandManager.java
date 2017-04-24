package tech.ferus.commander.core;

import tech.ferus.commander.api.Command;
import tech.ferus.commander.api.CommandGroup;
import tech.ferus.commander.api.CommandManager;
import tech.ferus.commander.core.context.GenericArgumentContext;
import tech.ferus.commander.core.context.GenericContextState;
import tech.ferus.commander.core.context.GenericFailures;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GenericCommandManager implements CommandManager {

    private static final Pattern ARGUMENT_PATTERN = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");

    @Nonnull private final Set<Command> commands;
    @Nonnull private final Set<CommandGroup> groups;
    @Nonnull private String prefix;

    public GenericCommandManager(@Nonnull final String prefix) {
        this.commands = Sets.newHashSet();
        this.groups = Sets.newHashSet();
        this.prefix = prefix;
    }

    public GenericCommandManager() {
        this("");
    }

    @Nonnull
    @Override
    public Set<Command> getCommands() {
        return this.commands;
    }

    @Override
    public void addCommand(@Nonnull final Command command) {
        this.commands.add(command);
    }

    @Nonnull
    @Override
    public Set<CommandGroup> getGroups() {
        return this.groups;
    }

    @Override
    public void addGroup(@Nonnull final CommandGroup group) {
        this.groups.add(group);
    }

    @Nonnull
    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public void setPrefix(@Nonnull final String prefix) {
        this.prefix = prefix;
    }

    @Nonnull
    @Override
    public GenericContextState parseInput(@Nonnull final String input) {
        final String rawInput;
        if (this.prefix.isEmpty()) {
            rawInput = input;
        } else {
            if (!input.startsWith(this.prefix)) {
                return new GenericContextState(GenericFailures.NO_PREFIX);
            } else {
                rawInput = input.replaceFirst(Pattern.quote(this.prefix), "");
            }
        }

        final String[] rawParse = rawInput.split("\\s+");
        final CommandLookup lookup = this.lookup(rawParse);

        if (lookup.getCommand() == null) {
            return new GenericContextState(GenericFailures.NO_COMMAND_MATCH);
        }

        final Command command = lookup.getCommand();
        final CommandGroup group = lookup.getGroup();
        final String[] rawArgs = this.parseArguments(String.join(" ", lookup.getRemaining()));

        if (rawArgs.length < command.getExecutor().getRequiredArguments()) {
            return new GenericContextState(GenericFailures.NOT_ENOUGH_ARGUMENTS);
        }

        return new GenericContextState(new GenericArgumentContext(rawInput, rawParse, rawArgs, group, command));
    }

    public String[] parseArguments(@Nonnull final String remaining) {
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
}
