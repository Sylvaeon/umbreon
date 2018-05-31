package me.sylvaeon.umbreon.rpg.entity.enemy;

import me.sylvaeon.umbreon.rpg.item.drop.ItemDrop;
import me.sylvaeon.umbreon.rpg.item.Items;

public class Enemies {
	public static Enemy RAT;
	public static Enemy BAT;
	
	public static void init() {
		RAT = new EnemyBasic("Rat", 1, 3, new ItemDrop(Items.RAT_SKIN, 1, 3), new ItemDrop(Items.RAT_TAIL, 0.25));
		BAT = new EnemyBasic("Bat", 1, 3, new ItemDrop(Items.BAT_WING, 0, 2));
	}
}
