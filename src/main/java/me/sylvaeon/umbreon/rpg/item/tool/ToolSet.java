package me.sylvaeon.umbreon.rpg.item.tool;

import java.util.*;

public class ToolSet {
	private Set<Tool> tools;
	
	public ToolSet() {
		tools = new TreeSet<>();
		for(ToolType toolType : ToolType.values()) {
			setMaterial(toolType, ToolMaterial.NONE);
		}
	}

	public ToolSet(List<Integer> materialList) {
		tools = new TreeSet<>();
		for(ToolType toolType : ToolType.values()) {
			setMaterial(toolType, ToolMaterial.NONE);
		}
		for(int i = 0; i < materialList.size(); i++) {
			setMaterial(ToolType.values()[i], materialList.get(i));
		}
	}

	public boolean isEmpty() {
		for(Tool tool : tools) {
			if(tool.getMaterial() > ToolMaterial.NONE) {
				return false;
			}
		}
		return true;
	}

	public Tool getTool(ToolType toolType) {
		for(Tool tool : tools) {
			if(tool.getType() == toolType) {
				return tool;
			}
		}
		return null;
	}

	public int getMaterial(ToolType toolType) {
		return getTool(toolType).getMaterial();
	}
	
	public void setMaterial(ToolType toolType, int material) {
		if(getTool(toolType) != null) {
			getTool(toolType).setMaterial(material);
		} else {
			addTool(toolType, material);
		}
	}

	private void addTool(ToolType toolType, int material) {
		Tool tool = getTool(toolType);
		if(tool != null) {
			tools.remove(tool);
		}
		tools.add(new Tool(toolType, material));
	}

	public boolean upgradeMaterial(ToolType toolType) {
		int b = getMaterial(toolType);
		boolean upgraded = false;
		if(b >= 7) {
			b = 7;
		} else {
			b++;
			upgraded = true;
		}
		setMaterial(toolType, b);
		return upgraded;
	}

	public Set<Tool> getTools() {
		return tools;
	}
}
