package me.sylvaeon.umbreon.helper;

import java.util.concurrent.ThreadLocalRandom;

public class MathHelper {
	static double clamp(double x, double min, double max) {
		return x < min ? min : (x > max ? max : x);
	}
	
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(
			Math.pow((x1 - x2), 2) +
			Math.pow((y1 - y2), 2)
		);
	}

	static double weightedAverage(double[] nums, double[] weights) {
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
}
