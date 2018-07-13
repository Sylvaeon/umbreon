package me.sylvaeon.umbreon.rpg.item;

import java.io.Serializable;

public class Item implements Comparable<Item>, Serializable {
	private static final long serialVersionUID = 2L;
	public String name;

	public ItemRarity itemRarity;

	public Item() {
		this.itemRarity = ItemRarity.BASIC;
	}

	public Item(String name) {
		this.name = name;
		this.itemRarity = ItemRarity.BASIC;
	}
	
	public Item(String name, ItemRarity itemRarity) {
		this.name = name;
		this.itemRarity = itemRarity;
	}

	public String getName() {
		if (name != null) {
			return name;
		} else {
			return "null";
		}
	}

	public ItemRarity getItemRarity() {
		return itemRarity;
	}

	@Override
	public int compareTo(Item o) {
		if(this.getItemRarity() != o.getItemRarity()) {
			return -itemRarity.compareTo(o.getItemRarity());
		} else {
			return this.getName().compareTo(o.getName());
		}
	}
}
