package me.sylvaeon.umbreon.rpg.command;

import me.sylvaeon.umbreon.helper.StringHelper;
import me.sylvaeon.umbreon.rpg.entity.player.Player;
import me.sylvaeon.umbreon.rpg.entity.player.Players;
import me.sylvaeon.umbreon.rpg.entity.player.skill.SkillType;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CommandRPGXp extends CommandRPG {
	@Override
	public void onCall(String[] args, Member member, TextChannel textChannel) {
		Player player = Players.getPlayer(member);
		String msg = "";
		msg += "Character Level: " + (player.getLvl() + round( (double) player.getXp() / player.getXpNeeded(), 2)) + "\n";
		for(SkillType skillType : SkillType.values()) {
			msg += StringHelper.formatEnum(skillType) + " Level: " + (player.getSkillSet().getSkill(skillType).getLvl() + round( (double) player.getSkillSet().getSkill(skillType).getXp() / player.getSkillSet().getSkill(skillType).getXpNeeded(), 2)) + "\n";
		}
		textChannel.sendMessage(msg).queue();
	}
	
	private static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();
		
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
