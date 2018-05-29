package me.sylvaeon.umbreon.rpg.command.action;

import me.sylvaeon.umbreon.command.Command;
import me.sylvaeon.umbreon.rpg.entity.enemy.Enemies;
import me.sylvaeon.umbreon.rpg.entity.enemy.Enemy;
import me.sylvaeon.umbreon.rpg.entity.player.Player;
import me.sylvaeon.umbreon.rpg.entity.player.Players;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class CommandRPGFight extends Command {
    @Override
    public void onCall(String[] args, Member member, TextChannel textChannel) {
        Player player = Players.getPlayer(member);
        textChannel.sendMessage(player.getName()).queue();
        Enemy enemy = Enemies.RAT;
        textChannel.sendMessage(enemy.getName()).queue();
        player.fight(enemy, textChannel);
    }
}
