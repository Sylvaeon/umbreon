package me.sylvaeon.umbreon.command;

import java.awt.Color;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;

public class CommandColorConvert extends Command {
	@Override
	public void onCall(String[] args, Member member, MessageChannel textChannel) {
		if(args.length == 3) {
			Color color = new Color((float) Integer.parseUnsignedInt(args[0]) / 256, (float) Integer.parseUnsignedInt(args[1]) / 256, (float) Integer.parseUnsignedInt(args[2]) / 256);
			textChannel.sendMessage(args[0] + ", " + args[1] + ", " + args[2] + " -> #" + String.format("%06X", color.getRGB()).substring(2)).queue();
		}
	}
}
