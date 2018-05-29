package me.sylvaeon.umbreon.music.command;

import com.google.api.services.youtube.model.SearchResult;
import me.sylvaeon.umbreon.helper.StringHelper;
import me.sylvaeon.umbreon.helper.MusicHelper;
import me.sylvaeon.umbreon.music.search.Search;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CommandMusicPlay extends CommandMusic {

    @Override
    public void onCall(String[] args, Member member, TextChannel textChannel) {
        MusicHelper.joinVoiceChannel(member);
        String searchTerm;
        if(args.length == 1) {
            searchTerm = args[0];
        } else {
            searchTerm = StringHelper.concatArray(args, ' ');
        }
        try {
            new URL(searchTerm);
            MusicHelper.loadAndPlay(member, textChannel, searchTerm);
        } catch (MalformedURLException e) {
            List<SearchResult> searchResultList = Search.searchByKeyword(searchTerm);
            String vid = searchResultList.get(0).getId().getVideoId();
            MusicHelper.loadAndPlay(member, textChannel, vid);
        }
    }

}
