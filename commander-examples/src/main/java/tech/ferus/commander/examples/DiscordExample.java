package tech.ferus.commander.examples;

import tech.ferus.commander.core.GenericCommand;
import tech.ferus.commander.core.context.GenericContextState;
import tech.ferus.commander.discord4j.DiscordCommandManager;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.RequestBuffer;

public class DiscordExample {

    private static final String CLIENT_TOKEN = "some-token";

    private final IDiscordClient bot;
    private final DiscordCommandManager commandManager;

    public DiscordExample() {
        this.bot = new ClientBuilder().withToken(CLIENT_TOKEN).build();
        this.commandManager = new DiscordCommandManager();

        commandManager.addCommand(GenericCommand.builder("ping").build());
        bot.getDispatcher().registerListener(this);
    }

    @EventSubscriber
    public void onMessageReceived(final MessageReceivedEvent event) {
        final GenericContextState state = this.commandManager.parseInput(event.getMessage());
        RequestBuffer.request(() -> {
            if (state.isFailing()) {
                event.getChannel().sendMessage("Failed to execute command!: " +
                        state.getFailures().stream().findFirst().get().getFailReason());
            } else {
                state.getContext().getCommand().getExecutor().execute(state.getContext());
            }
        });
    }

    public static void main(final String[] args) {
        new DiscordExample();
    }
}
