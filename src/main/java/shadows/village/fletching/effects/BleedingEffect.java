package shadows.village.fletching.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import shadows.Apotheosis;

public class BleedingEffect extends Effect {

	public static final DamageSource BLEEDING = new DamageSource(Apotheosis.MODID + ".bleeding").setDamageBypassesArmor();

	public BleedingEffect() {
		super(EffectType.HARMFUL, 0x8B0000);
	}

	@Override
	public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
		entityLivingBaseIn.attackEntityFrom(BLEEDING, 1.0F + amplifier * 0.5F);
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration % 40 == 0;
	}

}
