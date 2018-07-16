package me.sylvaeon.umbreon.rpg.world.entity.player;

import me.sylvaeon.umbreon.Saving;
import me.sylvaeon.umbreon.Umbreon;
import net.dv8tion.jda.core.entities.User;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Players {
	public static Map<User, Player> players;
	
	public static void init() {
		players = new HashMap<>();
		File file = new File("src/main/resources/players/");
		if(!file.exists()) {
			file.mkdirs();
		}
		loadPlayers();
	}

	public static void close() {
		savePlayers();
	}
	
	public static void initPlayer(User user) {
		putPlayer(user, new Player(user.getName()));
	}

	private static void putPlayer(User user, Player player) {
		if(!user.isBot() && !user.isFake()) {
			players.put(user, player);
		}
	}
	
	public static Player getPlayer(User user) {
		return players.get(user);
	}
	
	private static Collection<Player> getPlayers() {
		return players.values();
	}
	
	public static void updatePeople() {
		for(Player player : getPlayers()) {
			player.update();
		}
	}

	private static String filename(User user) {
		return "src/main/resources/players/" + user.getId() + ".player";
	}

	public static void loadPlayer(User user) {
		Object object = Saving.readObject(filename(user));
		Player player;
		if(object == null) {
			player = new Player(user.getName());
			savePlayer(user);
		} else {
			player = (Player) object;
		}
		putPlayer(user, player);
	}

	public static void savePlayer(User user) {
		Player player = getPlayer(user);
		Saving.saveObject(player, filename(user));
	}

	public static void savePlayers() {
		for(Map.Entry<User, Player> entry : players.entrySet()) {
			savePlayer(entry.getKey());
		}
	}

	public static void loadPlayers() {
		for(User user : Umbreon.getJda().getUsers()) {
			if(!user.isBot() && !user.isFake()) {
				loadPlayer(user);
			}
		}
	}

}
