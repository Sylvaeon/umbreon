package me.sylvaeon.umbreon.rpg.command;

import me.sylvaeon.umbreon.rpg.entity.player.Players;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class CommandRPGLoadPlayers extends CommandRPG {
	@Override
	public void onCall(String[] args, Member member, TextChannel textChannel) {
		Players.loadPlayers();
	}
}
