package me.sylvaeon.umbreon.command;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandCat extends Command {
	@Override
	public void onCall(String[] args, User user, MessageChannel messageChannel) {
		messageChannel.sendMessage("meow.").queue();
	}
}
