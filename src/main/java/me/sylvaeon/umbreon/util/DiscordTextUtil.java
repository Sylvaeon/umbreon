package me.sylvaeon.umbreon.util;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.util.List;

public class DiscordTextUtil {
	
	public static void removeMessage(Message message) {
    	try {
    		message.delete().complete();
	    } catch (Exception e) {}
	}
	
	public static Message sendMessage(MessageChannel channel, String contents) {
		try {
			return channel.sendMessage(contents).complete();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Message sendMessage(MessageChannel channel, MessageEmbed contents) {
		try {
			return channel.sendMessage(contents).complete();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Message editMessage(Message message, String newContents) {
		try {
			return message.editMessage(newContents).complete();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Message editMessage(Message message, MessageEmbed newContents) {
		try {
			return message.editMessage(newContents).complete();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static EmbedBuilder createEmbedMessage(Color color, String authorText, String authorImage, String titleText, String titleImage, List<String> fieldNames, List<String> fieldValues, String footerText, String footerImage) {
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setColor(color);
		if(authorImage == null) {
			embedBuilder.setAuthor(authorText);
		} else {
			embedBuilder.setAuthor(authorText, authorImage);
		}
		if(titleText == null) {
			embedBuilder.setTitle(titleText);
		} else {
			embedBuilder.setTitle(titleText, titleImage);
		}
		for(int i = 0; i < fieldNames.size(); i++) {
			embedBuilder.addField(fieldNames.get(i), fieldValues.get(i), false);
		}
		embedBuilder.setFooter(footerText, footerImage);
		return embedBuilder;
	}
	
}
