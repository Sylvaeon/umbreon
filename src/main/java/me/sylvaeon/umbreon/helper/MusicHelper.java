package me.sylvaeon.umbreon.helper;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.sylvaeon.umbreon.Umbreon;
import me.sylvaeon.umbreon.music.GuildMusicManager;
import me.sylvaeon.umbreon.music.TrackScheduler;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;

public class MusicHelper {
    public static void loadAndPlay(Member member, final TextChannel channel, final String searchTerm) {
        GuildMusicManager musicManager = Umbreon.getGuildAudioPlayer();

        Umbreon.playerManager.loadItemOrdered(musicManager, searchTerm, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                channel.sendMessage("Adding to queue " + track.getInfo().title).queue();

                play(musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                channel.sendMessage("Adding to queue playlist " + playlist.getName()).queue();

                for(AudioTrack track : playlist.getTracks()) {
                    play(musicManager, track);
                }
            }

            @Override
            public void noMatches() {
                channel.sendMessage("No matches found").queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("Could not play: " + exception.getMessage()).queue();
            }
        });
    }

    public static void play(GuildMusicManager musicManager, AudioTrack track) {
        musicManager.scheduler.queue(track);
    }

    public static void skipTrack(TextChannel channel) {
        GuildMusicManager musicManager = Umbreon.getGuildAudioPlayer();
        musicManager.scheduler.nextTrack();
    }

    public static void skipAllTracks(TextChannel channel) {
        GuildMusicManager musicManager = Umbreon.getGuildAudioPlayer();
        musicManager.scheduler.clearTrackQueue();
    }

    public static void joinVoiceChannel(Member member) {
        if(!Umbreon.UMBREON.getVoiceState().inVoiceChannel()) {
            try {
                VoiceChannel vc = member.getVoiceState().getChannel();
                member.getGuild().getAudioManager().openAudioConnection(vc);
            } catch (Exception e) {

            }
        }
    }

    public static void leaveVoiceChannel() {
        if(Umbreon.UMBREON.getVoiceState().inVoiceChannel()) {
            Umbreon.guild.getAudioManager().closeAudioConnection();
        }
    }

    public static AudioTrack[] getAudioTrackArray() {
        GuildMusicManager manager = Umbreon.getGuildAudioPlayer();
        AudioTrack[] audioTracks = new AudioTrack[manager.scheduler.getQueue().size() + 1];
        AudioTrack[] queueArray = manager.scheduler.getQueueArray();
        audioTracks[0] = manager.player.getPlayingTrack().makeClone();
        for(int i = 0; i < queueArray.length; i++) {
            audioTracks[i+1] = queueArray[i];
        }
        return audioTracks;
    }

    public static AudioTrack getCurrentTrack() {
        return Umbreon.getGuildAudioPlayer().player.getPlayingTrack();
    }

    public static void repeatTrackBack() {
        GuildMusicManager manager = Umbreon.getGuildAudioPlayer();
        manager.scheduler.queue(manager.player.getPlayingTrack().makeClone());
    }

    public static void repeatTrackFront() {
        GuildMusicManager manager = Umbreon.getGuildAudioPlayer();
        TrackScheduler scheduler = manager.scheduler;
        AudioTrack[] trackClone = new AudioTrack[scheduler.getQueue().size()];
        trackClone = scheduler.getQueue().toArray(trackClone);
        scheduler.clearTrackQueue();
        scheduler.queue(manager.player.getPlayingTrack().makeClone());
        for(AudioTrack track : trackClone) {
            scheduler.queue(track);
        }
    }

}
