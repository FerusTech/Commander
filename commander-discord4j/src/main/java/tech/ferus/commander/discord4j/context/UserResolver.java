package tech.ferus.commander.discord4j.context;

import tech.ferus.commander.api.context.Argument;
import tech.ferus.commander.api.context.Possibility;
import tech.ferus.commander.core.context.GenericPossibility;
import tech.ferus.commander.discord4j.util.DiscordMention;

import com.google.common.collect.Lists;
import java.util.List;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import javax.annotation.Nonnull;

public class UserResolver extends DiscordResolver<IUser> {

    public UserResolver(@Nonnull final IGuild guild) {
        super(guild);
    }

    @Nonnull
    @Override
    public List<Possibility<IUser>> resolve(@Nonnull final Argument argument) {
        final List<Possibility<IUser>> resolved = Lists.newArrayList();

        final DiscordMention mention = DiscordMention.parse(argument.getRawInput());

        if (mention != null) {
            final IUser user = this.getGuild().getUserByID(mention.getSnowflake());
            if (user != null) {
                resolved.add(this.createPossibility(user));
            }
        }

        try {
            final long snowflake = Long.parseLong(argument.getRawInput());
            final IUser user = this.getGuild().getUserByID(snowflake);
            if (user != null) {
                resolved.add(this.createPossibility(user));
            }

        } catch (final NumberFormatException ignored) {} // Don't care

        final List<IUser> soFar = argument.getPossibleValues(IUser.class);

        for (final IUser user : this.getGuild().getUsers()) {
            if (soFar.contains(user)) {
                continue;
            }

            if (user.getDisplayName(this.getGuild()).toLowerCase().contains(argument.getRawInput().toLowerCase())) {
                resolved.add(this.createPossibility(user));
                continue;
            }

            if (user.getName().toLowerCase().contains(argument.getRawInput())) {
                resolved.add(this.createPossibility(user));
            }
        }

        return resolved;
    }

    private Possibility<IUser> createPossibility(@Nonnull final IUser user) {
        final String displayName = user.getDisplayName(this.getGuild());
        return new GenericPossibility<>(user.getName()
                + (displayName.equals(user.getName()) ? "" : " (" + displayName + ")")
                + " #" + user.getDiscriminator(), user);
    }
}
