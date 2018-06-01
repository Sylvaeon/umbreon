package me.sylvaeon.umbreon.rpg.item.equipable.tool;

import me.sylvaeon.umbreon.rpg.item.Item;

public abstract class Tool extends Item {
	
	public static final double MATERIAL_WOODEN = 0;
	public static final double MATERIAL_FLINT = 1;
	public static final double MATERIAL_STONE = 2;
	public static final double MATERIAL_COPPER = 3;
	public static final double MATERIAL_TIN = 3;
	public static final double MATERIAL_BRONZE = 4;
	public static final double MATERIAL_IRON = 5;
	public static final double MATERIAL_NICKLE = 3;
	public static final double MATERIAL_STEEL = 6;
	public static final double MATERIAL_TITANIUM = 7;
	public static final double MATERIAL_TITAN = 7;
	public static final double MATERIAL_MYTHRIL = 8;
	public static final double MATERIAL_DRAGONSTONE = 9;
	public static final double MATERIAL_MAGISTEEL = 10;
	
	double toolLevel;
	double toolBonus;

	public Tool(String name, double toolLevel, double toolBonus) {
		super(name);
		this.toolLevel = toolLevel;
		this.toolBonus = toolBonus;
	}

	public Tool(String name, double toolLevel) {
		super(name);
		this.toolLevel = toolLevel;
		this.toolBonus = 0;
	}

	public double getToolLevel() {
		return toolLevel;
	}

	public double getToolBonus() {
		return toolBonus;
	}
}
