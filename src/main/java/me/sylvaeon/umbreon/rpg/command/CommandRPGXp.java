package me.sylvaeon.umbreon.rpg.command;

import me.sylvaeon.umbreon.Utility;
import me.sylvaeon.umbreon.rpg.world.entity.player.Player;
import me.sylvaeon.umbreon.rpg.world.entity.player.Players;
import me.sylvaeon.umbreon.rpg.world.entity.player.skill.Skill;
import me.sylvaeon.umbreon.rpg.world.entity.player.skill.SkillSet;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.awt.*;

public class CommandRPGXp extends CommandRPG {
	@Override
	public void onCall(String[] args, Member member, MessageChannel textChannel) {
		Player player = Players.getPlayer(member);
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setColor(Color.MAGENTA);
		embedBuilder.setTitle("Skill Levels");
		embedBuilder.setAuthor(member.getEffectiveName());
		embedBuilder.addField("Character Level", Utility.getProgressBar(player.getLvl(), player.getXp(), player.getXpNeeded()), false);
		SkillSet skillSet = player.getSkillSet();
		Skill skill;
        for (Skill.SkillType skillType : Skill.SkillType.values()) {
			skill = skillSet.getSkill(skillType);
			embedBuilder.addField(Utility.formatEnum(skillType) + " Level", Utility.getProgressBar(skill.getLvl(), skill.getXp(), skill.getXpNeeded()), true);
		}
		textChannel.sendMessage(embedBuilder.build()).queue();
	}
}
