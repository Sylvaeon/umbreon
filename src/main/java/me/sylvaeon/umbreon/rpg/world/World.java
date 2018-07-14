package me.sylvaeon.umbreon.rpg.world;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import me.sylvaeon.umbreon.Google;
import me.sylvaeon.umbreon.Saving;
import me.sylvaeon.umbreon.Utility;
import me.sylvaeon.umbreon.rpg.world.entity.AnimalSpecies;
import me.sylvaeon.umbreon.rpg.world.entity.PlantSpecies;
import me.sylvaeon.umbreon.rpg.world.entity.Species;
import me.sylvaeon.umbreon.rpg.world.entity.TreeSpecies;

import java.io.File;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class World {

	private static Table<Integer, Integer, Tile> map;
	private static final File DIR = new File("src/main/resources/tiles");
	private static Map<String, AnimalSpecies> animalSpecies;
	private static Map<String, PlantSpecies> plantSpecies;
	private static Map<String, TreeSpecies> treeSpecies;
	private static Table<String, Tile.Biome, Set<Species>> speciesByBiome;
	private static Table<String, Tile.Feature, Set<Species>> speciesByFeature;

	private static final String[] KEYS = new String[] {
		"animals", "plants", "trees"
	};

	public static void init() {
		if(!DIR.exists()) {
			DIR.mkdirs();
		}

		speciesByBiome = HashBasedTable.create();
		speciesByFeature = HashBasedTable.create();
		map = HashBasedTable.create();

		for(String key : KEYS) {
			for(Tile.Biome biome : Tile.Biome.values()) {
				speciesByBiome.put(key, biome, new HashSet<>());
			}
			for(Tile.Feature feature : Tile.Feature.values()) {
				speciesByFeature.put(key, feature, new HashSet<>());
			}
		}
		initSpecies();
		loadTiles();
	}

	public static void loadTiles() {
		Tile tile;
		String[] split;
		int x, y;
		if(DIR.listFiles().length == 0) {
			loadTile(0, 0);
			saveTiles();
		} else {
			for (File file : DIR.listFiles()) {
				tile = (Tile) Saving.readObject(file.getPath());
				split = file.getName().split("\\.")[0].split("_");
				x = Integer.parseInt(split[0]);
				y = Integer.parseInt(split[1]);
				map.put(x, y, tile);
			}
		}
	}

	public static void saveTiles() {
		for(Table.Cell<Integer, Integer, Tile> cell : map.cellSet()) {
			saveTile(cell.getRowKey(), cell.getColumnKey());
		}
	}
	
	public static void saveTile(int x, int y) {
		String path = DIR.getPath() + "/" + x + "_" + y + ".tile";
		Saving.saveObject(getTile(x, y), path);
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
		Tile tile = new Tile(Tile.Biome.values()[ThreadLocalRandom.current().nextInt(Tile.Biome.values().length)]);
		map.put(x, y, tile);
		saveTile(x, y);
		return tile;
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
		while(true) {
			species = (AnimalSpecies) Utility.randomCollectionElement(speciesByBiome.get("animals", tile.getBiome()));
			if (species.getStandards().getFeature() == null) {
				return species;
			} else if(species.getStandards().getFeature() == tile.getFeature()) {
				return species;
			} else {
				continue;
			}
		}
	}

	public static PlantSpecies getRandomPlantSpecies(Tile tile) {
		PlantSpecies species;
		while(true) {
			species = (PlantSpecies) Utility.randomCollectionElement(speciesByBiome.get("plants", tile.getBiome()));
			if (species.getStandards().getFeature() == null) {
				return species;
			} else if(species.getStandards().getFeature() == tile.getFeature()) {
				return species;
			} else {
				continue;
			}
		}
	}

	public static TreeSpecies getRandomTreeSpecies(Tile tile) {
		TreeSpecies species;
		while(true) {
			species = (TreeSpecies) Utility.randomCollectionElement(speciesByBiome.get("trees", tile.getBiome()));
			if (species.getStandards().getFeature() == null) {
				return species;
			} else if(species.getStandards().getFeature() == tile.getFeature()) {
				return species;
			} else {
				continue;
			}
		}
	}

	public static void addAnimalSpecies(String name, boolean elemental, boolean nightOnly, boolean unique, boolean hostile, boolean canBeTamed, Standards standards) {
		AnimalSpecies species = new AnimalSpecies(name, elemental, nightOnly, unique, hostile, canBeTamed, standards);
		for(Tile.Biome biome : standards.biomes) {
			speciesByBiome.get("animals", biome).add(species);
		}
		if(standards.feature != null) {
			speciesByFeature.get("animals", standards.feature).add(species);
		}
		animalSpecies.putIfAbsent(name, species);
	}

	public static void addPlantSpecies(String name, PlantSpecies.PlantType type, Standards standards) {
		PlantSpecies species = new PlantSpecies(name, type, standards);
		for(Tile.Biome biome : standards.biomes) {
			speciesByBiome.get("plants", biome).add(species);
		}
		if(standards.feature != null) {
			speciesByFeature.get("plants", standards.feature).add(species);
		}
		plantSpecies.putIfAbsent(name, species);
	}

	public static void addTreeSpecies(String name, Standards standards) {
		TreeSpecies species = new TreeSpecies(name, standards);
		for(Tile.Biome biome : standards.biomes) {
			speciesByBiome.get("trees", biome).add(species);
		}
		if(standards.feature != null) {
			speciesByFeature.get("trees", standards.feature).add(species);
		}
		treeSpecies.putIfAbsent(name, species);
	}

	private static Tile.Feature getFeatureFromName(String name) {
		if (name == "" || name == null) {
			return null;
		} else {
			return Tile.Feature.valueOf(name.toUpperCase());
		}
	}

	public static Tile getTile(int x, int y) {
		if(!map.contains(x, y)) {
			generateTile(x, y);
		}
		return map.get(x, y);
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

	
	
}
