package me.sylvaeon.umbreon.rpg.item.equipable.tool;

import me.sylvaeon.umbreon.rpg.item.Item;

public abstract class Tool extends Item {

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
