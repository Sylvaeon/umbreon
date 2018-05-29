package me.sylvaeon.umbreon.rpg.item;

public interface Equipable {
	default int getHands() {
		return 1;
	}
}
