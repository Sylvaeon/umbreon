package me.sylvaeon.umbreon.rpg.world;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import me.sylvaeon.umbreon.Google;
import me.sylvaeon.umbreon.rpg.world.entity.AnimalSpecies;

import java.util.*;

import static me.sylvaeon.umbreon.Utility.clamp;
import static me.sylvaeon.umbreon.Utility.inRange;

public class World {

    public static long seed = -1;
    public static Table<Integer, Integer, Tile> map;
    private static Map<String, AnimalSpecies> speciesMap;

    public static void init() {
        initSpecies();
        if (seed == -1) {
            seed = System.currentTimeMillis();
        }
        Random random = new Random(seed);
        map = HashBasedTable.create();
        Feature[] features = Feature.values();
        Biome[] biomes = Biome.values();
        for (int x = -1000; x <= 1000; x++) {
            for (int y = -1000; y <= 1000; y++) {
                //map.put(x, y, new Tile())
            }
        }
    }

    private static void initSpecies() {
        speciesMap = new HashMap<>();

        List<List<Object>> lists = Google.getSpreadsheet("Animals");
        List<Object> list;
        for (int i = 0; i < lists.size(); i++) {
            list = lists.get(i);
            for (int j = 0; j < 12; j++) {
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

            addSpecies((String) list.get(0),
                    ((String) list.get(1)).equalsIgnoreCase("Y"),
                    ((String) list.get(2)).equalsIgnoreCase("Y"),
                    ((String) list.get(3)).equalsIgnoreCase("Y"),
                    ((String) list.get(4)).equalsIgnoreCase("Y"),
                    ((String) list.get(5)).equalsIgnoreCase("Y"),
                    standards
            );
        }

        System.out.println(getSpecies("Armadillo").getStandards());
    }

    private static World.Feature getFeatureFromName(String name) {
        if (name == "" || name == null) {
            return null;
        } else {
            return World.Feature.valueOf(name.toUpperCase());
        }
    }

    public static void addSpecies(String name, boolean elemental, boolean nightOnly, boolean unique, boolean hostile, boolean canBeTamed, Standards standards) {
        speciesMap.putIfAbsent(name, new AnimalSpecies(name, elemental, nightOnly, unique, hostile, canBeTamed, standards));
    }

    public static AnimalSpecies getSpecies(String name) {
        return speciesMap.get(name);
    }

    public static Set<Map.Entry<String, AnimalSpecies>> getEntrySet() {
        return speciesMap.entrySet();
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
