package me.sylvaeon.umbreon.rpg.command;

import me.sylvaeon.umbreon.rpg.world.World;
import me.sylvaeon.umbreon.rpg.world.entity.player.Player;
import me.sylvaeon.umbreon.rpg.world.entity.player.Players;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class CommandRPGMove extends CommandRPG {
	@Override
	public void onCall(String[] args, Member member, TextChannel textChannel) {
		Player player = Players.getPlayer(member.getUser());
		if(args[0].equalsIgnoreCase("up")) {
			player.addY();
			textChannel.sendMessage("Moved up").queue();
		} else if(args[0].equalsIgnoreCase("down")) {
			player.subtractY();
			textChannel.sendMessage("Moved down").queue();
		} else if(args[0].equalsIgnoreCase("left")) {
			player.subtractX();
			textChannel.sendMessage("Moved left").queue();
		} else if(args[0].equalsIgnoreCase("right")) {
			player.addX();
			textChannel.sendMessage("Moved right").queue();
		} else {
			textChannel.sendMessage("Invalid direction! (up|down|left|right)").queue();
		}
		World.getTile(player.getXPos(), player.getYPos());
	}
}
