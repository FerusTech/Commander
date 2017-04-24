package tech.ferus.util.commander.core.context;

import tech.ferus.util.commander.core.Command;
import tech.ferus.util.commander.core.CommandGroup;
import tech.ferus.util.commander.api.context.Argument;
import tech.ferus.util.commander.api.context.ArgumentContext;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GenericArgumentContext implements ArgumentContext {

    @Nonnull private final String rawInput;
    @Nonnull private final String[] rawParse;
    @Nonnull private final String[] rawArgs;
    @Nonnull private final List<GenericArgument> args;
    @Nullable private final CommandGroup group;
    @Nonnull private final Command command;
    @Nonnull private final Map<Property<?>, Object> properties;

    public GenericArgumentContext(@Nonnull final String rawInput,
                                  @Nonnull final String[] rawParse,
                                  @Nonnull final String[] rawArgs,
                                  @Nullable final CommandGroup group,
                                  @Nonnull final Command command) {
        this.rawInput = rawInput;
        this.rawParse = rawParse;
        this.rawArgs = rawArgs;
        this.args = Lists.newArrayList();
        this.group = group;
        this.command = command;
        this.properties = Maps.newHashMap();
    }

    public GenericArgumentContext(@Nonnull final String rawInput,
                                  @Nonnull final String[] rawParse,
                                  @Nonnull final String[] rawArgs,
                                  @Nonnull final Command command) {
        this(rawInput, rawParse, rawArgs, null, command);
    }

    @Nonnull public String getRawInput() {
        return this.rawInput;
    }

    @Nonnull public String[] getRawParse() {
        return this.rawParse;
    }

    @Nonnull public String[] getRawArgs() {
        return this.rawArgs;
    }

    @Nonnull public List<GenericArgument> getArgs() {
        return this.args;
    }

    @Nullable public GenericArgument getFirstForClarification() {
        return this.args.stream()
                .filter(argument -> !argument.isClarified())
                .findFirst().orElse(null);
    }

    public void addArguments(final Collection<GenericArgument> arguments) {
        this.args.addAll(arguments);
    }

    public boolean isAllClarified() {
        return this.getFirstForClarification() != null;
    }

    @Nullable public CommandGroup getGroup() {
        return this.group;
    }

    @Nonnull public Command getCommand() {
        return this.command;
    }

    @Nonnull public Map<Property<?>, Object> getProperties() {
        return this.properties;
    }

    public boolean hasProperties() {
        return !this.properties.isEmpty();
    }

    public boolean hasProperty(final Property property) {
        return this.properties.containsKey(property);
    }

    public <T> void setProperty(final Property<T> property, final T value) {
        this.properties.put(property, value);
    }

    @Nullable public <T> T getProperty(final Property<T> property) {
        return this.hasProperty(property) ? property.getType().cast(this.properties.get(property)) : null;
    }

    @Nonnull
    @Override
    public Iterator<Argument> iterator() {
        return new ArgumentIterator(this.args);
    }
}
