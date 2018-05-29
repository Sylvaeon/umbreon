package me.sylvaeon.umbreon.rpg.entity.player.skill;

import me.sylvaeon.umbreon.helper.StringHelper;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.HashMap;
import java.util.Map;

public class SkillSet {
	Map<SkillType, Skill> skills;
	
	public SkillSet() {
		this.skills = new HashMap<>();
		initSkills();
	}
	
	public SkillSet(Skill... skills) {
		this.skills = new HashMap<>();
		for(Skill skill : skills) {
			this.skills.put(skill.getType(), skill);
		}
		initSkills();
	}
	
	public Skill getSkill(SkillType skillType) {
		return skills.get(skillType);
	}
	
	public int getLvl(SkillType skillType) {
		return skills.get(skillType).getLvl();
	}
	
	public int getXp(SkillType skillType) {
		return skills.get(skillType).getXp();
	}
	
	public void addXp(SkillType skillType, int xp) {
		Skill skill = skills.get(skillType);
		skill.addXp(xp);
		skill.update();
	}
	
	public void setXp(SkillType skillType, int xp) {
		skills.get(skillType).setXp(xp);
	}
	
	public void setLvl(SkillType skillType, int lvl) {
		skills.get(skillType).setLvl(lvl);
	}
	
	private void initSkills() {
		for(SkillType type : SkillType.values()) {
			skills.putIfAbsent(type, new Skill(type));
		}
	}
	
	
}
