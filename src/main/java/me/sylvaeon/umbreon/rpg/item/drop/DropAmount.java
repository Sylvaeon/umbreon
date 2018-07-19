package me.sylvaeon.umbreon.rpg.item.drop;

import me.sylvaeon.umbreon.util.Utility;

import java.util.concurrent.ThreadLocalRandom;

public class DropAmount {
	public double chance;
	public int min, max;
    public DropAmount(double chance) {
        this.chance = chance;
        this.min = -1;
        this.max = -1;
    }

    public DropAmount(int min, int max) {
        this.chance = -1;
        this.min = min;
        this.max = max;
    }

    public int getDropAmount() {
        if(this.chance == -1) {
            return ThreadLocalRandom.current().nextInt(min, max + 1);
        } else {
        	int base = (int) Math.floor(chance);
            return base + (Utility.intervalChance(chance - base) ? 1 : 0);
        }
    }
}
