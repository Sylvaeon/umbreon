package me.sylvaeon.umbreon.rpg.command.action.gathering;

import me.sylvaeon.umbreon.rpg.command.CommandRPG;
import me.sylvaeon.umbreon.rpg.world.entity.player.Player;
import me.sylvaeon.umbreon.rpg.world.entity.player.Players;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class CommandRPGMine extends CommandRPG {
	@Override
	public void onCall(String[] args, Member member, TextChannel textChannel) {
		Player player = Players.getPlayer(member.getUser());
		player.mine(textChannel);
	}
}
