package tech.ferus.util.commander.api;

import tech.ferus.util.commander.api.context.ArgumentContext;

import javax.annotation.Nonnull;

public interface CommandExecutor {

    void process(@Nonnull final ArgumentContext context);

    int getRequiredArguments();
}
