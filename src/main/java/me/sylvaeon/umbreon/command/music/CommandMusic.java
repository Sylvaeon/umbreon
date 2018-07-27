package me.sylvaeon.umbreon.command.music;

import me.sylvaeon.umbreon.command.Command;

public abstract class CommandMusic extends Command {
	@Override
	public boolean requiresGuild() {
		return true;
	}
	
	@Override
	public Character getPrefix() {
		return 'm';
	}
}
