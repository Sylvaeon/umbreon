package me.sylvaeon.umbreon.rpg.entity.player;

import me.sylvaeon.umbreon.Umbreon;
import me.sylvaeon.umbreon.rpg.entity.player.skill.SkillType;
import me.sylvaeon.umbreon.rpg.item.ItemStack;
import me.sylvaeon.umbreon.rpg.item.Items;
import me.sylvaeon.umbreon.rpg.item.equipable.tool.Axe;
import me.sylvaeon.umbreon.rpg.item.equipable.tool.Pickaxe;
import net.dv8tion.jda.core.entities.Member;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Players {
	public static Map<Member, Player> players;
	
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
	
	public static void initPlayer(Member member) {
		putPlayer(member, new Player(member.getUser().getName()));
	}

	private static void putPlayer(Member member, Player player) {
		if(!member.getUser().isBot() && !member.getUser().isFake()) {
			players.put(member, player);
		}
	}
	
	private static Player addPlayer(Member member) {
		Player player = new Player(member.getUser().getName());
		putPlayer(member, player);
		return player;
	}
	
	public static Player getPlayer(Member member) {
		return players.get(member);
	}
	
	private static Collection<Player> getPlayers() {
		return players.values();
	}
	
	public static void updatePeople() {
		for(Player player : getPlayers()) {
			player.update();
		}
	}
	
	public static void loadPlayers() {
		for(Member member : Umbreon.getGuild().getMembers()) {
			if(!member.getUser().isBot() && !member.getUser().isFake()) {
				loadPlayer(member);
			}
		}
	}
	
	public static void loadPlayer(Member member) {
		Player player = addPlayer(member);
		File file = getDataFile(member);
		JSONParser parser = new JSONParser();
		if(!file.exists()) {
			player.getInventory().setTools(null, null);
			savePlayer(member, false);
			loadPlayer(member);
		} else {
			try {
				Object obj = parser.parse(new FileReader(file));
				JSONObject jsonObject = (JSONObject) obj;
				int lvl = Math.toIntExact((long) jsonObject.get("lvl"));
				player.setLvl(lvl);
				int xp = Math.toIntExact((long) jsonObject.get("xp"));
				player.setXp(xp);
				JSONObject inventory = (JSONObject) jsonObject.get("inventory");
				for (Iterator iterator = inventory.keySet().iterator(); iterator.hasNext(); ) {
					String key = (String) iterator.next();
					int value = Math.toIntExact((long) inventory.get(key));
					player.getInventory().addItem(Items.getItem(key), value);
				}
				JSONObject skills = (JSONObject) jsonObject.get("skills");
				JSONObject xps = (JSONObject) skills.get("xps");
				JSONObject lvls = (JSONObject) skills.get("lvls");
				for (Iterator iterator = xps.keySet().iterator(); iterator.hasNext(); ) {
					String key = (String) iterator.next();
					int value = Math.toIntExact((long) xps.get(key));
					player.getSkillSet().getSkill(SkillType.valueOf(key)).setXp(value);
				}
				for (Iterator iterator = lvls.keySet().iterator(); iterator.hasNext(); ) {
					String key = (String) iterator.next();
					int value = Math.toIntExact((long) lvls.get(key));
					player.getSkillSet().getSkill(SkillType.valueOf(key)).setLvl(value);
				}

				if(jsonObject.containsKey("tools")) {
					JSONObject tools = (JSONObject) jsonObject.get("tools");
					Object pickaxeObject = tools.get("pickaxe"), axeObject = tools.get("axe");
					String pickaxeName, axeName;
					Pickaxe pickaxe = null;
					Axe axe = null;
					if (pickaxeObject != null) {
						pickaxeName = (String) tools.get("pickaxe");
						if (pickaxeName == "null") {
							pickaxe = null;
						} else {
							pickaxe = (Pickaxe) Items.getItem(pickaxeName);
						}
					}
					if (axeObject != null) {
						axeName = (String) tools.get("axe");
						if (axeName == "null") {
							axe = null;
						} else {
							axe = (Axe) Items.getItem(axeName);
						}
					}
					player.getInventory().setTools(pickaxe, axe);
				} else {
					player.getInventory().initTools();
				}

			} catch (ParseException | IOException | NullPointerException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void savePlayers() {
		for(Map.Entry<Member, Player> entry : players.entrySet()) {
			savePlayer(entry.getKey(), true);
		}
	}

	public static void savePlayer(Member member, boolean saveTools) {
		Player player = getPlayer(member);
		File file = getDataFile(member);
		if(file.exists()) {
			file.delete();
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("lvl", player.getLvl());
		jsonObject.put("xp", player.getXp());
		JSONObject skills = new JSONObject();
		JSONObject xps = new JSONObject();
		JSONObject lvls = new JSONObject();
		for(SkillType skillType : SkillType.values()) {
			xps.put(skillType.name(), player.getSkillSet().getSkill(skillType).getXp());
			lvls.put(skillType.name(), player.getSkillSet().getSkill(skillType).getLvl());
		}
		skills.put("xps", xps);
		skills.put("lvls", lvls);
		jsonObject.put("skills", skills);
		JSONObject inventory = new JSONObject();
		for (ItemStack itemStack : player.getInventory().getItemStacks()) {
			inventory.put(itemStack.getItem().getName(), itemStack.getAmount());
		}
		jsonObject.put("inventory", inventory);
		if(saveTools) {
			JSONObject tools = new JSONObject();
			Pickaxe pickaxe = player.getInventory().getPickaxe();
			if(pickaxe != null) {
				tools.put("pickaxe", pickaxe.getName());
			} else {
				tools.put("pickaxe", "null");
			}
			Axe axe = player.getInventory().getAxe();
			if(axe != null) {
				tools.put("axe", axe.getName());
			} else {
				tools.put("axe", "null");
			}
			jsonObject.put("tools", tools);
		}
		try(FileWriter fileWriter = new FileWriter(file)) {
			fileWriter.write(jsonObject.toJSONString());
			fileWriter.flush();
		} catch (IOException e) {

		}
	}

	private static File getDataFile(Member member) {
		return new File("src/main/resources/players/" + member.getUser().getId() + ".json");
	}
	
}
