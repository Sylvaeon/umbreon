package me.sylvaeon.umbreon.command;

import me.sylvaeon.umbreon.music.command.*;
import me.sylvaeon.umbreon.rpg.command.CommandRPGInventory;
import me.sylvaeon.umbreon.rpg.command.CommandRPGLeaderboard;
import me.sylvaeon.umbreon.rpg.command.CommandRPGXp;
import me.sylvaeon.umbreon.rpg.command.action.CommandRPGCraft;
import me.sylvaeon.umbreon.rpg.command.action.CommandRPGGive;
import me.sylvaeon.umbreon.rpg.command.action.gathering.CommandRPGLog;
import me.sylvaeon.umbreon.rpg.command.action.gathering.CommandRPGMine;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;

import java.util.Map;
import java.util.TreeMap;

public final class Commands {
	private static Map<String, Command> commands;
	
	public static void init() {
		commands = new TreeMap<>();
		addCommand("help", new CommandHelp(), "Shows this list");
		addCommand("cat", new CommandCat(), "Meow.");
		addCommand("dog", new CommandDog(), "Bork.");
		addCommand("quit", new CommandQuit(), "Quits the bot");
		addCommand("xp", new CommandRPGXp(), "Gets your current xp/level", "lvl");
		addCommand("color", new CommandColor(), "Outputs a color/gradient");
		addCommand("leaderboard", new CommandRPGLeaderboard(), "Views the server-wide level leaderboard", "lb");
		addCommand("rgbtohex", new CommandColorConvert(), "Converts an RGB to HEX");
		addCommand("moveall", new CommandMoveAll(), "Moves everyone from one voice channel to another");
		addCommand("play", new CommandMusicPlay(), "Queues a song");
		addCommand("skip", new CommandMusicSkip(), "Skips the current song");
		addCommand("clear", new CommandMusicClearQueue(), "Clears the queue");
		addCommand("stop", new CommandMusicStopQueue(), "Clears the queue and stops current song");
		addCommand("shake", new CommandShake(), "Shakes");
		addCommand("join", new CommandMusicJoin(), "Joins the voice channel you're in");
		addCommand("leave", new CommandMusicLeave(), "Leaves the current voice channel");
		addCommand("now-playing", new CommandMusicNowPlaying(), "Shows the currently playing song", "np");
		addCommand("pulse", new CommandTogglePulse(), "Toggles pulsing effect");
		addCommand("mine", new CommandRPGMine(), "Go mining");
		addCommand("log", new CommandRPGLog(), "Go logging");
		addCommand("inventory", new CommandRPGInventory(), "View your inventory", "inv");
		addCommand("craft", new CommandRPGCraft(), "Basic crafting", "crafting");
		addCommand("give", new CommandRPGGive(), "Gives (cheats) an item");
		addCommand("private-room", new CommandCreatePrivateRoom(), "Creates a private room");
		addCommand("move-all-openmic", new CommandMoveAllOpenMic(), "Moves everyone with their mic on", "maom", "move-open-mic");
	}

	public static Command getCommand(String name) {
		for(Command command : commands.values()) {
			if(command.getName().equalsIgnoreCase(name)) {
				return command;
			} else {
				for(String alias : command.getAliases()) {
					if(alias.equalsIgnoreCase(name)) {
						return command;
					}
				}
			}
		}
		return null;
	}

	private static void addCommand(String name, Command command, String description, String... aliases) {
		command.setName(name);
		command.setDescription(description);
		command.setAliases(aliases);
		commands.put(name, command);
	}

	public static boolean canExecute(Member member, Command command) {
		if(member.hasPermission(Permission.ADMINISTRATOR) || !command.requiresAdmin()) {
			return true;
		}
		return false;
	}

	public static Map<String, Command> getCommands() {
		return commands;
	}
}
