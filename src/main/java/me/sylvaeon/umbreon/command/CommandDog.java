package me.sylvaeon.umbreon.command;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandDog extends Command {
	@Override
	public void onCall(String[] args, User user, MessageChannel messageChannel) {
		messageChannel.sendMessage("dogs are shit and youre shit unironically kill yourself").queue();
	}
}
