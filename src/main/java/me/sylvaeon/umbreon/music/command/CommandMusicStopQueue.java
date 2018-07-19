package me.sylvaeon.umbreon.music.command;

import me.sylvaeon.umbreon.util.DiscordVoiceUtil;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandMusicStopQueue extends CommandMusic {
    @Override
    public void onCall(String[] args, User user, MessageChannel messageChannel) {
        DiscordVoiceUtil.skipAllTracks(messageChannel);
        DiscordVoiceUtil.skipTrack(messageChannel);
    }
}
