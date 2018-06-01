package me.sylvaeon.umbreon.rpg.command.action;

import me.sylvaeon.umbreon.helper.StringHelper;
import me.sylvaeon.umbreon.rpg.command.CommandRPG;
import me.sylvaeon.umbreon.rpg.entity.player.Player;
import me.sylvaeon.umbreon.rpg.entity.player.Players;
import me.sylvaeon.umbreon.rpg.item.Item;
import me.sylvaeon.umbreon.rpg.item.Items;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class CommandRPGEquipItem extends CommandRPG {
	@Override
	public void onCall(String[] args, Member member, TextChannel textChannel) {
		try {
			Player player = Players.getPlayer(member);
			String arg = StringHelper.concatArray(args, ' ');
			String[] quoteSeperated = arg.split("\"");
			String itemName = quoteSeperated[1];
			Item item = Items.getItem(itemName);
			player.getInventory().equipItem(item);
		} catch (Exception e) {
		
		}
	}
}
