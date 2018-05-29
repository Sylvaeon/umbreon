package me.sylvaeon.umbreon.rpg.entity.enemy;

import me.sylvaeon.umbreon.rpg.entity.Entity;
import me.sylvaeon.umbreon.rpg.item.loot.LootDrop;
import me.sylvaeon.umbreon.rpg.item.loot.LootTable;

public abstract class Enemy extends Entity {
    private LootTable loot;
    private String name;
    
    private int xpMin, xpMax;

    public Enemy(String name) {
        super();
        this.name = name;
        this.loot = new LootTable();
    }

    public Enemy(String name, int xpMin, int xpMax, LootTable lootTable) {
		super();
		this.name = name;
		this.loot = lootTable;
		this.xpMin = xpMin;
		this.xpMax = xpMax;
	}
	
	public Enemy(String name, int xpMin, int xpMax, LootDrop... drops) {
		super();
		this.name = name;
		this.loot = new LootTable(drops);
		this.xpMin = xpMin;
		this.xpMax = xpMax;
	}
	
	@Override
	public double getDmg() {
		return 1;
	}
	
	public LootTable getLoot() {
        return loot;
    }
    
    public String getName() {
        return name;
    }
	
	public int getXpMin() {
		return xpMin;
	}
	
	public int getXpMax() {
		return xpMax;
	}
}
