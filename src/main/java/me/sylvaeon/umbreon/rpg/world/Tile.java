package me.sylvaeon.umbreon.rpg.world;

import me.sylvaeon.umbreon.rpg.world.entity.AnimalSpecies;
import me.sylvaeon.umbreon.rpg.world.entity.PlantSpecies;
import me.sylvaeon.umbreon.rpg.world.entity.TreeSpecies;

import java.awt.*;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

public class Tile implements Serializable {
	private static final long serialVersionUID = 5L;

    private Feature feature;
	private Biome biome;
	private Set<AnimalSpecies> animals;
	private Set<PlantSpecies> plants;
	private TreeSpecies treeSpecies;

    public Tile(Biome biome) {
        this.feature = null;
	    if(ThreadLocalRandom.current().nextDouble() <= (1 / 3d) || true) {
		    feature = Feature.values()[ThreadLocalRandom.current().nextInt(Feature.values().length)];
	    }
        this.biome = biome;
        this.animals = new TreeSet<>();
        this.plants = new TreeSet<>();
        for (int i = 0; i < ThreadLocalRandom.current().nextInt(2, 5); i++) {
        	AnimalSpecies species;
        	do {
		        species = World.getRandomAnimalSpecies(this);
	        } while (animals.contains(species));
            animals.add(species);
        }
        for (int i = 0; i < ThreadLocalRandom.current().nextInt(2, 7); i++) {
	        PlantSpecies species;
	        do {
		        species = World.getRandomPlantSpecies(this);
	        } while (plants.contains(species));
	        plants.add(species);
        }
        this.treeSpecies = World.getRandomTreeSpecies(this);
        
    }

    public Tile(Feature feature, Biome biome, Set<AnimalSpecies> animals, Set<PlantSpecies> plants, TreeSpecies treeSpecies) {
        this.feature = feature;
        this.biome = biome;
        this.animals = animals;
        this.plants = plants;
        this.treeSpecies = treeSpecies;
    }

	public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public Biome getBiome() {
        return biome;
    }

    public void setBiome(Biome biome) {
        this.biome = biome;
    }

    public Set<AnimalSpecies> getAnimals() {
        return animals;
    }

    public void setAnimals(Set<AnimalSpecies> animals) {
        this.animals = animals;
    }

    public Set<PlantSpecies> getPlants() {
        return plants;
    }

    public void setPlants(Set<PlantSpecies> plants) {
        this.plants = plants;
    }

    public TreeSpecies getTreeSpecies() {
        return treeSpecies;
    }

    public void setTreeSpecies(TreeSpecies treeSpecies) {
        this.treeSpecies = treeSpecies;
    }

	public enum Biome {
		TUNDRA, TAIGA, DESERT, GRASSLAND, SHRUBLAND,
		SAVANNA, FOREST, SWAMP, TROPICS, MOUNTAIN;

		public Color getColor() {
			if(this == Biome.TAIGA) {
				return new Color(0x4a86e8);
			} else if(this == Biome.TROPICS) {
				return new Color(0x274e13);
			} else if(this == Biome.SWAMP) {
				return new Color(0x38761d);
			} else if(this == Biome.FOREST) {
				return new Color(0x00ff00);
			} else if(this == Biome.SAVANNA) {
				return new Color(0x783f04);
			} else if(this == Biome.TUNDRA) {
				return new Color(0x00ffff);
			} else if(this == Biome.SHRUBLAND) {
				return new Color(0x7f6000);
			} else if(this == Biome.GRASSLAND) {
				return new Color(0xff9900);
			} else if(this == Biome.DESERT) {
				return new Color(0xffff00);
			} else {
				return new Color(0x777777);
			}
		}

	}

	public enum Feature {
		MOUNTAIN, RAVINE, RIVER, RUINS
	}
}
