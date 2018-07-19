package me.sylvaeon.umbreon.command;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;

public class CommandShake extends Command {
    @Override
    public void onCall(String[] args, User user, MessageChannel messageChannel) {
        VoiceChannel currentChannel = user.getVoiceState().getChannel();
        User target = null;
        if(args.length == 2) {
            target = user.getGuild().getMembersByEffectiveName(args[1], false).get(0);
        }
        if(target == null) {
            target = user;
        }
        if(currentChannel != null) {
            for(VoiceChannel vc : user.getGuild().getVoiceChannels()) {
                if(vc != currentChannel) {
                    try {
                        user.getGuild().getController().moveVoiceMember(target, vc).queue();
                        break;
                    } catch (Exception e) {

                    }
                }
            }
            user.getGuild().getController().moveVoiceMember(target, currentChannel).queue();
        }
    }
}
