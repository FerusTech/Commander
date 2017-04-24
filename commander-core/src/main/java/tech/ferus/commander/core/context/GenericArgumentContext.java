package tech.ferus.commander.core.context;

import tech.ferus.commander.api.Command;
import tech.ferus.commander.api.CommandGroup;
import tech.ferus.commander.api.context.Argument;
import tech.ferus.commander.api.context.ArgumentContext;
import tech.ferus.commander.api.context.Property;

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
    @Nonnull private final List<Argument> args;
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

    @Nonnull
    @Override
    public String getRawInput() {
        return this.rawInput;
    }

    @Nonnull
    @Override
    public String[] getRawParse() {
        return this.rawParse;
    }

    @Nonnull
    @Override
    public String[] getRawArgs() {
        return this.rawArgs;
    }

    @Nonnull
    @Override
    public List<Argument> getArgs() {
        return this.args;
    }

    @Nullable
    @Override
    public Argument getFirstForClarification() {
        return this.args.stream()
                .filter(argument -> !argument.isClarified())
                .findFirst().orElse(null);
    }

    @Override
    public void addArguments(@Nonnull Collection<Argument> arguments) {
        this.args.addAll(arguments);
    }

    @Override
    public boolean isAllClarified() {
        return this.getFirstForClarification() != null;
    }

    @Nullable
    @Override
    public CommandGroup getGroup() {
        return this.group;
    }

    @Nonnull
    @Override
    public Command getCommand() {
        return this.command;
    }

    @Nonnull
    @Override
    public Map<Property<?>, Object> getProperties() {
        return this.properties;
    }

    @Override
    public boolean hasProperties() {
        return !this.properties.isEmpty();
    }

    @Override
    public boolean hasProperty(@Nonnull Property property) {
        return this.properties.containsKey(property);
    }

    @Override
    public <T> void setProperty(@Nonnull Property<T> property, T value) {
        this.properties.put(property, value);
    }

    @Nullable
    @Override
    public <T> T getProperty(@Nonnull Property<T> property) {
        return this.hasProperty(property) ? property.getType().cast(this.properties.get(property)) : null;
    }

    @Nonnull
    @Override
    public Iterator<Argument> iterator() {
        return new GenericArgumentIterator(this.args);
    }
}
