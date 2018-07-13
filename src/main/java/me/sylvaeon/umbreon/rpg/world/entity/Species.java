package me.sylvaeon.umbreon.rpg.world.entity;

import me.sylvaeon.umbreon.rpg.world.Standards;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public abstract class Species implements Serializable, Comparable<Species> {
	private static final long serialVersionUID = 4L;

	protected String name;
	protected Standards standards;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Standards getStandards() {
        return standards;
    }

    public void setStandards(Standards standards) {
        this.standards = standards;
    }
	
	@Override
	public int compareTo(@NotNull Species o) {
		return name.compareTo(o.getName());
	}
}
