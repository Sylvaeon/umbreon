package me.sylvaeon.umbreon.rpg.command;

import me.sylvaeon.umbreon.Utility;
import me.sylvaeon.umbreon.rpg.entity.player.Player;
import me.sylvaeon.umbreon.rpg.entity.player.Players;
import me.sylvaeon.umbreon.rpg.item.Item;
import me.sylvaeon.umbreon.rpg.item.Items;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;

public class CommandRPGEquipItem extends CommandRPG {
	@Override
	public void onCall(String[] args, Member member, MessageChannel textChannel) {
		try {
			Player player = Players.getPlayer(member);
			String arg = Utility.concatArray(args, ' ');
			String[] quoteSeperated = arg.split("\"");
			String itemName = quoteSeperated[1];
			Item item = Items.getItem(itemName);
			if(player.getInventory().equipItem(item)) {

			} else {

			}
		} catch (Exception e) {
		
		}
	}
}
