package me.sylvaeon.umbreon.command;

import me.sylvaeon.umbreon.music.command.CommandMusic;
import me.sylvaeon.umbreon.rpg.command.CommandRPG;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CommandHelp extends Command {
	@Override
	public void onCall(String[] args, Member member, TextChannel textChannel) {
		User user = member.getUser();
		List<Command> normalCommands = new ArrayList<>();
		List<Command> musicCommands = new ArrayList<>();
		List<Command> rpgCommands = new ArrayList<>();
		for(Command c : Commands.getAllCommands()) {
			if(c instanceof CommandMusic) {
				musicCommands.add(c);
			} else if(c instanceof CommandRPG) {
				rpgCommands.add(c);
			} else {
				normalCommands.add(c);
			}
		}
		if(args.length == 0) {
			EmbedBuilder generalBuilder = new EmbedBuilder();
			EmbedBuilder musicBuilder = new EmbedBuilder();
			EmbedBuilder rpgBuilder = new EmbedBuilder();
			
			generalBuilder.setTitle("General Commands:");
			generalBuilder.setColor(Color.MAGENTA);
			for(Command c : normalCommands) {
				addCommandAsField(generalBuilder, c);
			}

			musicBuilder.setTitle("Music Commands:");
			musicBuilder.setColor(Color.CYAN);
			for(Command c : musicCommands) {
				addCommandAsField(musicBuilder, c);
			}

			rpgBuilder.setTitle("RPG Commands:");
			rpgBuilder.setColor(Color.MAGENTA);
			for(Command c : rpgCommands) {
				addCommandAsField(rpgBuilder, c);
			}
			
			EmbedBuilder finalGeneralBuilder = generalBuilder;
			user.openPrivateChannel().queue(privateChannel -> {
				privateChannel.sendMessage(finalGeneralBuilder.build()).queue();
				privateChannel.sendMessage(musicBuilder.build()).queue();
				privateChannel.sendMessage(rpgBuilder.build()).queue();
			});
			
			
		} else if(args.length == 1) {
			EmbedBuilder embedBuilder = new EmbedBuilder();
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
