package me.sylvaeon.umbreon.helper;

import java.util.Random;

public class ArrayHelper {
	static double[] castToDoubleArray(int[] array) {
		double[] finalArray = new double[array.length];
		for(int i = 0; i < array.length; i++) {
			finalArray[i] = (double) array[i];
		}
		return finalArray;
	}

	public static <T extends Object> T getRandomArrayElement(T[] array) {
		Random random = new Random();
		return array[random.nextInt(array.length)];
	}
}
