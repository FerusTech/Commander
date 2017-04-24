package tech.ferus.commander.discord4j.context;

import tech.ferus.commander.api.Command;
import tech.ferus.commander.api.CommandGroup;
import tech.ferus.commander.core.context.GenericArgumentContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DiscordArgumentContext extends GenericArgumentContext {

    private final long guild;
    private final long channel;
    private final long message;
    private final long user;

    public DiscordArgumentContext(final long guild,
                                  final long channel,
                                  final long message,
                                  final long user,
                                  @Nonnull final String rawInput,
                                  @Nonnull final String[] rawParse,
                                  @Nonnull final String[] rawArgs,
                                  @Nullable final CommandGroup group,
                                  @Nonnull final Command command) {
        super(rawInput, rawParse, rawArgs, group, command);

        this.guild = guild;
        this.channel = channel;
        this.message = message;
        this.user = user;
    }

    public DiscordArgumentContext(final long guild,
                                  final long channel,
                                  final long message,
                                  final long user,
                                  @Nonnull final String rawInput,
                                  @Nonnull final String[] rawParse,
                                  @Nonnull final String[] rawArgs,
                                  @Nonnull final Command command) {
        super(rawInput, rawParse, rawArgs, command);

        this.guild = guild;
        this.channel = channel;
        this.message = message;
        this.user = user;
    }

    public long getGuild() {
        return this.guild;
    }

    public long getChannel() {
        return this.channel;
    }

    public long getMessage() {
        return this.message;
    }

    public long getUser() {
        return this.user;
    }
}
