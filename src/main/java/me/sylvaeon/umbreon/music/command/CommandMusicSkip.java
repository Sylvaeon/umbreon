package me.sylvaeon.umbreon.music.command;

import me.sylvaeon.umbreon.Umbreon;
import me.sylvaeon.umbreon.util.DiscordVoiceUtil;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandMusicSkip extends CommandMusic {

    @Override
    public void onCall(String[] args, User user, MessageChannel messageChannel) {
	    TextChannel textChannel = (TextChannel) messageChannel;
	    DiscordVoiceUtil.skipTrack(textChannel);
	    Guild guild = textChannel.getGuild();
	    Umbreon.updateMusicMessage(guild);
    }

}
