package me.sylvaeon.umbreon.rpg.item.drop;

import me.sylvaeon.umbreon.rpg.item.Item;

public class ItemDrop {
    Item drop;
    DropAmount amount;

    public ItemDrop(Item drop, int min, int max) {
    	this.drop = drop;
    	this.amount = new DropAmount(min, max);
	}
	
	public ItemDrop(Item drop, double chance) {
    	this.drop = drop;
    	this.amount = new DropAmount(chance);
	}
	
    public Item getDrop() {
        return drop;
    }

    public DropAmount getAmount() {
        return amount;
    }
}
