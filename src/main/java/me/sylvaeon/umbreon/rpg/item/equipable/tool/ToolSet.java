package me.sylvaeon.umbreon.rpg.item.equipable.tool;

import java.util.HashMap;

public class ToolSet extends HashMap<Tool.ToolType, Tool> {

	public ToolSet() {
		super();
		for(Tool.ToolType toolType : Tool.ToolType.values()) {
			putIfAbsent(toolType, new Tool(Tool.ToolMaterial.WOOD, toolType));
		}
	}

	public void setTool(Tool tool) {
		put(tool.toolType, tool);
	}

}