package me.sylvaeon.umbreon.music.command;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.sylvaeon.umbreon.Utility;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.awt.*;

public class CommandMusicShowQueue extends CommandMusic {
    @Override
    public void onCall(String[] args, Member member, MessageChannel textChannel) {
        AudioTrack[] tracks = Utility.getAudioTrackArray();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.MAGENTA);
    }
}
