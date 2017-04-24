package tech.ferus.util.commander.core;

import tech.ferus.util.commander.core.context.GenericArgumentContext;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(Command.class);

    public static int SHORT_DESC_LENGTH = 50;
    public static boolean IGNORE_SHORT_LENGTH = false;

    @Nonnull private final String trigger;
    @Nonnull private final Set<String> aliases;
    @Nonnull private final Set<Command> children;
    @Nonnull private final String description;
    @Nonnull private final String shortDescription;
    @Nonnull private final String usage;
    @Nonnull private final CommandExecutor executor;

    public Command(@Nonnull final String trigger,
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

    @Nonnull public String getTrigger() {
        return this.trigger;
    }

    @Nonnull public Set<String> getAliases() {
        return this.aliases;
    }

    public boolean isTriggered(@Nonnull final String trigger) {
        return this.trigger.equalsIgnoreCase(trigger)
                || this.aliases.stream().anyMatch(alias -> alias.equalsIgnoreCase(trigger));
    }

    @Nonnull public Set<Command> getChildren() {
        return this.children;
    }

    public boolean hasChildren() {
        return !this.children.isEmpty();
    }

    public boolean hasChild(@Nonnull final String trigger) {
        return this.getChild(trigger) != null;
    }

    @Nullable public Command getChild(@Nonnull final String trigger) {
        return this.children.stream()
                .filter(child -> child.isTriggered(trigger))
                .findFirst().orElse(null);
    }

    public void addChild(@Nonnull final Command child) {
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

    public void addChildren(@Nonnull final Collection<Command> children) {
        for (final Command child : children) {
            this.addChild(child);
        }
    }

    public void removeChild(@Nonnull final String trigger) {
        this.removeChild(this.getChild(trigger));
    }

    public void removeChild(@Nullable final Command child) {
        if (child != null) {
            this.children.remove(child);
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

    @Nonnull public String getUsage() {
        return this.usage;
    }

    @Nonnull public CommandExecutor getExecutor() {
        return this.executor;
    }

    @Nonnull public static Builder builder(@Nonnull final String trigger, @Nonnull final Set<String> aliases) {
        return new Builder(trigger, aliases);
    }

    @Nonnull public static Builder builder(@Nonnull final String trigger, @Nonnull final String... aliases) {
        return new Builder(trigger, Sets.newHashSet(aliases));
    }

    @Nonnull public static Builder builder(@Nonnull final String trigger) {
        return new Builder(trigger, Sets.newHashSet());
    }

    public static class Builder {

        private static final CommandExecutor DEFAULT_EXECUTOR = new DefaultExecutor();

        private final String trigger;
        private final Set<String> aliases;
        private final Set<Builder> childBuilders;
        private final Set<Command> children;
        private String description;
        private String shortDescription;
        private String usage;
        private CommandExecutor executor;

        private Builder(@Nonnull final String trigger, @Nonnull final Set<String> aliases) {
            this.trigger = trigger;
            this.aliases = aliases;
            this.childBuilders = Sets.newHashSet();
            this.children = Sets.newHashSet();
            this.description = "";
            this.shortDescription = "";
            this.usage = "";
            this.executor = DEFAULT_EXECUTOR;
        }

        @Nonnull public Builder addChild(@Nonnull final Builder child) {
            this.childBuilders.add(child);
            return this;
        }

        @Nonnull public Builder addChild(@Nonnull final Command child) {
            this.children.add(child);
            return this;
        }

        @Nonnull public Builder setDescription(@Nonnull final String description) {
            this.description = description;
            return this;
        }

        @Nonnull public Builder setShortDescription(@Nonnull final String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        @Nonnull public Builder setUsage(@Nonnull final String usage) {
            this.usage = usage;
            return this;
        }

        @Nonnull public Builder setExecutor(@Nonnull final CommandExecutor executor) {
            this.executor = executor;
            return this;
        }

        public Command build() {
            for (final Builder child : this.childBuilders) {
                this.children.add(child.build());
            }

            final Command command = new Command(this.trigger, this.aliases, this.description, this.shortDescription, this.usage, this.executor);
            command.addChildren(this.children);
            return command;
        }
    }

    private static class DefaultExecutor implements CommandExecutor {

        @Override
        public int getRequiredArguments() {
            return 0;
        }

        @Override
        public void process(@Nonnull final GenericArgumentContext context) {

        }
    }
}
