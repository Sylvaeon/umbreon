package me.sylvaeon.umbreon.rpg.item.equipable.tool;

public class Pickaxe extends Tool {

	public static final double MATERIAL_WOOD = 0, MATERIAL_FLINT = 1, MATERIAL_STONE = 2, MATERIAL_COPPER = 3, MATERIAL_TIN = 3,
			MATERIAL_BRONZE = 4, MATERIAL_IRON = 5, MATERIAL_NICKLE = 3, MATERIAL_STEEL = 6, MATERIAL_TITANIUM = 7, MATERIAL_TITAN = 7,
			MATERIAL_MYTHRIL = 8, MATERIAL_DRAGONSTONE = 9, MATERIAL_MAGISTEEL = 10;

	public Pickaxe(String name, double toolLevel, double toolBonus) {
		super(name, toolLevel, toolBonus);
	}

	public Pickaxe(String name, double toolLevel) {
		super(name, toolLevel);
	}

}
