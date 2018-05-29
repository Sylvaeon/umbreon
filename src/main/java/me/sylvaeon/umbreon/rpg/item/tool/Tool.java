package me.sylvaeon.umbreon.rpg.item.tool;

import me.sylvaeon.umbreon.helper.StringHelper;
import me.sylvaeon.umbreon.rpg.item.Item;

public class Tool extends Item {

	private ToolType type;
	private int material;

	public Tool(ToolType toolType, int toolMaterial) {
		super();
		this.material = toolMaterial;
		this.type = toolType;
	}

	public ToolType getType() {
		return type;
	}

	public int getMaterial() {
		return material;
	}

	public void setType(ToolType type) {
		this.type = type;
	}

	public void setMaterial(int material) {
		this.material = material;
	}

	@Override
	public String getName() {
		String materialName;
		switch(material) {
			case ToolMaterial.FLINT:
				materialName = "Flint";
				break;
			case ToolMaterial.STONE:
				materialName = "Stone";
				break;
			case ToolMaterial.COPPER:
				materialName = "Copper";
				break;
			case ToolMaterial.BRONZE:
				materialName = "Bronze";
				break;
			case ToolMaterial.IRON:
				materialName = "Iron";
				break;
			case ToolMaterial.STEEL:
				materialName = "Steel";
				break;
			default:
				materialName = "None";
				break;
		}
		String typeName = StringHelper.formatEnum(type);
		return materialName + " " + typeName;
	}
}
