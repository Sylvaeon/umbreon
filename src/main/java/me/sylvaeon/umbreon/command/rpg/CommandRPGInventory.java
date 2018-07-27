package me.sylvaeon.umbreon.command.rpg;

import me.sylvaeon.umbreon.Counter;
import me.sylvaeon.umbreon.rpg.item.Item;
import me.sylvaeon.umbreon.rpg.item.equipable.tool.Tool;
import me.sylvaeon.umbreon.rpg.world.entity.player.Player;
import me.sylvaeon.umbreon.rpg.world.entity.player.Players;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.Map;

public class CommandRPGInventory extends CommandRPG {
	@Override
	public void onCall(String[] args, User user, MessageChannel messageChannel) {
		Player player = Players.getPlayer(user);
		String str = "";
		player.getInventory().removeEmptys();
		if(!player.getInventory().isEmpty()) {
			str += "\n" + user.getAsMention() + "'s Inventory (" + player.getInventory().total() + "):\n";
			for (Map.Entry<Item, Counter> entry : player.getInventory().entrySet()) {
				Item item = entry.getKey();
				long l = entry.getValue().count;
				str += l + "x " + item.name;
				if(item instanceof Tool) {
					Tool tool = (Tool) item;
					if(player.getInventory().isEquipped(tool)) {
						str += " (Equipped)";
					}
				}
				str += "\n";
			}
		} else {
			str = user.getAsMention() + "'s ItemSet is empty!";
		}
		messageChannel.sendMessage(str).queue();
	}
}
