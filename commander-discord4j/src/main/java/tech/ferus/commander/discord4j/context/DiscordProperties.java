package tech.ferus.commander.discord4j.context;

import tech.ferus.commander.api.context.Property;
import tech.ferus.commander.core.context.GenericProperty;

public final class DiscordProperties {
    public static final Property<Long> GUILD = new GenericProperty<>(Long.class);
    public static final Property<Long> CHANNEL = new GenericProperty<>(Long.class);
    public static final Property<Long> MESSAGE = new GenericProperty<>(Long.class);
    public static final Property<Long> USER = new GenericProperty<>(Long.class);
}
