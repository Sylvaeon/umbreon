package me.sylvaeon.umbreon.rpg.crafting;

import me.sylvaeon.umbreon.rpg.item.ItemStack;
import me.sylvaeon.umbreon.rpg.item.Items;
import me.sylvaeon.umbreon.rpg.item.tool.Tool;
import me.sylvaeon.umbreon.rpg.item.tool.ToolMaterial;
import me.sylvaeon.umbreon.rpg.item.tool.ToolType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Recipes {
	private static List<Recipe> recipes;
	
	public static void init() {
		recipes = new ArrayList<>();
		
		addRecipe(
			asList(new ItemStack(Items.CAMPFIRE)),
			asList(new ItemStack(Items.LOG, 8), new ItemStack(Items.BRANCH, 12)
		));
		addRecipe(
			asList(new ItemStack(Items.WOOD_ROD)),
			asList(new ItemStack(Items.BRANCH, 4))
		);
		addRecipe(
			asList(new ItemStack(Items.WOOD_TOOL_ROD)),
			asList(new ItemStack(Items.WOOD_ROD, 2))
		);
		addRecipe(
			asList(new ItemStack(Items.STONE_ROD)),
			asList(new ItemStack(Items.STONE, 4))
		);

		addRecipe(
			asList(new ItemStack(Items.FLINT_AXE_HEAD)),
			asList(new ItemStack(Items.FLINT_SHARD, 2))
		);
		addRecipe(
			asList(new ItemStack(Items.FLINT_KNIFE_BLADE)),
			asList(new ItemStack(Items.FLINT_SHARD, 2))
		);
		addRecipe(
			asList(new ItemStack(Items.FLINT_PICK_HEAD)),
			asList(new ItemStack(Items.FLINT_SHARD, 2))
		);
		addRecipe(
			asList(new ItemStack(Items.FLINT_SPEAR_HEAD)),
			asList(new ItemStack(Items.FLINT_SHARD, 2))
		);
		addRecipe(
			asList(new ItemStack(Items.FLINT_FISHING_HOOK)),
			asList(new ItemStack(Items.FLINT_SHARD, 2))
		);

		addRecipe(
			asList(new ItemStack(Items.FLINT_PESTLE)),
			asList(new ItemStack(Items.FLINT_SHARD, 3))
		);
		addRecipe(
			asList(new ItemStack(Items.FLINT_SAW_BLADE)),
			asList(new ItemStack(Items.FLINT_SHARD, 3))
		);
		addRecipe(
			asList(new ItemStack(Items.FLINT_CAULDRON_BASE)),
			asList(new ItemStack(Items.FLINT_SHARD, 3))
		);
		addRecipe(
			asList(new ItemStack(Items.FLINT_HAMMER_HEAD)),
			asList(new ItemStack(Items.FLINT_SHARD, 3))
		);

		addRecipe(
			asList(new ItemStack(Items.FLINT_FISHING_ROD)),
			asList(new ItemStack(Items.WOOD_TOOL_ROD), new ItemStack(Items.FLINT_FISHING_HOOK))
		);
		addRecipe(
			asList(new ItemStack(Items.FLINT_CUTTING_TOOL)),
			asList(new ItemStack(Items.WOOD_TOOL_ROD), new ItemStack(Items.FLINT_KNIFE_BLADE))
		);
		addRecipe(
			asList(new ItemStack(Items.FLINT_SPEAR)),
			asList(new ItemStack(Items.WOOD_TOOL_ROD), new ItemStack(Items.FLINT_SPEAR_HEAD))
		);
		addRecipe(
			asList(new ItemStack(Items.FLINT_AXE)),
			asList(new ItemStack(Items.WOOD_TOOL_ROD), new ItemStack(Items.FLINT_AXE_HEAD))
		);
		addRecipe(
			asList(new ItemStack(Items.FLINT_PICK)),
			asList(new ItemStack(Items.WOOD_TOOL_ROD), new ItemStack(Items.FLINT_PICK_HEAD))
		);

		addRecipe(
			asList(new ItemStack(Items.FLINT_ALCHEMIC_TOOLS)),
			asList(new ItemStack(Items.WOOD_MORTAR), new ItemStack(Items.FLINT_PESTLE))
		);
		addRecipe(
			asList(new ItemStack(Items.FLINT_SAW)),
			asList(new ItemStack(Items.WOOD_TOOL_ROD, 2), new ItemStack(Items.FLINT_SAW_BLADE))
		);
		addRecipe(
			asList(new ItemStack(Items.FLINT_CAULDRON)),
			asList(new ItemStack(Items.CAMPFIRE), new ItemStack(Items.FLINT_CAULDRON_BASE))
		);
		addRecipe(
			asList(new ItemStack(Items.FLINT_HAMMER)),
			asList(new ItemStack(Items.WOOD_TOOL_ROD, 2), new ItemStack(Items.FLINT_HAMMER_HEAD))
		);
		addRecipe(
			new Tool(ToolType.SAW, ToolMaterial.FLINT),
			asList(new ItemStack(Items.SHARPENED_STICK)),
			asList(new ItemStack(Items.BRANCH))
		);
		addRecipe(
			new Tool(ToolType.CUTTING_TOOL, ToolMaterial.FLINT),
			asList(new ItemStack(Items.ROPE)),
			asList(new ItemStack(Items.VINE, 4))
		);
		addRecipe(
			new Tool(ToolType.HAMMER, ToolMaterial.STONE),
			asList(new ItemStack(Items.COPPER_INGOT)),
			asList(new ItemStack(Items.COPPER_ORE, 2))
		);
	}
	
	private static void addRecipe(Recipe recipe) {
		recipes.add(recipe);
	}
	
	private static void addRecipe(List<ItemStack> outputs, List<ItemStack> inputs) {
		addRecipe(new Recipe(outputs, inputs));
	}

	private static void addRecipe(Tool tool, List<ItemStack> outputs, List<ItemStack> inputs) {
		addRecipe(new Recipe(tool, outputs, inputs));
	}

	public static List<Recipe> getRecipes() {
		return recipes;
	}
	
	private static <T> List<T> asList(List<T> list) {
		return list;
	}
	
	private static <T> List<T> asList(T... list) {
		return Arrays.asList(list);
	}
	
	private static <T> List<T> asList(T t) {
		List<T> list = new ArrayList<>();
		list.add(t);
		return list;
	}
	
}
