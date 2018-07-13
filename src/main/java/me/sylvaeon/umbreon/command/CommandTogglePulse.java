package me.sylvaeon.umbreon.command;

import me.sylvaeon.umbreon.Umbreon;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class CommandTogglePulse extends Command {
	@Override
	public void onCall(String[] args, Member member, TextChannel textChannel) {
		Umbreon.flipPulse();
	}

	@Override
	public boolean requiresAdmin() {
		return true;
	}
}
