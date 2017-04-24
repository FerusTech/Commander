package tech.ferus.util.commander.core.context;

import tech.ferus.util.commander.api.context.ContextFailure;

import javax.annotation.Nonnull;

public enum GenericFailures implements ContextFailure {
    NO_PREFIX("No prefix was given."),
    NO_COMMAND_MATCH("No command was triggered."),
    NOT_ENOUGH_ARGUMENTS("Not enough arguments were given!"),
    ;

    @Nonnull private final String failReason;

    GenericFailures(@Nonnull final String failReason) {
        this.failReason = failReason;
    }

    @Nonnull
    @Override
    public String getFailReason() {
        return this.failReason;
    }
}
