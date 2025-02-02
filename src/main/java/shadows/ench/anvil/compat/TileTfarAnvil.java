package shadows.ench.anvil.compat;

import com.tfar.anviltweaks.AnvilTile;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import shadows.ApotheosisObjects;

public class TileTfarAnvil extends AnvilTile implements IAnvilTile {

	public TileTfarAnvil() {
		super(ApotheosisObjects.ANVIL);
	}

	int unbreaking = 0;
	int splitting = 0;

	@Override
	public CompoundNBT write(CompoundNBT tag) {
		tag.putInt("ub", unbreaking);
		tag.putInt("splitting", splitting);
		return super.write(tag);
	}

	@Override
	public void read(CompoundNBT tag) {
		super.read(tag);
		unbreaking = tag.getInt("ub");
		splitting = tag.getInt("splitting");
	}

	@Override
	public void setUnbreaking(int level) {
		unbreaking = level;
	}

	@Override
	public int getUnbreaking() {
		return unbreaking;
	}

	@Override
	public void setSplitting(int level) {
		splitting = level;
	}

	@Override
	public int getSplitting() {
		return splitting;
	}

	@Override
	public TileEntityType<?> getType() {
		return ApotheosisObjects.ANVIL;
	}

}
