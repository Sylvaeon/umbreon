package me.sylvaeon.umbreon.music.command;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.sylvaeon.umbreon.Utility;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;

public class CommandMusicNowPlaying extends CommandMusic {
    @Override
    public void onCall(String[] args, Member member, MessageChannel textChannel) {
        AudioTrack audioTrack = Utility.getCurrentTrack();
        if(audioTrack != null) {
            textChannel.sendMessage("Now playing: " + audioTrack.getInfo().title).queue();
        }
    }
}
