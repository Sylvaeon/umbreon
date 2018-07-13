package me.sylvaeon.umbreon.command;

import me.sylvaeon.umbreon.Umbreon;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class CommandCreatePrivateRoom extends Command {
	@Override
	public void onCall(String[] args, Member member, TextChannel textChannel) {
		if(!Umbreon.addPrivateChannel(member.getUser())) {
			textChannel.sendMessage("Your private room already exists!").queue();
		}
	}
}
