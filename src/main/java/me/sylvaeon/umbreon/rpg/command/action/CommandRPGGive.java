package me.sylvaeon.umbreon.rpg.command.action;

import me.sylvaeon.umbreon.rpg.command.CommandRPG;
import me.sylvaeon.umbreon.rpg.item.Item;
import me.sylvaeon.umbreon.rpg.item.Items;
import me.sylvaeon.umbreon.rpg.world.entity.player.Player;
import me.sylvaeon.umbreon.rpg.world.entity.player.Players;
import me.sylvaeon.umbreon.util.Utility;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandRPGGive extends CommandRPG {
	@Override
	public void onCall(String[] args, User user, MessageChannel messageChannel) {
		try {
			String arg = Utility.concatArray(args, ' ');
			String[] quoteSeperated = arg.split("\"");
			String itemName = quoteSeperated[1];
			String amountName = quoteSeperated[2].replaceAll(" ", "");
			Item item = Items.getItem(itemName);
			int amount = Integer.parseUnsignedInt(amountName);
			Player player = Players.getPlayer(user);
			player.getInventory().add(item, amount);
		} catch (Exception e) {
		}
	}

	@Override
	public boolean requiresAdmin() {
		return true;
	}
}
