package me.sylvaeon.umbreon.command;

import me.sylvaeon.umbreon.music.command.*;
import me.sylvaeon.umbreon.rpg.command.*;
import me.sylvaeon.umbreon.rpg.command.action.CommandRPGCraft;
import me.sylvaeon.umbreon.rpg.command.action.CommandRPGGive;
import me.sylvaeon.umbreon.rpg.command.action.gathering.CommandRPGLog;
import me.sylvaeon.umbreon.rpg.command.action.gathering.CommandRPGMine;
import net.dv8tion.jda.core.entities.User;

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
		addCommand("clear", new CommandMusicClear(), "Clears the queue");
		addCommand("stop", new CommandMusicStop(), "Clears the queue and stops current song");
		addCommand("join", new CommandMusicJoin(), "Joins the voice channel you're in");
		addCommand("leave", new CommandMusicLeave(), "Leaves the current voice channel");
		addCommand("mine", new CommandRPGMine(), "Go mining");
		addCommand("log", new CommandRPGLog(), "Go logging");
		addCommand("inventory", new CommandRPGInventory(), "View your inventory", "inv");
		addCommand("craft", new CommandRPGCraft(), "Basic crafting", "crafting");
		addCommand("give", new CommandRPGGive(), "Gives (cheats) an item");
		addCommand("load-players", new CommandRPGLoadPlayers(), "Loads players");
		addCommand("save-players", new CommandRPGSavePlayers(), "Saves players");
		addCommand("equip-item", new CommandRPGEquipItem(), "Equips an unquipped item", "equip");
		addCommand("unequip-item", new CommandRPGUnequipItem(), "Unequipps an equipped item", "unequip");
		addCommand("tile", new CommandRPGTile(), "View information of the current tile");
		addCommand("move", new CommandRPGMove(), "Move");
		addCommand("create", new CommandMusicCreate(), "Creates music channel");
	}

	public static Command getCommand(String name) {
		if(commands == null) {
			System.out.println("o w o");
		}
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

	public static boolean canExecute(User user, Command command, boolean isAdmin, boolean inGuild) {
		boolean adminCheck = isAdmin || !command.requiresAdmin();
		boolean guildCheck = inGuild || !command.requiresGuild();
		
		return adminCheck && guildCheck;
	}

	public static Map<String, Command> getCommands() {
		return commands;
	}
}
