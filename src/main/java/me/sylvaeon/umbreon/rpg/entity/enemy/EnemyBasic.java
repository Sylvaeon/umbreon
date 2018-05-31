package me.sylvaeon.umbreon.rpg.entity.enemy;

import me.sylvaeon.umbreon.rpg.item.drop.ItemDrop;
import me.sylvaeon.umbreon.rpg.item.drop.DropTable;

public class EnemyBasic extends Enemy {
	public EnemyBasic(String name) {
		super(name);
	}
	
	public EnemyBasic(String name, int xpMin, int xpMax, DropTable lootTable) {
		super(name, xpMin, xpMax, lootTable);
	}
	
	public EnemyBasic(String name, int xpMin, int xpMax, ItemDrop... drops) {
		super(name, xpMin, xpMax, drops);
	}
}
