package me.sylvaeon.umbreon.music.command;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.sylvaeon.umbreon.util.DiscordVoiceUtil;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandMusicNowPlaying extends CommandMusic {
    @Override
    public void onCall(String[] args, User user, MessageChannel messageChannel) {
        AudioTrack audioTrack = DiscordVoiceUtil.getCurrentTrack(user.getGuild());
        if(audioTrack != null) {
            messageChannel.sendMessage("Now playing: " + audioTrack.getInfo().title).queue();
        }
    }
}
