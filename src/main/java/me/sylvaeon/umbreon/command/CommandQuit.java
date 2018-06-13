package me.sylvaeon.umbreon.command;

import me.sylvaeon.umbreon.Umbreon;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;

public class CommandQuit extends Command {
	@Override
	public boolean requiresAdmin() {
		return true;
	}
	
	@Override
	public void onCall(String[] args, Member member, MessageChannel textChannel) {
		Umbreon.quit();
	}
}
