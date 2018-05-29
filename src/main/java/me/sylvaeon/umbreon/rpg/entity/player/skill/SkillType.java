package me.sylvaeon.umbreon.rpg.entity.player.skill;

public enum SkillType {
	BLOCKING, HEAVY_ARMOR, LIGHT_ARMOR, MEDIUM_ARMOR, ONE_HANDED, TWO_HANDED,
	FISHING, FORAGING, HUNTING, MINING, LOGGING,
	ALCHEMY, CARPENTRY, COOKING, FORGING;
	
	public static SkillType[] getCombatSkillTypes() {
		return new SkillType[] {
			BLOCKING, HEAVY_ARMOR, LIGHT_ARMOR, MEDIUM_ARMOR, ONE_HANDED, TWO_HANDED
		};
	}
	
	public static SkillType[] getGatheringSkillTypes() {
		return new SkillType[] {
			FISHING, FORAGING, HUNTING, MINING, LOGGING
		};
	}
	
	public static SkillType[] getCraftingSkillTypes() {
		return new SkillType[] {
			ALCHEMY, CARPENTRY, COOKING, FORGING
		};
	}
}
