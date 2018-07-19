package me.sylvaeon.umbreon.music.command;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.sylvaeon.umbreon.util.DiscordVoiceUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;

public class CommandMusicShowQueue extends CommandMusic {
    @Override
    public void onCall(String[] args, User user, MessageChannel messageChannel) {
        AudioTrack[] tracks = DiscordVoiceUtil.getAudioTrackArray(user.getGuild());
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.MAGENTA);
    }
}
