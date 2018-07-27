package me.sylvaeon.umbreon.command.rpg;

import me.sylvaeon.umbreon.command.Command;

public abstract class CommandRPG extends Command {
	@Override
	public Character getPrefix() {
		return 'r';
	}
}
