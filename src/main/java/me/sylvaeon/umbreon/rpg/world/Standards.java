package me.sylvaeon.umbreon.rpg.world;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Standards {
    World.Feature feature;
    List<World.Biome> biomes;

    public Standards(World.Biome... biomes) {
        this.feature = null;
        this.biomes = new LinkedList<>(Arrays.asList(biomes));
    }

    public static Standards NONE() {
        return new Standards();
    }

    public boolean meetsStandards(Tile tile) {
        return (feature == null || feature == tile.getFeature()) && biomes.contains(tile.getBiome());
    }

    public void removeBiome(String name) {
        if (name == "" || name == null) {

        } else {
            removeBiome(World.Biome.valueOf(name.toUpperCase()));
        }
    }

    public void removeBiome(World.Biome biome) {
        if (biome != null) {
            biomes.remove(biome);
        }
    }

    public void addBiome(String name) {
        if (name == "" || name == null) {
			return;
		} else if (name.equalsIgnoreCase("All")) {
			biomes.addAll(ALL().biomes);
		} else {
			addBiome(World.Biome.valueOf(name.toUpperCase()));
		}
    }

    public void addBiome(World.Biome biome) {
    	if(biome != null) {
    		biomes.add(biome);
		}
	}

    public static Standards ALL() {
        Standards standards = new Standards(World.Biome.values());
        standards.removeBiome(World.Biome.OCEAN);
        return standards;
    }

    public List<World.Biome> getBiomes() {
        return biomes;
    }

    public void setBiomes(List<World.Biome> biomes) {
        this.biomes = biomes;
    }

    public World.Feature getFeature() {
        return feature;
    }

    public void setFeature(World.Feature feature) {
        this.feature = feature;
    }

    @Override
    public String toString() {
        String ret = "";
        if (feature != null) ret += "feature:" + feature.name() + ";";
        ret += "biomes:[";
        for (World.Biome biome : biomes) {
            ret += biome.name() + ",";
        }
        ret = ret.substring(0, ret.length() - 1);
        ret += "]";
        return ret;
    }
}
