package tech.ferus.commander.discord4j;

import tech.ferus.commander.core.GenericCommandManager;
import tech.ferus.commander.core.context.GenericContextState;

import sx.blah.discord.handle.obj.IMessage;

import javax.annotation.Nonnull;

import static tech.ferus.commander.discord4j.context.DiscordProperties.CHANNEL;
import static tech.ferus.commander.discord4j.context.DiscordProperties.GUILD;
import static tech.ferus.commander.discord4j.context.DiscordProperties.MESSAGE;
import static tech.ferus.commander.discord4j.context.DiscordProperties.USER;

public class DiscordCommandManager extends GenericCommandManager {

    public DiscordCommandManager() {
        super();
    }

    public DiscordCommandManager(@Nonnull final String prefix) {
        super(prefix);
    }

    @Nonnull
    public GenericContextState parseInput(@Nonnull final IMessage message) {
        final GenericContextState state = this.parseInput(message.getContent());

        if (state.getContext() != null) {
            state.getContext().setProperty(GUILD, message.getGuild());
            state.getContext().setProperty(CHANNEL, message.getChannel());
            state.getContext().setProperty(MESSAGE, message);
            state.getContext().setProperty(USER, message.getAuthor());
        }

        return state;
    }
}
