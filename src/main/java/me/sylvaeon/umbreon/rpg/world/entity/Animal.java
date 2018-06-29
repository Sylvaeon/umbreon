package me.sylvaeon.umbreon.rpg.world.entity;

public class Animal extends Entity {

    boolean isTamed;

    public Animal(AnimalSpecies species) {
        this.name = species.getName();
    }

    public boolean isTamed() {
        return isTamed;
    }

    public void setTamed(boolean tamed) {
        isTamed = tamed;
    }
}
