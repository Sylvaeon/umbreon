package me.sylvaeon.umbreon.rpg.world.entity.kingdom;

import me.sylvaeon.umbreon.Umbreon;
import net.dv8tion.jda.core.entities.Guild;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Kingdoms {
	private static Map<Guild, Kingdom> kingdoms;
	
	public static void init() {
		kingdoms = new LinkedHashMap<>(Umbreon.getJda().getGuilds().size());
	}
}
