package me.sylvaeon.umbreon.rpg.command.action;

import me.sylvaeon.umbreon.rpg.command.CommandRPG;
import me.sylvaeon.umbreon.rpg.crafting.Recipe;
import me.sylvaeon.umbreon.rpg.world.entity.player.Player;
import me.sylvaeon.umbreon.rpg.world.entity.player.Players;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.List;

public class CommandRPGCraft extends CommandRPG {
	@Override
	public void onCall(String[] args, User user, MessageChannel messageChannel) {
		Player player = Players.getPlayer(user.getUser());
		List<Recipe> recipeList = player.getAvailableRecipes();
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
			messageChannel.sendMessage(string).queue();
		} else if(args.length == 1) {
			try {
				int index = Integer.parseUnsignedInt(args[0]);
				if(index < 1 || index > recipeList.size()) {
					messageChannel.sendMessage("Not a valid number!").queue();
				} else {
					Recipe recipe = recipeList.get(index - 1);
					player.craft(recipe, messageChannel);
				}
			} catch (NumberFormatException e) {
				messageChannel.sendMessage("Not a valid number!");
			}
		}
	}
}
