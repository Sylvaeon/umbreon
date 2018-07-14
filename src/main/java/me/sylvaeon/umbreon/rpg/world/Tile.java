package me.sylvaeon.umbreon.rpg.world;

import me.sylvaeon.umbreon.rpg.world.entity.AnimalSpecies;
import me.sylvaeon.umbreon.rpg.world.entity.PlantSpecies;
import me.sylvaeon.umbreon.rpg.world.entity.TreeSpecies;

import java.awt.*;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

import static me.sylvaeon.umbreon.Utility.clamp;
import static me.sylvaeon.umbreon.Utility.inRange;

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
		        System.out.println("Tile#constructor - Animals");
	        } while (animals.contains(species));
            animals.add(species);
        }
        for (int i = 0; i < ThreadLocalRandom.current().nextInt(2, 5); i++) {
	        PlantSpecies species;
	        do {
		        species = World.getRandomPlantSpecies(this);
		        System.out.println("Tile#constructor - Plants");
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
		TUNDRA(0, 20, 0, 40), TAIGA(0, 20, 40, 100), DESERT(20, 100, 0, 10),
		GRASSLAND(20, 100, 10, 20), SHRUBLAND(20, 100, 20, 40), SAVANNA(20, 100, 40, 50),
		FOREST(20, 100, 50, 70), SWAMP(20, 100, 70, 80), TROPICS(20, 100, 80, 100),
		OCEAN(101, 101, 101, 101);

		int temperatureMin, temperatureMax, precipitationMin, precipitationMax;

		Biome(int temperatureMin, int temperatureMax, int precipitationMin, int precipitationMax) {
			this.temperatureMin = temperatureMin;
			this.temperatureMax = temperatureMax;
			this.precipitationMin = precipitationMin;
			this.precipitationMax = precipitationMax;
		}

		public static Biome getBiomeFromClimate(double temperature, double precipitation) {
			temperature = clamp(temperature, 0, 100);
			precipitation = clamp(precipitation, 0, 100);
			for (Biome biome : values()) {
				if (inRange(temperature, biome.temperatureMin, biome.temperatureMax) && inRange(precipitation, biome.precipitationMin, biome.precipitationMax)) {
					return biome;
				}
			}
			return null;
		}

		public Color getColor() {
			if(this == Biome.OCEAN) {
				return new Color(0x0000FF);
			} else if(this == Biome.TAIGA) {
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
