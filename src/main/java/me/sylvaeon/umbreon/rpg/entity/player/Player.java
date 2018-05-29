package me.sylvaeon.umbreon.rpg.entity.player;

import com.sun.istack.internal.NotNull;
import me.sylvaeon.umbreon.helper.MapHelper;
import me.sylvaeon.umbreon.helper.StringHelper;
import me.sylvaeon.umbreon.rpg.crafting.Recipe;
import me.sylvaeon.umbreon.rpg.crafting.Recipes;
import me.sylvaeon.umbreon.rpg.entity.Entity;
import me.sylvaeon.umbreon.rpg.entity.enemy.Enemy;
import me.sylvaeon.umbreon.rpg.item.ItemStack;
import me.sylvaeon.umbreon.rpg.item.loot.LootDrop;
import me.sylvaeon.umbreon.rpg.entity.player.skill.Skill;
import me.sylvaeon.umbreon.rpg.entity.player.skill.SkillSet;
import me.sylvaeon.umbreon.rpg.entity.player.skill.SkillType;
import me.sylvaeon.umbreon.rpg.item.Equipable;
import me.sylvaeon.umbreon.rpg.item.Item;
import me.sylvaeon.umbreon.rpg.item.Items;
import me.sylvaeon.umbreon.rpg.item.loot.LootTable;
import me.sylvaeon.umbreon.rpg.item.tool.Tool;
import me.sylvaeon.umbreon.rpg.item.tool.ToolSet;
import me.sylvaeon.umbreon.rpg.item.tool.ToolType;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Player extends Entity implements Comparable<Player> {
	
	private int xp;
	private int lvl;
	private Set<ItemStack> inventory;

	private SkillSet skillSet;
	private ToolSet toolSet;
	
	private Equipable leftHand;
	private Equipable rightHand;
	
	public Player(String name) {
		super();
		this.name = name;
		this.xp = 0;
		this.lvl = 1;
		this.inventory = new TreeSet<>();
		this.skillSet = new SkillSet();
		this.toolSet = new ToolSet();
	}
	
	public void fight(Enemy enemy, TextChannel textChannel) {
		int turn = 0;
		double playerDamage, enemyDamage;
		StringBuilder builder = new StringBuilder();
		builder.append(outputStatus(enemy));
		loop:
		while(turn <= 256) {
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
				addXp(textChannel, xp);
				builder.append("Loot drops: ").append("\n");
				Map<Item, Integer> drops = new TreeMap<>();
				for(Item drop : loot) {
					if(!drops.containsKey(drop)) {
						drops.put(drop, 1);
					} else {
						drops.put(drop, drops.get(drop) + 1);
					}
				}
				for(Map.Entry<Item, Integer> entry : drops.entrySet()) {
					builder.append(entry.getValue()).append("x ").append(entry.getKey().getName()).append("\n");
				}
				addItems(enemy.getLoot().getDrops());
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
		resetHealth();
	}
	
	private byte getLifeState(Enemy enemy) {
		if(dead()) {
			return 1;
		} else if(enemy.dead()) {
			return 2;
		} else {
			return 0;
		}
	}
	
	private String outputStatus(Enemy enemy) {
		StringBuilder builder = new StringBuilder();
		builder.append("Your HP: ").append((int) getHealth()).append("/").append((int) getMaxHealth()).append("\n");
		builder.append("Their HP: ").append((int) enemy.getHealth()).append("/").append((int) enemy.getMaxHealth()).append("\n");
		return builder.toString();
	}
	
	@Override
	public double getDmg() {
		return getLvl();
	}
	
	public void addSkillXp(TextChannel textChannel, SkillType skillType, int xp) {
		int lvl = getLvl(skillType);
		skillSet.addXp(skillType, xp);
		if(getLvl(skillType) != lvl) {
			textChannel.sendMessage(StringHelper.formatEnum(skillType) + " Level Up! (" + lvl + " -> " + getLvl(skillType) + ")").queue();
			addXp(textChannel, (int) Math.floor(Math.sqrt(getLvl(skillType))));
		}
	}
	
	public boolean canCraft(Recipe recipe) {
		for(ItemStack itemStack : recipe.getInputs()) {
			if(getItemCount(itemStack) < itemStack.getAmount()) {
				return false;
			}
		}
		Tool tool = recipe.getTool();
		if(tool != null) {
			int requiredMaterial = tool.getMaterial();
			int currentMaterial = getToolMaterial(tool.getType());
			if (!(currentMaterial >= requiredMaterial)) {
				return false;
			}
		}
		Item firstItem = recipe.getOutputs().get(0).getItem();
		if(firstItem instanceof Tool) {
			Tool outputTool = (Tool) firstItem;
			ToolType toolType = outputTool.getType();
			int newMaterial = outputTool.getMaterial();
			int currentMaterial = getToolMaterial(toolType);
			if(newMaterial <= currentMaterial) {
				return false;
			}
		}
		return true;
	}

	public List<Recipe> getAvailableBasicRecipes() {
		List<Recipe> recipeList = new ArrayList<>();
		for(Recipe recipe : Recipes.getRecipes()) {
			if(canCraft(recipe) && recipe.getTool() == null) {
				recipeList.add(recipe);
			}
		}
		return recipeList;
	}

	public List<Recipe> getAvailableAlchemyRecipes() {
		List<Recipe> recipeList = new ArrayList<>();
		for(Recipe recipe : Recipes.getRecipes()) {
			if(canCraft(recipe) && recipe.getTool().getType() == ToolType.ALCHEMIC_TOOLS) {
				recipeList.add(recipe);
			}
		}
		return recipeList;
	}

	public List<Recipe> getAvailableCarpentryRecipes() {
		List<Recipe> recipeList = new ArrayList<>();
		for(Recipe recipe : Recipes.getRecipes()) {
			if(canCraft(recipe) && recipe.getTool().getType() == ToolType.SAW) {
				recipeList.add(recipe);
			}
		}
		return recipeList;
	}

	public List<Recipe> getAvailableCookingRecipes() {
		List<Recipe> recipeList = new ArrayList<>();
		for(Recipe recipe : Recipes.getRecipes()) {
			if(canCraft(recipe) && recipe.getTool().getType() == ToolType.CAULDRON) {
				recipeList.add(recipe);
			}
		}
		return recipeList;
	}

	public List<Recipe> getAvailableForgingRecipes() {
		List<Recipe> recipeList = new ArrayList<>();
		for(Recipe recipe : Recipes.getRecipes()) {
			if(canCraft(recipe) && recipe.getTool().getType() == ToolType.HAMMER) {
				recipeList.add(recipe);
			}
		}
		return recipeList;
	}

	private void gather(TextChannel textChannel, SkillType skillType, LootDrop... drops) {
		LootTable lootTable = new LootTable(drops);
		List<Item> list = lootTable.getDrops();
		int xp = 1 + getToolMaterial(skillType);
		if(!list.isEmpty()) {
			Map<Item, Integer> map = MapHelper.collectionToAmountMap(list);
			String msg = "Resources gathered:\n";
			for(Item item : map.keySet()) {
				msg += map.get(item) + "x " + item.getName() + "\n";
			}
			textChannel.sendMessage(msg).queue();
			addItems(list);
			addSkillXp(textChannel, skillType, xp);
		} else {
			textChannel.sendMessage("No resources gathered").queue();
		}

	}

	public int getToolMaterial(ToolType toolType) {
		return getToolSet().getMaterial(toolType);
	}

	public int getToolMaterial(SkillType skillType) {
		ToolType toolType = getToolType(skillType);
		return getToolMaterial(toolType);
	}

	public ToolType getToolType(SkillType skillType) {
		if(skillType == SkillType.FISHING) {
			return ToolType.FISHING_ROD;
		} else if(skillType == SkillType.FORAGING) {
			return ToolType.CUTTING_TOOL;
		} else if(skillType == SkillType.HUNTING) {
			return ToolType.SPEAR;
		} else if(skillType == SkillType.LOGGING) {
			return ToolType.AXE;
		} else if(skillType == SkillType.MINING) {
			return ToolType.PICK;
		} else if(skillType == SkillType.ALCHEMY) {

		}
		return null;
	}


	//Gathering
	public void fish(TextChannel textChannel) {
		gather(textChannel, SkillType.FISHING,
			new LootDrop(Items.FISH, 1 + getToolMaterial(ToolType.FISHING_ROD))
		);
	}
	public void forage(TextChannel textChannel) {
		gather(textChannel, SkillType.FORAGING,
			new LootDrop(Items.BERRY, 0 + getToolMaterial(ToolType.CUTTING_TOOL), 3 + getToolMaterial(ToolType.CUTTING_TOOL)),
			new LootDrop(Items.MUSHROOM, 0.1d),
			new LootDrop(Items.VINE, 0.25d),
			new LootDrop(Items.HERB, 0.1d)
		);
	}
	public void hunt(TextChannel textChannel) {
		gather(textChannel, SkillType.HUNTING,
			new LootDrop(Items.DEER_HIDE, 0, 2),
			new LootDrop(Items.DEER_MEAT, 1 + getToolMaterial(ToolType.SPEAR) / 3d)
		);
	}
	public void log(TextChannel textChannel) {
		gather(textChannel, SkillType.LOGGING,
			new LootDrop(Items.LOG, 1 + getToolMaterial(ToolType.AXE)),
			new LootDrop(Items.BRANCH, 1, 2)
		);
	}
	public void mine(TextChannel textChannel) {
		gather(textChannel, SkillType.MINING,
			new LootDrop(Items.FLINT_SHARD, 1 + getToolMaterial(ToolType.PICK)),
			new LootDrop(Items.STONE, 			getToolMaterial(ToolType.PICK) / 5d),
			new LootDrop(Items.COPPER_ORE, 		(getToolMaterial(ToolType.PICK) - 1) / 5d),
			new LootDrop(Items.TIN_ORE, (getToolMaterial(ToolType.PICK) - 2) / 5d),
			new LootDrop(Items.NICKLE_ORE, (getToolMaterial(ToolType.PICK) - 3) / 5d)
		);
	}

	public void setToolMaterial(ToolType toolType, int material) {
		toolSet.setMaterial(toolType, material);
	}

	public boolean craft(Recipe recipe, TextChannel textChannel) {
		if(canCraft(recipe)) {
			for(ItemStack itemStack : recipe.getInputs()) {
				removeItem(itemStack);
			}
			for(ItemStack itemStack : recipe.getOutputs()) {
				Item item = itemStack.getItem();
				if(item instanceof Tool) {
					Tool tool = (Tool) item;
					setToolMaterial(tool.getType(), tool.getMaterial());
				} else {
					addItem(itemStack);
				}
			}
			textChannel.sendMessage("Crafted " + recipe.outputsAsString()).queue();
			return true;
		} else {
			return false;
		}
	}

	public boolean craft(Recipe recipe, TextChannel textChannel, SkillType skillType) {
		boolean ret = craft(recipe, textChannel);
		addSkillXp(textChannel, skillType, 1 + getToolMaterial(skillType));
		return ret;
	}

	//Crafting
	public void alchemy(Recipe recipe, TextChannel textChannel) {
		craft(recipe, textChannel, SkillType.ALCHEMY);
	}
	public void carpenter(Recipe recipe, TextChannel textChannel) {
		craft(recipe, textChannel, SkillType.CARPENTRY);
	}
	public void cook(Recipe recipe, TextChannel textChannel) {
		craft(recipe, textChannel, SkillType.COOKING);
	}
	public void forge(Recipe recipe, TextChannel textChannel) {
		craft(recipe, textChannel, SkillType.FORGING);
	}
	
	public int getLvl(SkillType skillType) {
		return getSkill(skillType).getLvl();
	}
	
	public int getXp(SkillType skillType) {
		return getSkill(skillType).getXp();
	}
	
	public int getXpLeft(SkillType skillType) {
		return getSkill(skillType).getXpLeft();
	}
	
	public int getXpNeeded(SkillType skillType) {
		return  getSkill(skillType).getXpNeeded();
	}
	
	public Skill getSkill(SkillType skillType) {
		return skillSet.getSkill(skillType);
	}
	
	public void setXp(SkillType skillType, int xp) {
		skillSet.setXp(skillType, xp);
	}
	
	public void setLvl(SkillType skillType, int lvl) {
		skillSet.setLvl(skillType, lvl);
	}
	
	public void addItem(ItemStack itemStack) {
		addItem(itemStack.getItem(), itemStack.getAmount());
	}
	
	public void addItem(Item item) {
		if(item instanceof Tool) {
			Tool tool = (Tool) item;
			toolSet.setMaterial(tool.getType(), tool.getMaterial());
		} else {
			addItem(item, 1);
		}
	}
	
	public void addItem(Item item, int amount) {
		if(containsItem(item)) {
			getItemStack(item).add(amount);
		} else {
			inventory.add(new ItemStack(item, amount));
		}
	}
	
	public void addItems(Collection<Item> items) {
		for(Item item : items) {
			addItem(item);
		}
	}
	
	public void removeItem(Item item) {
		removeItem(item, 1);
	}
	
	public boolean removeItem(Item item, int amount) {
		boolean canRemove = false;
		if(containsItem(item)) {
			if (getItemCount(item) >= amount) {
				getItemStack(item).subtract(amount);
				canRemove = true;
			}
			if (getItemCount(item) == 0) {
				inventory.remove(getItemStack(item));
			}
		}
		return canRemove;
	}
	
	public boolean removeItem(ItemStack itemStack) {
		return removeItem(itemStack.getItem(), itemStack.getAmount());
	}
	
	public void removeAllItem(Item item) {
		removeItem(item, getItemCount(item));
	}
	
	public ItemStack getItemStack(Item item) {
		for(ItemStack itemStack : inventory) {
			if(itemStack.getItem() == item) {
				return itemStack;
			}
		}
		return null;
	}
	
	public boolean containsItem(Item item) {
		for(ItemStack itemStack : inventory) {
			if(itemStack.getItem() == item) {
				return true;
			}
		}
		return false;
	}
	
	public int getItemCount(Item item) {
		for(ItemStack itemStack : inventory) {
			if(itemStack.getItem() == item) {
				return itemStack.getAmount();
			}
		}
		return 0;
	}
	
	public int getItemCount(ItemStack itemStack) {
		return getItemCount(itemStack.getItem());
	}
	
	public void update() {
		while(xp > 0) {
			if(xp >= getXpNeeded()) {
				xp -= getXpNeeded();
				lvl++;
			} else {
				break;
			}
		}
	}
	
	public boolean upgradeTool(ToolType type) {
		return toolSet.upgradeMaterial(type);
	}

	public static int getXpNeeded(int lvl) {
		return lvl;
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

	public void addXp(TextChannel textChannel, int xp) {
		int lvl = getLvl();
		addXp(xp);
		if(lvl != getLvl()) {
			textChannel.sendMessage("Level Up! (" + lvl + " -> " + getLvl() + ")").queue();
		}
	}

	public void addXp(int xp) {
		this.xp += xp;
		update();
	}
	
	public int getLvl() {
		return lvl;
	}

	public int getTotalXp() {
		return (getLvl() * (getLvl() + 1) / 2) + getXp();
	}

	public int compareTo(@NotNull Player o) {
		return Integer.compare(this.getTotalXp(), o.getTotalXp());
	}
	
	public void setXp(int xp) {
		this.xp = xp;
	}
	
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
	
	public Set<ItemStack> getInventory() {
		return inventory;
	}
	
	public void setInventory(Set<ItemStack> inventory) {
		this.inventory = inventory;
	}
	
	public SkillSet getSkillSet() {
		return skillSet;
	}
	
	public void setSkillSet(SkillSet skillSet) {
		this.skillSet = skillSet;
	}
	
	public ToolSet getToolSet() {
		return toolSet;
	}
	
	public void setToolSet(ToolSet toolSet) {
		this.toolSet = toolSet;
	}
	
	public Equipable getLeftHand() {
		return leftHand;
	}
	
	public void setLeftHand(Equipable leftHand) {
		this.leftHand = leftHand;
	}
	
	public Equipable getRightHand() {
		return rightHand;
	}
	
	public void setRightHand(Equipable rightHand) {
		this.rightHand = rightHand;
	}
}
