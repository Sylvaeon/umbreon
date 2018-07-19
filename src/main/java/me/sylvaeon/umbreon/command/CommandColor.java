package me.sylvaeon.umbreon.command;

import me.sylvaeon.umbreon.util.Utility;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CommandColor extends Command {
	@Override
	public void onCall(String[] args, User user, MessageChannel messageChannel) {
		if(args.length == 0) {
			
			return;
		}
		boolean quad = false;
		int tiles = 0;
		boolean caching = false;
		List<String> colorArgs = new ArrayList<>();
		Random rand = new Random();
		for(String arg : args) {
			arg = arg.toLowerCase();
			if(arg.equals("?"))
				colorArgs.add(String.format("%06X", rand.nextInt(0x1000000)));
			else if(arg.equalsIgnoreCase("-quad") || arg.equalsIgnoreCase("-q"))
				quad = true;
			else if(arg.startsWith("-tiled") || arg.startsWith("-t")) {
				String[] array = arg.split("=");
				if(array.length > 1) {
					tiles = Integer.parseUnsignedInt(array[1]);
				} else {
					tiles = 16;
				}
			}
			else
				colorArgs.add(Utility.getColorFromCode(arg));
		}
		String fileName = "src/main/resources/colors/" + Utility.concatList(colorArgs);
		if(quad) fileName += ".quad";
		if(tiles > 0) fileName += ".tiled" + tiles;
		fileName = fileName.toLowerCase();
		File file = new File(fileName + ".png");
		if(!file.exists() || !caching) {
			int width = 256 * (colorArgs.size() - 1);
			if(width < 256 || quad) width = 256;
			int height = 256;
			Color[] colors = new Color[colorArgs.size()];
			for(int i = 0; i < colors.length; i++) {
				colors[i] = new Color(Integer.parseUnsignedInt(colorArgs.get(i), 16));
			}
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			double weight;
			Color color;
			if(!quad) {
				if(colorArgs.size() == 1) {
					color = colors[0];
					for(int x = 0; x < width; x++) {
						for(int y = 0; y < height; y++) {
							bufferedImage.setRGB(x, y, color.getRGB());
						}
					}
				} else {
					for(int n = 0; n < colorArgs.size() - 1; n++) {
						for(int x = 0; x < 256; x++) {
							weight = (double) ((256 - 1) - x) / (256 - 1);
							color = Utility.averageColors(colors[n], colors[n+1], weight);
							for(int y = 0; y < height; y++) {
								bufferedImage.setRGB(256 * n + x, y, color.getRGB());
							}
						}
					}
				}
			} else {
				double[] weights = new double[4];
				double[][] distances = new double[4][256*256];
				if(colorArgs.size() == 4) {
					for(int x = 0; x < width; x++) {
						for(int y = 0; y < height; y++) {
							weights[0] = Utility.distance(x, y, 0, 255);
							weights[1] = Utility.distance(x, y, 255, 255);
							weights[2] = Utility.distance(x, y, 255, 0);
							weights[3] = Utility.distance(x, y, 0, 0);
							for(int i = 0; i < weights.length; i++) {
								distances[i][256*x + y] = weights[i];
							}
						}
					}
					for(double[] distanceArray : distances) {
						Arrays.sort(distanceArray);
					}
					for(int x = 0; x < width; x++) {
						for(int y = 0; y < height; y++) {
							weights[0] = Utility.distance(x, y, 0, 0);
							weights[1] = Utility.distance(x, y, 0, 255);
							weights[2] = Utility.distance(x, y, 255, 255);
							weights[3] = Utility.distance(x, y, 255, 0);
							for(int i = 0; i < weights.length; i++) {
								weights[i] = weights[i] / distances[i][256*256 - 1];
							}
							color = Utility.averageColors(colors, weights);
							bufferedImage.setRGB(x, y, color.getRGB());
						}
					}
				}
				//AffineTransform transform = new AffineTransform();
			    //transform.rotate(Math.toRadians(90), bufferedImage.getWidth()/2, bufferedImage.getHeight()/2);
			    //AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			    //bufferedImage = op.filter(bufferedImage, null);
			}
			bufferedImage = Utility.resize(bufferedImage, 256, 256);
			if(tiles > 0) {
				bufferedImage = Utility.resize(bufferedImage, tiles, tiles);
				bufferedImage = Utility.resize(bufferedImage, 256, 256);
			}
			try {
				ImageIO.write(bufferedImage, "png", file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			bufferedImage.flush();
		}
		messageChannel.sendFile(file).queue();
	}
}
