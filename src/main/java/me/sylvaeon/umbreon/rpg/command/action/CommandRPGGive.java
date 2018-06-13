package me.sylvaeon.umbreon.rpg.command.action;

import me.sylvaeon.umbreon.Utility;
import me.sylvaeon.umbreon.rpg.command.CommandRPG;
import me.sylvaeon.umbreon.rpg.entity.player.Player;
import me.sylvaeon.umbreon.rpg.entity.player.Players;
import me.sylvaeon.umbreon.rpg.item.Item;
import me.sylvaeon.umbreon.rpg.item.Items;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;

public class CommandRPGGive extends CommandRPG {
	@Override
	public void onCall(String[] args, Member member, MessageChannel textChannel) {
		try {
			String arg = Utility.concatArray(args, ' ');
			String[] quoteSeperated = arg.split("\"");
			String itemName = quoteSeperated[1];
			String amountName = quoteSeperated[2].replaceAll(" ", "");
			Item item = Items.getItem(itemName);
			int amount = Integer.parseUnsignedInt(amountName);
			Player player = Players.getPlayer(member);
			player.getInventory().addItem(item, amount);
		} catch (Exception e) {
		}
	}

	@Override
	public boolean requiresAdmin() {
		return true;
	}
}
