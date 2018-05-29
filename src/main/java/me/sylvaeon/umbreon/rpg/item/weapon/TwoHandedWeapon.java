package me.sylvaeon.umbreon.rpg.item.weapon;

import me.sylvaeon.umbreon.rpg.item.Equipable;
import me.sylvaeon.umbreon.rpg.item.ItemRarity;

public class TwoHandedWeapon extends Weapon implements Equipable {
	public TwoHandedWeapon(String name) {
		super(name);
		this.weaponType = WeaponType.TWO_HANDED;
	}
	
	public TwoHandedWeapon(String name, ItemRarity itemRarity) {
		super(name, itemRarity);
		this.weaponType = WeaponType.TWO_HANDED;
	}
}
