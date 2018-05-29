package me.sylvaeon.umbreon.helper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.Color;
import java.io.FileReader;
import java.io.IOException;

public class ColorHelper {
	private static JSONParser parser = new JSONParser();
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
		System.out.println((float) MathHelper.weightedAverage(ArrayHelper.castToDoubleArray(rgbs[0]), weights) / 255f);
		System.out.println((float) MathHelper.weightedAverage(ArrayHelper.castToDoubleArray(rgbs[1]), weights) / 255f);
		System.out.println((float) MathHelper.weightedAverage(ArrayHelper.castToDoubleArray(rgbs[2]), weights) / 255f);
		finalColor = new Color(
			(float) MathHelper.weightedAverage(ArrayHelper.castToDoubleArray(rgbs[0]), weights) / 255f,
			(float) MathHelper.weightedAverage(ArrayHelper.castToDoubleArray(rgbs[1]), weights) / 255f,
			(float) MathHelper.weightedAverage(ArrayHelper.castToDoubleArray(rgbs[2]), weights) / 255f
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
		n = (int) MathHelper.clamp(n, 0, 14);
		int[] colorInts = new int[] {
				0x880088, 0x990099, 0xAA00AA, 0xBB00BB, 0xCC00CC, 0xDD00DD, 0xEE00EE, 0xFF00FF, 0xEE00EE, 0xDD00DD, 0xCC00CC, 0xBB00BB, 0xAA00AA, 0x990099
		};
		return new Color(colorInts[n]);
	}
}
