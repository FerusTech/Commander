package tech.ferus.util.commander.api.context;

import javax.annotation.Nonnull;

public interface ContextFailure {

    @Nonnull String getFailReason();
}
