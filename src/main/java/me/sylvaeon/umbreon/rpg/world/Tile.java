package me.sylvaeon.umbreon.rpg.world;

import me.sylvaeon.umbreon.rpg.item.Plant;
import me.sylvaeon.umbreon.rpg.world.entity.Animal;

import java.util.Collection;

public class Tile {
    private World.Feature feature;
    private World.Biome biome;
    private Collection<Animal> animals;
    private Collection<Plant> plants;

    public Tile(World.Feature feature, World.Biome biome, Collection<Animal> animals, Collection<Plant> plants) {
        this.feature = feature;
        this.biome = biome;
        this.animals = animals;
        this.plants = plants;
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

    public Collection<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(Collection<Animal> animals) {
        this.animals = animals;
    }

    public Collection<Plant> getPlants() {
        return plants;
    }

    public void setPlants(Collection<Plant> plants) {
        this.plants = plants;
    }
}
