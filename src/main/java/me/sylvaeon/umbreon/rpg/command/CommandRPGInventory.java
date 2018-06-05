package me.sylvaeon.umbreon.rpg.command;

import me.sylvaeon.umbreon.rpg.entity.player.Player;
import me.sylvaeon.umbreon.rpg.entity.player.Players;
import me.sylvaeon.umbreon.rpg.item.ItemStack;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.Collection;

public class CommandRPGInventory extends CommandRPG {
	@Override
	public void onCall(String[] args, Member member, TextChannel textChannel) {
		Player player = Players.getPlayer(member);
		Collection<ItemStack> inventory = player.getInventory().getItemStacks();
		String str = "";
		if(!inventory.isEmpty()) {
			str += "\n" + member.getAsMention() + "'s Inventory (" + player.getInventory().getAmount() + "):\n";
			for (ItemStack itemStack : inventory) {
				str += itemStack.formatted();
				if(player.getInventory().isEquipped(itemStack.getItem())) {
					str += " (Equipped)";
				}
				str += "\n";
			}
		} else {
			str = member.getAsMention() + "'s Inventory is empty!";
		}
		textChannel.sendMessage(str).queue();
	}
}
