package me.sylvaeon.umbreon.rpg.command.action.crafting;

import me.sylvaeon.umbreon.rpg.command.CommandRPG;
import me.sylvaeon.umbreon.rpg.crafting.Recipe;
import me.sylvaeon.umbreon.rpg.entity.player.Player;
import me.sylvaeon.umbreon.rpg.entity.player.Players;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.List;

public class CommandRPGCarpenter extends CommandRPG {
	@Override
	public void onCall(String[] args, Member member, TextChannel textChannel) {
		Player player = Players.getPlayer(member);
		List<Recipe> recipeList = player.getAvailableCarpentryRecipes();
		if(args.length == 0) {
			String string;
			if(recipeList.isEmpty()) {
				string = "No available recipes";
			} else {
				string = "Available recipes:\n";
				for(int i = 0; i < recipeList.size(); i++) {
					Recipe recipe = recipeList.get(i);
					string += (i + 1) + ". " + recipe.toString() + "\n";
				}
			}
			textChannel.sendMessage(string).queue();
		} else if(args.length == 1) {
			try {
				int index = Integer.parseUnsignedInt(args[0]);
				if(index < 1 || index > recipeList.size()) {
					textChannel.sendMessage("Not a valid number!").queue();
				} else {
					Recipe recipe = recipeList.get(index - 1);
					player.carpenter(recipe, textChannel);
				}
			} catch (NumberFormatException e) {
				textChannel.sendMessage("Not a valid number!");
			}
		}
	}
}
