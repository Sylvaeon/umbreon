package me.sylvaeon.umbreon.util;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.sylvaeon.umbreon.Umbreon;
import me.sylvaeon.umbreon.music.GuildMusicManager;
import net.dv8tion.jda.core.entities.*;

public class DiscordVoiceUtil {
	
	public static void loadAndPlay(Member member, final MessageChannel channel, final String searchTerm) {
		GuildMusicManager musicManager = Umbreon.getGuildAudioPlayer(member.getGuild());

		Umbreon.getPlayerManager().loadItemOrdered(musicManager, searchTerm, new AudioLoadResultHandler() {
			@Override
			public void trackLoaded(AudioTrack track) {

				play(musicManager, track);
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {

				for(AudioTrack track : playlist.getTracks()) {
					play(musicManager, track);
				}
			}

			@Override
			public void noMatches() {
			}

			@Override
			public void loadFailed(FriendlyException exception) {
			}
		});
	}
	
	public static void play(GuildMusicManager musicManager, AudioTrack track) {
		musicManager.scheduler.queue(track);
	}
	
	public static void skipTrack(TextChannel channel) {
		GuildMusicManager musicManager = Umbreon.getGuildAudioPlayer(channel.getGuild());
		musicManager.scheduler.nextTrack();
	}
	
	public static void skipAllTracks(TextChannel channel) {
		GuildMusicManager musicManager = Umbreon.getGuildAudioPlayer(channel.getGuild());
		musicManager.scheduler.clearTrackQueue();
	}
	
	public static void joinVoiceChannel(Guild guild, VoiceChannel voiceChannel) {
		Member umbreon = guild.getMember(Umbreon.UMBREON);
		if(!umbreon.getVoiceState().inVoiceChannel()) {
			try {
				guild.getAudioManager().openAudioConnection(voiceChannel);
			} catch (Exception e) {

			}
		}
	}
	
	public static void leaveVoiceChannel(Guild guild) {
		if(guild.getMember(Umbreon.UMBREON).getVoiceState().inVoiceChannel()) {
			guild.getAudioManager().closeAudioConnection();
		}
	}
	
	public static AudioTrack[] getAudioTrackArray(Guild guild) {
		GuildMusicManager manager = Umbreon.getGuildAudioPlayer(guild);
		boolean hasNowPlaying = manager.player.getPlayingTrack() != null;
		AudioTrack[] audioTracks = new AudioTrack[manager.scheduler.getQueue().size() + (hasNowPlaying ? 1 : 0)];
		AudioTrack[] queueArray = manager.scheduler.getQueueArray();
		if(hasNowPlaying) {
			audioTracks[0] = manager.player.getPlayingTrack().makeClone();
		}
		for(int i = 0; i < queueArray.length; i++) {
			audioTracks[i+1] = queueArray[i];
		}
		return audioTracks;
	}
	
	public static AudioTrack getCurrentTrack(Guild guild) {
		return Umbreon.getGuildAudioPlayer(guild).player.getPlayingTrack();
	}
	
	public static Guild getCurrentGuild(User user) {
		Guild guild = null;
		Member member;
		for(Guild g : Umbreon.getJda().getGuilds()) {
			member = g.getMember(user);
			if(member == null) {
				continue;
			} else {
				GuildVoiceState voiceState = member.getVoiceState();
				if(voiceState.inVoiceChannel()) {
					guild = g;
				} else {
					continue;
				}
			}
		}
		return guild;
	}
	
}
