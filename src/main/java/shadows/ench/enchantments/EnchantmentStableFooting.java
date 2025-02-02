package shadows.ench.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class EnchantmentStableFooting extends Enchantment {

	public EnchantmentStableFooting() {
		super(Rarity.RARE, EnchantmentType.ARMOR_FEET, new EquipmentSlotType[] { EquipmentSlotType.FEET });
	}

	@Override
	public int getMinEnchantability(int level) {
		return 40;
	}

	@Override
	public int getMaxEnchantability(int level) {
		return 100;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

}
