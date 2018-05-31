package me.sylvaeon.umbreon.rpg.entity.enemy;

import me.sylvaeon.umbreon.rpg.entity.Entity;
import me.sylvaeon.umbreon.rpg.item.drop.ItemDrop;
import me.sylvaeon.umbreon.rpg.item.drop.DropTable;

public abstract class Enemy extends Entity {
    private DropTable loot;
    private String name;
    
    private int xpMin, xpMax;

    public Enemy(String name) {
        super();
        this.name = name;
        this.loot = new DropTable();
    }

    public Enemy(String name, int xpMin, int xpMax, DropTable lootTable) {
		super();
		this.name = name;
		this.loot = lootTable;
		this.xpMin = xpMin;
		this.xpMax = xpMax;
	}
	
	public Enemy(String name, int xpMin, int xpMax, ItemDrop... drops) {
		super();
		this.name = name;
		this.loot = new DropTable(drops);
		this.xpMin = xpMin;
		this.xpMax = xpMax;
	}
	
	@Override
	public double getDmg() {
		return 1;
	}
	
	public DropTable getLoot() {
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
