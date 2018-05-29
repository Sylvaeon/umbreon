package me.sylvaeon.umbreon.music.command;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.sylvaeon.umbreon.helper.MusicHelper;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

import java.awt.*;

public class CommandMusicShowQueue extends CommandMusic {
    @Override
    public void onCall(String[] args, Member member, TextChannel textChannel) {
        AudioTrack[] tracks = MusicHelper.getAudioTrackArray();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.MAGENTA);
    }
}
