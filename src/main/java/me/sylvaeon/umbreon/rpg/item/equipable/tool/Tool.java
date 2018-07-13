package me.sylvaeon.umbreon.rpg.item.equipable.tool;

import me.sylvaeon.umbreon.rpg.item.Item;
import me.sylvaeon.umbreon.rpg.item.equipable.Equipable;

import static me.sylvaeon.umbreon.Utility.formatEnum;

public class Tool extends Item implements Equipable {

	public ToolMaterial toolMaterial;
	public ToolType toolType;

	public Tool(ToolMaterial toolMaterial, ToolType toolType) {
		this.toolMaterial = toolMaterial;
		this.toolType = toolType;
		this.name = formatEnum(toolMaterial) + " " + formatEnum(toolType);
	}

	public enum ToolMaterial {
		WOOD(0, 0), FLINT(1, 0);

		double materialLevel;
		double materialBonus;
		ToolMaterial(double toolLevel, double toolBonus) {
			this.materialLevel = toolLevel;
			this.materialBonus = toolBonus;
		}

		public double getMaterialLevel() {
			return materialLevel;
		}

		public double getMaterialBonus() {
			return materialBonus;
		}
	}

	public enum ToolType {
		PICKAXE, AXE, SHOVEL
	}
}
