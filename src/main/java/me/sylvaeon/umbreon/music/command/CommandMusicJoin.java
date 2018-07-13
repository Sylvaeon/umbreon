package me.sylvaeon.umbreon.music.command;

import me.sylvaeon.umbreon.Utility;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class CommandMusicJoin extends CommandMusic {
    @Override
    public void onCall(String[] args, Member member, TextChannel textChannel) {
        Utility.joinVoiceChannel(member.getGuild(), member.getVoiceState().getChannel());
    }
}
