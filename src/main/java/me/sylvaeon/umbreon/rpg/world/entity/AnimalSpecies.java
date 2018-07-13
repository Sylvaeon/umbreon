package me.sylvaeon.umbreon.rpg.world.entity;

import me.sylvaeon.umbreon.rpg.world.Standards;

public class AnimalSpecies extends Species {
	boolean elemental;
	boolean nightOnly;
	boolean unique;
	boolean hostile;
	boolean canBeTamed;

    public AnimalSpecies(String name, boolean elemental, boolean nightOnly, boolean unique, boolean hostile, boolean canBeTamed, Standards standards) {
        this.name = name;
        this.elemental = elemental;
        this.nightOnly = nightOnly;
        this.unique = unique;
        this.hostile = hostile;
        this.canBeTamed = canBeTamed;
        this.standards = standards;
    }

    public boolean isElemental() {
        return elemental;
    }

    public void setElemental(boolean elemental) {
        this.elemental = elemental;
    }

    public boolean isNightOnly() {
        return nightOnly;
    }

    public void setNightOnly(boolean nightOnly) {
        this.nightOnly = nightOnly;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public boolean isHostile() {
        return hostile;
    }

    public void setHostile(boolean hostile) {
        this.hostile = hostile;
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
