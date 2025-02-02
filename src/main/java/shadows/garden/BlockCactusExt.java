package shadows.garden;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class BlockCactusExt extends CactusBlock {

	public BlockCactusExt() {
		super(Block.Properties.create(Material.CACTUS).tickRandomly().hardnessAndResistance(0.4F).sound(SoundType.CLOTH));
		setRegistryName(new ResourceLocation("cactus"));
	}

	@Override
	public void tick(BlockState state, World world, BlockPos pos, Random random) {
		if (!world.isAreaLoaded(pos, 1)) return; // Forge: prevent growing cactus from loading unloaded chunks with block update
		BlockPos blockpos = pos.up();

		if (pos.getY() != 255 && world.isAirBlock(blockpos)) {
			int i = 1;

			if (GardenModule.maxCactusHeight != 255) for (; world.getBlockState(pos.down(i)).getBlock() == this; ++i)
				;

			if (i < GardenModule.maxCactusHeight) {
				int j = state.get(AGE);

				if (ForgeHooks.onCropsGrowPre(world, blockpos, state, true)) {
					if (j == 15) {
						world.setBlockState(blockpos, getDefaultState());
						BlockState iblockstate = state.with(AGE, Integer.valueOf(0));
						world.setBlockState(pos, iblockstate, 4);
						iblockstate.neighborChanged(world, blockpos, this, pos, false);
					} else {
						world.setBlockState(pos, state.with(AGE, Integer.valueOf(j + 1)), 4);
					}
					ForgeHooks.onCropsGrowPost(world, pos, state);
				}
			}
		}
	}

}
