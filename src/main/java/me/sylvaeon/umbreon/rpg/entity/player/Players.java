package me.sylvaeon.umbreon.rpg.entity.player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import me.sylvaeon.umbreon.Umbreon;
import me.sylvaeon.umbreon.rpg.entity.player.skill.SkillType;
import me.sylvaeon.umbreon.rpg.item.Item;
import me.sylvaeon.umbreon.rpg.item.ItemStack;
import me.sylvaeon.umbreon.rpg.item.Items;
import me.sylvaeon.umbreon.rpg.item.tool.Tool;
import me.sylvaeon.umbreon.rpg.item.tool.ToolType;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Players {
	public static Map<Member, Player> players;
	
	public static void init() {
		players = new HashMap<>();
		for(Member member : Umbreon.guild.getMembers()) {
			addPlayer(member);
		}
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

	public static void addXp(Member member) {
		if(!member.getUser().isBot()) {
			getPlayer(member).addXp(1);
		}
	}
	
	public static void addXp(Member member, TextChannel textChannel) {
		if(!member.getUser().isBot()) {
			getPlayer(member).addXp(textChannel, 1);
		}
	}
	
	public static void addXp(Member member, int xp) {
		if(!member.getUser().isBot()) {
			getPlayer(member).addXp(xp);
		}
	}
	
	public static void addXp(Member member, TextChannel textChannel, int xp) {
		if(!member.getUser().isBot()) {
			getPlayer(member).addXp(textChannel, xp);
		}
	}
	
	private static void addPlayer(Member member) {
		putPlayer(member, new Player(member.getUser().getName()));
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
		for(Map.Entry<Member, Player> entry : players.entrySet()) {
			loadPlayer(entry.getKey());
		}
	}
	
	public static void loadPlayer(Member member) {
		Player player = getPlayer(member);
		File file = getDataFile(member);
		JSONParser parser = new JSONParser();
		if(!file.exists()) {
			savePlayer(member);
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
					player.addItem(Items.getItem(key), value);
				}
				JSONObject skills = (JSONObject) jsonObject.get("skills");
				JSONObject xps = (JSONObject) skills.get("xps");
				JSONObject lvls = (JSONObject) skills.get("lvls");
				for (Iterator iterator = xps.keySet().iterator(); iterator.hasNext(); ) {
					String key = (String) iterator.next();
					int value = Math.toIntExact((long) xps.get(key));
					player.setXp(SkillType.valueOf(key), value);
				}
				for (Iterator iterator = lvls.keySet().iterator(); iterator.hasNext(); ) {
					String key = (String) iterator.next();
					int value = Math.toIntExact((long) lvls.get(key));
					player.setLvl(SkillType.valueOf(key), value);
				}
				JSONObject tools = (JSONObject) jsonObject.get("tools");
				for (Iterator iterator = tools.keySet().iterator(); iterator.hasNext(); ) {
					String key = (String) iterator.next();
					if(key.equalsIgnoreCase("UTENSIL")) {
						key = "CAULDRON";
					} else if(key.equalsIgnoreCase("MORTAR_AND_PESTLE")) {
						key = "ALCHEMIC_TOOLS";
					}
					int value = Math.toIntExact((long) tools.get(key));
					player.setToolMaterial(ToolType.valueOf(key), value);
				}
			} catch (ParseException | IOException | NullPointerException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void savePlayers() {
		for(Map.Entry<Member, Player> entry : players.entrySet()) {
			savePlayer(entry.getKey());
		}
	}
	
	public static void savePlayer(Member member) {
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
			xps.put(skillType.name(), player.getXp(skillType));
			lvls.put(skillType.name(), player.getLvl(skillType));
		}
		skills.put("xps", xps);
		skills.put("lvls", lvls);
		jsonObject.put("skills", skills);
		JSONObject inventory = new JSONObject();
		for(ItemStack itemStack : player.getInventory()) {
			inventory.put(itemStack.getItem().getName(), itemStack.getAmount());
		}
		jsonObject.put("inventory", inventory);
		JSONObject tools = new JSONObject();
		for(Tool tool : player.getToolSet().getTools()) {
			tools.put(tool.getType(), tool.getMaterial());
		}
		jsonObject.put("tools", tools);
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
