package me.sylvaeon.umbreon.music.command;

import me.sylvaeon.umbreon.Utility;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;

public class CommandMusicStopQueue extends CommandMusic {
    @Override
    public void onCall(String[] args, Member member, MessageChannel textChannel) {
        Utility.skipAllTracks(textChannel);
        Utility.skipTrack(textChannel);
    }
}
