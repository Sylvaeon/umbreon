package me.sylvaeon.umbreon.rpg.item.equipable.weapon;

import me.sylvaeon.umbreon.rpg.item.Item;
import me.sylvaeon.umbreon.rpg.item.ItemRarity;
import me.sylvaeon.umbreon.rpg.item.equipable.Equipable;

public abstract class Weapon extends Item implements Equipable {
	public Weapon() {
	}

	public Weapon(String name) {
		super(name);
	}

	public Weapon(String name, ItemRarity itemRarity) {
		super(name, itemRarity);
	}
}
