package me.sylvaeon.umbreon.command;

import me.sylvaeon.umbreon.helper.StringHelper;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;

import java.util.List;

public class CommandMoveAll extends Command {
    @Override
    public void onCall(String[] args, Member member, TextChannel textChannel) {
        VoiceChannel voiceChannel = member.getVoiceState().getChannel();
        List<VoiceChannel> channelList = textChannel.getGuild().getVoiceChannelsByName(StringHelper.concatArray(args, ' '), true);
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
    public boolean requiresAdmin() {
        return true;
    }
}
