package me.sylvaeon.umbreon.music.command;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.sylvaeon.umbreon.helper.MusicHelper;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class CommandMusicNowPlaying extends CommandMusic {
    @Override
    public void onCall(String[] args, Member member, TextChannel textChannel) {
        AudioTrack audioTrack = MusicHelper.getCurrentTrack();
        if(audioTrack != null) {
            textChannel.sendMessage("Now playing: " + audioTrack.getInfo().title).queue();
        }
    }
}
