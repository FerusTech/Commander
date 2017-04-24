package tech.ferus.commander.discord4j.context;

import tech.ferus.commander.api.context.Possibility;

import com.google.common.collect.Lists;
import java.util.List;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import javax.annotation.Nonnull;

public class UserResolver extends DiscordResolver<IUser> {

    public UserResolver(@Nonnull final IGuild guild, @Nonnull final DiscordArgument argument) {
        super(guild, argument);
    }

    @Nonnull
    @Override
    public List<Possibility<IUser>> resolve() {
        return Lists.newArrayList();
    }
}
