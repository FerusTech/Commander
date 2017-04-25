package tech.ferus.commander.discord4j.context;

import tech.ferus.commander.api.context.Property;
import tech.ferus.commander.core.context.GenericProperty;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

public final class DiscordProperties {
    public static final Property<IGuild> GUILD = new GenericProperty<>(IGuild.class);
    public static final Property<IChannel> CHANNEL = new GenericProperty<>(IChannel.class);
    public static final Property<IMessage> MESSAGE = new GenericProperty<>(IMessage.class);
    public static final Property<IUser> USER = new GenericProperty<>(IUser.class);
}
