package me.sylvaeon.umbreon.command;

import me.sylvaeon.umbreon.music.command.*;
import me.sylvaeon.umbreon.rpg.command.CommandRPGInventory;
import me.sylvaeon.umbreon.rpg.command.CommandRPGLeaderboard;
import me.sylvaeon.umbreon.rpg.command.CommandRPGXp;
import me.sylvaeon.umbreon.helper.MapHelper;
import me.sylvaeon.umbreon.rpg.command.action.CommandRPGGive;
import me.sylvaeon.umbreon.rpg.command.action.gathering.*;
import me.sylvaeon.umbreon.rpg.command.action.crafting.CommandRPGBasicCraft;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class Commands {
	private static Map<String, Command> commands;
	
	public static void init() {
		commands = new HashMap<>();
		addCommand("help", new CommandHelp(), "Shows this list");
		addCommand("cat", new CommandCat(), "Meow.");
		addCommand("dog", new CommandDog(), "Bork");
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
		//addCommand("fight", new CommandRPGFight(), "Fight!!!!!");
		addCommand("pulse", new CommandTogglePulse(), "Toggles pulsing effect");
		addCommand("fish", new CommandRPGFish(), "Go fishing");
		addCommand("forage", new CommandRPGForage(), "Go foraging");
		addCommand("hunt", new CommandRPGHunt(), "Go hunting");
		addCommand("mine", new CommandRPGMine(), "Go mining");
		addCommand("log", new CommandRPGLog(), "Go logging");
		addCommand("inventory", new CommandRPGInventory(), "View your inventory", "inv");
		addCommand("basic-craft", new CommandRPGBasicCraft(), "Basic crafting", "craft");
		addCommand("give", new CommandRPGGive(), "Gives (cheats) an item");
	}

	static Collection<Command> getAllCommands() {
		return MapHelper.sortByValues(commands).values();
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
	
}
