package shadows.ench.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import shadows.ench.EnchModule;

public class EnchantmentShieldBash extends Enchantment {

	public EnchantmentShieldBash() {
		super(Rarity.RARE, EnchModule.SHIELD, new EquipmentSlotType[] { EquipmentSlotType.MAINHAND });
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 1 + (enchantmentLevel - 1) * 11;
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return getMinEnchantability(enchantmentLevel) + 40;
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return super.canApplyAtEnchantingTable(stack) || stack.getItem().isShield(stack, null);
	}

	@Override
	public void onEntityDamaged(LivingEntity user, Entity target, int level) {
		if (target instanceof LivingEntity) {
			ItemStack stack = user.getHeldItemMainhand();
			if (stack.getItem().isShield(stack, user)) {
				stack.damageItem(35, user, (e) -> {
					e.sendBreakAnimation(EquipmentSlotType.OFFHAND);
				});
				DamageSource src = user instanceof PlayerEntity ? DamageSource.causePlayerDamage((PlayerEntity) user) : DamageSource.GENERIC;
				((LivingEntity) target).attackEntityFrom(src, EnchModule.localAtkStrength * 2.35F * level);
			}
		}
	}

}