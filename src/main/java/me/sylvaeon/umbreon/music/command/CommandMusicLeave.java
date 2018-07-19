package me.sylvaeon.umbreon.music.command;

import me.sylvaeon.umbreon.util.DiscordVoiceUtil;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandMusicLeave extends CommandMusic {
    @Override
    public void onCall(String[] args, User user, MessageChannel messageChannel) {
        DiscordVoiceUtil.leaveVoiceChannel(messageChannel.getGuild());
    }
}
