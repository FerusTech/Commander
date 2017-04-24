package tech.ferus.commander.core;

import tech.ferus.commander.api.Command;
import tech.ferus.commander.api.CommandExecutor;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GenericCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericCommand.class);

    public static int SHORT_DESC_LENGTH = 50;
    public static boolean IGNORE_SHORT_LENGTH = false;

    @Nonnull private final String trigger;
    @Nonnull private final Set<String> aliases;
    @Nonnull private final Set<Command> children;
    @Nonnull private final String description;
    @Nonnull private final String shortDescription;
    @Nonnull private final String usage;
    @Nonnull private final CommandExecutor executor;

    public GenericCommand(@Nonnull final String trigger,
                          @Nonnull final Set<String> aliases,
                          @Nonnull final String description,
                          @Nonnull final String shortDescription,
                          @Nonnull final String usage,
                          @Nonnull final CommandExecutor executor) {
        this.trigger = trigger;
        this.aliases = aliases;
        this.children = Sets.newHashSet();
        this.description = description;
        this.shortDescription = shortDescription;
        this.usage = usage;
        this.executor = executor;
    }

    @Nonnull
    @Override
    public String getTrigger() {
        return this.trigger;
    }

    @Nonnull
    @Override
    public Set<String> getAliases() {
        return this.aliases;
    }

    @Override public boolean isTriggered(@Nonnull final String trigger) {
        return this.trigger.equalsIgnoreCase(trigger)
                || this.aliases.stream().anyMatch(alias -> alias.equalsIgnoreCase(trigger));
    }

    @Nonnull
    @Override
    public Set<Command> getChildren() {
        return this.children;
    }

    @Override public boolean hasChildren() {
        return !this.children.isEmpty();
    }

    @Override public boolean hasChild(@Nonnull final String trigger) {
        return this.getChild(trigger) != null;
    }

    @Nullable
    @Override
    public Command getChild(@Nonnull final String trigger) {
        return this.children.stream()
                .filter(child -> child.isTriggered(trigger))
                .findFirst().orElse(null);
    }

    @Override public void addChild(@Nonnull final Command child) {
        if (this.hasChild(child.getTrigger())) {
            LOGGER.error("A child of \"{}\" is already using the trigger/alias \"{}\"!", this.trigger, child.getTrigger());
            return;
        }

        for (final String alias : child.getAliases()) {
            if (this.hasChild(alias)) {
                LOGGER.error("A child of \"{}\" is already using the trigger/alias \"{}\"!", this.trigger, child.getTrigger());
                return;
            }
        }

        this.children.add(child);
    }

    @Override public void addChildren(@Nonnull final Collection<Command> children) {
        for (final Command child : children) {
            this.addChild(child);
        }
    }

    @Override public void removeChild(@Nonnull final String trigger) {
        this.removeChild(this.getChild(trigger));
    }

    @Override public void removeChild(@Nullable final Command child) {
        if (child != null) {
            this.children.remove(child);
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

    @Nonnull
    @Override
    public String getUsage() {
        return this.usage;
    }

    @Nonnull
    @Override
    public CommandExecutor getExecutor() {
        return this.executor;
    }

    @Nonnull public static CommandBuilder builder(@Nonnull final String trigger, @Nonnull final Set<String> aliases) {
        return new CommandBuilder(trigger, aliases);
    }

    @Nonnull public static CommandBuilder builder(@Nonnull final String trigger, @Nonnull final String... aliases) {
        return new CommandBuilder(trigger, Sets.newHashSet(aliases));
    }

    @Nonnull public static CommandBuilder builder(@Nonnull final String trigger) {
        return new CommandBuilder(trigger, Sets.newHashSet());
    }
}
