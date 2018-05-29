package me.sylvaeon.umbreon.rpg.item;

public class Item implements Comparable<Item> {
	protected String name;
	
	protected ItemRarity itemRarity;

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
		return name;
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
