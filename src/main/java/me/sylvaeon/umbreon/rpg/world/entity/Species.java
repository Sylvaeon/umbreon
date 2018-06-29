package me.sylvaeon.umbreon.rpg.world.entity;

import me.sylvaeon.umbreon.rpg.world.Standards;

public abstract class Species {
    String name;
    Standards standards;

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
