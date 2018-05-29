package me.sylvaeon.umbreon.rpg.item;

import me.sylvaeon.umbreon.helper.StringHelper;
import me.sylvaeon.umbreon.rpg.item.tool.Tool;
import me.sylvaeon.umbreon.rpg.item.tool.ToolMaterial;
import me.sylvaeon.umbreon.rpg.item.tool.ToolType;
import me.sylvaeon.umbreon.rpg.item.weapon.OneHandedWeapon;
import me.sylvaeon.umbreon.rpg.item.weapon.TwoHandedWeapon;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Items {
	private static Map<String, Item> items;
	
	public static Item RAT_TAIL;
	public static Item RAT_SKIN;
	public static Item BAT_WING;
	public static Item DEER_MEAT;
	public static Item DEER_HIDE;
	public static Item RABBIT_MEAT;
	public static Item RABBIT_HIDE;
	public static Item LOG;
	public static Item BRANCH;
	public static Item FISH;
	public static Item BERRY;
	public static Item MUSHROOM;
	public static Item HERB;
	public static Item STONE;
	public static Item CAMPFIRE;
	public static Item DURENDAL;
	public static Item CROCEA_MORS;
	public static Item KOGISTUNE;
	public static Item MEN_PHARNAKOU;
	public static Item MJOLNIR;
	public static Item SKOFNUNG;
	public static Item COLADA;
	public static Item GAEAS_CLAW;
	public static Item AMATERASUS_SCARF;
	public static Item ASCLEPIUSS_DAGGER;
	public static Item WOOD_ROD;
	public static Item COPPER_ORE;
	public static Item COPPER_INGOT;
	public static Item IRON_ORE;
	public static Item NICKLE_ORE;
	public static Item TIN_ORE;
	public static Item WOOD_TOOL_ROD;
	public static Item STONE_ROD;
	public static Item VINE;
	public static Item ROPE;
	public static Item SHARPENED_STICK;
	public static Item WOOD_MORTAR;

	public static Item FLINT_SHARD;
	public static Item FLINT_FISHING_HOOK;
	public static Item FLINT_KNIFE_BLADE;
	public static Item FLINT_SPEAR_HEAD;
	public static Item FLINT_AXE_HEAD;
	public static Item FLINT_PICK_HEAD;
	public static Item FLINT_PESTLE;
	public static Item FLINT_SAW_BLADE;
	public static Item FLINT_CAULDRON_BASE;
	public static Item FLINT_HAMMER_HEAD;

	public static Item FLINT_FISHING_ROD;
	public static Item FLINT_CUTTING_TOOL;
	public static Item FLINT_SPEAR;
	public static Item FLINT_AXE;
	public static Item FLINT_PICK;

	public static Item FLINT_ALCHEMIC_TOOLS;
	public static Item FLINT_SAW;
	public static Item FLINT_CAULDRON;
	public static Item FLINT_HAMMER;

	public static void init() {
		items = new HashMap<>();

		FLINT_FISHING_ROD = new Tool(ToolType.FISHING_ROD, ToolMaterial.FLINT);
		FLINT_CUTTING_TOOL = new Tool(ToolType.CUTTING_TOOL, ToolMaterial.FLINT);
		FLINT_SPEAR = new Tool(ToolType.SPEAR, ToolMaterial.FLINT);
		FLINT_AXE = new Tool(ToolType.AXE, ToolMaterial.FLINT);
		FLINT_PICK = new Tool(ToolType.PICK, ToolMaterial.FLINT);
		FLINT_ALCHEMIC_TOOLS = new Tool(ToolType.ALCHEMIC_TOOLS, ToolMaterial.FLINT);
		FLINT_SAW = new Tool(ToolType.SAW, ToolMaterial.FLINT);
		FLINT_CAULDRON = new Tool(ToolType.CAULDRON, ToolMaterial.FLINT);
		FLINT_HAMMER = new Tool(ToolType.HAMMER, ToolMaterial.FLINT);

		DURENDAL = new OneHandedWeapon("Durendal", ItemRarity.LEGENDARY);
		CROCEA_MORS = new TwoHandedWeapon("Crocea Mors", ItemRarity.LEGENDARY);
		GAEAS_CLAW = new OneHandedWeapon("Gaea's Claw", ItemRarity.LEGENDARY);
		ASCLEPIUSS_DAGGER = new OneHandedWeapon("Asclepius's Twisting Dagger", ItemRarity.LEGENDARY);
		MJOLNIR = new TwoHandedWeapon("Mjölnir, Thor's Hammer", ItemRarity.LEGENDARY);
		MEN_PHARNAKOU = new TwoHandedWeapon("Mēn Pharnakou", ItemRarity.LEGENDARY);
		KOGISTUNE = new TwoHandedWeapon("Kogistune", ItemRarity.LEGENDARY);
		SKOFNUNG = new OneHandedWeapon("Skofnung", ItemRarity.LEGENDARY);
		COLADA = new TwoHandedWeapon("Colada", ItemRarity.LEGENDARY);
		AMATERASUS_SCARF = new Item("Amaterasu's Scarf", ItemRarity.LEGENDARY);

		for(Field field : Items.class.getDeclaredFields()) {
			if(Item.class.isAssignableFrom(field.getType())) {
				try {
					Item item = (Item) field.get(new Items());
					if(item == null) {
						item = new Item(StringHelper.formatEnumName(field.getName()));
						field.set(null, item);
					}
					items.put(item.getName(), item);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static Item getItem(String name) {
		return items.get(name);
	}
}
