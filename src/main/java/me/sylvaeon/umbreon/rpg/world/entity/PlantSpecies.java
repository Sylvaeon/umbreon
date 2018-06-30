package me.sylvaeon.umbreon.rpg.world.entity;

import me.sylvaeon.umbreon.rpg.world.Standards;

public class PlantSpecies extends Species {

    PlantType plantType;

    public PlantSpecies(String name, PlantType plantType, Standards standards) {
        this.name = name;
        this.plantType = plantType;
        this.standards = standards;
    }

    public enum PlantType {
        FLOWER, FRUIT, GRAIN, SEED, VEGETABLE
    }

}
