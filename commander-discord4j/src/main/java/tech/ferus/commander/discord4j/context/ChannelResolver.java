package tech.ferus.commander.discord4j.context;

import tech.ferus.commander.api.context.Argument;
import tech.ferus.commander.api.context.ArgumentResolver;
import tech.ferus.commander.api.context.Possibility;
import tech.ferus.commander.core.context.GenericPossibility;
import tech.ferus.commander.discord4j.util.DiscordMention;

import com.google.common.collect.Lists;
import java.util.List;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;

import javax.annotation.Nonnull;

public class ChannelResolver implements ArgumentResolver<IChannel> {

    @Nonnull
    @Override
    public List<Possibility<IChannel>> resolve(@Nonnull final Argument argument) {
        final IGuild guild = argument.getContext().getProperty(DiscordProperties.GUILD);

        if (guild == null) {
            return Lists.newArrayList();
        }

        final List<Possibility<IChannel>> resolved = Lists.newArrayList();

        final DiscordMention mention = DiscordMention.parse(argument.getRawInput());

        if (mention != null) {
            final IChannel channel = guild.getChannelByID(mention.getSnowflake());
            if (channel != null) {
                resolved.add(this.createPossibility(channel));
            }

            return resolved; // Mention patterns can't be channel names, so we can skip.
        }

        try {
            final long snowflake = Long.parseLong(argument.getRawInput());
            final IChannel channel = guild.getChannelByID(snowflake);
            if (channel != null) {
                resolved.add(this.createPossibility(channel));
            }
        } catch (final NumberFormatException ignored) {} // Don't care

        final List<IChannel> soFar = argument.getPossibleValues(IChannel.class);

        for (final IChannel channel : guild.getChannels()) {
            if (soFar.contains(channel)) {
                continue;
            }

            if (channel.getName().toLowerCase().contains(argument.getRawInput())) {
                resolved.add(this.createPossibility(channel));
            }
        }

        return resolved;
    }

    private Possibility<IChannel> createPossibility(@Nonnull final IChannel channel) {
        return new GenericPossibility<>(channel.getName() + " (" + channel.getLongID() + ")", channel);
    }
}
