package me.sylvaeon.umbreon.command.music;

import me.sylvaeon.umbreon.Umbreon;
import me.sylvaeon.umbreon.util.DiscordVoiceUtil;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandMusicLeave extends CommandMusic {
    @Override
    public void onCall(String[] args, User user, MessageChannel messageChannel) {
	    Guild guild = DiscordVoiceUtil.getCurrentGuild(user);
	    if(guild.getMember(Umbreon.UMBREON).getVoiceState().inVoiceChannel()) {
		    DiscordVoiceUtil.leaveVoiceChannel(guild);
	    }
    }
}
