package me.sylvaeon.umbreon.rpg.command;

import me.sylvaeon.umbreon.helper.MathHelper;
import me.sylvaeon.umbreon.helper.StringHelper;
import me.sylvaeon.umbreon.rpg.entity.player.Player;
import me.sylvaeon.umbreon.rpg.entity.player.Players;
import me.sylvaeon.umbreon.rpg.entity.player.skill.Skill;
import me.sylvaeon.umbreon.rpg.entity.player.skill.SkillSet;
import me.sylvaeon.umbreon.rpg.entity.player.skill.SkillType;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class CommandRPGXp extends CommandRPG {
	@Override
	public void onCall(String[] args, Member member, TextChannel textChannel) {
		Player player = Players.getPlayer(member);
		String msg = "Xp/Levels of " + member.getAsMention() + ":\n";
		SkillSet skillSet = player.getSkillSet();
		Skill skill;
		msg += "Character Level: " + StringHelper.getProgressBar(player.getLvl(), player.getXp(), player.getXpNeeded()) + "\n";
		for(SkillType skillType : SkillType.values()) {
			skill = skillSet.getSkill(skillType);
			msg += StringHelper.formatEnum(skillType) + " Level: " + StringHelper.getProgressBar(skill.getLvl(), skill.getXp(), skill.getXpNeeded()) + "\n";
		}
		textChannel.sendMessage(msg).queue();
	}



}
