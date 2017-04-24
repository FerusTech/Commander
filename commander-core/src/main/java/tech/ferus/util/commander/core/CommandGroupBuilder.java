package tech.ferus.util.commander.core;

import tech.ferus.util.commander.api.Command;
import tech.ferus.util.commander.api.CommandGroup;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

public class CommandGroupBuilder {

    @Nonnull private final String name;
    @Nonnull private final Set<CommandGroupBuilder> childBuilders;
    @Nonnull private final Set<CommandGroup> children;
    @Nonnull private final Set<CommandBuilder> commandBuilders;
    @Nonnull private final Set<Command> commands;
    @Nonnull private String description;
    @Nonnull private String shortDescription;

    public CommandGroupBuilder(@Nonnull final String name) {
        this.name = name;
        this.childBuilders = Sets.newHashSet();
        this.children = Sets.newHashSet();
        this.commandBuilders = Sets.newHashSet();
        this.commands = Sets.newHashSet();
        this.description = "";
        this.shortDescription = "";
    }

    @Nonnull public CommandGroupBuilder addChild(@Nonnull final CommandGroupBuilder child) {
        this.childBuilders.add(child);
        return this;
    }

    @Nonnull public CommandGroupBuilder addChild(@Nonnull final CommandGroup child) {
        this.children.add(child);
        return this;
    }

    @Nonnull public CommandGroupBuilder addCommand(@Nonnull final CommandBuilder command) {
        this.commandBuilders.add(command);
        return this;
    }

    @Nonnull public CommandGroupBuilder addCommand(@Nonnull final Command command) {
        this.commands.add(command);
        return this;
    }

    @Nonnull public CommandGroupBuilder setDescription(@Nonnull final String description) {
        this.description = description;
        return this;
    }

    @Nonnull public CommandGroupBuilder setShortDescription(@Nonnull final String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public GenericCommandGroup build() {
        this.children.addAll(this.childBuilders.stream().map(CommandGroupBuilder::build).collect(Collectors.toSet()));
        this.commands.addAll(this.commandBuilders.stream().map(CommandBuilder::build).collect(Collectors.toSet()));

        final GenericCommandGroup group = new GenericCommandGroup(this.name, this.description, this.shortDescription);
        group.addChildren(this.children);
        group.addCommands(this.commands);

        return group;
    }
}
