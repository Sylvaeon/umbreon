package me.sylvaeon.umbreon.maze;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Maze {
	int x, y;
	private int[][] set;
	private boolean[][] bottom;
	private boolean[][] right;
	
	public static void main(String[] args) {
		Maze maze = new Maze(20, 20);
		maze.generate();
	}
	
	public Maze(int x, int y) {
		this.x = x;
		this.y = y;
		set = new int[x][y];
		bottom = new boolean[x][y];
		right = new boolean[x][y];
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				bottom[i][j] = ThreadLocalRandom.current().nextBoolean();
				right[i][j] = ThreadLocalRandom.current().nextBoolean();
			}
		}
	}
	
	public void generate() {
		int width = 8*x + 1, height = 8*y + 1;
		File file = new File("src/main/resources/maze/maze.png");
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				bufferedImage.setRGB(i, j, Color.WHITE.getRGB());
			}
		}
		try {
			ImageIO.write(bufferedImage, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void solve() {
	
	}
}
