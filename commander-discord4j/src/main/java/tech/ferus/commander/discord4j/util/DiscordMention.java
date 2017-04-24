package tech.ferus.commander.discord4j.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sx.blah.discord.handle.obj.IGuild;

public class DiscordMention {

    public enum Type {
        USER,
        USER_NICKNAME,
        ROLE,
        CHANNEL,
        INVALID,
        ;

        public static Type of(final String seq) {
            switch (seq) {
                case "@":
                    return USER;
                case "@!":
                    return USER_NICKNAME;
                case "@&":
                    return ROLE;
                case "#":
                    return CHANNEL;
                default:
                    return INVALID;
            }
        }
    }

    private static final String MENTION_REGEX = "<(#|@!|@&|@)([0-9]+)>";
    private static final Pattern MENTION_PATTERN = Pattern.compile(MENTION_REGEX);

    private final Type type;
    private final String original;
    private final long snowflake;

    private DiscordMention(final Type type, final String original, final long snowflake) {
        this.type = type;
        this.original = original;
        this.snowflake = snowflake;
    }

    public Type getType() {
        return this.type;
    }

    public String getOriginal() {
        return this.original;
    }

    public long getSnowflake() {
        return this.snowflake;
    }

    public Object getSubject(final IGuild guild) {
        switch (this.type) {
            case ROLE:
                return guild.getRoleByID(this.snowflake);
            case CHANNEL:
                return guild.getChannelByID(this.snowflake);
            default:
                return guild.getUserByID(this.snowflake);
        }
    }

    public static DiscordMention parse(final String original) {
        final Matcher matcher = MENTION_PATTERN.matcher(original);

        if (!matcher.find()) {
            return null;
        }

        if (matcher.groupCount() != 2) {
            return null;
        }

        final Type type = Type.of(matcher.group(1));

        if (type == Type.INVALID) {
            return null;
        }

        return new DiscordMention(type, original, Long.parseLong(matcher.group(2)));
    }
}
