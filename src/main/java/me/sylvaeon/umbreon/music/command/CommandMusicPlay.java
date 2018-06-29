package me.sylvaeon.umbreon.music.command;

import com.google.api.services.youtube.model.SearchResult;
import me.sylvaeon.umbreon.Google;
import me.sylvaeon.umbreon.Utility;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CommandMusicPlay extends CommandMusic {

    @Override
    public void onCall(String[] args, Member member, MessageChannel textChannel) {
        Utility.joinVoiceChannel(member);
        String searchTerm;
        if(args.length == 1) {
            searchTerm = args[0];
        } else {
            searchTerm = Utility.concatArray(args, ' ');
        }
        try {
            new URL(searchTerm);
            Utility.loadAndPlay(member, textChannel, searchTerm);
        } catch (MalformedURLException e) {
            List<SearchResult> searchResultList = Google.youtubeSearchByKeyword(searchTerm);
            String vid = searchResultList.get(0).getId().getVideoId();
            Utility.loadAndPlay(member, textChannel, vid);
        }
    }

}
