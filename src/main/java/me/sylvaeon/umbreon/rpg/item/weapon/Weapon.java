package me.sylvaeon.umbreon.rpg.item.weapon;

import me.sylvaeon.umbreon.rpg.item.Item;
import me.sylvaeon.umbreon.rpg.item.ItemRarity;

public abstract class Weapon extends Item {
	
	protected WeaponType weaponType;
	
	Weapon(String name) {
		super(name);
	}
	
	Weapon(String name, ItemRarity itemRarity) {
		super(name, itemRarity);
	}
	
	public WeaponType getWeaponType() {
		return weaponType;
	}
}
