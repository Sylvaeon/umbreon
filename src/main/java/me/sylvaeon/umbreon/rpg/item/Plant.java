package me.sylvaeon.umbreon.rpg.item;

import me.sylvaeon.umbreon.rpg.world.Standards;

public class Plant extends Item {
    Standards standards;

    public Plant(Standards standards, String name) {
        super(name);
        this.standards = standards;
    }
}
