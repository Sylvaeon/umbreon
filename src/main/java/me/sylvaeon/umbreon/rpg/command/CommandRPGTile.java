package me.sylvaeon.umbreon.rpg.command;

import me.sylvaeon.umbreon.rpg.world.Tile;
import me.sylvaeon.umbreon.rpg.world.World;
import me.sylvaeon.umbreon.rpg.world.entity.AnimalSpecies;
import me.sylvaeon.umbreon.rpg.world.entity.PlantSpecies;
import me.sylvaeon.umbreon.rpg.world.entity.TreeSpecies;
import me.sylvaeon.umbreon.rpg.world.entity.player.Player;
import me.sylvaeon.umbreon.rpg.world.entity.player.Players;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.Set;

import static me.sylvaeon.umbreon.util.Utility.formatEnum;

public class CommandRPGTile extends CommandRPG {
	@Override
	public void onCall(String[] args, User user, MessageChannel messageChannel) {
		Player player = Players.getPlayer(user.getUser());
		int x = player.getXPos();
		int y = player.getYPos();
		final Tile tile = World.getTile(player.getXPos(), player.getYPos());
		final Tile.Biome biome = tile.getBiome();
		final Tile.Feature feature = tile.getFeature();
		final Set<AnimalSpecies> animals = tile.getAnimals();
		final Set<PlantSpecies> plants = tile.getPlants();
		final TreeSpecies tree = tile.getTreeSpecies();
		EmbedBuilder builder = new EmbedBuilder();
		builder.setAuthor(user.getUser().getName() + " - x:" + x + ", y:" + y);
		builder.setColor(biome.getColor());
		if(feature != null) {
			builder.setTitle(formatEnum(feature));
		}
		String animalString = "";
		for(AnimalSpecies species : animals) {
			animalString += species.getName() + ", ";
		}
		animalString = animalString.split(", $")[0];
		String plantString = "";
		for(PlantSpecies species : plants) {
			plantString += species.getName() + ", ";
		}
		plantString = plantString.split(", $")[0];
		builder.addField("Animals", animalString, false);
		builder.addField("Plants", plantString, false);
		builder.addField("Tree", tree.getName(), false);
		builder.setFooter(formatEnum(biome), null);
		messageChannel.sendMessage(builder.build()).queue();
	}
}
