package me.sylvaeon.umbreon.rpg.crafting;

import me.sylvaeon.umbreon.rpg.item.ItemSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Recipes {
	public static List<Recipe> recipes;
	
	public static void init() {
		recipes = new ArrayList<>();
	}
	
	private static void addRecipe(Recipe recipe) {
		recipes.add(recipe);
	}
	
	private static void addRecipe(ItemSet outputs, ItemSet inputs) {
		addRecipe(new Recipe(outputs, inputs));
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
