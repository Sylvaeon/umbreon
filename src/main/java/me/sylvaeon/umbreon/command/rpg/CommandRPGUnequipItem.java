package me.sylvaeon.umbreon.command.rpg;

import me.sylvaeon.umbreon.rpg.item.Item;
import me.sylvaeon.umbreon.rpg.item.Items;
import me.sylvaeon.umbreon.rpg.item.equipable.tool.Tool;
import me.sylvaeon.umbreon.rpg.world.entity.player.Player;
import me.sylvaeon.umbreon.rpg.world.entity.player.Players;
import me.sylvaeon.umbreon.util.Utility;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandRPGUnequipItem extends CommandRPG {
	@Override
	public void onCall(String[] args, User user, MessageChannel messageChannel) {
		try {
			Player player = Players.getPlayer(user);
			String arg = Utility.concatArray(args, ' ');
			String[] quoteSeperated = arg.split("\"");
			String itemName = quoteSeperated[1];
			Item item = Items.getItem(itemName);
			if(item instanceof Tool) {
				Tool tool = (Tool) item;
				if (player.getInventory().unequipTool(tool)) {

				} else {

				}
			}
		} catch (Exception e) {

		}
	}
}
