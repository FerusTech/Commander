package tech.ferus.commander.discord4j.context;

import tech.ferus.commander.core.context.GenericResolver;

import sx.blah.discord.handle.obj.IGuild;

import javax.annotation.Nonnull;

public class DiscordResolver<T> extends GenericResolver<T> {

    @Nonnull private final IGuild guild;

    public DiscordResolver(@Nonnull final IGuild guild, @Nonnull final DiscordArgument argument) {
        super(argument);

        this.guild = guild;
    }

    @Nonnull
    @Override
    public DiscordArgument getArgument() {
        return (DiscordArgument) super.getArgument();
    }

    @Nonnull public IGuild getGuild() {
        return this.guild;
    }
}
