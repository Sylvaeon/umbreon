package me.sylvaeon.umbreon.music.command;

import me.sylvaeon.umbreon.helper.MusicHelper;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class CommandMusicLeave extends CommandMusic {
    @Override
    public void onCall(String[] args, Member member, TextChannel textChannel) {
        MusicHelper.leaveVoiceChannel();
    }
}
