package me.sylvaeon.umbreon.music.command;

import me.sylvaeon.umbreon.helper.MusicHelper;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class CommandMusicSkip extends CommandMusic {

    @Override
    public void onCall(String[] args, Member member, TextChannel textChannel) {
        MusicHelper.skipTrack(textChannel);
        textChannel.sendMessage("Skipped to next track.").queue();
    }

}
