package me.sylvaeon.umbreon.rpg.item.loot;

import me.sylvaeon.umbreon.rpg.item.Item;

import java.util.*;

public class LootTable {
    private Map<Item, LootAmount> loot;

    public LootTable() {
        loot = new HashMap<>();
    }

    public LootTable(LootDrop... drops) {
        loot = new HashMap<>();
        for(LootDrop drop : drops) {
            loot.put(drop.getLoot(), drop.getAmount());
        }
    }

    public List<Item> getDrops() {
        List<Item> items = new ArrayList<>();
        for(Map.Entry<Item, LootAmount> entry : loot.entrySet()) {
            for(int i = 0; i < entry.getValue().getDropAmount(); i++) {
                items.add(entry.getKey());
            }
        }
        return items;
    }
}
