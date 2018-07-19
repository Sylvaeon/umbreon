package me.sylvaeon.umbreon.command;

import me.sylvaeon.umbreon.music.command.CommandMusic;
import me.sylvaeon.umbreon.rpg.command.CommandRPG;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CommandHelp extends Command {
	@Override
	public void onCall(String[] args, User user, MessageChannel messageChannel) {
		List<Command> normalCommands = new ArrayList<>();
		List<Command> musicCommands = new ArrayList<>();
		List<Command> rpgCommands = new ArrayList<>();
		for(Command c : Commands.getCommands().values()) {
			boolean isAdmin = false;
			if(messageChannel instanceof TextChannel) {
				Guild guild = ((TextChannel) messageChannel).getGuild();
				Member member = guild.getMember(user);
				isAdmin = member.hasPermission(Permission.ADMINISTRATOR);
			}
			if(Commands.canExecute(user, c, isAdmin)) {
				if (c instanceof CommandMusic) {
					musicCommands.add(c);
				} else if (c instanceof CommandRPG) {
					rpgCommands.add(c);
				} else {
					normalCommands.add(c);
				}
			}
		}
		if(args.length == 0) {
			PrivateChannel privateChannel = user.openPrivateChannel().complete();
			if(!normalCommands.isEmpty()) {
				EmbedBuilder generalBuilder = new EmbedBuilder();
				generalBuilder.setTitle("General Commands:");
				generalBuilder.setColor(Color.MAGENTA);
				for(Command c : normalCommands) {
					addCommandAsField(generalBuilder, c);
				}
				privateChannel.sendMessage(generalBuilder.build()).queue();
			}
			if(!musicCommands.isEmpty()) {
				EmbedBuilder musicBuilder = new EmbedBuilder();
				musicBuilder.setTitle("Music Commands:");
				musicBuilder.setColor(Color.CYAN);
				for(Command c : musicCommands) {
					addCommandAsField(musicBuilder, c);
				}
				privateChannel.sendMessage(musicBuilder.build()).queue();

			}
			if(!rpgCommands.isEmpty()) {
				EmbedBuilder rpgBuilder = new EmbedBuilder();
				rpgBuilder.setTitle("RPG Commands:");
				rpgBuilder.setColor(Color.MAGENTA);
				for(Command c : rpgCommands) {
					addCommandAsField(rpgBuilder, c);
				}
				privateChannel.sendMessage(rpgBuilder.build()).queue();
			}
		}
	}

	private static void addCommandAsField(EmbedBuilder embedBuilder, Command command) {
		String desc;
		if(command.getDescription() == null) {
			desc = "No description has been set";
		} else {
			desc = command.getDescription();
		}
		String name = command.getName();
		for(String alias : command.getAliases()) {
			name += "/" + alias;
		}
		name += command.requiresAdmin() ? " - Admin Only" : "";
		embedBuilder.addField(name, desc, false);
	}
}
