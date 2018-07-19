package me.sylvaeon.umbreon.music.command;

import com.google.api.services.youtube.model.SearchResult;
import me.sylvaeon.umbreon.Google;
import me.sylvaeon.umbreon.util.DiscordVoiceUtil;
import me.sylvaeon.umbreon.util.Utility;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CommandMusicPlay extends CommandMusic {

    @Override
    public void onCall(String[] args, User user, MessageChannel messageChannel) {
        DiscordVoiceUtil.joinVoiceChannel(user.getGuild(), user.getVoiceState().getChannel());
        String searchTerm;
        if(args.length == 1) {
            searchTerm = args[0];
        } else {
            searchTerm = Utility.concatArray(args, ' ');
        }
        try {
            new URL(searchTerm);
            DiscordVoiceUtil.loadAndPlay(user, messageChannel, searchTerm);
        } catch (MalformedURLException e) {
            List<SearchResult> searchResultList = Google.youtubeSearchByKeyword(searchTerm);
            String vid = searchResultList.get(0).getId().getVideoId();
            DiscordVoiceUtil.loadAndPlay(user, messageChannel, vid);
        }
    }

}
