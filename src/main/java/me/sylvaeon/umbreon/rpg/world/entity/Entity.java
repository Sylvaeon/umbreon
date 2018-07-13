package me.sylvaeon.umbreon.rpg.world.entity;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public abstract class Entity implements Serializable, Comparable<Entity> {

	private static final long serialVersionUID = 3L;

	private String name;
	private double health, maxHealth;

	public Entity() {
        this.maxHealth = 100;
        this.health = maxHealth;
    }

    public Entity(String name) {
		this.name = name;
	    this.maxHealth = 100;
	    this.health = maxHealth;
    }

    public void updateHealth() {
		if(health > maxHealth) {
			health = maxHealth;
		} else if(health < 0) {
			health = 0;
		}
    }

    public boolean dead() {
		return health <= 0;
    }
	
	public double getDmg() {
		return 0;
	}
    
    public static double getStatScalar(int stat) {
        return 1 - Math.pow(Math.E, -(stat + 5) / 20);
    }

    public void takeDamage(double damage) {
        this.health = this.getHealth() - damage;
    }

    public void resetHealth() {
    	this.health = maxHealth;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public double getHealth() {
	    updateHealth();
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
        updateHealth();
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }
	
	@Override
	public int compareTo(@NotNull Entity o) {
		return name.compareTo(o.name);
	}
}
