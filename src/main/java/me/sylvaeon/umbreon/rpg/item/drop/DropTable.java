package me.sylvaeon.umbreon.rpg.item.drop;

import me.sylvaeon.umbreon.rpg.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DropTable {
	public Map<Item, DropAmount> drops;

    public DropTable() {
        drops = new HashMap<>();
    }

    public DropTable(ItemDrop... drops) {
        this.drops = new HashMap<>();
        for(ItemDrop drop : drops) {
            this.drops.put(drop.getDrop(), drop.getAmount());
        }
    }

    public List<Item> getDrops() {
        List<Item> items = new ArrayList<>();
        for(Map.Entry<Item, DropAmount> entry : drops.entrySet()) {
            for(int i = 0; i < entry.getValue().getDropAmount(); i++) {
                items.add(entry.getKey());
            }
        }
        return items;
    }
}
