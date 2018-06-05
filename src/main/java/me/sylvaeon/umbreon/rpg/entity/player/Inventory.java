package me.sylvaeon.umbreon.rpg.entity.player;

import me.sylvaeon.umbreon.rpg.item.Item;
import me.sylvaeon.umbreon.rpg.item.ItemStack;
import me.sylvaeon.umbreon.rpg.item.equipable.tool.Axe;
import me.sylvaeon.umbreon.rpg.item.equipable.tool.Pickaxe;
import me.sylvaeon.umbreon.rpg.item.equipable.tool.Tool;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Inventory {
	private Map<Item, ItemStack> items;

	private Pickaxe pickaxe;
	private Axe axe;

	public Inventory() {
		items = new TreeMap<>();
	}

	public void initTools() {
		pickaxe = new Pickaxe("Wooden Pickaxe", Tool.MATERIAL_WOODEN);
		addItem(pickaxe);
		equipItem(pickaxe);
		axe = new Axe("Wooden Axe", Tool.MATERIAL_WOODEN);
		addItem(axe);
		equipItem(axe);
	}

	public void setTools(Pickaxe pickaxe, Axe axe) {
		equipItem(pickaxe);
		equipItem(axe);
	}

	public boolean isEquipped(Item item) {
		if(item == pickaxe) {
			return true;
		} else if(item == axe) {
			return true;
		}
		return false;
	}

	public boolean equipItem(Item item) {
		if(item != null && hasItem(item)) {
			if (item instanceof Pickaxe) {
				Pickaxe pickaxe = (Pickaxe) item;
				this.pickaxe = pickaxe;
				return true;
			} else if (item instanceof Axe) {
				Axe axe = (Axe) item;
				this.axe = axe;
				return true;
			}
		}
		return false;
	}

	public boolean unequipItem(Item item) {
		if(item == pickaxe) {
			pickaxe = null;
			return true;
		} else if(item == axe) {
			axe = null;
			return true;
		}
		return false;
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
		if(pickaxe == item) {
			pickaxe = null;
		} else if(axe == item) {
			axe = null;
		}
	}

	public boolean isEmpty() {
		return items.isEmpty();
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
	
	public Pickaxe getPickaxe() {
		return pickaxe;
	}
	
	public Axe getAxe() {
		return axe;
	}

	public void setItems(Map<Item, ItemStack> items) {
		this.items = items;
	}

	public void setPickaxe(Pickaxe pickaxe) {
		this.pickaxe = pickaxe;
	}

	public void setAxe(Axe axe) {
		this.axe = axe;
	}

}
