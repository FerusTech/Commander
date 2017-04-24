package tech.ferus.util.commander.core;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CommandGroup {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandGroup.class);

    public static int SHORT_DESC_LENGTH = 50;
    public static boolean IGNORE_SHORT_LENGTH = false;

    @Nonnull private final String name;
    @Nonnull private final Set<CommandGroup> children;
    @Nonnull private final Set<Command> commands;
    @Nonnull private final String description;
    @Nonnull private final String shortDescription;

    public CommandGroup(@Nonnull final String name,
                        @Nonnull final String description,
                        @Nonnull final String shortDescription) {
        this.name = name;
        this.children = Sets.newHashSet();
        this.commands = Sets.newHashSet();
        this.description = description;
        this.shortDescription = shortDescription;
    }

    @Nonnull public final String getName() {
        return this.name;
    }

    @Nonnull public Set<CommandGroup> getChildren() {
        return this.children;
    }

    public boolean hasChildren() {
        return !this.children.isEmpty();
    }

    public boolean hasChild(@Nonnull final String name) {
        return this.getChild(name) != null;
    }

    @Nullable public CommandGroup getChild(final String name) {
        return this.children.stream()
                .filter(child -> child.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

    public void addChild(@Nonnull final CommandGroup child) {
        if (this.hasChild(child.getName())) {
            LOGGER.error("A child of \"{}\" already has the name \"{}\"!", this.name, child.getName());
            return;
        }

        this.children.add(child);
    }

    public void addChildren(@Nonnull final Collection<CommandGroup> children) {
        for (final CommandGroup child : children) {
            this.addChild(child);
        }
    }

    public void removeChild(@Nonnull final String name) {
        this.removeChild(this.getChild(name));
    }

    public void removeChild(@Nullable final CommandGroup child) {
        if (child != null) {
            this.children.remove(child);
        }
    }

    @Nonnull public Set<Command> getCommands() {
        return this.commands;
    }

    public boolean hasCommands() {
        return !this.commands.isEmpty();
    }

    public boolean hasCommand(final String trigger) {
        return this.getCommand(trigger) != null;
    }

    @Nullable public Command getCommand(final String trigger) {
        return this.commands.stream()
                .filter(command -> command.isTriggered(trigger))
                .findFirst().orElse(null);
    }

    public void addCommand(@Nonnull final Command command) {
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

    public void addCommands(@Nonnull final Collection<Command> commands) {
        for (final Command command : commands) {
            this.addCommand(command);
        }
    }

    @Nonnull public String getDescription() {
        return this.description;
    }

    @Nonnull public String getShortDescription() {
        return this.shortDescription.isEmpty() ?
                IGNORE_SHORT_LENGTH ?
                        this.description :
                        this.description.substring(0, Math.min(this.description.length(), SHORT_DESC_LENGTH)) :
                this.shortDescription;
    }
}
