package me.sylvaeon.umbreon.rpg.command;

import me.sylvaeon.umbreon.Utility;
import me.sylvaeon.umbreon.rpg.entity.player.Player;
import me.sylvaeon.umbreon.rpg.entity.player.Players;
import me.sylvaeon.umbreon.rpg.item.Item;
import me.sylvaeon.umbreon.rpg.item.Items;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;

public class CommandRPGUnequipItem extends CommandRPG {
	@Override
	public void onCall(String[] args, Member member, MessageChannel textChannel) {
		try {
			Player player = Players.getPlayer(member);
			String arg = Utility.concatArray(args, ' ');
			Item item = Items.getItem(arg);
			player.getInventory().unequipItem(item);
		} catch (Exception e) {

		}
	}
}
