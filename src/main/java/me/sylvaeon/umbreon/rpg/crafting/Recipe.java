package me.sylvaeon.umbreon.rpg.crafting;

import me.sylvaeon.umbreon.rpg.item.ItemStack;

import java.util.List;

public class Recipe {
	List<ItemStack> outputs;
	List<ItemStack> inputs;

	public Recipe(List<ItemStack> outputs, List<ItemStack> inputs) {
		this.outputs = outputs;
		this.inputs = inputs;
	}


	public List<ItemStack> getOutputs() {
		return outputs;
	}
	
	public List<ItemStack> getInputs() {
		return inputs;
	}
	
	@Override
	public String toString() {
		String string = "";
		for(ItemStack itemStack : inputs) {
			string += itemStack.toString() + ", ";
		}
		string = string.replaceAll(", $", "");
		string += " -> ";
		for(ItemStack itemStack : outputs) {
			string += itemStack.toString() + ", ";
		}
		string = string.replaceAll(", $", "");
		return string;
	}
	
	public String outputsAsString() {
		String string = "";
		for(ItemStack itemStack : outputs) {
			string += itemStack.toString() + "\n";
		}
		return string;
	}
	
	public String inputsAsString() {
		String string = "";
		for(ItemStack itemStack : inputs) {
			string += itemStack.toString() + "\n";
		}
		return string;
	}
	
}
