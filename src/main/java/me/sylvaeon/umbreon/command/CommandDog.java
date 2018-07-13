package me.sylvaeon.umbreon.command;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class CommandDog extends Command {
	@Override
	public void onCall(String[] args, Member member, TextChannel textChannel) {
		textChannel.sendMessage("dogs are shit and youre shit unironically kill yourself").queue();
	}
}
