package me.sylvaeon.umbreon.command;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;

public class CommandCat extends Command {
	@Override
	public void onCall(String[] args, Member member, MessageChannel textChannel) {
		textChannel.sendMessage("meow.").queue();
	}
}
