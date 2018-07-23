package me.sylvaeon.umbreon.music.command;

import me.sylvaeon.umbreon.command.Command;

public abstract class CommandMusic extends Command {
	@Override
	public boolean requiresGuild() {
		return true;
	}
}
