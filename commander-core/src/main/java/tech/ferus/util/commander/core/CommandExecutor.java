package tech.ferus.util.commander.core;

import tech.ferus.util.commander.core.context.GenericArgumentContext;

public interface CommandExecutor {

    int getRequiredArguments();

    void process(final GenericArgumentContext context);
}
