package me.sylvaeon.umbreon.rpg.world;

import me.sylvaeon.umbreon.rpg.world.entity.AnimalSpecies;
import me.sylvaeon.umbreon.rpg.world.entity.PlantSpecies;
import me.sylvaeon.umbreon.rpg.world.entity.TreeSpecies;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static me.sylvaeon.umbreon.Utility.clamp;
import static me.sylvaeon.umbreon.Utility.inRange;

public class Tile {
    public Feature feature;
	public Biome biome;
	public Set<AnimalSpecies> animals;
	public Set<PlantSpecies> plants;
	public TreeSpecies treeSpecies;

    public Tile(Biome biome) {
        this.feature = null;
        this.biome = biome;
        this.animals = new HashSet<>();
        this.plants = new HashSet<>();
        for (int i = 0; i < ThreadLocalRandom.current().nextInt(2, 5); i++) {
            animals.add(World.getRandomAnimalSpecies(this));
        }
        for (int i = 0; i < ThreadLocalRandom.current().nextInt(2, 5); i++) {
            plants.add(World.getRandomPlantSpecies(this));
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

	}

	public enum Feature {
		MOUNTAIN, RAVINE, RIVER, RUINS
	}
}
