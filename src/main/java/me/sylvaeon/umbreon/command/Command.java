package me.sylvaeon.umbreon.command;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public abstract class Command implements Comparable<Command> {
	
	private String name;
	private String description;
	private String[] aliases;
	
	public boolean requiresAdmin() {
		return false;
	}
	
	public abstract void onCall(String[] args, Member member, TextChannel textChannel);

	@Override
	public int compareTo(Command o) {
		if(this.requiresAdmin() == o.requiresAdmin()) {
			return name.compareTo(o.getName());
		} else if(this.requiresAdmin()) {
			return 1000000;
		} else {
			return -1000000;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String[] getAliases() {
		return aliases;
	}
	
	public void setAliases(String[] aliases) {
		this.aliases = aliases;
	}
}
