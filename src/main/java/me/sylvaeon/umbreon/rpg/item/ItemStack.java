package me.sylvaeon.umbreon.rpg.item;

public class ItemStack implements Comparable<ItemStack> {
	final Item item;
	int amount;
	
	public ItemStack(final Item item) {
		this.item = item;
		this.amount = 1;
	}
	
	public ItemStack(final Item item, int amount) {
		this.item = item;
		this.amount = amount;
	}
	
	@Override
	public int compareTo(ItemStack o) {
		return item.compareTo(o.getItem());
	}
	
	@Override
	public String toString() {
		return amount + "x " + item.getName();
	}
	
	public String formatted() {
		return item.getName() + " - " + amount;
	}
	
	public final Item getItem() {
		return item;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void increment() {
		amount++;
	}
	
	public void decrement() {
		amount--;
	}
	
	public void add(int amount) {
		this.amount += amount;
	}
	
	public void subtract(int amount) {
		this.amount -= amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean empty() {
		if(getAmount() <= 0) {
			return true;
		} else {
			return false;
		}
	}
}
