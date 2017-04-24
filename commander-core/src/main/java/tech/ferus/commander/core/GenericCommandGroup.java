package tech.ferus.commander.core;

import tech.ferus.commander.api.Command;
import tech.ferus.commander.api.CommandGroup;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GenericCommandGroup implements CommandGroup {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericCommandGroup.class);

    public static int SHORT_DESC_LENGTH = 50;
    public static boolean IGNORE_SHORT_LENGTH = false;

    @Nonnull private final String name;
    @Nonnull private final Set<CommandGroup> children;
    @Nonnull private final Set<Command> commands;
    @Nonnull private final String description;
    @Nonnull private final String shortDescription;

    public GenericCommandGroup(@Nonnull final String name,
                               @Nonnull final String description,
                               @Nonnull final String shortDescription) {
        this.name = name;
        this.children = Sets.newHashSet();
        this.commands = Sets.newHashSet();
        this.description = description;
        this.shortDescription = shortDescription;
    }

    @Nonnull
    @Override
    public String getName() {
        return this.name;
    }

    @Nonnull
    @Override
    public Set<CommandGroup> getChildren() {
        return this.children;
    }

    @Override public boolean hasChildren() {
        return !this.children.isEmpty();
    }

    @Override public boolean hasChild(@Nonnull final String name) {
        return this.getChild(name) != null;
    }

    @Nullable
    @Override
    public CommandGroup getChild(final String name) {
        return this.children.stream()
                .filter(child -> child.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

    @Override public void addChild(@Nonnull final CommandGroup child) {
        if (this.hasChild(child.getName())) {
            LOGGER.error("A child of \"{}\" already has the name \"{}\"!", this.name, child.getName());
            return;
        }

        this.children.add(child);
    }

    @Override public void addChildren(@Nonnull final Collection<CommandGroup> children) {
        for (final CommandGroup child : children) {
            this.addChild(child);
        }
    }

    @Override public void removeChild(@Nonnull final String name) {
        this.removeChild(this.getChild(name));
    }

    @Override public void removeChild(@Nullable final CommandGroup child) {
        if (child != null) {
            this.children.remove(child);
        }
    }

    @Nonnull
    @Override
    public Set<Command> getCommands() {
        return this.commands;
    }

    @Override public boolean hasCommands() {
        return !this.commands.isEmpty();
    }

    @Override public boolean hasCommand(final String trigger) {
        return this.getCommand(trigger) != null;
    }

    @Nullable
    @Override
    public Command getCommand(final String trigger) {
        return this.commands.stream()
                .filter(command -> command.isTriggered(trigger))
                .findFirst().orElse(null);
    }

    @Override public void addCommand(@Nonnull final Command command) {
        if (this.hasCommand(command.getTrigger())) {
            LOGGER.error("A command of \"{}\" is already using the trigger/alias \"{}\"!", this.name, command.getTrigger());
            return;
        }

        for (final String alias : command.getAliases()) {
            if (this.hasCommand(alias)) {
                LOGGER.error("A command of \"{}\" is already using the trigger/alias \"{}\"!", this.name, command.getTrigger());
                return;
            }
        }

        this.commands.add(command);
    }

    @Override public void addCommands(@Nonnull final Collection<Command> commands) {
        for (final Command command : commands) {
            this.addCommand(command);
        }
    }

    @Nonnull
    @Override
    public String getDescription() {
        return this.description;
    }

    @Nonnull
    @Override
    public String getShortDescription() {
        return this.shortDescription.isEmpty() ?
                IGNORE_SHORT_LENGTH ?
                        this.description :
                        this.description.substring(0, Math.min(this.description.length(), SHORT_DESC_LENGTH)) :
                this.shortDescription;
    }
}
