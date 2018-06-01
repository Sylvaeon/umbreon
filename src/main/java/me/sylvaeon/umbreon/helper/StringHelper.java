package me.sylvaeon.umbreon.helper;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import java.util.List;

public class StringHelper {
	public static String concatList(List<String> list) {
		StringBuilder stringBuilder = new StringBuilder();
		for(String s : list) {
			stringBuilder.append(s);
		}
		return stringBuilder.toString();
	}

	public static String concatArray(String[] list) {
		StringBuilder stringBuilder = new StringBuilder();
		for(String s : list) {
			stringBuilder.append(s);
		}
		return stringBuilder.toString();
	}

	public static String concatArray(String[] list, char c) {
		StringBuilder stringBuilder = new StringBuilder();
		for(String s : list) {
			stringBuilder.append(s).append(c);
		}
		return stringBuilder.reverse().deleteCharAt(0).reverse().toString();
	}

	public static String formatAudioTrack(AudioTrack track) {
		AudioTrackInfo info = track.getInfo();
		StringBuilder builder = new StringBuilder();
		builder.append("\"").append(info.title).append("\"").append(" by ").append(info.author).append("(").append(milisAsTimeStamp(info.length)).append(")");
		return builder.toString();
	}

	public static String milisAsTimeStamp(long milis) {
		int seconds = (int) (milis / 1000) % 60;
		int minutes = seconds / 60;
		return minutes + ":" + seconds;
	}
	
	public static String formatEnum(Enum e) {
		String name = e.name();
		String[] split = name.split("_");
		for(int i = 0; i < split.length; i++) {
			split[i] = split[i].toLowerCase();
			split[i] = split[i].substring(0, 1).toUpperCase() + split[i].substring(1);
		}
		return concatArray(split, ' ');
	}

	public static String formatEnumName(String name) {
		String[] split = name.split("_");
		for(int i = 0; i < split.length; i++) {
			split[i] = split[i].toLowerCase();
			split[i] = split[i].substring(0, 1).toUpperCase() + split[i].substring(1);
		}
		return concatArray(split, ' ');
	}

	public static String getProgressBar(int lvl, int xp, int xpNeeded) {
		return lvl + " " + getProgressBar((double) xp / xpNeeded) + " " + (lvl + 1);
	}

	/**
	 * @param progress
	 * Should be a double at least 0 and less than 1
	 * @return
	 */
	public static String getProgressBar(double progress) {
		progress *= 10;
		progress = (int) Math.round(progress);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("-[");
		for(int i = 0; i < progress; i++) {
			stringBuilder.append("■");
		}
		for(int i = 0; i < 10 - progress; i++) {
			stringBuilder.append("□");
		}
		stringBuilder.append("]-");
		return stringBuilder.toString();
	}

}
