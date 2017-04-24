package tech.ferus.commander.discord4j.context;

import tech.ferus.commander.core.context.GenericArgument;

import javax.annotation.Nonnull;

public class DiscordArgument extends GenericArgument {

    public DiscordArgument(@Nonnull final DiscordArgumentContext context, @Nonnull final String rawInput) {
        super(context, rawInput);
    }

    @Nonnull
    @Override
    public DiscordArgumentContext getContext() {
        return (DiscordArgumentContext) super.getContext();
    }
}
