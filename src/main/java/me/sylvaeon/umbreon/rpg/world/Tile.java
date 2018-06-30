package me.sylvaeon.umbreon.rpg.world;

import me.sylvaeon.umbreon.rpg.world.entity.AnimalSpecies;
import me.sylvaeon.umbreon.rpg.world.entity.PlantSpecies;
import me.sylvaeon.umbreon.rpg.world.entity.TreeSpecies;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Tile {
    private World.Feature feature;
    private World.Biome biome;
    private Set<AnimalSpecies> animals;
    private Set<PlantSpecies> plants;
    private TreeSpecies treeSpecies;

    public Tile(World.Biome biome) {
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

    public Tile(World.Feature feature, World.Biome biome, Set<AnimalSpecies> animals, Set<PlantSpecies> plants, TreeSpecies treeSpecies) {
        this.feature = feature;
        this.biome = biome;
        this.animals = animals;
        this.plants = plants;
        this.treeSpecies = treeSpecies;
    }

    public World.Feature getFeature() {
        return feature;
    }

    public void setFeature(World.Feature feature) {
        this.feature = feature;
    }

    public World.Biome getBiome() {
        return biome;
    }

    public void setBiome(World.Biome biome) {
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
}
