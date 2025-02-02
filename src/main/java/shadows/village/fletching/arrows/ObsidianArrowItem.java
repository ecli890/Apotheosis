package shadows.village.fletching.arrows;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ObsidianArrowItem extends ArrowItem {

	public ObsidianArrowItem() {
		super(new Item.Properties().group(ItemGroup.COMBAT));
	}

	@Override
	public AbstractArrowEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
		AbstractArrowEntity e = new ObsidianArrowEntity(shooter, world);
		e.getEntityData().putBoolean("apoth_obsidian_arrow", true);
		return e;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("info.apotheosis.obsidian_arrow").setStyle(new Style().setColor(TextFormatting.BLUE)));
	}

	@SubscribeEvent
	public void handleArrowJoin(EntityJoinWorldEvent e) {
		if (!e.getWorld().isRemote && e.getEntity() instanceof AbstractArrowEntity) {
			AbstractArrowEntity ent = (AbstractArrowEntity) e.getEntity();
			if (ent.getEntityData().getBoolean("apoth_obsidian_arrow")) {
				ent.setDamage(ent.getDamage() * 1.2F);
				ent.getEntityData().remove("apoth_obsidian_arrow");
			}
		}
	}

}
