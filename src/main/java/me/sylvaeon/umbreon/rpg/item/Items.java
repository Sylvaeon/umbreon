package me.sylvaeon.umbreon.rpg.item;

import me.sylvaeon.umbreon.Utility;
import me.sylvaeon.umbreon.rpg.item.equipable.tool.Tool;
import me.sylvaeon.umbreon.rpg.item.equipable.weapon.Sword;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Items {
	public static Map<String, Item> items;

	/**Mining + Smithing**/
	//Ores
	public static Item FLINT_SHARD;
	public static Item STONE;
	public static Item COPPER_ORE;
	public static Item TIN_ORE;
	public static Item IRON_ORE;
	public static Item NICKLE_ORE;
	public static Item TITANIUM_ORE;
	public static Item MYTHRIL_ORE;
	public static Item DRAGONSTONE_ORE;
	//Ingots
	public static Item COPPER_INGOT;
	public static Item TIN_INGOT;
	public static Item BRONZE_INGOT;
	public static Item IRON_INGOT;
	public static Item NICKLE_INGOT;
	public static Item STEEL_INGOT;
	public static Item TITANIUM_INGOT;
	public static Item TITAN_INGOT;
	public static Item MYTHRIL_INGOT;
	public static Item MYSTIC_INGOT;
	public static Item DRAGONSTONE_INGOT;
	public static Item MAGISTEEL_INGOT;

	/**Logging**/
	public static Item LOG;
	public static Item BRANCH;

	/**Monster Drops**/
	public static Item RAT_SKIN, RAT_TAIL;
	public static Item BAT_WING;

	//God-Tier Weapons
	public static Item DURENDAL;
	public static Item CROCEA_MORS;
	public static Item GAEAS_CLAW;
	public static Item ASCLEPIUSS_DAGGER;
	public static Item MJOLNIR;
	public static Item MEN_PHARNAKOU;
	public static Item KOGISTUNE;
	public static Item SKOFNUNG;
	public static Item COLADA;
	public static Item AMATERASUS_FANGED_BLADE;

	public static void init() {
		items = new HashMap<>();

		DURENDAL = new Sword("Durendal", ItemRarity.LEGENDARY);
		CROCEA_MORS = new Sword("Crocea Mors", ItemRarity.LEGENDARY);
		GAEAS_CLAW = new Sword("Gaea's Claw", ItemRarity.LEGENDARY);
		ASCLEPIUSS_DAGGER = new Sword("Asclepius's Twisting Dagger", ItemRarity.LEGENDARY);
		MJOLNIR = new Sword("Mjölnir, Thor's Hammer", ItemRarity.LEGENDARY);
		MEN_PHARNAKOU = new Sword("Mēn Pharnakou", ItemRarity.LEGENDARY);
		KOGISTUNE = new Sword("Kogistune", ItemRarity.LEGENDARY);
		SKOFNUNG = new Sword("Skofnung", ItemRarity.LEGENDARY);
		COLADA = new Sword("Colada", ItemRarity.LEGENDARY);
		AMATERASUS_FANGED_BLADE = new Sword("Amaterasu's Fanged Blade", ItemRarity.LEGENDARY);

		for(Field field : Items.class.getDeclaredFields()) {
			if(Item.class.isAssignableFrom(field.getType())) {
				try {
					Item item = (Item) field.get(new Items());
					if(item == null) {
						item = new Item(Utility.formatEnumName(field.getName()));
						field.set(null, item);
					}
					addItem(item);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		for(Tool.ToolMaterial toolMaterial : Tool.ToolMaterial.values()) {
			for(Tool.ToolType toolType : Tool.ToolType.values()) {
				addItem(new Tool(toolMaterial, toolType));
			}
		}
	}

	public static void addItem(Item item) {
		items.put(item.name, item);
	}

	public static Item getItem(String name) {
		return items.get(name);
	}
}
