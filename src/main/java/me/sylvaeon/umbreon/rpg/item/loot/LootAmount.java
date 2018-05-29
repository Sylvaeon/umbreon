package me.sylvaeon.umbreon.rpg.item.loot;

import me.sylvaeon.umbreon.helper.MathHelper;

import java.util.concurrent.ThreadLocalRandom;

public class LootAmount {
    double chance;
    int min, max;
    public LootAmount(double chance) {
        this.chance = chance;
        this.min = -1;
        this.max = -1;
    }

    public LootAmount(int min, int max) {
        this.chance = -1;
        this.min = min;
        this.max = max;
    }

    public int getDropAmount() {
        if(this.chance == -1) {
            return ThreadLocalRandom.current().nextInt(min, max + 1);
        } else {
        	int base = (int) Math.floor(chance);
            return base + (MathHelper.intervalChance((double) chance - base) ? 1 : 0);
        }
    }
}
