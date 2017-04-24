package tech.ferus.util.commander.api.context;

import java.util.List;

import javax.annotation.Nonnull;

public interface ArgumentResolver {

    List<Possibility<?>> resolve(@Nonnull final Argument argument);
}
