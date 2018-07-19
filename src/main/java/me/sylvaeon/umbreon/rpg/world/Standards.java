package me.sylvaeon.umbreon.rpg.world;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Standards implements Serializable {
	private transient Tile.Feature feature;
	private transient List<Tile.Biome> biomes;

    public Standards(Tile.Biome... biomes) {
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
			return;
        } else {
            removeBiome(Tile.Biome.valueOf(name.toUpperCase()));
        }
    }

    public void removeBiome(Tile.Biome biome) {
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
			addBiome(Tile.Biome.valueOf(name.toUpperCase()));
		}
    }

    public void addBiome(Tile.Biome biome) {
    	if(biome != null) {
    		biomes.add(biome);
		}
	}

    public static Standards ALL() {
        Standards standards = new Standards(Tile.Biome.values());
        return standards;
    }

    public List<Tile.Biome> getBiomes() {
        return biomes;
    }
    
    public Tile.Feature getFeature() {
        return feature;
    }

    public void setFeature(Tile.Feature feature) {
        this.feature = feature;
    }

    @Override
    public String toString() {
        String ret = "";
        if (feature != null) ret += "feature:" + feature.name() + ";";
        ret += "biomes:[";
        for (Tile.Biome biome : biomes) {
            ret += biome.name() + ",";
        }
        ret = ret.substring(0, ret.length() - 1);
        ret += "]";
        return ret;
    }
}
