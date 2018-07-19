package me.sylvaeon.umbreon.rpg.world.entity;

import me.sylvaeon.umbreon.rpg.world.Standards;

public class AnimalSpecies extends Species {
	boolean unique;
	boolean canBeTamed;

    public AnimalSpecies(String name, boolean unique, boolean canBeTamed, Standards standards) {
        this.name = name;
        this.unique = unique;
        this.canBeTamed = canBeTamed;
        this.standards = standards;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }
    
    public boolean isCanBeTamed() {
        return canBeTamed;
    }

    public void setCanBeTamed(boolean canBeTamed) {
        this.canBeTamed = canBeTamed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Standards getStandards() {
        return standards;
    }

    public void setStandards(Standards standards) {
        this.standards = standards;
    }
}
