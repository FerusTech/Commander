package tech.ferus.commander.discord4j.context;

import tech.ferus.commander.api.context.ArgumentResolver;

import sx.blah.discord.handle.obj.IGuild;

import javax.annotation.Nonnull;

public abstract class DiscordResolver<T> implements ArgumentResolver<T> {

    @Nonnull private final IGuild guild;

    public DiscordResolver(@Nonnull final IGuild guild) {
        this.guild = guild;
    }

    @Nonnull public IGuild getGuild() {
        return this.guild;
    }
}
