package me.sylvaeon.umbreon.command.music;

import com.google.api.services.youtube.model.SearchResult;
import me.sylvaeon.umbreon.Google;
import me.sylvaeon.umbreon.Umbreon;
import me.sylvaeon.umbreon.util.DiscordVoiceUtil;
import me.sylvaeon.umbreon.util.Utility;
import net.dv8tion.jda.core.entities.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CommandMusicPlay extends CommandMusic {

    @Override
    public void onCall(String[] args, User user, MessageChannel messageChannel) {
	    TextChannel textChannel = (TextChannel) messageChannel;
	    Guild guild = textChannel.getGuild();
	    Member member = guild.getMember(user);
        DiscordVoiceUtil.joinVoiceChannel(member.getGuild(), member.getVoiceState().getChannel());
        String searchTerm;
        if(args.length == 1) {
            searchTerm = args[0];
        } else {
            searchTerm = Utility.concatArray(args, ' ');
        }
        try {
            new URL(searchTerm);
            DiscordVoiceUtil.loadAndPlay(member, textChannel, searchTerm);
        } catch (MalformedURLException e) {
            List<SearchResult> searchResultList = Google.youtubeSearchByKeyword(searchTerm);
            String vid = searchResultList.get(0).getId().getVideoId();
            DiscordVoiceUtil.loadAndPlay(member, textChannel, vid);
        }
        Object lock = new Object();
        synchronized (lock) {
        	try {
        		lock.wait(100);
	        } catch (InterruptedException e) {}
        }
	    Umbreon.updateMusicMessage(guild);
	    System.out.println(DiscordVoiceUtil.getCurrentTrack(guild));
    }

}
