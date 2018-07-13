package me.sylvaeon.umbreon.rpg.command;

import me.sylvaeon.umbreon.Utility;
import me.sylvaeon.umbreon.rpg.item.Item;
import me.sylvaeon.umbreon.rpg.item.Items;
import me.sylvaeon.umbreon.rpg.item.equipable.tool.Tool;
import me.sylvaeon.umbreon.rpg.world.entity.player.Player;
import me.sylvaeon.umbreon.rpg.world.entity.player.Players;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class CommandRPGEquipItem extends CommandRPG {
	@Override
	public void onCall(String[] args, Member member, TextChannel textChannel) {
		try {
			Player player = Players.getPlayer(member.getUser());
			String arg = Utility.concatArray(args, ' ');
			String[] quoteSeperated = arg.split("\"");
			String itemName = quoteSeperated[1];
			Item item = Items.getItem(itemName);
			if(item instanceof Tool) {
				Tool tool = (Tool) item;
				if (player.getInventory().equipTool(tool)) {

				} else {

				}
			}
		} catch (Exception e) {
		
		}
	}
}
