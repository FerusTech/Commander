package tech.ferus.commander.api;

import tech.ferus.commander.api.context.ArgumentContext;

import javax.annotation.Nonnull;

public interface CommandExecutor {

    void process(@Nonnull final ArgumentContext context);

    void execute(@Nonnull final ArgumentContext context);

    int getRequiredArguments();
}
