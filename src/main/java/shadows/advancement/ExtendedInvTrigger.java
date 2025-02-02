package shadows.advancement;

import java.util.Map;
import java.util.function.Predicate;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.MinMaxBounds.IntBound;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedInvTrigger extends InventoryChangeTrigger {

	@Override
	public InventoryChangeTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
		JsonObject jsonobject = JSONUtils.getJsonObject(json, "slots", new JsonObject());
		MinMaxBounds.IntBound minmaxbounds$intbound = MinMaxBounds.IntBound.fromJson(jsonobject.get("occupied"));
		MinMaxBounds.IntBound minmaxbounds$intbound1 = MinMaxBounds.IntBound.fromJson(jsonobject.get("full"));
		MinMaxBounds.IntBound minmaxbounds$intbound2 = MinMaxBounds.IntBound.fromJson(jsonobject.get("empty"));
		ItemPredicate[] aitempredicate = ItemPredicate.deserializeArray(json.get("items"));
		if (json.has("apoth")) aitempredicate = deserializeApoth(json.getAsJsonObject("apoth"));
		return new InventoryChangeTrigger.Instance(minmaxbounds$intbound, minmaxbounds$intbound1, minmaxbounds$intbound2, aitempredicate);
	}

	ItemPredicate[] deserializeApoth(JsonObject json) {
		String type = json.get("type").getAsString();
		if (type.equals("spawn_egg")) return new ItemPredicate[] { new TrueItemPredicate(s -> s.getItem() instanceof SpawnEggItem) };
		if (type.equals("enchanted")) {
			Enchantment ench = json.has("enchantment") ? ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(json.get("enchantment").getAsString())) : null;
			IntBound bound = IntBound.fromJson(json.get("level"));
			return new ItemPredicate[] { new TrueItemPredicate(s -> {
				Map<Enchantment, Integer> enchMap = EnchantmentHelper.getEnchantments(s);
				if (ench != null) return bound.test(enchMap.getOrDefault(ench, 0));
				return enchMap.values().stream().anyMatch(bound::test);
			}) };
		}
		return new ItemPredicate[0];
	}

	private static class TrueItemPredicate extends ItemPredicate {

		Predicate<ItemStack> predicate;

		TrueItemPredicate(Predicate<ItemStack> predicate) {
			this.predicate = predicate;
		}

		@Override
		public boolean test(ItemStack item) {
			return predicate.test(item);
		}
	}

}
