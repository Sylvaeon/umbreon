package me.sylvaeon.umbreon.rpg.entity.player;

import me.sylvaeon.umbreon.rpg.item.Item;
import me.sylvaeon.umbreon.rpg.item.ItemStack;

import java.util.*;

public class Inventory {
	Map<Item, ItemStack> items;

	public Inventory() {
		items = new TreeMap<>();
	}

	public Map<Item, ItemStack> getItems() {
		return items;
	}

	public Collection<ItemStack> getItemStacks() {
		return items.values();
	}

	public Set<Item> getItemKeys() {
		return items.keySet();
	}

	public ItemStack getItemStack(Item item) {
		return items.get(item);
	}

	public void addItem(Item item, int amount) {
		if(hasItem(item)) {
			getItemStack(item).add(amount);
		} else {
			items.put(item, new ItemStack(item, amount));
		}
	}

	public <T> void addItems(Collection<T> items) {
		for (T t : items) {
			if(t instanceof ItemStack) {
				ItemStack itemStack = (ItemStack) t;
				addItem(itemStack.getItem(), itemStack.getAmount());
			} else if(t instanceof Item) {
				Item item = (Item) t;
				addItem(item, 1);
			}
		}
	}

	public void addItem(Item item) {
		addItem(item, 1);
	}

	public void subtractItem(Item item, int amount) {
		if (hasItem(item) && getAmount(item) >= amount) {
			getItemStack(item).subtract(amount);
			if(getItemStack(item).empty()) {
				removeItem(item);
			}
		}
	}

	public void subtractItem(Item item) {
		subtractItem(item, 1);
	}

	public boolean hasItem(Item item) {
		return items.containsKey(item);
	}

	public int getAmount(Item item) {
		if(hasItem(item)) {
			return getItemStack(item).getAmount();
		} else {
			return 0;
		}
	}

	public int getAmount() {
		int count = 0;
		for(ItemStack itemStack : getItemStacks()) {
			count += itemStack.getAmount();
		}
		return count;
	}

	public void removeItem(Item item) {
		items.remove(item);
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

}
