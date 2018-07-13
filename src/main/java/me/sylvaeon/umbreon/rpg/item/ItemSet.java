package me.sylvaeon.umbreon.rpg.item;

import me.sylvaeon.umbreon.Counter;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class ItemSet extends TreeMap<Item, Counter> {
	public ItemSet() {
		super();
	}

	public long total() {
		long total = 0;
		for(Counter counter : values()) {
			total += counter.count;
		}
		return total;
	}

	public void add(Item item, long l) {
		if(containsKey(item)) {
			get(item).add(l);
		} else {
			put(item, l);
		}
	}

	public void add(Item... items) {
		for(Item item : items) {
			add(item, 1);
		}
	}

	public void add(Collection<Item> items) {
		for(Item item : items) {
			add(item);
		}
	}

	public void add(ItemSet itemSet) {
		for(Map.Entry<Item, Counter> entry : itemSet.entrySet()) {
			add(entry.getKey(), entry.getValue().count);
		}
	}

	public boolean subtract(Item item) {
		return subtract(item, 1);
	}

	public boolean subtract(Item item, long l) {
		if(containsKey(item)) {
			if(get(item).count >= l) {
				get(item).subtract(l);
				removeIfEmpty(item);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean removeEmptys() {
		boolean changed = false;
		for(Item item : keySet()) {
			changed = removeIfEmpty(item) || changed;
		}
		return changed;
	}

	public boolean removeIfEmpty(Item item) {
		if(!get(item).positive()) {
			remove(item);
			return true;
		} else {
			return false;
		}
	}

	public void put(Item item, long l) {
		put(item, new Counter(l));
	}

}
