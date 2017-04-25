package tech.ferus.commander.discord4j.context;

import tech.ferus.commander.api.context.Argument;
import tech.ferus.commander.api.context.ArgumentResolver;
import tech.ferus.commander.api.context.Possibility;
import tech.ferus.commander.core.context.GenericPossibility;
import tech.ferus.commander.discord4j.util.DiscordMention;

import com.google.common.collect.Lists;
import java.util.List;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;

import javax.annotation.Nonnull;

public class RoleResolver implements ArgumentResolver<IRole> {

    @Nonnull
    @Override
    public List<Possibility<IRole>> resolve(@Nonnull final Argument argument) {
        final IGuild guild = argument.getContext().getProperty(DiscordProperties.GUILD);

        if (guild == null) {
            return Lists.newArrayList();
        }

        final List<Possibility<IRole>> resolved = Lists.newArrayList();

        final DiscordMention mention = DiscordMention.parse(argument.getRawInput());

        if (mention != null) {
            final IRole role = guild.getRoleByID(mention.getSnowflake());
            if (role != null) {
                resolved.add(this.createPossibility(role));
            }
        }

        try {
            final long snowflake = Long.parseLong(argument.getRawInput());
            final IRole role = guild.getRoleByID(snowflake);
            if (role != null) {
                resolved.add(this.createPossibility(role));
            }
        } catch (final NumberFormatException ignored) {} // Don't care

        final List<IRole> soFar = argument.getPossibleValues(IRole.class);

        for (final IRole role : guild.getRoles()) {
            if (soFar.contains(role)) {
                continue;
            }

            if (role.getName().toLowerCase().contains(argument.getRawInput())) {
                resolved.add(this.createPossibility(role));
            }
        }

        return resolved;
    }

    private Possibility<IRole> createPossibility(@Nonnull final IRole role) {
        return new GenericPossibility<>(role.getName() + " (" + role.getLongID() + ")", role);
    }
}
