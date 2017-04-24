package tech.ferus.util.commander.core;

import tech.ferus.util.commander.api.Command;
import tech.ferus.util.commander.api.CommandExecutor;
import tech.ferus.util.commander.api.context.ArgumentContext;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

public class CommandBuilder {

    private static final CommandExecutor DEFAULT_EXECUTOR = new DefaultExecutor();

    private final String trigger;
    private final Set<String> aliases;
    private final Set<CommandBuilder> childBuilders;
    private final Set<Command> children;
    private String description;
    private String shortDescription;
    private String usage;
    private CommandExecutor executor;

    public CommandBuilder(@Nonnull final String trigger, @Nonnull final Set<String> aliases) {
        this.trigger = trigger;
        this.aliases = aliases;
        this.childBuilders = Sets.newHashSet();
        this.children = Sets.newHashSet();
        this.description = "";
        this.shortDescription = "";
        this.usage = "";
        this.executor = DEFAULT_EXECUTOR;
    }

    @Nonnull public CommandBuilder addChild(@Nonnull final CommandBuilder child) {
        this.childBuilders.add(child);
        return this;
    }

    @Nonnull public CommandBuilder addChild(@Nonnull final Command child) {
        this.children.add(child);
        return this;
    }

    @Nonnull public CommandBuilder setDescription(@Nonnull final String description) {
        this.description = description;
        return this;
    }

    @Nonnull public CommandBuilder setShortDescription(@Nonnull final String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    @Nonnull public CommandBuilder setUsage(@Nonnull final String usage) {
        this.usage = usage;
        return this;
    }

    @Nonnull public CommandBuilder setExecutor(@Nonnull final CommandExecutor executor) {
        this.executor = executor;
        return this;
    }

    public GenericCommand build() {
        this.children.addAll(this.childBuilders.stream().map(CommandBuilder::build).collect(Collectors.toSet()));

        final GenericCommand command = new GenericCommand(this.trigger, this.aliases, this.description, this.shortDescription, this.usage, this.executor);
        command.addChildren(this.children);

        return command;
    }

    public static class DefaultExecutor implements CommandExecutor {

        @Override
        public void process(@Nonnull ArgumentContext context) {}

        @Override
        public int getRequiredArguments() {
            return 0;
        }
    }
}
