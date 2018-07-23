package me.sylvaeon.umbreon.command;

import me.sylvaeon.umbreon.util.Utility;
import net.dv8tion.jda.core.entities.*;

import java.util.List;

public class CommandMoveAll extends Command {
    @Override
    public void onCall(String[] args, User user, MessageChannel messageChannel) {
	    TextChannel textChannel = (TextChannel) messageChannel;
	    Guild guild = textChannel.getGuild();
	    Member member = guild.getMember(user);
        VoiceChannel voiceChannel = member.getVoiceState().getChannel();
        List<VoiceChannel> channelList = guild.getVoiceChannelsByName(Utility.concatArray(args, ' '), true);
        if(!channelList.isEmpty()) {
            VoiceChannel target = channelList.get(0);
            if (voiceChannel != null && target != null) {
                for (Member m : voiceChannel.getMembers()) {
                    voiceChannel.getGuild().getController().moveVoiceMember(m, target).queue();
                }
            }
        }
    }
	
	@Override
	public boolean requiresGuild() {
		return true;
	}
	
	@Override
    public boolean requiresAdmin() {
        return true;
    }
}
