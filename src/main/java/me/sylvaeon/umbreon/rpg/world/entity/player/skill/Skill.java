package me.sylvaeon.umbreon.rpg.world.entity.player.skill;

import java.io.Serializable;

public class Skill implements Serializable {

    public enum SkillType {
        FORAGING, LOGGING, MINING
    }

	private String type;
	private int lvl;
	private int xp;
	
	public Skill(SkillType type) {
		this.type = type.name();
		this.lvl = 1;
		this.xp = 0;
	}
	
	public void update() {
		while(xp > 0) {
			if(xp >= getXpNeeded()) {
				xp -= getXpNeeded();
				addLvl();
			} else {
				break;
			}
		}
	}
	
	public int getXpNeeded() {
		return lvl;
	}
	
	public int getXpLeft() {
		return getXpNeeded() - getXp();
	}
	
	public SkillType getType() {
		return SkillType.valueOf(type.toUpperCase());
	}
	
	public int getLvl() {
		return lvl;
	}
	
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
	
	public void addLvl(int lvl) {
		this.lvl += lvl;
	}
	
	public void addLvl() {
		this.lvl++;
	}
	
	public int getXp() {
		return xp;
	}
	
	public void setXp(int xp) {
		this.xp = xp;
	}
	
	public void addXp(int xp) {
		this.xp += xp;
	}
	
	public void addXp() {
		this.xp++;
	}


}
