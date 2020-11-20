package awsome.realistech.tileentity;

import java.util.Random;

import awsome.realistech.blocks.BellowsBlock;
import awsome.realistech.registry.Registration;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

public class BellowsTileEntity extends TileEntity implements ITickableTileEntity {
	
	private boolean shouldAnimate;
	public int animationTimer;
	private int animationStage;
	
	public BellowsTileEntity() {
		super(Registration.BELLOWS_TILEENTITY.get());
	}
	
	public void setShouldAnimate(boolean animate) {
		this.shouldAnimate = animate;
	}
	
	public boolean shouldAnimate() {
		return this.shouldAnimate;
	}
	
	@Override
	public void read(CompoundNBT compound) {
		this.shouldAnimate = compound.getBoolean("animate");
		super.read(compound);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.putBoolean("animate", shouldAnimate);
		return super.write(compound);
	}

	@Override
	public void tick() {
		if (shouldAnimate) {
			if (this.animationStage == 0) {
				
				this.animationTimer++;
				if (animationTimer == 5) {
					animationStage = 1;
				}
				if (!this.world.isRemote) {
					markDirty();
					pushAirToBloomery();
				}
				
				if(this.world.isRemote) {
					spawnSmoke();
				}
			}else{
				this.animationTimer--;
				if (this.animationTimer == -3) {
					this.shouldAnimate = false;
					this.animationStage = 0;
					world.setBlockState(this.pos, this.getBlockState().with(BellowsBlock.STATIC, true), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
					markDirty();
				}
			}
		}
	}

	private void pushAirToBloomery() {
		BlockState state = world.getBlockState(this.pos);
		
		int x = state.get(BlockStateProperties.HORIZONTAL_FACING).getXOffset();
		int z = state.get(BlockStateProperties.HORIZONTAL_FACING).getZOffset();
		
		if (world.isBlockPresent(this.pos)) {
			TileEntity adjacentTileEntity = world.getTileEntity(new BlockPos(this.pos.getX() + x, this.pos.getY(), this.pos.getZ() + z));
			if (adjacentTileEntity instanceof BloomeryTileEntity) {
				BloomeryTileEntity bloomeryTe = (BloomeryTileEntity) adjacentTileEntity;
				
				bloomeryTe.pullAirFromBellows();
			}
		}
		
	}

	private void spawnSmoke() {
		BlockState state = world.getBlockState(this.pos);
		
		Random random = new Random();
		
		int x = state.get(BlockStateProperties.HORIZONTAL_FACING).getXOffset();
		int z = state.get(BlockStateProperties.HORIZONTAL_FACING).getZOffset();

		float f = (float) this.pos.getX() + x + 0.5F;
		float f1 = this.pos.getY() + 0.1F + random.nextFloat() * 6F / 16F;
		float f2 = (float) this.pos.getZ() + z + 0.5F;

		float f4 = random.nextFloat() * 0.6F;
		float f5 = random.nextFloat() * -0.6F;
		
		world.addParticle(ParticleTypes.SMOKE, f + f4 - 0.3F, f1, f2 + f5 + 0.3F, 0.0, 0.0, 0.0);
	}
	
}
