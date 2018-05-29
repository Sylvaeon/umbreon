package me.sylvaeon.umbreon.rpg.entity.enemy;

import me.sylvaeon.umbreon.rpg.item.loot.LootDrop;
import me.sylvaeon.umbreon.rpg.item.loot.LootTable;

public class EnemyBasic extends Enemy {
	public EnemyBasic(String name) {
		super(name);
	}
	
	public EnemyBasic(String name, int xpMin, int xpMax, LootTable lootTable) {
		super(name, xpMin, xpMax, lootTable);
	}
	
	public EnemyBasic(String name, int xpMin, int xpMax, LootDrop... drops) {
		super(name, xpMin, xpMax, drops);
	}
}
