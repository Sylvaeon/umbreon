package me.sylvaeon.umbreon.rpg.world.entity.player;

import me.sylvaeon.umbreon.rpg.item.ItemSet;
import me.sylvaeon.umbreon.rpg.item.equipable.tool.Tool;
import me.sylvaeon.umbreon.rpg.item.equipable.tool.ToolSet;

public class Inventory extends ItemSet {

	public ToolSet toolSet;
	public Inventory() {
		super();
		this.toolSet = new ToolSet();
		for(Tool tool : toolSet.values()) {
			add(tool);
		}
	}

	public boolean isEquipped(Tool tool) {
		return toolSet.containsValue(tool);
	}

	public boolean equipTool(Tool tool) {
		if(containsKey(tool)) {
			toolSet.setTool(tool);
			return true;
		}
		return false;
	}

	public boolean unequipTool(Tool tool) {
		if(toolSet.containsValue(tool)) {
			toolSet.remove(tool.toolType);
			return true;
		} else {
			return false;
		}
	}

}