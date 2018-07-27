package me.sylvaeon.umbreon.command.rpg;

import me.sylvaeon.umbreon.rpg.world.World;
import me.sylvaeon.umbreon.rpg.world.entity.player.Player;
import me.sylvaeon.umbreon.rpg.world.entity.player.Players;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandRPGMove extends CommandRPG {
	@Override
	public void onCall(String[] args, User user, MessageChannel messageChannel) {
		Player player = Players.getPlayer(user);
		if(args[0].equalsIgnoreCase("up")) {
			player.addY();
			messageChannel.sendMessage("Moved up").queue();
		} else if(args[0].equalsIgnoreCase("down")) {
			player.subtractY();
			messageChannel.sendMessage("Moved down").queue();
		} else if(args[0].equalsIgnoreCase("left")) {
			player.subtractX();
			messageChannel.sendMessage("Moved left").queue();
		} else if(args[0].equalsIgnoreCase("right")) {
			player.addX();
			messageChannel.sendMessage("Moved right").queue();
		} else {
			messageChannel.sendMessage("Invalid direction! (up|down|left|right)").queue();
		}
		World.getTile(player.getXPos(), player.getYPos());
	}
}
