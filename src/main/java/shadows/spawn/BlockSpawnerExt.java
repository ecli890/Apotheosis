package shadows.spawn;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import shadows.advancement.AdvancementTriggers;
import shadows.spawn.modifiers.SpawnerModifier;

public class BlockSpawnerExt extends SpawnerBlock {

	public BlockSpawnerExt() {
		super(Block.Properties.create(Material.ROCK).hardnessAndResistance(5.0F).sound(SoundType.METAL));
		setRegistryName("minecraft", "spawner");
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
		ItemStack s = new ItemStack(this);
		TileEntity te = world.getTileEntity(pos);
		if (te != null) te.write(s.getOrCreateChildTag("BlockEntityTag"));
		return s;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		TileEntity te = world.getTileEntity(pos);
		if (te != null) {
			te.read(stack.getOrCreateChildTag("BlockEntityTag"));
			te.setPos(pos);
		}
	}

	@Override
	public void harvestBlock(World world, PlayerEntity player, BlockPos pos, BlockState state, TileEntity te, ItemStack stack) {
		if (SpawnerModule.spawnerSilkLevel != -1 && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) >= SpawnerModule.spawnerSilkLevel) {
			ItemStack s = new ItemStack(this);
			if (te != null) te.write(s.getOrCreateChildTag("BlockEntityTag"));
			spawnAsEntity(world, pos, s);
			player.getHeldItemMainhand().attemptDamageItem(1, world.rand, (ServerPlayerEntity) player);
		}
		world.setBlockState(pos, Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
		super.harvestBlock(world, player, pos, state, te, stack);
	}

	@Override
	public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid) {
		onBlockHarvested(world, pos, state, player);
		if (player.isCreative()) return world.setBlockState(pos, Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
		return willHarvest;
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new TileSpawnerExt();
	}

	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		if (world.isRemote) return true;
		ItemStack stack = player.getHeldItem(hand);
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileSpawnerExt) {
			TileSpawnerExt tile = (TileSpawnerExt) te;
			boolean inverse = SpawnerModifiers.inverseItem.test(player.getHeldItem(hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND));
			for (SpawnerModifier sm : SpawnerModifiers.MODIFIERS) {
				if (sm.canModify(tile, stack, inverse) && sm.modify(tile, stack, inverse)) {
					if (!player.isCreative()) stack.shrink(1);
					AdvancementTriggers.SPAWNER_MODIFIER.trigger((ServerPlayerEntity) player, tile, sm);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if (stack.hasTag() && stack.getTag().contains("BlockEntityTag", Constants.NBT.TAG_COMPOUND)) {
			CompoundNBT tag = stack.getTag().getCompound("BlockEntityTag");
			if (tag.contains("SpawnData")) tooltip.add(new TranslationTextComponent("info.spw.entity", tag.getCompound("SpawnData").getString("id")));
			if (tag.contains("MinSpawnDelay")) tooltip.add(new TranslationTextComponent("waila.spw.mindelay", tag.getShort("MinSpawnDelay")));
			if (tag.contains("MaxSpawnDelay")) tooltip.add(new TranslationTextComponent("waila.spw.maxdelay", tag.getShort("MaxSpawnDelay")));
			if (tag.contains("SpawnCount")) tooltip.add(new TranslationTextComponent("waila.spw.spawncount", tag.getShort("SpawnCount")));
			if (tag.contains("MaxNearbyEntities")) tooltip.add(new TranslationTextComponent("waila.spw.maxnearby", tag.getShort("MaxNearbyEntities")));
			if (tag.contains("RequiredPlayerRange")) tooltip.add(new TranslationTextComponent("waila.spw.playerrange", tag.getShort("RequiredPlayerRange")));
			if (tag.contains("SpawnRange")) tooltip.add(new TranslationTextComponent("waila.spw.spawnrange", tag.getShort("SpawnRange")));
			if (tag.getBoolean("ignore_players")) tooltip.add(new TranslationTextComponent("waila.spw.ignoreplayers"));
			if (tag.getBoolean("ignore_conditions")) tooltip.add(new TranslationTextComponent("waila.spw.ignoreconditions"));
			if (tag.getBoolean("ignore_cap")) tooltip.add(new TranslationTextComponent("waila.spw.ignorecap"));
			if (tag.getBoolean("redstone_control")) tooltip.add(new TranslationTextComponent("waila.spw.redstone"));
		}
	}

	@Override
	public Item asItem() {
		return Items.SPAWNER;
	}

	@Override
	public int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silktouch) {
		return silktouch == 0 ? super.getExpDrop(state, world, pos, fortune, silktouch) : 0;
	}

}
