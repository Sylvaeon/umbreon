package me.sylvaeon.umbreon.rpg.item.loot;

import me.sylvaeon.umbreon.rpg.item.Item;

public class LootDrop {
    Item loot;
    LootAmount amount;

    public LootDrop(Item loot, LootAmount amount) {
        this.loot = loot;
        this.amount = amount;
    }

    public LootDrop(Item loot, int min, int max) {
    	this.loot = loot;
    	this.amount = new LootAmount(min, max);
	}
	
	public LootDrop(Item loot, double chance) {
    	this.loot = loot;
    	this.amount = new LootAmount(chance);
	}
 
	public LootDrop(Item loot) {
    	this.loot = loot;
    	this.amount = new LootAmount(1, 1);
	}
	
	public LootDrop(Item loot, int amount) {
    	this.loot = loot;
    	this.amount = new LootAmount(amount, amount);
	}
	
    public Item getLoot() {
        return loot;
    }

    public LootAmount getAmount() {
        return amount;
    }
}
