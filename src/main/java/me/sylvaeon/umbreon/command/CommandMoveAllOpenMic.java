package me.sylvaeon.umbreon.command;

import me.sylvaeon.umbreon.util.Utility;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;

import java.util.List;

public class CommandMoveAllOpenMic extends Command {
	@Override
	public void onCall(String[] args, User user, MessageChannel messageChannel) {
		VoiceChannel voiceChannel = user.getVoiceState().getChannel();
		List<VoiceChannel> channelList = user.getGuild().getVoiceChannelsByName(Utility.concatArray(args, ' '), true);
		if(!channelList.isEmpty()) {
			VoiceChannel target = channelList.get(0);
			if (voiceChannel != null && target != null) {
				for (Member m : voiceChannel.getMembers()) {
					if(!m.getVoiceState().isMuted()) {
						voiceChannel.getGuild().getController().moveVoiceMember(m, target).queue();
					}
				}
			}
		}
	}

	@Override
	public boolean requiresAdmin() {
		return true;
	}
}
