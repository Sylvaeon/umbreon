package me.sylvaeon.umbreon.rpg.command;

import me.sylvaeon.umbreon.rpg.entity.player.Player;
import me.sylvaeon.umbreon.rpg.entity.player.Players;
import me.sylvaeon.umbreon.rpg.item.ItemStack;
import me.sylvaeon.umbreon.rpg.item.tool.Tool;
import me.sylvaeon.umbreon.rpg.item.tool.ToolMaterial;
import me.sylvaeon.umbreon.rpg.item.tool.ToolSet;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.Set;

public class CommandRPGInventory extends CommandRPG {
	@Override
	public void onCall(String[] args, Member member, TextChannel textChannel) {
		Player player = Players.getPlayer(member);
		Set<ItemStack> inventory = player.getInventory();
		ToolSet tools = player.getToolSet();
		String str = "";
		if(inventory.isEmpty() && tools.isEmpty()) {
			str += "Your inventory is empty!";
		} else {
			if(!tools.isEmpty()) {
				str += "Tools:\n";
				for(Tool tool : tools.getTools()) {
					if(tool.getMaterial() > ToolMaterial.NONE) {
						str += tool.getName() + "\n";
					}
				}
			}
			if(!inventory.isEmpty()) {
				str += "\nItems:\n";
				for (ItemStack itemStack : inventory) {
					str += itemStack.formatted() + "\n";
				}
			}
		}
		textChannel.sendMessage(str).queue();
	}
}
