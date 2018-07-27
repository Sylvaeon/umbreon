package me.sylvaeon.umbreon.command.music;

import me.sylvaeon.umbreon.util.DiscordVoiceUtil;
import net.dv8tion.jda.core.entities.*;

public class CommandMusicJoin extends CommandMusic {
    @Override
    public void onCall(String[] args, User user, MessageChannel messageChannel) {
    	Guild guild = DiscordVoiceUtil.getCurrentGuild(user);
	    if(guild != null) {
    		Member member = guild.getMember(user);
    		GuildVoiceState voiceState = member.getVoiceState();
		    DiscordVoiceUtil.joinVoiceChannel(guild, voiceState.getChannel());
	    } else {
	    }
	    
    }
}
