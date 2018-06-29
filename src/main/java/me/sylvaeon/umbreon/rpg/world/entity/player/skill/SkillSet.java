package me.sylvaeon.umbreon.rpg.world.entity.player.skill;

import java.util.HashMap;
import java.util.Map;

public class SkillSet {
    Map<Skill.SkillType, Skill> skills;
	
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

    public Skill getSkill(Skill.SkillType skillType) {
		return skills.get(skillType);
	}

    public void addXp(Skill.SkillType skillType, int xp) {
		Skill skill = skills.get(skillType);
		skill.addXp(xp);
		skill.update();
	}
	
	private void initSkills() {
        for (Skill.SkillType type : Skill.SkillType.values()) {
			skills.putIfAbsent(type, new Skill(type));
		}
	}
	
	
}
