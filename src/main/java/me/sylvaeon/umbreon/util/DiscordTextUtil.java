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
	
	public static void sendMessage(String contents, MessageChannel channel) {
		try {
			channel.sendMessage(contents).queue();
		} catch (Exception e) {}
	}
	
	public static void editMessage(Message message, String newContents) {
		try {
			message.editMessage(newContents).queue();
		} catch (Exception e) {}
	}
	
	public static MessageEmbed createEmbedMessage(Color color, String authorText, String authorImage, String titleText, String titleImage, List<String> fieldNames, List<String> fieldValues, List<Boolean> isFieldInline, String footerText, String footerImage) {
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
			embedBuilder.addField(fieldNames.get(i), fieldValues.get(i), isFieldInline.get(i));
		}
		embedBuilder.setFooter(footerText, footerImage);
		return embedBuilder.build();
	}
	
}
