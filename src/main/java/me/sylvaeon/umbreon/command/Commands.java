package me.sylvaeon.umbreon.command;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import me.sylvaeon.umbreon.command.music.*;
import me.sylvaeon.umbreon.command.rpg.*;

public final class Commands {
	private static Table<Character, String, Command> commands;
	
	public static void init() {
		commands = HashBasedTable.create();
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

	public static Command getCommand(Character prefix, String name) {
		name = name.toLowerCase();
		return commands.get(prefix, name);
	}

	private static void addCommand(String name, Command command, String description, String... aliases) {
		command.setName(name);
		command.setDescription(description);
		command.setAliases(aliases);
		commands.put(command.getPrefix(), name, command);
		for(String alias : aliases) {
			commands.put(command.getPrefix(), alias, command);
		}
	}

}
