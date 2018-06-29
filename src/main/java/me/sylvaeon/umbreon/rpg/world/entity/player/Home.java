package me.sylvaeon.umbreon.rpg.world.entity.player;

import me.sylvaeon.umbreon.rpg.item.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Home {
    List<Item> items;

    public Home(Item... items) {
        this.items = new ArrayList<>(Arrays.asList(items));
    }

    public List<Item> getItems() {
        return items;
    }
}
