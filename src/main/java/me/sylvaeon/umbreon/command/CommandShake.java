package me.sylvaeon.umbreon.command;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;

public class CommandShake extends Command {
    @Override
    public void onCall(String[] args, Member member, MessageChannel textChannel) {
        VoiceChannel currentChannel = member.getVoiceState().getChannel();
        Member target = null;
        if(args.length == 2) {
            target = member.getGuild().getMembersByEffectiveName(args[1], false).get(0);
        }
        if(target == null) {
            target = member;
        }
        if(currentChannel != null) {
            for(VoiceChannel vc : member.getGuild().getVoiceChannels()) {
                if(vc != currentChannel) {
                    try {
                        member.getGuild().getController().moveVoiceMember(target, vc).queue();
                        break;
                    } catch (Exception e) {

                    }
                }
            }
            member.getGuild().getController().moveVoiceMember(target, currentChannel).queue();
        }
    }
}
