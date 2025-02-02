package shadows.village;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.potion.Effect;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import shadows.Apotheosis;
import shadows.Apotheosis.ApotheosisSetup;
import shadows.ApotheosisObjects;
import shadows.placebo.util.PlaceboUtil;
import shadows.placebo.util.ReflectionHelper;
import shadows.village.fletching.BlockFletchingTable;
import shadows.village.fletching.FletchingContainer;
import shadows.village.fletching.FletchingRecipe;
import shadows.village.fletching.arrows.BroadheadArrowEntity;
import shadows.village.fletching.arrows.BroadheadArrowItem;
import shadows.village.fletching.arrows.ObsidianArrowEntity;
import shadows.village.fletching.arrows.ObsidianArrowItem;
import shadows.village.fletching.effects.BleedingEffect;

public class VillagerModule {

	public static final IRecipeType<FletchingRecipe> FLETCHING = IRecipeType.register(Apotheosis.MODID + ":fletching");
	public static final IRecipeSerializer<FletchingRecipe> FLETCHING_SERIALIZER = new FletchingRecipe.Serializer();

	@SubscribeEvent
	public void setup(ApotheosisSetup e) {
		MinecraftForge.EVENT_BUS.addListener(WandererReplacements::replaceWandererArrays);
		MinecraftForge.EVENT_BUS.addGenericListener(World.class, this::starting);
		MinecraftForge.EVENT_BUS.addListener(ApotheosisObjects.OBSIDIAN_ARROW::handleArrowJoin);
	}

	@SubscribeEvent
	public void serializers(Register<IRecipeSerializer<?>> e) {
		e.getRegistry().register(FLETCHING_SERIALIZER.setRegistryName(FletchingRecipe.Serializer.NAME));
	}

	@SubscribeEvent
	public void blocks(Register<Block> e) {
		PlaceboUtil.registerOverrideBlock(new BlockFletchingTable(), Apotheosis.MODID);
	}

	@SubscribeEvent
	public void items(Register<Item> e) {
		e.getRegistry().registerAll(new ObsidianArrowItem().setRegistryName("obsidian_arrow"), new BroadheadArrowItem().setRegistryName("broadhead_arrow"));
	}

	@SubscribeEvent
	public void entities(Register<EntityType<?>> e) {
		//Formatter::off
		e.getRegistry().register(EntityType.Builder
				.<ObsidianArrowEntity>create(ObsidianArrowEntity::new, EntityClassification.MISC)
				.setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(4)
				.setUpdateInterval(20)
				.size(0.5F, 0.5F)
				.setCustomClientFactory((se, w) -> new ObsidianArrowEntity(w))
				.build("ob_arrow")
				.setRegistryName("ob_arrow_entity"));
		e.getRegistry().register(EntityType.Builder
				.<BroadheadArrowEntity>create(BroadheadArrowEntity::new, EntityClassification.MISC)
				.setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(4)
				.setUpdateInterval(20)
				.size(0.5F, 0.5F)
				.setCustomClientFactory((se, w) -> new BroadheadArrowEntity(w))
				.build("bh_arrow")
				.setRegistryName("bh_arrow_entity"));
		//Formatter::on
	}

	@SubscribeEvent
	public void containers(Register<ContainerType<?>> e) {
		e.getRegistry().register(new ContainerType<>(FletchingContainer::new).setRegistryName("fletching"));
	}

	@SubscribeEvent
	public void effects(Register<Effect> e) {
		e.getRegistry().register(new BleedingEffect().setRegistryName("bleeding"));
	}

	public void starting(AttachCapabilitiesEvent<World> e) {
		if (e.getObject() instanceof ServerWorld) {
			ServerWorld w = (ServerWorld) e.getObject();
			if (w.dimension.getType() == DimensionType.OVERWORLD) ReflectionHelper.setPrivateValue(ServerWorld.class, w, new WandererSpawnerExt(w), "field_217496_L", "wanderingTraderSpawner");
		}
	}
}
