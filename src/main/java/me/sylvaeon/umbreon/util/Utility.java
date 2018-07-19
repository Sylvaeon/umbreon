package me.sylvaeon.umbreon.util;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class Utility {
	private static JSONParser parser = new JSONParser();

	public static <T> T randomCollectionElement(Collection<T> collection) {
		return (T) collection.toArray()[ThreadLocalRandom.current().nextInt(collection.size())];
	}

    public static int nextInt(Random random, int origin, int bound) {
        int range = bound - origin;
        return random.nextInt(range) + origin;
    }

    public static <R extends Comparable, C extends Comparable, V> R getTableMaxRow(Table<R, C, V> table) {
        R max = null;
        for (Table.Cell<R, C, V> cell : table.cellSet()) {
            if (cell.getRowKey().compareTo(max) > 0 || max == null) {
                max = cell.getRowKey();
            }
        }
        return max;
    }

    public static <R extends Comparable, C extends Comparable, V> C getTableMaxColumn(Table<R, C, V> table) {
        C max = null;
        for (Table.Cell<R, C, V> cell : table.cellSet()) {
            if (cell.getColumnKey().compareTo(max) > 0 || max == null) {
                max = cell.getColumnKey();
            }
        }
        return max;
    }

    public static boolean inRange(double x, double min, double max) {
        return (x < max && x >= min);
    }

    public static <T> Table<Integer, Integer, T> nestedListToTable(List<List<T>> lists) {
        Table<Integer, Integer, T> table = HashBasedTable.create();
        List<T> list;
        for (int i = 0; i < lists.size(); i++) {
            list = lists.get(i);
            for (int j = 0; j < list.size(); j++) {
                table.put(i, j, list.get(j));
            }
        }
        return table;
    }

	public static String concatList(List<String> list) {
		StringBuilder stringBuilder = new StringBuilder();
		for(String s : list) {
			stringBuilder.append(s);
		}
		return stringBuilder.toString();
	}

	public static String concatArray(String[] list) {
		StringBuilder stringBuilder = new StringBuilder();
		for(String s : list) {
			stringBuilder.append(s);
		}
		return stringBuilder.toString();
	}

	public static String concatArray(String[] list, char c) {
		StringBuilder stringBuilder = new StringBuilder();
		for(String s : list) {
			stringBuilder.append(s).append(c);
		}
		return stringBuilder.reverse().deleteCharAt(0).reverse().toString();
	}
	
	public static String milisAsTimeStamp(long milis) {
		int seconds = (int) (milis / 1000) % 60;
		int minutes = seconds / 60;
		return minutes + ":" + seconds;
	}

	public static String formatEnum(Enum e) {
		String name = e.name();
		String[] split = name.split("_");
		for(int i = 0; i < split.length; i++) {
			split[i] = split[i].toLowerCase();
			split[i] = split[i].substring(0, 1).toUpperCase() + split[i].substring(1);
		}
		return concatArray(split, ' ');
	}

	public static String formatEnumName(String name) {
		String[] split = name.split("_");
		for(int i = 0; i < split.length; i++) {
			split[i] = split[i].toLowerCase();
			split[i] = split[i].substring(0, 1).toUpperCase() + split[i].substring(1);
		}
		return concatArray(split, ' ');
	}

	public static String getProgressBar(int lvl, int xp, int xpNeeded) {
		return lvl + " " + getProgressBar((double) xp / xpNeeded) + " " + (lvl + 1);
	}

	/**
	 * @param progress
	 * Should be a double at least 0 and less than 1
	 * @return
	 */
	public static String getProgressBar(double progress) {
		progress *= 10;
		progress = (int) Math.round(progress);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("-[");
		for(int i = 0; i < progress; i++) {
			stringBuilder.append("■");
		}
		for(int i = 0; i < 10 - progress; i++) {
			stringBuilder.append("□");
		}
		stringBuilder.append("]-");
		return stringBuilder.toString();
	}
	
	public static double clamp(double x, double min, double max) {
		return x < min ? min : (x > max ? max : x);
	}

	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(
			Math.pow((x1 - x2), 2) +
			Math.pow((y1 - y2), 2)
		);
	}

	public static double weightedAverage(double[] nums, double[] weights) {
		double total = 0;
		double weight = 0;
		for(double w : weights) {
			weight += w;
		}
		System.out.println(weight);
		for(int i = 0; i < weights.length; i++) {
			weights[i] /= weight;
		}
		for(int i = 0; i < nums.length; i++) {
			total += weights[i] + nums[i];
		}
		return total;
	}

	public static boolean intervalChance(double chance) {
		if(chance >= 1) {
			return true;
		} else if(chance <= 0) {
			return false;
		} else {
			return ThreadLocalRandom.current().nextDouble() <= chance;
		}
	}

	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static <K, V extends Comparable> Map<K, V> sortByValues(Map<K, V> tempMap) {
		TreeMap<K, V> map = new TreeMap<>(buildComparator(tempMap));
		map.putAll(tempMap);
		return map;
	}

	public static <K, V extends Comparable> Comparator<? super K> buildComparator(final Map<K, V> tempMap) {
        return (o1, o2) -> tempMap.get(o1).compareTo(tempMap.get(o2));
    }

	public static <T extends Comparable<T>> Map<T, Integer> collectionToAmountMap(Collection<T> list) {
    	Map<T, Integer> map = new TreeMap<>();
    	if(!list.isEmpty()) {
			for (T t : list) {
				if(t == null) {
					System.out.println("null");
				}
				map.putIfAbsent(t, 0);
				map.put(t, map.get(t) + 1);
			}
		}
		return map;
	}

	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}

	public static Color averageColors(Color c1, Color c2, double weight) {
		return new Color(
			(int) ((weight * c1.getRed()) + ((1 - weight) * c2.getRed())),
			(int) ((weight * c1.getGreen()) + ((1 - weight) * c2.getGreen())),
			(int) ((weight * c1.getBlue()) + ((1 - weight) * c2.getBlue()))
		);
	}

	public static Color averageColors(Color[] colors, double[] weights) {
		Color finalColor;
		int[][] rgbs = new int[3][colors.length];
		for(int i = 0; i < colors.length; i++) {
			rgbs[0][i] = colors[i].getRed();
			rgbs[1][i] = colors[i].getGreen();
			rgbs[2][i] = colors[i].getBlue();
		}
		System.out.println((float) weightedAverage(castToDoubleArray(rgbs[0]), weights) / 255f);
		System.out.println((float) weightedAverage(castToDoubleArray(rgbs[1]), weights) / 255f);
		System.out.println((float) weightedAverage(castToDoubleArray(rgbs[2]), weights) / 255f);
		finalColor = new Color(
			(float) weightedAverage(castToDoubleArray(rgbs[0]), weights) / 255f,
			(float) weightedAverage(castToDoubleArray(rgbs[1]), weights) / 255f,
			(float) weightedAverage(castToDoubleArray(rgbs[2]), weights) / 255f
		);
		return finalColor;
	}

	public static String getColorFromCode(String colorCode) {
		Object object;
		try {
			object = parser.parse(new FileReader("src/main/resources/colors/color-codes.json"));
			JSONObject colors = (JSONObject) object;
			int hex = 0x0;
			if(colors.containsKey(colorCode.toLowerCase())) {
				String string = ((String) colors.get(colorCode.toLowerCase())).substring(1);
				hex = Integer.parseUnsignedInt(string, 16);
			}
			return String.format("%06X", hex);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return colorCode;
	}

	public static Color getPinkGradientColor(int n) {
		n = (int) clamp(n, 0, 14);
		int[] colorInts = new int[] {
				0x880088, 0x990099, 0xAA00AA, 0xBB00BB, 0xCC00CC, 0xDD00DD, 0xEE00EE, 0xFF00FF, 0xEE00EE, 0xDD00DD, 0xCC00CC, 0xBB00BB, 0xAA00AA, 0x990099
		};
		return new Color(colorInts[n]);
	}

	public static double[] castToDoubleArray(int[] array) {
		double[] finalArray = new double[array.length];
		for(int i = 0; i < array.length; i++) {
			finalArray[i] = (double) array[i];
		}
		return finalArray;
	}

}
