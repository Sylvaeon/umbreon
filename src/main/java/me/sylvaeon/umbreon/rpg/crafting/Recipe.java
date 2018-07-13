package me.sylvaeon.umbreon.rpg.crafting;

import me.sylvaeon.umbreon.Counter;
import me.sylvaeon.umbreon.rpg.item.Item;
import me.sylvaeon.umbreon.rpg.item.ItemSet;

import java.util.Map;

public class Recipe {

	public ItemSet inputs;
	public ItemSet outputs;

	public Recipe(ItemSet inputs, ItemSet outputs) {
		this.inputs = inputs;
		this.outputs = outputs;
	}

	public ItemSet getInputs() {
		return inputs;
	}

	public ItemSet getOutputs() {
		return outputs;
	}

	@Override
	public String toString() {
		String string = "";
		for(Map.Entry<Item, Counter> entry : inputs.entrySet()) {
			string +=  + entry.getValue().count + "x" + entry.getKey().name + ", ";
		}
		string = string.replaceAll(", $", "");
		string += " -> ";
		for(Map.Entry<Item, Counter> entry : outputs.entrySet()) {
			string +=  + entry.getValue().count + "x" + entry.getKey().name + ", ";
		}
		string = string.replaceAll(", $", "");
		return string;
	}
	
}
