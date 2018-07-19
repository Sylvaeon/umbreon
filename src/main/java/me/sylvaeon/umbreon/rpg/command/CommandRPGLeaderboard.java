package me.sylvaeon.umbreon.rpg.command;

import me.sylvaeon.umbreon.rpg.world.entity.player.Player;
import me.sylvaeon.umbreon.rpg.world.entity.player.Players;
import me.sylvaeon.umbreon.util.Utility;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CommandRPGLeaderboard extends CommandRPG {
	@Override
	public void onCall(String[] args, User user, MessageChannel messageChannel) {
		List<User> sortedList = new ArrayList<>(Utility.sortByValues(Players.players).keySet());
		List<Member> memberList = new ArrayList<>();
		for(int i = 0; i < sortedList.size(); i++) {
			memberList.add(user.getGuild().getMember(sortedList.get(i)));
		}
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setAuthor("\uD83D\uDC98\uD83E\uDDE1\uD83D\uDC9B\uD83D\uDC9A\uD83D\uDC99\uD83D\uDC9CLevel Leaderboard\uD83D\uDC9C\uD83D\uDC99\uD83D\uDC9A\uD83D\uDC9B\uD83E\uDDE1\uD83D\uDC98");
		embedBuilder.setTitle("(1 message = 1 xp)");
		embedBuilder.setColor(Color.MAGENTA);
		for(int i = 0; i < sortedList.size(); i++) {
			Member m = memberList.get(memberList.size() - i - 1);
			User u = m.getUser();
			Player p = Players.players.get(m);
			String index = (i + 1) + "";
			String name = m.getEffectiveName() + " (" + u.getName() + "#" + u.getDiscriminator() + ")";
			String level = "Level: " + p.getLvl();
			String exp = "Total Xp: " + p.getTotalXp();
			embedBuilder.addField(index + ". " + name, level + " | " + exp, false);
		}
		messageChannel.sendMessage(embedBuilder.build()).queue();
	}
}
