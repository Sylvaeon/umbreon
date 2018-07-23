package me.sylvaeon.umbreon.music.command;

import me.sylvaeon.umbreon.Umbreon;
import me.sylvaeon.umbreon.util.DiscordTextUtil;
import me.sylvaeon.umbreon.util.Utility;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandMusicCreate extends CommandMusic {
	@Override
	public void onCall(String[] args, User user, MessageChannel messageChannel) {
		TextChannel textChannel = (TextChannel) messageChannel;
		Guild guild = textChannel.getGuild();
		String channelName;
		if(args.length == 0) {
			channelName = "music";
		} else {
			channelName = Utility.concatArray(args, '-');
		}
		TextChannel musicChannel = Umbreon.getMusicChannel(guild);
		if (musicChannel != null) {
			DiscordTextUtil.sendMessage(messageChannel, "Music channel already exists (" + musicChannel.getAsMention() + ")");
		} else {
			Umbreon.initMusicChannel(textChannel.getGuild(), channelName);
		}
	}
}
