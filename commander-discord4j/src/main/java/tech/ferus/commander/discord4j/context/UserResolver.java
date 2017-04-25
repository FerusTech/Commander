package tech.ferus.commander.discord4j.context;

import tech.ferus.commander.api.context.Argument;
import tech.ferus.commander.api.context.ArgumentResolver;
import tech.ferus.commander.api.context.Possibility;
import tech.ferus.commander.core.context.GenericPossibility;
import tech.ferus.commander.discord4j.util.DiscordMention;

import com.google.common.collect.Lists;
import java.util.List;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import javax.annotation.Nonnull;

public class UserResolver implements ArgumentResolver<IUser> {

    @Nonnull
    @Override
    public List<Possibility<IUser>> resolve(@Nonnull final Argument argument) {
        final IGuild guild = argument.getContext().getProperty(DiscordProperties.GUILD);

        if (guild == null) {
            return Lists.newArrayList();
        }

        final List<Possibility<IUser>> resolved = Lists.newArrayList();

        final DiscordMention mention = DiscordMention.parse(argument.getRawInput());

        if (mention != null) {
            final IUser user = guild.getUserByID(mention.getSnowflake());
            if (user != null) {
                resolved.add(this.createPossibility(guild, user));
            }
        }

        try {
            final long snowflake = Long.parseLong(argument.getRawInput());
            final IUser user = guild.getUserByID(snowflake);
            if (user != null) {
                resolved.add(this.createPossibility(guild, user));
            }

        } catch (final NumberFormatException ignored) {} // Don't care

        final List<IUser> soFar = argument.getPossibleValues(IUser.class);

        for (final IUser user : guild.getUsers()) {
            if (soFar.contains(user)) {
                continue;
            }

            if (user.getDisplayName(guild).toLowerCase().contains(argument.getRawInput().toLowerCase())) {
                resolved.add(this.createPossibility(guild, user));
                continue;
            }

            if (user.getName().toLowerCase().contains(argument.getRawInput())) {
                resolved.add(this.createPossibility(guild, user));
            }
        }

        return resolved;
    }

    private Possibility<IUser> createPossibility(@Nonnull final IGuild guild, @Nonnull final IUser user) {
        final String displayName = user.getDisplayName(guild);
        return new GenericPossibility<>(user.getName()
                + (displayName.equals(user.getName()) ? "" : " (" + displayName + ")")
                + " #" + user.getDiscriminator(), user);
    }
}
