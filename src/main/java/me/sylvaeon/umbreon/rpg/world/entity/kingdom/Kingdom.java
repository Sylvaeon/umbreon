package me.sylvaeon.umbreon.rpg.world.entity.kingdom;

import me.sylvaeon.umbreon.rpg.world.entity.player.Player;
import me.sylvaeon.umbreon.rpg.world.entity.player.Players;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

public class Kingdom implements Serializable {
	private static final long serialVersionUID = 1555L;
	
	private Player ruler;
	private Set<Player> knights;
	private Set<Player> nobles;
	
	private long coins;
	private int maxCoinTier;
	
	public Kingdom(Guild guild) {
		knights = new LinkedHashSet<>();
		nobles = new LinkedHashSet<>();
		for(Member member : guild.getMembers()) {
			User user = member.getUser();
			if(member.isOwner()) {
				ruler = Players.getPlayer(user);
			} else if(member.hasPermission(Permission.ADMINISTRATOR)) {
				nobles.add(Players.getPlayer(user));
			} else {
				knights.add(Players.getPlayer(user));
			}
		}
		coins = 0;
		maxCoinTier = 0;
	}
	
	public void transferRuler(Player player) {
		if(getCitizens().contains(player) && player != ruler) {
			nobles.add(ruler);
			ruler = player;
		}
	}
	
	public boolean promoteNoble(Player player) {
		if(getKnights().contains(player)) {
			knights.remove(player);
			nobles.add(player);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean demoteNoble(Player player) {
		if(getNobles().contains(player)) {
			nobles.remove(player);
			knights.add(player);
			return true;
		} else {
			return false;
		}
	}
	
	public Set<Player> getCitizens() {
		Set<Player> citizens = new LinkedHashSet<>();
		citizens.add(getRuler());
		citizens.addAll(nobles);
		citizens.addAll(knights);
		return citizens;
	}
	
	public Player getRuler() {
		return ruler;
	}
	
	public Set<Player> getKnights() {
		return knights;
	}
	
	public void addKnight(Player player) {
		knights.add(player);
	}
	
	public Set<Player> getNobles() {
		return nobles;
	}
	
	public long getCoins() {
		return coins;
	}
	
	private void addCoins(long amount) {
		this.coins += amount;
	}
	
	public boolean canGainCoins(long amount) {
		return getCoins() <= getMaxCoins();
	}
	
	public boolean gainCoins(long amount) {
		if(canGainCoins(amount)) {
			addCoins(amount);
		}
		updateCoins();
		return canGainCoins(amount);
	}
	
	private void subtractCoins(long amount) {
		this.coins -= amount;
	}
	
	public boolean canSpendCoins(long amount) {
		return getCoins() >= amount;
	}
	
	public boolean spendCoins(long amount) {
		if(canSpendCoins(amount)) {
			subtractCoins(amount);
		}
		updateCoins();
		return canSpendCoins(amount);
	}
	
	private void updateCoins() {
		if(getCoins() < 0) {
			coins = 0;
		} else if(getCoins() > getMaxCoins()) {
			coins = getMaxCoins();
		}
	}
	
	public long getMaxCoins() {
		return 1000 * ((long) Math.pow(100, getMaxCoinTier()));
	}
	
	public int getMaxCoinTier() {
		return maxCoinTier;
	}
}
