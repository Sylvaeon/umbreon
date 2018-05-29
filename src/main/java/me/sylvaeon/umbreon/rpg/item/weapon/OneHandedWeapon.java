package me.sylvaeon.umbreon.rpg.item.weapon;

import me.sylvaeon.umbreon.rpg.item.Equipable;
import me.sylvaeon.umbreon.rpg.item.ItemRarity;

public class OneHandedWeapon extends Weapon implements Equipable {
	public OneHandedWeapon(String name) {
		super(name);
		this.weaponType = WeaponType.ONE_HANDED;
	}
	
	public OneHandedWeapon(String name, ItemRarity itemRarity) {
		super(name, itemRarity);
		this.weaponType = WeaponType.ONE_HANDED;
	}
}
