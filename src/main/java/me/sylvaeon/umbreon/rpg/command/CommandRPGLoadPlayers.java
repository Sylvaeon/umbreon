package me.sylvaeon.umbreon.rpg.command;

import me.sylvaeon.umbreon.rpg.world.entity.player.Players;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandRPGLoadPlayers extends CommandRPG {
	@Override
	public void onCall(String[] args, User user, MessageChannel messageChannel) {
		Players.loadPlayers();
	}
}
