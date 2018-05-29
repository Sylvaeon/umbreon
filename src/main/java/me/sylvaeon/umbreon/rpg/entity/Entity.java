package me.sylvaeon.umbreon.rpg.entity;

public abstract class Entity {
    protected String name;
    protected double health, maxHealth;

    protected Entity() {
        this.maxHealth = 100;
        this.health = maxHealth;
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
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }
}
