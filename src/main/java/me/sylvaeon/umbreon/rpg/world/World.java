package me.sylvaeon.umbreon.rpg.world;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import me.sylvaeon.umbreon.Google;
import me.sylvaeon.umbreon.Utility;
import me.sylvaeon.umbreon.rpg.world.entity.AnimalSpecies;
import me.sylvaeon.umbreon.rpg.world.entity.PlantSpecies;
import me.sylvaeon.umbreon.rpg.world.entity.Species;
import me.sylvaeon.umbreon.rpg.world.entity.TreeSpecies;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static me.sylvaeon.umbreon.Utility.clamp;
import static me.sylvaeon.umbreon.Utility.inRange;

public class World {

	public static Table<Integer, Integer, Tile> map;
	private static final File file = new File("src/main/resources/world_map.json");
	private static Map<String, AnimalSpecies> animalSpecies;
	private static Map<String, PlantSpecies> plantSpecies;
	private static Map<String, TreeSpecies> treeSpecies;
	private static Table<String, Biome, Set<Species>> speciesByBiome;
	private static Table<String, Feature, Set<Species>> speciesByFeature;

	private static final String[] KEYS = new String[] {
		"animals", "plants", "trees"
	};

	public static void init() {
		speciesByBiome = HashBasedTable.create();
		speciesByFeature = HashBasedTable.create();
		map = HashBasedTable.create();

		for(String key : KEYS) {
			for(Biome biome : Biome.values()) {
				speciesByBiome.put(key, biome, new HashSet<>());
			}
			for(Feature feature : Feature.values()) {
				speciesByFeature.put(key, feature, new HashSet<>());
			}
		}
		initSpecies();
		loadTiles();
	}

	public static void close() {
		saveTiles();
	}

	private static void initSpecies() {
		initAnimalSpecies();
		initPlantSpecies();
		initTreeSpecies();
	}

	private static void initAnimalSpecies() {
		animalSpecies = new HashMap<>();

		List<List<Object>> lists = Google.getSpreadsheet("Animals");
		List<Object> list;
		int max = maxListSize(lists);
		for (int i = 0; i < lists.size(); i++) {
			list = lists.get(i);
			for (int j = 0; j < max; j++) {
				try {
					list.get(j);
				} catch (IndexOutOfBoundsException e) {
					list.add("");
				}
			}
		}

		Standards standards;

		for (int i = 1; i < lists.size(); i++) {
			list = lists.get(i);
			if (((String) list.get(7)).startsWith("!")) {
				standards = Standards.ALL();
				standards.removeBiome(((String) list.get(7)).replaceAll("!", ""));
				standards.removeBiome(((String) list.get(8)).replaceAll("!", ""));
				standards.removeBiome(((String) list.get(9)).replaceAll("!", ""));
				standards.removeBiome(((String) list.get(10)).replaceAll("!", ""));
				standards.removeBiome(((String) list.get(11)).replaceAll("!", ""));
			} else {
				standards = Standards.NONE();
				standards.addBiome((String) list.get(7));
				standards.addBiome((String) list.get(8));
				standards.addBiome((String) list.get(9));
				standards.addBiome((String) list.get(10));
				standards.addBiome((String) list.get(11));
			}
			standards.setFeature(getFeatureFromName((String) list.get(6)));

			addAnimalSpecies((String) list.get(0),
					((String) list.get(1)).equalsIgnoreCase("Y"),
					((String) list.get(2)).equalsIgnoreCase("Y"),
					((String) list.get(3)).equalsIgnoreCase("Y"),
					((String) list.get(4)).equalsIgnoreCase("Y"),
					((String) list.get(5)).equalsIgnoreCase("Y"),
					standards
			);
		}
	}

	private static void initPlantSpecies() {
		plantSpecies = new HashMap<>();

		List<List<Object>> lists = Google.getSpreadsheet("Foraging");
		List<Object> list;
		int max = maxListSize(lists);
		for (int i = 0; i < lists.size(); i++) {
			list = lists.get(i);
			for (int j = 0; j < max; j++) {
				try {
					list.get(j);
				} catch (IndexOutOfBoundsException e) {
					list.add("");
				}
			}
		}

		Standards standards;
		for (int i = 1; i < lists.size(); i++) {
			list = lists.get(i);
			PlantSpecies.PlantType type = PlantSpecies.PlantType.valueOf(((String) list.get(1)).toUpperCase());
			String name = (String) list.get(0);
			if (((String) list.get(3)).startsWith("!")) {
				standards = Standards.ALL();
				standards.removeBiome(((String) list.get(3)).replaceAll("!", ""));
				standards.removeBiome(((String) list.get(4)).replaceAll("!", ""));
				standards.removeBiome(((String) list.get(5)).replaceAll("!", ""));
			} else {
				standards = Standards.NONE();
				standards.addBiome((String) list.get(3));
				standards.addBiome((String) list.get(4));
				standards.addBiome((String) list.get(5));
			}
			standards.setFeature(getFeatureFromName((String) list.get(2)));
			addPlantSpecies(name, type, standards);
		}
	}

	private static void initTreeSpecies() {
		treeSpecies = new HashMap<>();

		List<List<Object>> lists = Google.getSpreadsheet("Logging");
		List<Object> list;
		int max = maxListSize(lists);
		for (int i = 0; i < lists.size(); i++) {
			list = lists.get(i);
			for (int j = 0; j < max; j++) {
				list.add("");
			}
		}
		Standards standards;
		for (int i = 1; i < lists.size(); i++) {
			list = lists.get(i);
			if (((String) list.get(1)).startsWith("!")) {
				standards = Standards.ALL();
				standards.removeBiome(((String) list.get(1)).replaceAll("!", ""));
			} else {
				standards = Standards.NONE();
				standards.addBiome((String) list.get(1));
			}
			addTreeSpecies((String) list.get(0), standards);
		}
	}

	public static Tile loadTile(int x, int y) {
		if (map.contains(x, y)) {
			return map.get(x, y);
		} else {
			return generateTile(x, y);
		}
	}

	public static Tile generateTile(int x, int y) {
		Tile tile = new Tile(Biome.getBiomeFromClimate(ThreadLocalRandom.current().nextInt(100), ThreadLocalRandom.current().nextInt(100)));
		map.put(x, y, tile);
		return tile;
	}

	public static void loadTiles() {
		JSONParser parser = new JSONParser();
		if (!file.exists()) {
			loadTile(0, 0);
			saveTiles();
		} else {
			try {
				Object object = parser.parse(new FileReader(file));
				JSONObject jsonObject = (JSONObject) object;
				for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext(); ) {
					String key = (String) iterator.next();
					JSONObject value = (JSONObject) jsonObject.get(key);
					Tile tile = JSONObjectToTile(value);
					String[] split = key.split(",");
					int x = Integer.parseUnsignedInt(split[0]);
					int y = Integer.parseUnsignedInt(split[1]);
					map.put(x, y, tile);
				}
			} catch (ParseException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void saveTiles() {
		if(file.exists()) {
			file.delete();
		}
		JSONObject jsonObject = new JSONObject();
		for(Table.Cell<Integer, Integer, Tile> cell : map.cellSet()) {
			String key = cell.getRowKey() + "," + cell.getColumnKey();
			JSONObject value = tileToJSONObject(cell.getValue());
			jsonObject.put(key, value);
		}
		try(FileWriter fileWriter = new FileWriter(file)) {
			fileWriter.write(jsonObject.toJSONString());
			fileWriter.flush();
		} catch (IOException e) {

		}
	}

	private static Tile JSONObjectToTile(JSONObject jsonObject) {
		Set<AnimalSpecies> animals = new HashSet<>();
		Set<PlantSpecies> plants = new HashSet<>();
		JSONArray animalArray = (JSONArray) jsonObject.get("animals");
		for(Object object : animalArray) {
			animals.add(getAnimalSpecies((String) object));
		}
		JSONArray plantArray = (JSONArray) jsonObject.get("plants");
		for(Object object : plantArray) {
			plants.add(getPlantSpecies((String) object));
		}
		Feature feature = null;
		if(jsonObject.containsKey("feature")) {
			feature = Feature.valueOf(((String) jsonObject.get("feature")).toUpperCase());
		}
		Biome biome = Biome.valueOf(((String) jsonObject.get("biome")).toUpperCase());
		TreeSpecies treeSpecies = getTreeSpecies((String) jsonObject.get("tree"));
		return new Tile(feature, biome, animals, plants, treeSpecies);
	}

	private static JSONObject tileToJSONObject(Tile tile) {
		JSONObject jsonObject = new JSONObject();
		JSONArray animals = new JSONArray();
		for (AnimalSpecies species : tile.getAnimals()) {
			animals.add(species.getName());
		}
		JSONArray plants = new JSONArray();
		for (PlantSpecies species : tile.getPlants()) {
			plants.add(species.getName());
		}
		jsonObject.put("biome", tile.getBiome().name());
		if(tile.getFeature() != null) {
			jsonObject.put("feature", tile.getFeature().name());
		}
		jsonObject.put("animals", animals);
		jsonObject.put("plants", plants);
		jsonObject.put("tree", tile.getTreeSpecies().getName());
		return jsonObject;
	}

	private static int maxListSize(List<List<Object>> lists) {
		int max = 0;
		for (int i = 0; i < lists.size(); i++) {
			if (lists.get(i).size() > max) {
				max = lists.get(i).size();
			}
		}
		return max;
	}

	public static AnimalSpecies getRandomAnimalSpecies(Tile tile) {
		AnimalSpecies species;
		do {
			species = (AnimalSpecies) Utility.randomCollectionElement(speciesByBiome.get("animals", tile.getBiome()));
			if (tile.getFeature() == null) {
				return species;
			}
		} while (!speciesByFeature.get("animals", tile.getFeature()).contains(species));
		return species;
	}

	public static PlantSpecies getRandomPlantSpecies(Tile tile) {
		PlantSpecies species;
		do {
			species = (PlantSpecies) Utility.randomCollectionElement(speciesByBiome.get("plants", tile.getBiome()));
			if(tile.getFeature() == null) {
				return species;
			}
		} while (!speciesByFeature.get("plants", tile.getFeature()).contains(species));
		return species;
	}

	public static TreeSpecies getRandomTreeSpecies(Tile tile) {
		TreeSpecies species;
		do {
			species = (TreeSpecies) Utility.randomCollectionElement(speciesByBiome.get("trees", tile.getBiome()));
			if(tile.getFeature() == null) {
				return species;
			}
		} while (!speciesByFeature.get("trees", tile.getFeature()).contains(species));
		return species;
	}

	public static void addAnimalSpecies(String name, boolean elemental, boolean nightOnly, boolean unique, boolean hostile, boolean canBeTamed, Standards standards) {
		AnimalSpecies species = new AnimalSpecies(name, elemental, nightOnly, unique, hostile, canBeTamed, standards);
		for(Biome biome : standards.biomes) {
			speciesByBiome.get("animals", biome).add(species);
		}
		if(standards.feature != null)
			speciesByFeature.get("animals", standards.feature).add(species);
		animalSpecies.putIfAbsent(name, species);
	}

	public static void addPlantSpecies(String name, PlantSpecies.PlantType type, Standards standards) {
		PlantSpecies species = new PlantSpecies(name, type, standards);
		for(Biome biome : standards.biomes)
			speciesByBiome.get("plants", biome).add(species);
		if(standards.feature != null)
			speciesByFeature.get("plants", standards.feature).add(species);
		plantSpecies.putIfAbsent(name, species);
	}

	public static void addTreeSpecies(String name, Standards standards) {
		TreeSpecies species = new TreeSpecies(name, standards);
		for(Biome biome : standards.biomes) {
			speciesByBiome.get("trees", biome).add(species);
		}
		if(standards.feature != null)
			speciesByFeature.get("trees", standards.feature).add(species);
		treeSpecies.putIfAbsent(name, species);
	}

	private static World.Feature getFeatureFromName(String name) {
		if (name == "" || name == null) {
			return null;
		} else {
			return World.Feature.valueOf(name.toUpperCase());
		}
	}

	public static AnimalSpecies getAnimalSpecies(String name) {
		return animalSpecies.get(name);
	}

	public static PlantSpecies getPlantSpecies(String name) {
		return plantSpecies.get(name);
	}

	public static TreeSpecies getTreeSpecies(String name) {
		return treeSpecies.get(name);
	}

	public enum Biome {
		TUNDRA(0, 20, 0, 40), TAIGA(0, 20, 40, 100), DESERT(20, 100, 0, 10),
		GRASSLAND(20, 100, 10, 20), SHRUBLAND(20, 100, 20, 40), SAVANNA(20, 100, 40, 50),
		FOREST(20, 100, 50, 70), SWAMP(20, 100, 70, 80), TROPICS(20, 100, 80, 100),
		OCEAN(101, 101, 101, 101);

		int temperatureMin, temperatureMax, precipitationMin, precipitationMax;

		Biome(int temperatureMin, int temperatureMax, int precipitationMin, int precipitationMax) {
			this.temperatureMin = temperatureMin;
			this.temperatureMax = temperatureMax;
			this.precipitationMin = precipitationMin;
			this.precipitationMax = precipitationMax;
		}

		public static Biome getBiomeFromClimate(double temperature, double precipitation) {
			temperature = clamp(temperature, 0, 100);
			precipitation = clamp(precipitation, 0, 100);
			for (Biome biome : values()) {
				if (inRange(temperature, biome.temperatureMin, biome.temperatureMax) && inRange(precipitation, biome.precipitationMin, biome.precipitationMax)) {
					return biome;
				}
			}
			return null;
		}

	}

	public enum Feature {
		MOUNTAIN, RAVINE, RIVER, RUINS
	}

}
