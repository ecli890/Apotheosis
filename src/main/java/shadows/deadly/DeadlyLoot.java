package shadows.deadly;

import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.Apotheosis;
import shadows.placebo.loot.LootSystem;
import shadows.placebo.loot.PoolBuilder;
import shadows.placebo.util.ChestBuilder;
import shadows.placebo.util.ChestBuilder.EnchantedEntry;

/**
 * Loot entries for deadly module
 * TODO: Make configurable.
 * @author Shadows
 *
 */
public class DeadlyLoot {

	public static final ResourceLocation SPAWNER_BRUTAL = new ResourceLocation(Apotheosis.MODID, "spawner_brutal");
	public static final ResourceLocation SPAWNER_SWARM = new ResourceLocation(Apotheosis.MODID, "spawner_swarm");

	public static final ResourceLocation CHEST_VALUABLE = new ResourceLocation(Apotheosis.MODID, "chest_valuable");

	public static void init() {
		PoolBuilder build = new PoolBuilder(5, 8);
		build.bonusRolls(1, 3);
		build.addEntries(ChestBuilder.loot(Items.SKELETON_SKULL, 1, 1, 1, 2));
		build.addEntries(ChestBuilder.loot(Items.WITHER_SKELETON_SKULL, 1, 1, 1, 2));
		build.addEntries(ChestBuilder.loot(Items.CREEPER_HEAD, 1, 1, 1, 2));
		build.addEntries(ChestBuilder.loot(Items.ZOMBIE_HEAD, 1, 1, 1, 2));
		build.addEntries(ChestBuilder.loot(Items.PLAYER_HEAD, 1, 1, 1, 2));
		build.addEntries(ChestBuilder.loot(Blocks.TNT, 1, 1, 2, 0));
		build.addEntries(ChestBuilder.loot(Items.DIAMOND, 1, 3, 3, 5));
		build.addEntries(ChestBuilder.loot(Items.EMERALD, 1, 3, 3, 6));
		build.addEntries(ChestBuilder.loot(Items.IRON_INGOT, 1, 5, 10, 3));
		build.addEntries(ChestBuilder.loot(Items.GOLD_INGOT, 1, 5, 10, 4));
		build.addEntries(ChestBuilder.loot(Items.GOLDEN_APPLE, 1, 1, 1, 3));
		build.addEntries(ChestBuilder.loot(Items.NAME_TAG, 1, 1, 5, 0));
		build.addEntries(ChestBuilder.loot(Items.LEAD, 1, 1, 5, 0));
		build.addEntries(ChestBuilder.loot(Items.SADDLE, 1, 1, 3, 0));
		build.addEntries(ChestBuilder.loot(Items.DIAMOND_HORSE_ARMOR, 1, 1, 1, 5));
		build.addEntries(ChestBuilder.loot(Items.SLIME_BALL, 1, 3, 3, 1));
		build.addEntries(ChestBuilder.loot(Items.BUCKET, 1, 1, 3, 0));
		build.addEntries(ChestBuilder.loot(Blocks.ANVIL, 1, 1, 3, 0));
		build.addEntries(ChestBuilder.loot(Blocks.ENCHANTING_TABLE, 1, 1, 3, 0));
		build.addEntries(ChestBuilder.loot(Blocks.IRON_BLOCK, 1, 1, 3, 0));
		build.addEntries(new EnchantedEntry(Items.BOOK, 3));
		LootSystem.registerLootTable(SPAWNER_BRUTAL, LootSystem.tableBuilder().addLootPool(build).build());

		build = new PoolBuilder(5, 6);
		build.bonusRolls(1, 4);
		build.addEntries(ChestBuilder.loot(egg("creeper"), 1, 3, 1, 1));
		build.addEntries(ChestBuilder.loot(egg("skeleton"), 1, 3, 1, 1));
		build.addEntries(ChestBuilder.loot(egg("spider"), 1, 3, 1, 1));
		build.addEntries(ChestBuilder.loot(egg("zombie"), 1, 3, 1, 1));
		build.addEntries(ChestBuilder.loot(egg("slime"), 1, 3, 1, 1));
		build.addEntries(ChestBuilder.loot(egg("enderman"), 1, 3, 1, 1));
		build.addEntries(ChestBuilder.loot(egg("cave_spider"), 1, 3, 1, 1));
		build.addEntries(ChestBuilder.loot(egg("silverfish"), 1, 3, 1, 1));
		build.addEntries(ChestBuilder.loot(Items.DIAMOND, 1, 3, 3, 4));
		build.addEntries(ChestBuilder.loot(Items.EMERALD, 1, 3, 3, 4));
		build.addEntries(ChestBuilder.loot(Items.IRON_INGOT, 1, 5, 10, 1));
		build.addEntries(ChestBuilder.loot(Items.GOLD_INGOT, 1, 5, 10, 3));
		build.addEntries(ChestBuilder.loot(Items.GOLDEN_APPLE, 1, 1, 1, 2));
		build.addEntries(ChestBuilder.loot(Items.NAME_TAG, 1, 1, 5, 1));
		build.addEntries(ChestBuilder.loot(Items.LEAD, 1, 1, 5, 1));
		build.addEntries(ChestBuilder.loot(Items.SADDLE, 1, 1, 3, 1));
		build.addEntries(ChestBuilder.loot(Items.DIAMOND_HORSE_ARMOR, 1, 1, 1, 3));
		build.addEntries(ChestBuilder.loot(Items.SLIME_BALL, 1, 3, 3, 0));
		build.addEntries(ChestBuilder.loot(Items.BUCKET, 1, 1, 3, 0));
		build.addEntries(ChestBuilder.loot(Blocks.ANVIL, 1, 1, 3, 0));
		build.addEntries(ChestBuilder.loot(Blocks.OBSIDIAN, 3, 8, 3, 0));
		build.addEntries(new EnchantedEntry(Items.BOOK, 3));
		LootSystem.registerLootTable(SPAWNER_SWARM, LootSystem.tableBuilder().addLootPool(build).build());

		build = new PoolBuilder(6, 12);
		build.bonusRolls(2, 5);
		build.addEntries(ChestBuilder.loot(potion(Potions.STRONG_REGENERATION), 1, 1, 20, 10));
		build.addEntries(ChestBuilder.loot(potion(Potions.STRONG_SWIFTNESS), 1, 1, 20, 10));
		build.addEntries(ChestBuilder.loot(potion(Potions.LONG_FIRE_RESISTANCE), 1, 1, 20, 10));
		build.addEntries(ChestBuilder.loot(potion(Items.SPLASH_POTION, Potions.STRONG_HEALING), 1, 1, 20, 10));
		build.addEntries(ChestBuilder.loot(potion(Potions.LONG_NIGHT_VISION), 1, 1, 20, 10));
		build.addEntries(ChestBuilder.loot(potion(Potions.LONG_STRENGTH), 1, 1, 20, 10));
		build.addEntries(ChestBuilder.loot(potion(Potions.LONG_INVISIBILITY), 1, 1, 20, 10));
		build.addEntries(ChestBuilder.loot(potion(Potions.LONG_WATER_BREATHING), 1, 1, 20, 10));
		build.addEntries(ChestBuilder.loot(Items.DIAMOND, 1, 3, 30, 4));
		build.addEntries(ChestBuilder.loot(Items.EMERALD, 1, 3, 30, 4));
		build.addEntries(ChestBuilder.loot(Items.IRON_INGOT, 1, 5, 100, 1));
		build.addEntries(ChestBuilder.loot(Items.GOLD_INGOT, 1, 5, 100, 3));
		build.addEntries(ChestBuilder.loot(Items.ENCHANTED_GOLDEN_APPLE, 1, 1, 1, 15));
		build.addEntries(ChestBuilder.loot(Items.NAME_TAG, 1, 2, 50, 1));
		build.addEntries(ChestBuilder.loot(Items.LEAD, 1, 2, 50, 1));
		build.addEntries(ChestBuilder.loot(Items.SADDLE, 1, 2, 40, 1));
		build.addEntries(ChestBuilder.loot(Items.DIAMOND_HORSE_ARMOR, 1, 1, 40, 3));
		build.addEntries(ChestBuilder.loot(Items.SLIME_BALL, 3, 6, 50, 0));
		build.addEntries(ChestBuilder.loot(Items.BUCKET, 1, 1, 50, 0));
		build.addEntries(new EnchantedEntry(Items.DIAMOND_SWORD, 30));
		build.addEntries(new EnchantedEntry(Items.DIAMOND_AXE, 30));
		build.addEntries(new EnchantedEntry(Items.DIAMOND_PICKAXE, 30));
		build.addEntries(new EnchantedEntry(Items.DIAMOND_BOOTS, 20));
		build.addEntries(new EnchantedEntry(Items.DIAMOND_LEGGINGS, 20));
		build.addEntries(new EnchantedEntry(Items.DIAMOND_HELMET, 20));
		build.addEntries(new EnchantedEntry(Items.DIAMOND_CHESTPLATE, 20));
		build.addEntries(new EnchantedEntry(Items.BOOK, 40));
		LootSystem.registerLootTable(CHEST_VALUABLE, LootSystem.tableBuilder().addLootPool(build).build());
	}

	private static ItemStack egg(String mob) {
		return new ItemStack(SpawnEggItem.EGGS.get(ForgeRegistries.ENTITIES.getValue(new ResourceLocation(mob))));
	}

	private static ItemStack potion(Potion type) {
		return potion(Items.POTION, type);
	}

	private static ItemStack potion(Item pot, Potion type) {
		ItemStack s = new ItemStack(pot);
		PotionUtils.addPotionToItemStack(s, type);
		return s;
	}

}
