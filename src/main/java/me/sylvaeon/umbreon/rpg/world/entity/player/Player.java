package me.sylvaeon.umbreon.rpg.world.entity.player;

import me.sylvaeon.umbreon.Counter;
import me.sylvaeon.umbreon.rpg.crafting.Recipe;
import me.sylvaeon.umbreon.rpg.crafting.Recipes;
import me.sylvaeon.umbreon.rpg.item.Item;
import me.sylvaeon.umbreon.rpg.item.Items;
import me.sylvaeon.umbreon.rpg.item.drop.DropTable;
import me.sylvaeon.umbreon.rpg.item.drop.ItemDrop;
import me.sylvaeon.umbreon.rpg.item.equipable.tool.Tool;
import me.sylvaeon.umbreon.rpg.world.entity.Entity;
import me.sylvaeon.umbreon.rpg.world.entity.player.skill.Skill;
import me.sylvaeon.umbreon.rpg.world.entity.player.skill.SkillSet;
import me.sylvaeon.umbreon.util.Utility;
import net.dv8tion.jda.core.entities.MessageChannel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player extends Entity {

	private static final long serialVersionUID = 30L;

	private int xp;
	private int lvl;
	private Inventory inventory;
	private SkillSet skillSet;
	private int xPos, yPos;
	private double mana, manaMax;

	public Player(String name) {
		super(name);
		this.xp = 0;
		this.lvl = 1;
		this.xPos = 0;
		this.yPos = 0;
		this.mana = 100;
		this.manaMax = 100;
		this.inventory = new Inventory();
        this.skillSet = new SkillSet();
	}

	@Contract(pure = true)
	public static int getTotalXp(int lvl) {
		int count = 0;
		for (int i = 1; i <= lvl; i++) {
			count += getXpNeeded(i);
		}
		return count;
	}

	@Contract(pure = true)
	public static int getXpNeeded(int lvl) {
		return lvl;
	}


	@NotNull
	@Contract(pure = true)
	private static String getLevelUpMessage(int start, int end) {
		return "Level up! (" + start + " -> " + end + ")";
	}

    //Gathering
    public void log(MessageChannel textChannel) {
        gather(textChannel, Skill.SkillType.LOGGING,
	        new ItemDrop(Items.LOG, 1),
	        new ItemDrop(Items.BRANCH, 1, 2)
        );
    }

    private void gather(MessageChannel textChannel, Skill.SkillType skillType, ItemDrop... drops) {
		DropTable lootTable = new DropTable(drops);
		List<Item> list = lootTable.getDrops();
		int xp = 1;
		if (!list.isEmpty()) {
			Map<Item, Integer> map = Utility.collectionToAmountMap(list);
			String msg = "Resources gathered:\n";
			for (Item item : map.keySet()) {
				msg += map.get(item) + "x " + item.getName() + "\n";
			}
			textChannel.sendMessage(msg).queue();
			getInventory().add(list);
			addSkillXp(textChannel, skillType, xp);
		} else {
			textChannel.sendMessage("No resources gathered").queue();
		}
	}

    public void addSkillXp(MessageChannel textChannel, Skill.SkillType skillType, int xp) {
        int lvl = getSkillSet().getSkill(skillType).getLvl();
        getSkillSet().addXp(skillType, xp);
        if (getSkillSet().getSkill(skillType).getLvl() != lvl) {
            textChannel.sendMessage(Utility.formatEnum(skillType) + " " + getLevelUpMessage(lvl, getSkillSet().getSkill(skillType).getLvl())).queue();
            addXp((int) Math.floor(Math.sqrt(getSkillSet().getSkill(skillType).getLvl())), textChannel);
        }
    }

	/*public void fight(Animal enemy, TextChannel textChannel) {
		int turn = 0;
		double playerDamage, enemyDamage;
		StringBuilder builder = new StringBuilder();
		builder.append(outputStatus(enemy));
		loop:
		while (turn <= 256) {
			turn++;
			playerDamage = getDmg();
			enemyDamage = enemy.getDmg();
			switch (getLifeState(enemy)) {
				case 1:
					builder.append("You lost :(");
					break loop;
				case 2:
					int xp = ThreadLocalRandom.current().nextInt(enemy.getXpMin(), enemy.getXpMax() + 1);
					List<Item> loot = enemy.getLoot().getDrops();
					builder.append("You won!").append("\n");
					addXp(xp, textChannel);
					builder.append("Loot drops: ").append("\n");
					Map<Item, Integer> drops = new TreeMap<>();
					for (Item drop : loot) {
						if (!drops.containsKey(drop)) {
							drops.put(drop, 1);
						} else {
							drops.put(drop, drops.get(drop) + 1);
						}
					}
					for (Map.Entry<Item, Integer> entry : drops.entrySet()) {
						builder.append(entry.getValue()).append("x ").append(entry.getKey().getName()).append("\n");
					}
					getInventory().addItems(enemy.getLoot().getDrops());
					break loop;
				case 0:
					builder.append("\nTurn ").append(turn).append("\n");
					enemy.takeDamage(playerDamage);
					builder.append("You did ").append(playerDamage).append(" dmg to ").append(enemy.getName()).append("\n");
					builder.append(outputStatus(enemy));
					if (enemy.dead()) {
						break;
					} else {
						builder.append("You took ").append(enemyDamage).append(" dmg from ").append(enemy.getName()).append("\n");
						takeDamage(enemyDamage);
						builder.append(outputStatus(enemy));
					}
					break;
			}
		}
		textChannel.sendMessage(builder.toString()).queue();
	}

	private byte getLifeState(Enemy enemy) {
		if (dead()) {
			return 1;
		} else if (enemy.dead()) {
			return 2;
		} else {
			return 0;
		}
	}

	@NotNull
	private String outputStatus(Enemy enemy) {
		StringBuilder builder = new StringBuilder();
		builder.append("Your HP: ").append((int) getHealth()).append("/").append((int) getMaxHealth()).append("\n");
		builder.append("Their HP: ").append((int) enemy.getHealth()).append("/").append((int) enemy.getMaxHealth()).append("\n");
		return builder.toString();
	}*/

	@Override
	public double getDmg() {
		return getLvl();
	}

    public void mine(MessageChannel textChannel) {
        double pickaxe = getInventory().toolSet.get(Tool.ToolType.PICKAXE).toolMaterial.getMaterialLevel();
        gather(textChannel, Skill.SkillType.MINING,
	        new ItemDrop(Items.FLINT_SHARD, 1, 2)
        );
	}

	public boolean canCraft(Recipe recipe) {
		for (Map.Entry<Item, Counter> entry : recipe.getInputs().entrySet()) {
			if (getInventory().get(entry.getKey()).count < entry.getValue().count) {
				return false;
			}
		}
		return true;
	}

	public List<Recipe> getAvailableRecipes() {
		List<Recipe> recipeList = new ArrayList<>();
		for (Recipe recipe : Recipes.getRecipes()) {
			if (canCraft(recipe)) {
				recipeList.add(recipe);
			}
		}
		return recipeList;
	}

	public boolean craft(Recipe recipe, MessageChannel textChannel) {
		if (canCraft(recipe)) {
			for(Map.Entry<Item, Counter> entry : recipe.inputs.entrySet()) {
				getInventory().subtract(entry.getKey(), entry.getValue().count);
			}
			for(Map.Entry<Item, Counter> entry : recipe.outputs.entrySet()) {
				getInventory().add(entry.getKey(), entry.getValue().count);
			}
			return true;
		} else {
			return false;
		}
	}

	public void update() {
		while (xp > 0) {
			if (xp >= getXpNeeded()) {
				xp -= getXpNeeded();
				lvl++;
			} else {
				break;
			}
		}
	}

	public int getTotalXp() {
		return getTotalXp(lvl) + getXp();
	}

	public int getXpNeeded() {
		return getXpNeeded(getLvl());
	}

	public int getXpLeft() {
		return getXpNeeded() - getXp();
	}

	public int getXp() {
		return xp;
	}

	public void addXp(int xp, MessageChannel textChannel) {
		int lvl = getLvl();
		this.xp += xp;
		update();
		if (getLvl() != lvl) {
			textChannel.sendMessage("Character " + getLevelUpMessage(lvl, getLvl())).queue();
		}
	}

	public int getLvl() {
		return lvl;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public SkillSet getSkillSet() {
		return skillSet;
	}

	public int getXPos() {
		return xPos;
	}

	public void addX() {
		xPos++;
	}
	
	public void subtractX() {
		xPos--;
	}
	
	public int getYPos() {
		return yPos;
	}
	
	public void addY() {
		yPos++;
	}
	
	public void subtractY() {
		yPos--;
	}

	public double getMana() {
		return mana;
	}

	public double getManaMax() {
		return manaMax;
	}

	@Override
	public int compareTo(@NotNull Entity o) {
		if(o instanceof Player) {
			return Integer.compare(getTotalXp(), ((Player) o).getTotalXp());
		} else {
			return super.compareTo(o);
		}
	}
}
