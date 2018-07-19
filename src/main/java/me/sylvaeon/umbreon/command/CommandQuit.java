package me.sylvaeon.umbreon.command;

import me.sylvaeon.umbreon.Umbreon;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandQuit extends Command {
	@Override
	public boolean requiresAdmin() {
		return true;
	}
	
	@Override
	public void onCall(String[] args, User user, MessageChannel messageChannel) {
		Umbreon.quit();
	}
}
