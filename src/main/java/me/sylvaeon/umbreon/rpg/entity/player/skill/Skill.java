package me.sylvaeon.umbreon.rpg.entity.player.skill;

public class Skill {
	SkillType type;
	int lvl;
	int xp;
	
	public Skill(SkillType type, int lvl, int xp) {
		this.type = type;
		this.lvl = lvl;
		this.xp = xp;
	}
	
	public Skill(SkillType type) {
		this.type = type;
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
		return type;
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
